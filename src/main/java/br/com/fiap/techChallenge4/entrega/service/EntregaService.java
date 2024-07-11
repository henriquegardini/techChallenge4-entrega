package br.com.fiap.techChallenge4.entrega.service;

import br.com.fiap.techChallenge4.entrega.Feign.PedidoClient;
import br.com.fiap.techChallenge4.entrega.dto.EntregaDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaExibicaoDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaRequestDto;
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

import java.math.BigDecimal;
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

    public EntregaExibicaoDto criarEntrega(EntregaRequestDto entregaRequestDto) {

        validaExistenciaDeEntregaParaPedido(entregaRequestDto.getIdPedido());
        var pedidoResponse = buscaPedido(entregaRequestDto.getIdPedido());
        var entregadorEntrega = buscaEntregador(entregaRequestDto);

        Entrega entrega = new Entrega();
        entrega.setStatus(StatusEntrega.EM_ANDAMENTO);
        entrega.setEtapa(EtapaEntrega.EM_SEPARACAO);
        entrega.setDataEstimada(LocalDate.now().plusDays(7));
        entrega.setIdPedido(pedidoResponse.getId());
        entrega.setIdEntregador(entregadorEntrega.get().getId());
        BeanUtils.copyProperties(entregaRequestDto, entrega);
        Entrega entregaCriada = entregaRepository.save(entrega);

        atualizaStatusPedido(entregadorEntrega.get().getId(), pedidoResponse);

        return new EntregaExibicaoDto(entregaCriada);
    }

    private PedidoDTO buscaPedido(Long idPedido) {
        try {
            return pedidoClient.getPedidoById(idPedido);
        } catch (Exception e) {
            throw new RuntimeException("O pedido não foi encontrado.");
        }
    }

    private void atualizaStatusPedido(Long idPedido, PedidoDTO pedidoClientAtual) {
        try {
            pedidoClientAtual.setStatusPedido("AGUARDANDO_ENTREGA");
            pedidoClient.updatePedido(idPedido, pedidoClientAtual);
        } catch (Exception e) {
            throw new RuntimeException("O pedido não foi encontrado.");
        }
    }

    private void validaExistenciaDeEntregaParaPedido(Long idPedido) {
        Optional<Entrega> entregaPedido = entregaRepository.findAll()
                .stream()
                .filter(entrega -> entrega.getIdPedido().equals(idPedido))
                .findFirst();
        if (entregaPedido.isPresent()) {
            throw new RuntimeException("O pedido já tem uma entrega em andamento.");
        }
    }

    private Optional<Entregador> buscaEntregador(EntregaRequestDto entregaRequestDto) {
        Optional<Entregador> entregadorEntrega = entregadorRepository.findAll()
                .stream()
                .filter(entregador -> entregador.getQuantidadeEntregas() < 10 &&
                        entregador.getCepInicial() <= entregaRequestDto.getCepEntrega() &&
                        entregador.getCepFinal() >= entregaRequestDto.getCepEntrega())
                .findFirst();
        if (entregadorEntrega.isPresent()) {
            Entregador entregadorAtual = new Entregador();
            entregadorAtual.setId(entregadorEntrega.get().getId());
            entregadorAtual.setNome(entregadorEntrega.get().getNome());
            entregadorAtual.setCepInicial(entregadorEntrega.get().getCepInicial());
            entregadorAtual.setCepFinal(entregadorEntrega.get().getCepFinal());
            entregadorAtual.setQuantidadeEntregas(entregadorEntrega.get().getQuantidadeEntregas() + 1);
            entregadorRepository.save(entregadorAtual);
            return entregadorEntrega;
        } else {
            throw new RuntimeException("Não foi encontrado nenhum entregador disponível.");
        }
    }

    public EntregaExibicaoDto buscarEntrega(Long idEntrega) {
        Optional<Entrega> entregaOptional = entregaRepository.findById(idEntrega);

        if (entregaOptional.isPresent()) {
            return new EntregaExibicaoDto(entregaOptional.get());
        } else {
            throw new EntregaNaoEncotradaException("Entrega não encontrada!");
        }
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
            throw new RuntimeException("Entrega não encontrada!");
        }
    }

    public EntregaExibicaoDto atualizarEntrega(EntregaDto entregaDto) {
        Entrega entrega = new Entrega();
        BeanUtils.copyProperties(entregaDto, entrega);
        Entrega entregaAtualizada = entregaRepository.save(entrega);

        return new EntregaExibicaoDto(entregaAtualizada);
    }

}













