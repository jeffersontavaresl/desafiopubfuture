package br.com.pubfuture.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Despesa;
import br.com.pubfuture.model.Receita;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DetalhesContaDTO {
	
	private Long id;
	private Double saldo;
	private TipoConta tipoConta;
	private String instituicaoFinanceira;
	private List<ReceitaDTO> receitas = new ArrayList<>();
	private List<DespesaDTO> despesas = new ArrayList<>();
	private Double saldoTotal;
	
	public DetalhesContaDTO(Conta conta) {
		this.id = conta.getId();
		this.saldo = conta.getSaldo();
		this.tipoConta = conta.getTipoConta();
		this.instituicaoFinanceira = conta.getInstituicaoFinanceira();
		this.receitas.addAll(conta.getReceita().stream().map(ReceitaDTO::new).collect(Collectors.toList()));
		this.despesas.addAll(conta.getDespesa().stream().map(DespesaDTO::new).collect(Collectors.toList()));
	}
	
	
	
	public static Page<ContaDTO> converter(Page<Conta> conta){
		return conta.map(ContaDTO::new);
	}
}
