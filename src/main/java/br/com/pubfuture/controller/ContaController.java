package br.com.pubfuture.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.pubfuture.dto.ContaDTO;
import br.com.pubfuture.dto.DetalhesContaDTO;
import br.com.pubfuture.form.ContaForm;
import br.com.pubfuture.service.ContaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("conta")
@Api(tags = "Conta")
public class ContaController {

	@Autowired
	private ContaService service;

	@GetMapping
	@ApiOperation("Lista todas as contas")
	public List<ContaDTO> listaDeContas() {
		return service.listaDeContas();
	}

	@GetMapping("/{id}")
	@ApiOperation("Listar detalhes de uma conta pelo ID")
	public ResponseEntity<DetalhesContaDTO> detalhesDaConta(@PathVariable Long id) {
		return service.detalhesDaConta(id);
	}

	@GetMapping("/transferencia/{contaOrigem}/{contaDestino}/{valorTransferencia}")
	@ApiOperation("Realizar a transferência do saldo de uma conta para outra")
	public ResponseEntity<ContaDTO> transferenciaEntreContas(@PathVariable Long contaOrigem,
			@PathVariable Long contaDestino, @PathVariable Double valorTransferencia) {
		return service.transferenciaEntreContas(contaOrigem, contaDestino, valorTransferencia);
	}

	@GetMapping("/saldoTotal")
	@ApiOperation("Mostrar o saldo líquido de todas as contas")
	public ResponseEntity<Double> saldoTotal() {
		return service.saldoTotal();
	}
	
	@GetMapping("/saldoTotal/{id}")
	@ApiOperation("Mostrar o saldo líquido de uma conta pelo ID")
	public ResponseEntity<Double> saldoTotalConta(@PathVariable Long id){
		return service.saldoTotalPorConta(id);
	}

	@GetMapping("/deposito/{id}/{valorDepositado}")
	@ApiOperation("Realizar deposito na conta")
	public ResponseEntity<ContaDTO> depositar(@PathVariable Long id, @PathVariable Double valorDepositado){
		return service.depositar(id, valorDepositado);
	}
	
	@GetMapping("/saque/{id}/{valorSacado}")
	@ApiOperation("Realizar saque na conta")
	public ResponseEntity<ContaDTO> sacar(@PathVariable Long id, @PathVariable Double valorSacado){
		return service.sacar(id, valorSacado);
	}
	
	@PostMapping
	@ApiOperation("Cadastrar uma conta")
	public ResponseEntity<ContaDTO> cadastrarConta(@RequestBody @Valid ContaForm form, UriComponentsBuilder uriBuilder) {
		return service.cadastrarConta(form, uriBuilder);
	}

	@PutMapping("/{id}")
	@ApiOperation("Alterar dados de uma conta")
	public ResponseEntity<ContaDTO> editarConta(@PathVariable Long id, @RequestBody @Valid ContaForm form) {
		return service.editarConta(id, form);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Deletar uma conta")
	public ResponseEntity<?> removerConta(@PathVariable Long id) {
		return service.removerConta(id);
	}
}
