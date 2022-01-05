package br.com.pubfuture.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import br.com.pubfuture.enums.TipoDespesa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Despesa {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate dataPagamento;
	private LocalDate dataPagamentoEsperado;
	private Double valor;
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private TipoDespesa tipoDespesa;
	
	@ManyToOne
	private Conta conta;
	
}
