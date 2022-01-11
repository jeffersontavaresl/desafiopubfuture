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
	
	@Autowired
    private ModelMapper modelMapper;

	/** Método para listar todas as receitas cadastradas
	 * 
	 * @return lista com todas as despesas
	 */
	public List<ReceitaDTO> lista() {
		List<Receita> receita = receitaRepository.findAll();
		return receita
				.stream()
				.map(r -> modelMapper.map(r, ReceitaDTO.class))
				.collect(Collectors.toList());
	}

	/** Método para detalhar informações de derterminada receita
	 * 
	 * @param id
	 * @return detalhes da receita informada
	 */
	public ResponseEntity<ReceitaDTO> detalharReceita(Long id) {
		Optional<Receita> receita = receitaRepository.findById(id);
		if (receita.isPresent()) {
			return ResponseEntity.ok(new ReceitaDTO(receita.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/** Método para filtrar as receitas pela conta e por um período de datas
	 * 
	 * @param contaId
	 * @param dataInicial
	 * @param dataFinal
	 * @return lista com todas as receitas pelo período que estejam na conta informada
	 */
	public List<Receita> filtroPorContaData(Long contaId, String dataInicial, String dataFinal) {
		LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		List<Receita> receita = receitaRepository.findByContaIdAndDataRecebimentoBetween(contaId, dataIni, dataFim);
		return receita;
	}
	
	/** Método para filtrar as receitas pelo período de datas
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @return lista com todas as receitas pelo período das datas informadas
	 */
	public List<Receita> filtroPorData(String dataInicial, String dataFinal){
		LocalDate dataIni = LocalDate.parse(dataInicial, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		LocalDate dataFim = LocalDate.parse(dataFinal, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		List<Receita> receita = receitaRepository.findByDataRecebimentoBetween(dataIni, dataFim);
		return receita;
	}

	/** Método para filtrar as receitas pelo tipo da receita
	 * 
	 * @param tipoReceita
	 * @return lista com todas as receitas de acordo com o tipo
	 */
	public List<Receita> filtroPorTipoReceita(String tipoReceita) {
		tipoReceita = tipoReceita.toUpperCase();
		TipoReceita tipo = TipoReceita.valueOf(tipoReceita);
		List<Receita> receita = receitaRepository.findByTipoReceita(tipo);
		return receita;
	}

	/** Método para filtrar as receitas por determinada conta e pelo tipo da receita
	 * 
	 * @param contaId
	 * @param tipoReceita
	 * @return lista com todas as receitas de acordo com o tipo e conta informada
	 */
	public List<Receita> filtroPorContaTipoReceita(Long contaId, String tipoReceita) {
		tipoReceita = tipoReceita.toUpperCase();
		TipoReceita tipo = TipoReceita.valueOf(tipoReceita);
		List<Receita> receita = receitaRepository.findByContaIdAndTipoReceita(contaId, tipo);
		return receita;
	}

	/** Método para visualizar o valor total das receitas
	 * 
	 * @return valor total das receitas
	 */
	public Optional<Double> valorTotalReceitas() {
		Optional<Double> valorTotal = receitaRepository.findValorTotalReceita();
		return valorTotal;
	}
	
	/** Método para visualizar o valor total das receitas por determinada conta
	 * 
	 * @param contaId
	 * @return valor total das receitas por conta
	 */
	public Optional<Double> valorTotalReceitaPorConta(Long contaId){
		Optional<Double> valorTotal = receitaRepository.findValorTotalReceitaConta(contaId);
		return valorTotal;
	}

	/** Método para cadastrar nova receita 
	 * 
	 * @param form
	 * @param uriBuilder
	 * @return
	 */
	public ResponseEntity<ReceitaDTO> cadastrarReceita(@Valid ReceitaForm form, UriComponentsBuilder uriBuilder) {
		Receita receita = form.converter(contaRepository);
		receitaRepository.save(receita);

		URI uri = uriBuilder.path("/receitas/{id}").buildAndExpand(receita.getId()).toUri();
		return ResponseEntity.created(uri).body(new ReceitaDTO(receita));
	}

	/** Método para atualizar os dados de uma receita já existente
	 * 
	 * @param id
	 * @param form
	 * @return
	 */
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

	/** Método para remover uma receita
	 * 
	 * @param id
	 * @return
	 */
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
