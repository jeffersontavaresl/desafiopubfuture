package br.com.pubfuture.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.model.Conta;
import lombok.Data;

@Data
public class ContaForm {

	@NotNull @NotEmpty
	private String instituicaoFinanceira;
	
	@NotNull
	private Double saldo;
	
	private TipoConta tipoConta;
	
	public Conta converter() {
		return new Conta(instituicaoFinanceira, saldo, tipoConta);
	}
}
