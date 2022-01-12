package br.com.pubfuture.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.repository.ContaRepository;
import lombok.Data;

@Data
public class ContaForm {

	@NotNull @NotEmpty
	private String instituicaoFinanceira;
	
	@NotNull
	private Double saldo;
	
	@NotNull
	private TipoConta tipoConta;
	
	public Conta converter() {
		return new Conta(instituicaoFinanceira, saldo, tipoConta);
	}
	
	public Conta atualizar(Long id, ContaRepository repository) {
		Conta conta = repository.findById(id).get();
		conta.setInstituicaoFinanceira(this.instituicaoFinanceira);
		conta.setTipoConta(this.tipoConta);
		conta.setSaldo(this.saldo);
		return conta;
	}
}
