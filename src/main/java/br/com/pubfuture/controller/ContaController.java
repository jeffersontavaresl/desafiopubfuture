package br.com.pubfuture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

@RestController
@RequestMapping("conta")
public class ContaController {

	@Autowired
	private ContaService service;

	@GetMapping
	public Page<ContaDTO> listaDeContas(Pageable paginacao) {
		return service.listaDeContas(paginacao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DetalhesContaDTO> detalhesDaConta(@PathVariable Long id) {
		return service.detalhesDaConta(id);
	}

	@GetMapping("{contaOrigem}/{contaDestino}/{valorTransferencia}")
	public ResponseEntity<ContaDTO> transferenciaEntreContas(@PathVariable Long contaOrigem,
			@PathVariable Long contaDestino, @PathVariable Double valor) {
		return service.transferenciaEntreContas(contaOrigem, contaDestino, valor);
	}

	@GetMapping("/saldoTotal")
	public ResponseEntity<Double> saldoTotal() {
		return service.saldoTotal();
	}
	
	@GetMapping("{id}/saldoTotal")
	public ResponseEntity<Double> saldoTotalConta(@PathVariable Long id){
		return service.saldoTotalPorConta(id);
	}

	@PostMapping
	public ResponseEntity<ContaDTO> cadastrarConta(@RequestBody ContaForm form, UriComponentsBuilder uriBuilder) {
		return service.cadastrarConta(form, uriBuilder);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ContaDTO> editarConta(@PathVariable Long id, @RequestBody EditarContaForm form) {
		return service.editarConta(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerConta(@PathVariable Long id) {
		return service.removerConta(id);
	}
}
