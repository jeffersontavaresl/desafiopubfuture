package br.com.pubfuture.form;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Despesa;
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.DespesaRepository;
import lombok.Data;

@Data
public class DespesaForm {

	@NotNull
	private Double valor;
	
	@NotNull 
	private LocalDate dataPagamento;
	
	@NotNull 
	private LocalDate dataPagamentoEsperado;
	
	@NotNull 
	private String descricao;
	
	@NotNull
	private TipoDespesa tipoDespesa;
	
	@NotNull
	private Long contaId;
	
	public Despesa converter(ContaRepository contaRepository) {
		Conta conta = contaRepository.findById(contaId).get();
		return new Despesa(valor, dataPagamento, dataPagamentoEsperado,
				descricao, tipoDespesa, conta);
	}
	
	public Despesa atualizar(Long id, DespesaRepository despesaRepository) {
		Despesa despesa = despesaRepository.findById(id).get();
		despesa.setValor(this.valor);
		despesa.setDataPagamento(this.dataPagamento);
		despesa.setDataPagamentoEsperado(this.dataPagamentoEsperado);
		despesa.setTipoDespesa(this.tipoDespesa);
		despesa.setDescricao(this.descricao);
		return despesa;
	}
}
