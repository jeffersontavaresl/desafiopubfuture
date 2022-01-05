package br.com.pubfuture.dto;

import org.springframework.data.domain.Page;

import br.com.pubfuture.model.Conta;
import lombok.Data;

@Data
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
	
	public static Page<ContaDTO> converter(Page<Conta> conta){
		return conta.map(ContaDTO::new);
	}
}
