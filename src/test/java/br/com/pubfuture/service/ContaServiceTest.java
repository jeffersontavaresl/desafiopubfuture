package br.com.pubfuture.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.model.Conta;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ContaServiceTest {
	
	@Autowired
	private ContaService service;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	void deveriaAdicionarUmaNovaConta() {
		Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
		em.persist(conta);
	}
	
	@Test
	void deveriaListarTodasAsContas(Pageable page) {
		Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
		em.persist(conta);
		Conta conta2 = new Conta("Bradesco", 50.00, TipoConta.CARTEIRA);
		em.persist(conta2);
		Conta conta3 = new Conta("Santander", 600.00, TipoConta.CONTA_CORRENTE);
		em.persist(conta3);
		Conta conta4 = new Conta("Banco do Brasil", 700.00, TipoConta.POUPANCA);
		em.persist(conta4);

		service.listaDeContas(page);
	}
	
	@Test
	void deveriaRealizarUmaTranferiaDeUmaContaParaOutra() {
		Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
		em.persist(conta);
		Conta conta2 = new Conta("Bradesco", 50.00, TipoConta.CARTEIRA);
		em.persist(conta2);
		
		service.transferenciaEntreContas(1L, 2L, 50.00);
	}
	
	@Test
	void deveriaDetalharUmaContaPeloId() {
		Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
		em.persist(conta);
		Conta conta2 = new Conta("Bradesco", 50.00, TipoConta.CARTEIRA);
		em.persist(conta2);
		Conta conta3 = new Conta("Santander", 600.00, TipoConta.CONTA_CORRENTE);
		em.persist(conta3);
		Conta conta4 = new Conta("Banco do Brasil", 700.00, TipoConta.POUPANCA);
		em.persist(conta4);

		service.detalhesDaConta(2L);
	}
	
	@Test
	void deveriaMostrarOSaldoDeTodasAsContas() {
		Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
		em.persist(conta);
		Conta conta2 = new Conta("Bradesco", 50.00, TipoConta.CARTEIRA);
		em.persist(conta2);
		
		service.saldoDaConta();
	}
	
	@Test
	void deveriaMostrarOSaldoDeUmaContaPeloId() {
		Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);
		em.persist(conta);
		Conta conta2 = new Conta("Bradesco", 50.00, TipoConta.CARTEIRA);
		em.persist(conta2);
		
		service.saldoDaContaId(1L);
	}
}
