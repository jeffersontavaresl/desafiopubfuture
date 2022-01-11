package br.com.pubfuture.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Despesa;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class DespesaRepositoryTest {

	@Autowired
	DespesaRepository despesaRepository;
	
	private Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
	
	@SuppressWarnings("unused")
	private Despesa despesa = new Despesa(300.00, LocalDate.now(), 
			LocalDate.now(), "Mercado",
			TipoDespesa.ALIMENTACAO, conta);
	
	@Test
	void deveriaFiltrarPelaContaEaDataRecebimento() {
		despesaRepository.findByContaIdAndDataPagamentoBetween(1L, LocalDate.now(),  LocalDate.now());
	}
	
	@Test
	void deveriaFiltrarPelaContaETipoReceita() {
		despesaRepository.findByContaIdAndTipoDespesa(1L, TipoDespesa.ALIMENTACAO);
	}
	
	@Test
	void deveriaFiltrarPeloTipoReceita() {
		despesaRepository.findByTipoDespesa(TipoDespesa.EDUCACAO);
	}
	
	@Test
	void deveriaSomarOValorTotalDeReceitas() {
		despesaRepository.findValorTotalDespesa();
	}
	
	@Test
	void deveriaSomarOValorTotalDeReceitaPelaConta() {
		despesaRepository.findValorTotalDespesaConta(1L);
	}
}
