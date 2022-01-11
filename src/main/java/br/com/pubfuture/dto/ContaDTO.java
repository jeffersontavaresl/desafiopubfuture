package br.com.pubfuture.dto;

import br.com.pubfuture.model.Conta;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContaDTO {

	private Long id;
	private String tipoConta;
	private String instituicaoFinanceira;
	private Double saldo;
		
	public ContaDTO(Conta conta) {
		this.id = conta.getId();
		this.tipoConta = conta.getTipoConta().name();
		this.saldo = conta.getSaldo();
		this.instituicaoFinanceira = conta.getInstituicaoFinanceira();
	}
	
}
