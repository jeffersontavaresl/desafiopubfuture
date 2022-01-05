package br.com.pubfuture.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.ReceitaRepository;

@RestController
@RequestMapping("receita")
public class ReceitaController {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ReceitaRepository receitaRepository;
	
	@GetMapping
	public Page<ReceitaDTO> lista(@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 5) Pageable paginacao){
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
	
	@PostMapping
	public ResponseEntity<ReceitaDTO> cadastrarReceita(@RequestBody @Valid ReceitaForm form, UriComponentsBuilder uriBuilder){
		Receita receita = form.converter(contaRepository);
		receitaRepository.save(receita);
		
		URI uri = uriBuilder.path("/receita/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDTO(receita));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ReceitaDTO> atualizarReceita(@PathVariable Long id, @RequestBody @Valid ReceitaDTO form){
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
