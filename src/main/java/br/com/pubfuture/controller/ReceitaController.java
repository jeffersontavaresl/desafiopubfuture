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

import br.com.pubfuture.dto.ReceitaDTO;
import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.form.ReceitaForm;
import br.com.pubfuture.model.Receita;
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.ReceitaRepository;

@RestController
@RequestMapping("receitas")
public class ReceitaController {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@GetMapping
	public Page<ReceitaDTO> lista(Pageable paginacao){
			Page<Receita> receita = receitaRepository.findAll(paginacao);
			return ReceitaDTO.converter(receita);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ReceitaDTO> detalharReceita(@PathVariable Long id){
		Optional<Receita> receita = receitaRepository.findById(id);
		if(receita.isPresent()) {
			return ResponseEntity.ok(new ReceitaDTO(receita.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("{contaId}/{dataInicial}/{dataFinal}")
	public List<Receita> filtroPorData(@PathVariable Long contaId, @PathVariable String dataInicial, 
			@PathVariable String dataFinal){
		 LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 
		List<Receita> receita = receitaRepository.findByContaIdAndDataRecebimentoBetween(contaId, dataIni, dataFim);
		return receita;
	}
	
	@GetMapping("{contaId}/{tipoReceita}")
	public List<Receita> filtroPorTipo(@PathVariable Long contaId, @PathVariable String tipoReceita){
		tipoReceita = tipoReceita.toUpperCase();
		TipoReceita tipo = TipoReceita.valueOf(tipoReceita);
		List<Receita> receita = receitaRepository.findByContaIdAndTipoReceita(contaId, tipo);
		return receita;
	}
	
	@PostMapping
	public ResponseEntity<ReceitaDTO> cadastrarReceita(@RequestBody @Valid ReceitaForm form, 
			UriComponentsBuilder uriBuilder){
		Receita receita = form.converter(contaRepository);
		receitaRepository.save(receita);
		
		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDTO(receita));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable Long id, 
			@RequestBody @Valid ReceitaForm form){
		Optional<Receita> optional = receitaRepository.findById(id);
		
		if(optional.isPresent()) {
			Receita receita = form.atualizar(id, receitaRepository);
			return ResponseEntity.ok(new ReceitaDTO(receita));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerReceita(@PathVariable Long id){
		Optional<Receita> optional = receitaRepository.findById(id);
		
		if(optional.isPresent()) {
			receitaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
 	}
	
}
