package br.com.pubfuture.controller;

import java.util.List;
import java.util.Optional;

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

import br.com.pubfuture.dto.ReceitaDTO;
import br.com.pubfuture.form.ReceitaForm;
import br.com.pubfuture.model.Receita;
import br.com.pubfuture.service.ReceitaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("receitas")
@Api(tags = "Receitas")
public class ReceitaController {

	@Autowired
	private ReceitaService service;

	@GetMapping
	@ApiOperation("Listar todas as receitas")
	public List<ReceitaDTO> lista() {
		return service.lista();
	}

	@GetMapping("/{id}")
	@ApiOperation("Detalhes de uma receita pelo ID")
	public ResponseEntity<ReceitaDTO> detalharReceita(@PathVariable Long id) {
		return service.detalharReceita(id);
	}

	@GetMapping("{contaId}/{dataInicial}/{dataFinal}")
	@ApiOperation("Filtrar receitas pelo ID da conta, data inicial e a data final")
	public List<Receita> filtroPorContaData(@PathVariable Long contaId, @PathVariable String dataInicial,
			@PathVariable String dataFinal) {
		return service.filtroPorContaData(contaId, dataInicial, dataFinal);
	}
	
	@GetMapping("/data/{dataInicial}/{dataFinal}")
	@ApiOperation("Filtrar receitas pelas datas")
	public List<Receita> filtroPorData(@PathVariable String dataInicial, @PathVariable String dataFinal){
		return service.filtroPorData(dataInicial, dataFinal);
	}

	@GetMapping("/tipo/{tipoReceita}")
	@ApiOperation("Filtrar despesas pelo tipo da receita")
	public List<Receita> filtroPorTipoReceita(@PathVariable String tipoReceita) {
		return service.filtroPorTipoReceita(tipoReceita);
	}

	@GetMapping("{contaId}/{tipoReceita}")
	@ApiOperation("Filtrar receitas pelo ID da conta e pelo tipo da receita")
	public List<Receita> filtroPorContaTipoReceita(@PathVariable Long contaId, @PathVariable String tipoReceita) {
		return service.filtroPorContaTipoReceita(contaId, tipoReceita);
	}

	@GetMapping("/valorTotalReceitas")
	@ApiOperation("Mostrar o valor total de todas as receitas")
	public Optional<Double> valorTotalReceitas() {
		return service.valorTotalReceitas();
	}
	
	@GetMapping("{contaId}/valorTotalReceitas")
	@ApiOperation("Mostrar o valor total de todas as receitas pelo ID da conta")
	public Optional<Double> valorTotalReceitaConta(@PathVariable Long contaId){
		return service.valorTotalReceitaPorConta(contaId);
	}

	@PostMapping
	@ApiOperation("Cadastrar nova receita")
	public ResponseEntity<ReceitaDTO> cadastrarReceita(@RequestBody @Valid ReceitaForm form,
			UriComponentsBuilder uriBuilder) {
		return service.cadastrarReceita(form, uriBuilder);
	}

	@PutMapping("/{id}")
	@ApiOperation("Atualizar dados de uma receita")
	public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable Long id, @RequestBody @Valid ReceitaForm form) {
		return service.atualizarReceita(id, form);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Remover uma receita pelo ID")
	public ResponseEntity<?> removerReceita(@PathVariable Long id) {
		return service.removerReceita(id);
	}
}
