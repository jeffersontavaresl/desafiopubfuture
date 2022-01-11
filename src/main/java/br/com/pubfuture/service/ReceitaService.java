package br.com.pubfuture.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.pubfuture.dto.ReceitaDTO;
import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.form.ReceitaForm;
import br.com.pubfuture.model.Receita;
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.ReceitaRepository;

@Service
public class ReceitaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ReceitaRepository receitaRepository;

	public Page<ReceitaDTO> lista(Pageable paginacao) {
		Page<Receita> receita = receitaRepository.findAll(paginacao);
		return ReceitaDTO.converter(receita);
	}

	public ResponseEntity<ReceitaDTO> detalharReceita(Long id) {
		Optional<Receita> receita = receitaRepository.findById(id);
		if (receita.isPresent()) {
			return ResponseEntity.ok(new ReceitaDTO(receita.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public List<Receita> filtroPorContaData(Long contaId, String dataInicial, String dataFinal) {
		LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		List<Receita> receita = receitaRepository.findByContaIdAndDataRecebimentoBetween(contaId, dataIni, dataFim);
		return receita;
	}
	
	public List<Receita> filtroPorData(String dataInicial, String dataFinal){
		LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		List<Receita> receita = receitaRepository.findByDataRecebimentoBetween(dataIni, dataFim);
		return receita;
	}

	public List<Receita> filtroPorTipoReceita(String tipoReceita) {
		tipoReceita = tipoReceita.toUpperCase();
		TipoReceita tipo = TipoReceita.valueOf(tipoReceita);
		List<Receita> receita = receitaRepository.findByTipoReceita(tipo);
		return receita;
	}

	public List<Receita> filtroPorContaTipoReceita(Long contaId, String tipoReceita) {
		tipoReceita = tipoReceita.toUpperCase();
		TipoReceita tipo = TipoReceita.valueOf(tipoReceita);
		List<Receita> receita = receitaRepository.findByContaIdAndTipoReceita(contaId, tipo);
		return receita;
	}

	public Optional<Double> valorTotalReceitas() {
		Optional<Double> valorTotal = receitaRepository.findValorTotalReceita();
		return valorTotal;
	}
	
	public Optional<Double> valorTotalReceitaPorConta(Long contaId){
		Optional<Double> valorTotal = receitaRepository.findValorTotalReceitaConta(contaId);
		return valorTotal;
	}

	public ResponseEntity<ReceitaDTO> cadastrarReceita(@Valid ReceitaForm form, UriComponentsBuilder uriBuilder) {
		Receita receita = form.converter(contaRepository);
		receitaRepository.save(receita);

		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDTO(receita));
	}

	public ResponseEntity<ReceitaDTO> atualizarReceita(Long id, @Valid ReceitaForm form) {
		Optional<Receita> optional = receitaRepository.findById(id);

		if (optional.isPresent()) {
			Receita receita = form.atualizar(id, receitaRepository);
			receitaRepository.save(receita);
			return ResponseEntity.ok(new ReceitaDTO(receita));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<?> removerReceita(Long id) {
		Optional<Receita> optional = receitaRepository.findById(id);

		if (optional.isPresent()) {
			receitaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
