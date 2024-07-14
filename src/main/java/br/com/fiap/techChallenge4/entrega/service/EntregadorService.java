package br.com.fiap.techChallenge4.entrega.service;

import br.com.fiap.techChallenge4.entrega.dto.EntregadorDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregadorExibicaoDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregadorRequestDto;
import br.com.fiap.techChallenge4.entrega.exception.EntregadorNotFoundException;
import br.com.fiap.techChallenge4.entrega.model.Entregador;
import br.com.fiap.techChallenge4.entrega.repository.EntregadorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntregadorService {

    @Autowired
    private EntregadorRepository entregadorRepository;

    public EntregadorExibicaoDto criarEntregador(EntregadorRequestDto entregadorRequestDto) {
        Entregador entregador = new Entregador();
        entregador.setId(entregadorRequestDto.getId());
        entregador.setNome(entregadorRequestDto.getNome());
        entregador.setQuantidadeEntregas(entregadorRequestDto.getQuantidadeEntregas());
        entregador.setCepInicial(entregadorRequestDto.getCepInicial());
        entregador.setCepFinal(entregadorRequestDto.getCepFinal());
        BeanUtils.copyProperties(entregadorRequestDto, entregador);
        Entregador entregadorCriado = entregadorRepository.save(entregador);

        return new EntregadorExibicaoDto(entregadorCriado);
    }

    public EntregadorExibicaoDto buscarEntregador(Long idEntregador) {
        Optional<Entregador> entregadorOptional = entregadorRepository.findById(idEntregador);

        if (entregadorOptional.isPresent()) {
            return new EntregadorExibicaoDto(entregadorOptional.get());
        } else {
            throw new EntregadorNotFoundException();
        }
    }

    public List<EntregadorExibicaoDto> listarEntregadores() {
        return entregadorRepository
                .findAll()
                .stream()
                .map(EntregadorExibicaoDto::new)
                .toList();
    }

    public void excluirEntregador(Long id) {
        Optional<Entregador> entregadorOptional = entregadorRepository.findById(id);

        if (entregadorOptional.isPresent()) {
            entregadorRepository.delete(entregadorOptional.get());
        } else {
            throw new EntregadorNotFoundException();
        }
    }

    public EntregadorExibicaoDto atualizarEntregador(EntregadorDto entregadorDto) {
        Entregador entregador = new Entregador();
        BeanUtils.copyProperties(entregadorDto, entregador);
        Entregador entregadorAtualizado = entregadorRepository.save(entregador);

        return new EntregadorExibicaoDto(entregadorAtualizado);
    }

}













