package br.com.pubfuture.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Despesa;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class DespesaServiceTest {

	@Autowired
	private DespesaService service;
	
	@Autowired
	private TestEntityManager em;
	
	private Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
	
	@Test
	void deveriaCadastrarNovaDespesa() {
		Despesa despesa = new Despesa(600.00, LocalDate.now(), 
				LocalDate.now(), "Compras no mercado",
				TipoDespesa.ALIMENTACAO, conta);
		em.persist(despesa);
	}
	
	@Test
	void deveriaListarTodasAsDespesas(Pageable page) {	
		Despesa despesa = new Despesa(600.00, LocalDate.now(), 
				LocalDate.now(), "Compras no mercado",
				TipoDespesa.ALIMENTACAO, conta);
		em.persist(despesa);
		
		Despesa despesa1 = new Despesa(500.00, LocalDate.now(), 
				LocalDate.now(), "Faculdade",
				TipoDespesa.EDUCACAO, conta);
		em.persist(despesa1);
		
		Despesa despesa2 = new Despesa(1000.00, LocalDate.now(), 
				LocalDate.now(), "Aluguel",
				TipoDespesa.MORADIA, conta);
		em.persist(despesa2);
		
		Despesa despesa3 = new Despesa(100.00, LocalDate.now(), 
				LocalDate.now(), "Uber",
				TipoDespesa.TRANSPORTE, conta);
		em.persist(despesa3);
		
		service.lista(page);
	}
	
	@Test
	void deveriaFiltrarADespesaPelaContaDataInicialEDataFinal() {
		Despesa despesa = new Despesa(600.00, LocalDate.now(), 
				LocalDate.now(), "Compras no mercado",
				TipoDespesa.ALIMENTACAO, conta);
		em.persist(despesa);
		
		Despesa despesa1 = new Despesa(500.00, LocalDate.now(), 
				LocalDate.now(), "Faculdade",
				TipoDespesa.EDUCACAO, conta);
		em.persist(despesa1);
		
		Despesa despesa2 = new Despesa(1000.00, LocalDate.now(), 
				LocalDate.now(), "Aluguel",
				TipoDespesa.MORADIA, conta);
		em.persist(despesa2);
		
		Despesa despesa3 = new Despesa(100.00, LocalDate.now(), 
				LocalDate.now(), "Uber",
				TipoDespesa.TRANSPORTE, conta);
		em.persist(despesa3);
		
		service.filtroPorData(1L, "2020-10-10", "2015-10-10");
	}
	
	@Test
	void deveriaMostrarOValorTotalDasDespesas() {
		Despesa despesa = new Despesa(600.00, LocalDate.now(), 
				LocalDate.now(), "Compras no mercado",
				TipoDespesa.ALIMENTACAO, conta);
		em.persist(despesa);
		
		Despesa despesa1 = new Despesa(500.00, LocalDate.now(), 
				LocalDate.now(), "Faculdade",
				TipoDespesa.EDUCACAO, conta);
		em.persist(despesa1);
		
		Despesa despesa2 = new Despesa(1000.00, LocalDate.now(), 
				LocalDate.now(), "Aluguel",
				TipoDespesa.MORADIA, conta);
		em.persist(despesa2);
		
		Despesa despesa3 = new Despesa(100.00, LocalDate.now(), 
				LocalDate.now(), "Uber",
				TipoDespesa.TRANSPORTE, conta);
		em.persist(despesa3);
		
		service.valorTotalDespesas();
	}
	
	@Test
	void deveriaMostrarOValorTotalDasDespesasPelaConta() {
		Despesa despesa = new Despesa(600.00, LocalDate.now(), 
				LocalDate.now(), "Compras no mercado",
				TipoDespesa.ALIMENTACAO, conta);
		em.persist(despesa);
		
		Despesa despesa1 = new Despesa(500.00, LocalDate.now(), 
				LocalDate.now(), "Faculdade",
				TipoDespesa.EDUCACAO, conta);
		em.persist(despesa1);
		
		Despesa despesa2 = new Despesa(1000.00, LocalDate.now(), 
				LocalDate.now(), "Aluguel",
				TipoDespesa.MORADIA, conta);
		em.persist(despesa2);
		
		Despesa despesa3 = new Despesa(100.00, LocalDate.now(), 
				LocalDate.now(), "Uber",
				TipoDespesa.TRANSPORTE, conta);
		em.persist(despesa3);
		
		service.valorTotalReceitaPorConta(1L);
	}
	
	@Test
	void deveriaFiltrarADespesaPeloTipo() {
		Despesa despesa = new Despesa(600.00, LocalDate.now(), 
				LocalDate.now(), "Compras no mercado",
				TipoDespesa.ALIMENTACAO, conta);
		em.persist(despesa);
		
		Despesa despesa1 = new Despesa(500.00, LocalDate.now(), 
				LocalDate.now(), "Faculdade",
				TipoDespesa.EDUCACAO, conta);
		em.persist(despesa1);
		
		Despesa despesa2 = new Despesa(1000.00, LocalDate.now(), 
				LocalDate.now(), "Aluguel",
				TipoDespesa.MORADIA, conta);
		em.persist(despesa2);
		
		Despesa despesa3 = new Despesa(100.00, LocalDate.now(), 
				LocalDate.now(), "Uber",
				TipoDespesa.TRANSPORTE, conta);
		em.persist(despesa3);
		
		service.filtroPorTipoDespesa("TRANSPORTE");
	}
}
