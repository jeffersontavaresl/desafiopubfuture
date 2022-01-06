package br.com.pubfuture.form;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Receita;
import br.com.pubfuture.repository.ContaRepository;
import br.com.pubfuture.repository.ReceitaRepository;
import lombok.Data;

@Data
public class ReceitaForm {
	
	@NotNull
	private Double valor;
	
	@NotNull
	private LocalDate dataRecebimento;
	
	@NotNull
	private LocalDate dataRecebimentoEsperado;
	
	@NotNull @NotEmpty
	private String descricao;
	
	@NotNull
	private TipoReceita tipoReceita;
	
	@NotNull
	private Long contaId;
	
	public Receita converter(ContaRepository contaRepository) {
		Conta conta = contaRepository.findById(contaId).get();
		return new Receita(valor, dataRecebimento, dataRecebimentoEsperado, 
				descricao, tipoReceita, conta);
	}
	
	public Receita atualizar(Long id, ReceitaRepository receitaRepository) {
		Receita receita = receitaRepository.findById(id).get();
		receita.setValor(this.valor);
		receita.setDataRecebimento(this.dataRecebimento);
		receita.setDataRecebimentoEsperado(this.dataRecebimentoEsperado);
		receita.setTipoReceita(this.tipoReceita);
		receita.setDescricao(this.descricao);
		return receita;
	}
}
