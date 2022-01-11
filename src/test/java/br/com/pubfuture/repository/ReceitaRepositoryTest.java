package br.com.pubfuture.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Receita;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ReceitaRepositoryTest {

	@Autowired
	ReceitaRepository receitaRepository;
	
	private Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
	
	@SuppressWarnings("unused")
	private Receita receita = new Receita(200.00, LocalDate.now(), 
			LocalDate.now(), "Férias da empresa",
			TipoReceita.OUTROS, conta);
	
	@Test
	void deveriaFiltrarPelaContaEaDataRecebimento() {
		receitaRepository.findByContaIdAndDataRecebimentoBetween(1L, LocalDate.now(),  LocalDate.now());
	}
	
	@Test
	void deveriaFiltrarPelaContaETipoReceita() {
		receitaRepository.findByContaIdAndTipoReceita(1L, TipoReceita.OUTROS);
	}
	
	@Test
	void deveriaFiltrarPeloTipoReceita() {
		receitaRepository.findByTipoReceita(TipoReceita.PREMIO);
	}
	
	@Test
	void deveriaSomarOValorTotalDeReceitas() {
		receitaRepository.findValorTotalReceita();
	}
	
	@Test
	void deveriaSomarOValorTotalDeReceitaPelaConta() {
		receitaRepository.findValorTotalReceitaConta(1L);
	}
}
