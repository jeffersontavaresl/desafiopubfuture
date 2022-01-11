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

import br.com.pubfuture.dto.DespesaDTO;
import br.com.pubfuture.form.DespesaForm;
import br.com.pubfuture.model.Despesa;
import br.com.pubfuture.service.DespesaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/despesas")
@Api(tags = "Despesas")
public class DespesaController {

	@Autowired
	private DespesaService service;

	@GetMapping
	@ApiOperation("Listar todas as despesas")
	public List<DespesaDTO> lista() {
		return service.lista();
	}

	@GetMapping("/{id}")
	@ApiOperation("Detalhes de uma despesa pelo ID")
	public ResponseEntity<DespesaDTO> detalharDespesa(@PathVariable Long id) {
		return service.detalharDespesa(id);
	}

	@GetMapping("{contaId}/{dataInicial}/{dataFinal}")
	@ApiOperation("Filtrar despesas pelo ID da conta, data inicial e a data final")
	public List<Despesa> filtroPorData(@PathVariable Long contaId, @PathVariable String dataInicial,
			@PathVariable String dataFinal) {
		return service.filtroPorContaData(contaId, dataInicial, dataFinal);
	}
	
	@GetMapping("/data/{dataInicial}/{dataFinal}")
	@ApiOperation("Filtrar despesas pelas datas")
	public List<Despesa> filtroPorData(@PathVariable String dataInicial, @PathVariable String dataFinal){
		return service.filtroPorData(dataInicial, dataFinal);
	}


	@GetMapping("{contaId}/{tipoDespesa}")
	@ApiOperation("Filtrar despesas pelo ID da conta e pelo tipo da despesa")
	public List<Despesa> filtroPorContaTipoDespesa(@PathVariable Long contaId, @PathVariable String tipoDespesa) {
		return service.filtroPorContaTipoDespesa(contaId, tipoDespesa);
	}

	@GetMapping("/tipo/{tipoDespesa}")
	@ApiOperation("Filtrar despesas pelo tipo da despesa")
	public List<Despesa> filtroPorTipoDespesa(@PathVariable String tipoDespesa) {
		return service.filtroPorTipoDespesa(tipoDespesa);
	}

	@GetMapping("/valorTotalDespesas")
	@ApiOperation("Mostrar o valor total de todas as despesas")
	public Optional<Double> valorTotalDespesas() {
		return service.valorTotalDespesas();
	}
	
	@GetMapping("{contaId}/valorTotalDespesas")
	@ApiOperation("Mostrar o valor total de todas as despesas pelo ID da conta")
	public Optional<Double> valorTotalDespesaConta(@PathVariable Long contaId){
		return service.valorTotalReceitaPorConta(contaId);
	}

	@PostMapping
	@ApiOperation("Cadastrar nova despesa")
	public ResponseEntity<DespesaDTO> cadastrarDespesa(@RequestBody @Valid DespesaForm form,
			UriComponentsBuilder uriBuilder) {
		return service.cadastrarDespesa(form, uriBuilder);
	}

	@PutMapping("/{id}")
	@ApiOperation("Atualizar dados de uma despesa")
	public ResponseEntity<DespesaDTO> atualizarDespesa(@PathVariable Long id, @RequestBody @Valid DespesaForm form) {
		return service.atualizarDespesa(id, form);
	}

	@DeleteMapping("/{id}")
	@ApiOperation("Remover uma despesa pelo ID")
	public ResponseEntity<?> removerDespesa(@PathVariable Long id) {
		return service.removerDespesa(id);
	}
}
