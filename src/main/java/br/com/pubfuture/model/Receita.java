package br.com.pubfuture.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.pubfuture.enums.TipoReceita;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Receita {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Double valor;
	private LocalDate dataRecebimento;
	private LocalDate dataRecebimentoEsperado;
	private String descricao;
	
	@ManyToOne
	@JsonBackReference
	private Conta conta;
	
	@Enumerated(EnumType.STRING)
	private TipoReceita tipoReceita;
	
	public Receita(Double valor, LocalDate dataRecebimento, 
			LocalDate dataRecebimentoEsperado, String descricao,
			TipoReceita tipoReceita, Conta conta) {
		this.valor = valor;
		this.dataRecebimento = dataRecebimento;
		this.dataRecebimentoEsperado = dataRecebimentoEsperado;
		this.descricao = descricao;
		this.tipoReceita = tipoReceita;
		this.conta = conta;
	}
}
