package br.com.pubfuture.dto;

import java.time.LocalDate;

import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.model.Despesa;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DespesaDTO {

	private Long id;
	private Double valor;
	private LocalDate dataPagamento;
	private LocalDate dataPagamentoEsperado;
	private String descricao;
	private Long contaId;
	private TipoDespesa tipoDespesa;
		
	public DespesaDTO(Despesa despesa) {
		this.id = despesa.getId();
		this.valor = despesa.getValor();
		this.dataPagamento = despesa.getDataPagamento();
		this.dataPagamentoEsperado = despesa.getDataPagamentoEsperado();
		this.descricao = despesa.getDescricao();
		this.contaId = despesa.getConta().getId();
		this.tipoDespesa = despesa.getTipoDespesa();
	}
	
}
