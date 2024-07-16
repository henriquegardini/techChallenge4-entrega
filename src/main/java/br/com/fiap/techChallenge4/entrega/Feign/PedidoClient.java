package br.com.fiap.techChallenge4.entrega.Feign;

import br.com.fiap.techChallenge4.entrega.dto.PedidoDTO;
import br.com.fiap.techChallenge4.entrega.dto.PedidoRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "Pedido", url = "http://localhost:8082")
public interface PedidoClient {

    @GetMapping(value = "/pedido/{id}")
    PedidoDTO getPedidoById(@PathVariable("id") Long id);

    @PutMapping(value = "/pedido-status/{id}")
    PedidoDTO updatePedido(@PathVariable("id") Long id, @RequestBody PedidoRequestDTO pedido);

}
