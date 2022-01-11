package br.com.pubfuture.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.pubfuture.dto.ContaDTO;
import br.com.pubfuture.dto.DetalhesContaDTO;
import br.com.pubfuture.form.ContaForm;
import br.com.pubfuture.form.EditarContaForm;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.DespesaRepository;
import br.com.pubfuture.repository.ReceitaRepository;

@Service
public class ContaService {

	@Autowired
	private ContaRepository repository;

	@Autowired
	private ReceitaRepository receitaRepository;

	@Autowired
	private DespesaRepository despesaRepository;
	
	@Autowired
    private ModelMapper modelMapper;
	
	/** Método para listar todas as contas cadastradas
	 * 
	 * @return lista de todas as contas
	 */
	public List<ContaDTO> listaDeContas() {
		List<Conta> contas = repository.findAll();
		return contas
				.stream()
                .map(c -> modelMapper.map(c, ContaDTO.class))
                .collect(Collectors.toList());
	}
	
	/** Método para buscar uma conta pelo id e retornar todas as informações sobre a conta
	 * 
	 * @param id
	 * @return todas as informações sobre a conta
	 */
	public ResponseEntity<DetalhesContaDTO> detalhesDaConta(Long id) {
		Optional<Conta> optional = repository.findById(id);

		if (optional.isPresent()) {
			return ResponseEntity.ok(new DetalhesContaDTO(optional.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/** Método para realizar a transferência de saldo entre duas contas diferentes
	 * 
	 * @param contaOrigem 
	 * @param contaDestino
	 * @param valorTransferencia
	 * @return os detalhes da conta que recebeu a transferência
	 */
	public ResponseEntity<ContaDTO> transferenciaEntreContas(Long contaOrigem, Long contaDestino, Double valorTransferencia) {
		Conta contaO = repository.findById(contaOrigem).get();
		Conta contaD = repository.findById(contaDestino).get();
		if (contaO.getSaldo() > 0) {
			contaD.setSaldo(contaD.getSaldo() + valorTransferencia);
			contaO.setSaldo(contaO.getSaldo() - valorTransferencia);
			repository.save(contaO);
			repository.save(contaD);
			return ResponseEntity.ok(new ContaDTO(contaD));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/** Método para visualizar o saldo total líquido em todas as contas
	 * 
	 * @return o valor do saldo total líquido
	 */
	public ResponseEntity<Double> saldoTotal() {
		Optional<Double> receita = receitaRepository.findValorTotalReceita();
		Optional<Double> despesa = despesaRepository.findValorTotalDespesa();
		Double saldoTotal = saldoDaConta();

		if(receita.isPresent() && despesa.isPresent()) {
			saldoTotal = (saldoDaConta() + receita.get() - despesa.get());
			return ResponseEntity.ok(saldoTotal);
		} 
		if(receita.isPresent() && despesa.isEmpty()) {
			saldoTotal = (receita.get() + saldoDaConta());
			return ResponseEntity.ok(saldoTotal);
		}
		if(receita.isEmpty() && despesa.isPresent()) {
			saldoTotal = (saldoDaConta() - despesa.get());
			return ResponseEntity.ok(saldoTotal);
		}
		else {
			return ResponseEntity.ok(saldoDaConta());
		}
	}
	
	/** Método para visualizar o saldo total líquido em uma conta específica
	 * 
	 * @param contaId
	 * @return o valor do saldo total líquido da conta informada
	 */
	public ResponseEntity<Double> saldoTotalPorConta(Long contaId) {
		Optional<Double> receita = receitaRepository.findValorTotalReceitaConta(contaId);
		Optional<Double> despesa = despesaRepository.findValorTotalDespesaConta(contaId);
		Double saldoTotal = saldoDaContaId(contaId);

		if(receita.isPresent() && despesa.isPresent()) {
			saldoTotal = (saldoDaContaId(contaId) + receita.get() - despesa.get());
			return ResponseEntity.ok(saldoTotal);
		} 
		if(receita.isPresent() && despesa.isEmpty()) {
			saldoTotal = (receita.get() + saldoDaContaId(contaId));
			return ResponseEntity.ok(saldoTotal);
		}
		if(receita.isEmpty() && despesa.isPresent()) {
			saldoTotal = (saldoDaContaId(contaId) - despesa.get());
			return ResponseEntity.ok(saldoTotal);
		}
		else {
			return ResponseEntity.ok(saldoDaContaId(contaId));
		}
	}
	
	/** Método que soma no banco o valor do saldo total de todas as contas
	 * 
	 * @return soma do saldo de todas as contas
	 */
	public Double saldoDaConta() {
		return repository.findValorTotalConta();
	}
	
	/** Método que soma no banco o valor do saldo total de uma determinada conta
	 * 
	 * @param contaId
	 * @return soma do saldo total da conta informada
	 */
	public Double saldoDaContaId(Long contaId) {
		return repository.findValorTotalContaId(contaId);
	}

	/** Método para cadastrar uma nova conta
	 * 
	 * @param form
	 * @param uriBuilder
	 * @return o corpo com as informações cadastradas
	 */
	public ResponseEntity<ContaDTO> cadastrarConta(@Valid ContaForm form, UriComponentsBuilder uriBuilder) {
		Conta conta = form.converter();
		repository.save(conta);

		URI uri = uriBuilder.path("/categoria/{id}").buildAndExpand(conta.getId()).toUri();
		return ResponseEntity.created(uri).body((new ContaDTO(conta)));
	}

	/** Método para atualizar as informações de uma conta já cadastrada
	 * 
	 * @param id
	 * @param form
	 * @return
	 */
	public ResponseEntity<ContaDTO> editarConta(Long id, @Valid EditarContaForm form) {
		Optional<Conta> optional = repository.findById(id);

		if (optional.isPresent()) {
			Conta conta = form.editarConta(id, repository);
			repository.save(conta);
			return ResponseEntity.ok(new ContaDTO(conta));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/** Método para remover uma conta
	 * 
	 * @param id
	 * @return
	 */
	public ResponseEntity<?> removerConta(Long id) {
		Optional<Conta> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
