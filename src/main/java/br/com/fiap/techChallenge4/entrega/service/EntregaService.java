package br.com.fiap.techChallenge4.entrega.service;

import br.com.fiap.techChallenge4.entrega.Feign.PedidoClient;
import br.com.fiap.techChallenge4.entrega.dto.AtualizaEntregaRequestDto;
import br.com.fiap.techChallenge4.entrega.dto.CriarEntregaRequestDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaExibicaoDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaResponseDTO;
import br.com.fiap.techChallenge4.entrega.dto.PedidoDTO;
import br.com.fiap.techChallenge4.entrega.exception.EntregaNaoEncotradaException;
import br.com.fiap.techChallenge4.entrega.model.Entrega;
import br.com.fiap.techChallenge4.entrega.model.Entregador;
import br.com.fiap.techChallenge4.entrega.model.EtapaEntrega;
import br.com.fiap.techChallenge4.entrega.model.StatusEntrega;
import br.com.fiap.techChallenge4.entrega.repository.EntregaRepository;
import br.com.fiap.techChallenge4.entrega.repository.EntregadorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final EntregadorRepository entregadorRepository;
    private final PedidoClient pedidoClient;

    @Autowired
    private EntregadorService entregadorService;

    public EntregaService(EntregaRepository entregaRepository, EntregadorRepository entregadorRepository, PedidoClient pedidoClient) {
        this.entregaRepository = entregaRepository;
        this.entregadorRepository = entregadorRepository;
        this.pedidoClient = pedidoClient;
    }

    public EntregaResponseDTO criarEntrega(CriarEntregaRequestDto criarEntregaRequestDto) {

        if (validaExistenciaDeEntregaParaPedido(criarEntregaRequestDto.getIdPedido())) {

            var entregador = buscaEntregador(criarEntregaRequestDto.getCepEntrega());
            if (entregador.isPresent()) {
                var pedido = buscaPedido(criarEntregaRequestDto.getIdPedido());
                Entrega entrega = new Entrega();
                entrega.setIdPedido(pedido.getId());
                entrega.setIdEntregador(entregador.get().getId());
                entrega.setStatus(StatusEntrega.EM_ANDAMENTO);
                entrega.setEtapa(EtapaEntrega.EM_SEPARACAO);
                entrega.setDataEstimada(LocalDate.now().plusDays(7));
                BeanUtils.copyProperties(criarEntregaRequestDto, entrega);
                Entrega entregaCriada = entregaRepository.save(entrega);

                reservaEntregador(entregador);
                atualizaStatusPedido(pedido.getId(), pedido);
                return getEntregaResponseDTO(entregaCriada, pedido);
            } else {
                throw new RuntimeException("Não foi encontrado nenhum entregador disponível.");
            }
        } else {
            throw new RuntimeException("O pedido já tem uma entrega em andamento.");
        }

    }

    private PedidoDTO buscaPedido(Long idPedido) {
        try {
            return pedidoClient.getPedidoById(idPedido);
        } catch (Exception e) {
            throw new RuntimeException("SERVIÇO DE PEDIDOS: Ocorreu um problema na busca do pedido. Exceção: ", e);
        }
    }

    private void atualizaStatusPedido(Long idPedido, PedidoDTO pedido) {
        try {
            pedido.setStatus("AGUARDANDO_ENTREGA");
            pedidoClient.updatePedido(idPedido, pedido);
        } catch (Exception e) {
            throw new RuntimeException("SERVIÇO DE PEDIDOS: Ocorreu um problema na atualização do status do pedido. Exceção: ", e);
        }
    }

    private boolean validaExistenciaDeEntregaParaPedido(Long idPedido) {
        Optional<Entrega> entregaPedido = entregaRepository.findAll()
                .stream()
                .filter(entrega -> entrega.getIdPedido().equals(idPedido))
                .findFirst();
        if (entregaPedido.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    private Optional<Entregador> buscaEntregador(Integer cepEntrega) {
        return entregadorRepository.findAll()
                .stream()
                .filter(entregador -> entregador.getQuantidadeEntregas() < 10 &&
                        entregador.getCepInicial() <= cepEntrega &&
                        entregador.getCepFinal() >= cepEntrega)
                .findFirst();
    }

    private void reservaEntregador(Optional<Entregador> entregador) {
        entregador.get().setQuantidadeEntregas(entregador.get().getQuantidadeEntregas() + 1);
        entregadorRepository.save(entregador.get());
    }

    public EntregaResponseDTO buscarEntrega(Long idEntrega) {
        Optional<Entrega> entrega = entregaRepository.findById(idEntrega);
        if (entrega.isPresent()) {
            var pedido = buscaPedido(entrega.get().getIdPedido());
            return getEntregaResponseDTO(entrega.get(), pedido);
        } else {
            throw new EntregaNaoEncotradaException("Entrega não encontrada!");
        }
    }

    private static EntregaResponseDTO getEntregaResponseDTO(Entrega entrega, PedidoDTO pedido) {
        EntregaResponseDTO entregaResponseDTO = new EntregaResponseDTO();
        entregaResponseDTO.setId(entrega.getId());
        entregaResponseDTO.setIdEntregador(entrega.getIdEntregador());
        entregaResponseDTO.setStatus(entrega.getStatus());
        entregaResponseDTO.setEtapa(entrega.getEtapa());
        entregaResponseDTO.setDataEstimada(entrega.getDataEstimada());
        entregaResponseDTO.setDataRealizada(entrega.getDataRealizada());
        entregaResponseDTO.setNomeReceptor(entrega.getNomeReceptor());
        entregaResponseDTO.setPedido(pedido);
        return entregaResponseDTO;
    }

    public List<EntregaExibicaoDto> listarEntregas() {
        return entregaRepository
                .findAll()
                .stream()
                .map(EntregaExibicaoDto::new)
                .toList();
    }

    public void excluirEntrega(Long numeroEntrega) {
        Optional<Entrega> entregaOptional = entregaRepository.findById(numeroEntrega);

        if (entregaOptional.isPresent()) {
            entregaRepository.delete(entregaOptional.get());
        } else {
            throw new RuntimeException("Entrega não encontrada.");
        }
    }

    public void atualizarEntrega(AtualizaEntregaRequestDto atualizaEntregaRequestDTO) {
        var entrega = entregaRepository.findById(atualizaEntregaRequestDTO.getId());
        if (entrega.isPresent()) {
            entrega.get().setStatus(atualizaEntregaRequestDTO.getStatus());
            entrega.get().setEtapa(atualizaEntregaRequestDTO.getEtapa());
            entrega.get().setDataEstimada(atualizaEntregaRequestDTO.getDataEstimada());
            entrega.get().setDataRealizada(atualizaEntregaRequestDTO.getDataRealizada());
            entrega.get().setNomeReceptor(atualizaEntregaRequestDTO.getNomeReceptor());
            Entrega entregaAtualizada = entregaRepository.save(entrega.get());
        } else {
            throw new RuntimeException("Entrega não encontrada.");
        }
    }

}