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

import br.com.pubfuture.dto.ReceitaDTO;
import br.com.pubfuture.form.ReceitaForm;
import br.com.pubfuture.model.Receita;
import br.com.pubfuture.service.ReceitaService;

@RestController
@RequestMapping("receitas")
public class ReceitaController {

	@Autowired
	private ReceitaService service;

	@GetMapping
	public Page<ReceitaDTO> lista(Pageable paginacao) {
		return service.lista(paginacao);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDTO> detalharReceita(@PathVariable Long id) {
		return service.detalharReceita(id);
	}

	@GetMapping("{contaId}/{dataInicial}/{dataFinal}")
	public List<Receita> filtroPorData(@PathVariable Long contaId, @PathVariable String dataInicial,
			@PathVariable String dataFinal) {
		return service.filtroPorData(contaId, dataInicial, dataFinal);
	}

	@GetMapping("/tipo/{tipoReceita}")
	public List<Receita> filtroPorTipoReceita(@PathVariable String tipoReceita) {
		return service.filtroPorTipoReceita(tipoReceita);
	}

	@GetMapping("{contaId}/{tipoReceita}")
	public List<Receita> filtroPorContaTipoReceita(@PathVariable Long contaId, @PathVariable String tipoReceita) {
		return service.filtroPorContaTipoReceita(contaId, tipoReceita);
	}

	@GetMapping("/valorTotalReceitas")
	public Optional<Double> valorTotalReceitas() {
		return service.valorTotalReceitas();
	}
	
	@GetMapping("{contaId}/valorTotalReceitas")
	public Optional<Double> valorTotalReceitaConta(@PathVariable Long contaId){
		return service.valorTotalReceitaPorConta(contaId);
	}

	@PostMapping
	public ResponseEntity<ReceitaDTO> cadastrarReceita(@RequestBody @Valid ReceitaForm form,
			UriComponentsBuilder uriBuilder) {
		return service.cadastrarReceita(form, uriBuilder);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable Long id, @RequestBody @Valid ReceitaForm form) {
		return service.atualizarReceita(id, form);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerReceita(@PathVariable Long id) {
		return service.removerReceita(id);
	}
}
