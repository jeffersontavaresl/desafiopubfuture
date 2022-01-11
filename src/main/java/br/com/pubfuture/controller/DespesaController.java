package br.com.pubfuture.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import br.com.pubfuture.dto.DespesaDTO;
import br.com.pubfuture.form.DespesaForm;
import br.com.pubfuture.model.Despesa;
import br.com.pubfuture.service.DespesaService;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private DespesaService service;

	@GetMapping
	public Page<DespesaDTO> lista(Pageable paginacao) {
		return service.lista(paginacao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DespesaDTO> detalharDespesa(@PathVariable Long id) {
		return service.detalharDespesa(id);
	}

	@GetMapping("{contaId}/{dataInicial}/{dataFinal}")
	public List<Despesa> filtroPorData(@PathVariable Long contaId, @PathVariable String dataInicial,
			@PathVariable String dataFinal) {
		return service.filtroPorData(contaId, dataInicial, dataFinal);
	}

	@GetMapping("{contaId}/{tipoDespesa}")
	public List<Despesa> filtroPorContaTipoDespesa(@PathVariable Long contaId, @PathVariable String tipoDespesa) {
		return service.filtroPorContaTipoDespesa(contaId, tipoDespesa);
	}

	@GetMapping("/tipo/{tipoDespesa}")
	public List<Despesa> filtroPorTipoDespesa(@PathVariable String tipoDespesa) {
		return service.filtroPorTipoDespesa(tipoDespesa);
	}

	@GetMapping("/valorTotalDespesas")
	public Optional<Double> valorTotalDespesas() {
		return service.valorTotalDespesas();
	}
	
	@GetMapping("{contaId}/valorTotalDespesas")
	public Optional<Double> valorTotalDespesaConta(@PathVariable Long contaId){
		return service.valorTotalReceitaPorConta(contaId);
	}

	@PostMapping
	public ResponseEntity<DespesaDTO> cadastrarDespesa(@RequestBody @Valid DespesaForm form,
			UriComponentsBuilder uriBuilder) {
		return service.cadastrarDespesa(form, uriBuilder);
	}

	@PutMapping("/{id}")
	public ResponseEntity<DespesaDTO> atualizarDespesa(@PathVariable Long id, @RequestBody @Valid DespesaForm form) {
		return service.atualizarDespesa(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerDespesa(@PathVariable Long id) {
		return service.removerDespesa(id);
	}
}
