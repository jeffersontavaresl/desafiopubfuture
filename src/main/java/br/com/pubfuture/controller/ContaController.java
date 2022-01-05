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

import br.com.pubfuture.dto.ContaDTO;
import br.com.pubfuture.dto.DetalhesContaDTO;
import br.com.pubfuture.form.ContaForm;
import br.com.pubfuture.form.EditarContaForm;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.repository.ContaRepository;


@RestController
@RequestMapping("conta")
public class ContaController {

	@Autowired
	private ContaRepository repository;
	
	@GetMapping
	public Page<ContaDTO> listaDeContas(@PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable paginacao) {
		Page<Conta> contas = repository.findAll(paginacao);
		return ContaDTO.converter(contas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesContaDTO> detalhesDaConta(@PathVariable Long id) {
		Optional<Conta> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok(new DetalhesContaDTO(optional.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<ContaDTO> cadastrarConta(@RequestBody @Valid ContaForm form, UriComponentsBuilder uriBuilder){
		Conta conta = form.converter();
		repository.save(conta);
		
		URI uri = uriBuilder.path("/categoria/{id}").buildAndExpand(conta.getId()).toUri();
		return ResponseEntity.created(uri).body((new ContaDTO(conta)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ContaDTO> editarConta(@PathVariable Long id, @RequestBody @Valid EditarContaForm form){
		Optional<Conta> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			Conta conta = form.editarConta(id, repository);
			return ResponseEntity.ok(new ContaDTO(conta));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> removerConta(@PathVariable Long id){
		Optional<Conta> optional = repository.findById(id);
		
		if(optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
