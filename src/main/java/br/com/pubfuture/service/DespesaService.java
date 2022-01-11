package br.com.pubfuture.service;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.pubfuture.dto.DespesaDTO;
import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.form.DespesaForm;
import br.com.pubfuture.model.Despesa;
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.DespesaRepository;

@Service
public class DespesaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
    private ModelMapper modelMapper;

	public List<DespesaDTO> lista() {
		List<Despesa> despesa = despesaRepository.findAll();
		return despesa
				.stream()
				.map(d -> modelMapper.map(d, DespesaDTO.class))
				.collect(Collectors.toList());
	}

	public ResponseEntity<DespesaDTO> detalharDespesa(Long id) {
		Optional<Despesa> despesa = despesaRepository.findById(id);
		if (despesa.isPresent()) {
			return ResponseEntity.ok(new DespesaDTO(despesa.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public List<Despesa> filtroPorContaData(Long contaId, String dataInicial, String dataFinal) {
		LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		List<Despesa> despesa = despesaRepository.findByContaIdAndDataPagamentoBetween(contaId, dataIni, dataFim);
		return despesa;
	}
	
	public List<Despesa> filtroPorData(String dataInicial, String dataFinal){
		LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		List<Despesa> despesa = despesaRepository.findByDataPagamentoBetween(dataIni, dataFim);
		return despesa;
	}

	public List<Despesa> filtroPorContaTipoDespesa(Long contaId, String tipoDespesa) {
		tipoDespesa = tipoDespesa.toUpperCase();
		TipoDespesa tipo = TipoDespesa.valueOf(tipoDespesa);
		List<Despesa> despesa = despesaRepository.findByContaIdAndTipoDespesa(contaId, tipo);
		return despesa;
	}

	public List<Despesa> filtroPorTipoDespesa(String tipoDespesa) {
		tipoDespesa = tipoDespesa.toUpperCase();
		TipoDespesa tipo = TipoDespesa.valueOf(tipoDespesa);
		List<Despesa> despesa = despesaRepository.findByTipoDespesa(tipo);
		return despesa;
	}

	public Optional<Double> valorTotalDespesas() {
		Optional<Double> detalhes = despesaRepository.findValorTotalDespesa();
		return detalhes;
	}
	
	public Optional<Double> valorTotalReceitaPorConta(Long contaId){
		Optional<Double> valorTotal = despesaRepository.findValorTotalDespesaConta(contaId);
		return valorTotal;
	}

	public ResponseEntity<DespesaDTO> cadastrarDespesa(@Valid DespesaForm form, UriComponentsBuilder uriBuilder) {
		Despesa despesa = form.converter(contaRepository);
		despesaRepository.save(despesa);

		URI uri = uriBuilder.path("/despesas/{id}").buildAndExpand(despesa.getId()).toUri();
		return ResponseEntity.created(uri).body(new DespesaDTO(despesa));
	}

	public ResponseEntity<DespesaDTO> atualizarDespesa(Long id, @Valid DespesaForm form) {
		Optional<Despesa> optional = despesaRepository.findById(id);

		if (optional.isPresent()) {
			Despesa despesa = form.atualizar(id, despesaRepository);
			despesaRepository.save(despesa);
			return ResponseEntity.ok(new DespesaDTO(despesa));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<?> removerDespesa(Long id) {
		Optional<Despesa> optional = despesaRepository.findById(id);

		if (optional.isPresent()) {
			despesaRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
