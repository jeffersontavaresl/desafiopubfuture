package br.com.pubfuture.form;

import java.time.LocalDate;

import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Receita;
import br.com.pubfuture.repository.ContaRepository;
import lombok.Data;

@Data
public class ReceitaForm {

	private Double valor;
	private LocalDate dataRecebimento;
	private LocalDate dataRecebimentoEsperado;
	private String descricao;
	private TipoReceita tipoReceita;
	private Long contaId;
	
	public Receita converter(ContaRepository contaRepository) {
		Conta conta = contaRepository.findById(contaId).get();
		return new Receita(valor, dataRecebimento, dataRecebimentoEsperado, 
				descricao, tipoReceita, conta);
	}
}
