package br.com.pubfuture.controller;

import java.util.List;

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
import br.com.pubfuture.form.EditarContaForm;
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

	@GetMapping("{contaOrigem}/{contaDestino}/{valorTransferencia}")
	@ApiOperation("Realizar a transferência do saldo de uma conta para outra")
	public ResponseEntity<ContaDTO> transferenciaEntreContas(@PathVariable Long contaOrigem,
			@PathVariable Long contaDestino, @PathVariable Double valor) {
		return service.transferenciaEntreContas(contaOrigem, contaDestino, valor);
	}

	@GetMapping("/saldoTotal")
	@ApiOperation("Mostrar o saldo líquido de todas as contas")
	public ResponseEntity<Double> saldoTotal() {
		return service.saldoTotal();
	}
	
	@GetMapping("{id}/saldoTotal")
	@ApiOperation("Mostrar o saldo líquido de uma conta pelo ID")
	public ResponseEntity<Double> saldoTotalConta(@PathVariable Long id){
		return service.saldoTotalPorConta(id);
	}

	@PostMapping
	@ApiOperation("Cadastrar uma conta")
	public ResponseEntity<ContaDTO> cadastrarConta(@RequestBody ContaForm form, UriComponentsBuilder uriBuilder) {
		return service.cadastrarConta(form, uriBuilder);
	}

	@PutMapping("/{id}")
	@ApiOperation("Alterar dados de uma conta")
	public ResponseEntity<ContaDTO> editarConta(@PathVariable Long id, @RequestBody EditarContaForm form) {
		return service.editarConta(id, form);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Deletar uma conta")
	public ResponseEntity<?> removerConta(@PathVariable Long id) {
		return service.removerConta(id);
	}
}
