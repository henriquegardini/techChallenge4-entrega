package br.com.fiap.techChallenge4.entrega.service;

import br.com.fiap.techChallenge4.entrega.dto.EntregaDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaExibicaoDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaRequestDto;
import br.com.fiap.techChallenge4.entrega.exception.EntregaNaoEncotradaException;
import br.com.fiap.techChallenge4.entrega.model.Entrega;
import br.com.fiap.techChallenge4.entrega.model.EtapaEntrega;
import br.com.fiap.techChallenge4.entrega.model.StatusEntrega;
import br.com.fiap.techChallenge4.entrega.repository.EntregaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EntregaService {

    @Autowired
    private EntregaRepository entregaRepository;

    public EntregaExibicaoDto criarEntrega(EntregaRequestDto entregaRequestDto) {
        Entrega entrega = new Entrega();
        // TODO:
        // 1-Fazer um GET pelo idPedido, para validar se o pedido existe
        entrega.setIdPedido(entregaRequestDto.getIdPedido());
        // 2-Criar o CRUD de entregadores
        // 3-Implementar a logica de atribuição do entregador baseado no CEP da entrega
        entrega.setStatusEntrega(StatusEntrega.EM_ANDAMENTO);
        entrega.setEtapaEntrega(EtapaEntrega.EM_SEPARACAO);
        entrega.setDataEntrega(LocalDate.now().plusDays(7));
        BeanUtils.copyProperties(entregaRequestDto, entrega);
        Entrega entregaCriada = entregaRepository.save(entrega);

        return new EntregaExibicaoDto(entregaCriada);
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













