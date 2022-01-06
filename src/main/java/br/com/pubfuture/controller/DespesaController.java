package br.com.pubfuture.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.form.DespesaForm;
import br.com.pubfuture.model.Despesa;
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.DespesaRepository;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private DespesaRepository despesaRepository;
	
	@GetMapping
	public Page<DespesaDTO> lista(Pageable paginacao){
			Page<Despesa> despesa = despesaRepository.findAll(paginacao);
			return DespesaDTO.converter(despesa);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DespesaDTO> detalharDespesa(@PathVariable Long id){
		Optional<Despesa> despesa = despesaRepository.findById(id);
		if(despesa.isPresent()) {
			return ResponseEntity.ok(new DespesaDTO(despesa.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("{contaId}/{dataInicial}/{dataFinal}")
	public List<Despesa> filtroPorData(@PathVariable Long contaId, 
			@PathVariable String dataInicial, @PathVariable String dataFinal){
		 LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 
		List<Despesa> despesa = despesaRepository.findByContaIdAndDataPagamentoBetween(contaId, dataIni, dataFim);
		return despesa;
	}
	
	@GetMapping("{contaId}/{tipoDespesa}")
	public List<Despesa> filtroPorTipo(@PathVariable Long contaId, @PathVariable String tipoDespesa){
		tipoDespesa = tipoDespesa.toUpperCase();
		TipoDespesa tipo = TipoDespesa.valueOf(tipoDespesa);
		List<Despesa> despesa = despesaRepository.findByContaIdAndTipoDespesa(contaId, tipo);
		return despesa;
	}
	
	@PostMapping
	public ResponseEntity<DespesaDTO> cadastrarDespesa(@RequestBody @Valid DespesaForm form, 
			UriComponentsBuilder uriBuilder){
		Despesa despesa = form.converter(contaRepository);
		despesaRepository.save(despesa);
		
		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(new DespesaDTO(despesa));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<DespesaDTO> atualizarDespesa(@PathVariable Long id, 
			@RequestBody @Valid DespesaForm form){
		Optional<Despesa> optional = despesaRepository.findById(id);
		
		if(optional.isPresent()) {
			Despesa despesa = form.atualizar(id, despesaRepository);
			return ResponseEntity.ok(new DespesaDTO(despesa));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerDespesa(@PathVariable Long id){
		Optional<Despesa> optional = despesaRepository.findById(id);
		
		if(optional.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
 	}
}
