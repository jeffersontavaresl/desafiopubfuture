package br.com.pubfuture.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.pubfuture.enums.TipoConta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Conta {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double saldo;	
	
	@Enumerated
	private TipoConta tipoConta;
	private String instituicaoFinanceira;
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private List<Receita> receita;
	
	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private List<Despesa> despesa;
	
	public Conta(String instituicaoFinanceira, Double saldo, TipoConta tipoConta) {
		this.instituicaoFinanceira = instituicaoFinanceira;
		this.saldo = saldo;
		this.tipoConta = tipoConta;
	}
	
	
}
