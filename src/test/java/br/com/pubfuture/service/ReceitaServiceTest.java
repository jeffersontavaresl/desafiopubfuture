package br.com.pubfuture.service;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import br.com.pubfuture.enums.TipoConta;
import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.model.Conta;
import br.com.pubfuture.model.Receita;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ReceitaServiceTest {

	@Autowired
	private ReceitaService service;
	
	@Autowired
	private TestEntityManager em;
	
	private Conta conta = new Conta("Banco do Brasil", 100.00, TipoConta.CARTEIRA);

	@Test
	void deveriaCadastrarNovaReceita() {
		Receita receita = new Receita(200.00, LocalDate.now(), 
				LocalDate.now(), "Férias da empresa",
				TipoReceita.OUTROS, conta);
		em.persist(receita);
	}
	
	@Test
	void deveriaListarTodasAsReceitas() {
		Receita receita = new Receita(200.00, LocalDate.now(), 
				LocalDate.now(), "Férias da empresa",
				TipoReceita.OUTROS, conta);
		em.persist(receita);

		
		Receita receita1 = new Receita(100.00, LocalDate.now(), 
				LocalDate.now(), "Presente da firma",
				TipoReceita.PRESENTE, conta);
		em.persist(receita1);

		Receita receita2 = new Receita(2000.00, LocalDate.now(), 
				LocalDate.now(), "Salário de novembro",
				TipoReceita.SALARIO, conta);
		em.persist(receita2);

		Receita receita3 = new Receita(500.00, LocalDate.now(), 
				LocalDate.now(), "Premiação",
				TipoReceita.PREMIO, conta);
		em.persist(receita3);

		service.lista();
	}
	
	@Test
	void deveriaFiltrarAReceitaPelaContaDataInicialEDataFinal() {
		Receita receita = new Receita(200.00, LocalDate.now(), 
				LocalDate.now(), "Férias da empresa",
				TipoReceita.OUTROS, conta);
		em.persist(receita);

		
		Receita receita1 = new Receita(100.00, LocalDate.now(), 
				LocalDate.now(), "Presente da firma",
				TipoReceita.PRESENTE, conta);
		em.persist(receita1);

		Receita receita2 = new Receita(2000.00, LocalDate.now(), 
				LocalDate.now(), "Salário de novembro",
				TipoReceita.SALARIO, conta);
		em.persist(receita2);

		Receita receita3 = new Receita(500.00, LocalDate.now(), 
				LocalDate.now(), "Premiação",
				TipoReceita.PREMIO, conta);
		em.persist(receita3);
		
		service.filtroPorContaData(1L, "2020-10-10", "2015-10-10");
	}
	
	@Test
	void deveriaMostrarOValorTotalDasReceitas() {
		Receita receita = new Receita(200.00, LocalDate.now(), 
				LocalDate.now(), "Férias da empresa",
				TipoReceita.OUTROS, conta);
		em.persist(receita);

		
		Receita receita1 = new Receita(100.00, LocalDate.now(), 
				LocalDate.now(), "Presente da firma",
				TipoReceita.PRESENTE, conta);
		em.persist(receita1);

		Receita receita2 = new Receita(2000.00, LocalDate.now(), 
				LocalDate.now(), "Salário de novembro",
				TipoReceita.SALARIO, conta);
		em.persist(receita2);
		
		service.valorTotalReceitas();
	}
	
	@Test
	void deveriaMostrarOValorTotalDasReceitasPelaConta() {
		Receita receita = new Receita(200.00, LocalDate.now(), 
				LocalDate.now(), "Férias da empresa",
				TipoReceita.OUTROS, conta);
		em.persist(receita);

		
		Receita receita1 = new Receita(100.00, LocalDate.now(), 
				LocalDate.now(), "Presente da firma",
				TipoReceita.PRESENTE, conta);
		em.persist(receita1);

		Receita receita2 = new Receita(2000.00, LocalDate.now(), 
				LocalDate.now(), "Salário de novembro",
				TipoReceita.SALARIO, conta);
		em.persist(receita2);
		
		service.valorTotalReceitaPorConta(1L);
	}
	
	@Test
	void deveriaFiltrarAReceitaPeloTipo() {
		Receita receita = new Receita(200.00, LocalDate.now(), 
				LocalDate.now(), "Férias da empresa",
				TipoReceita.OUTROS, conta);
		em.persist(receita);

		
		Receita receita1 = new Receita(100.00, LocalDate.now(), 
				LocalDate.now(), "Presente da firma",
				TipoReceita.PRESENTE, conta);
		em.persist(receita1);

		Receita receita2 = new Receita(2000.00, LocalDate.now(), 
				LocalDate.now(), "Salário de novembro",
				TipoReceita.SALARIO, conta);
		em.persist(receita2);
		
		service.filtroPorTipoReceita("SALARIO");
	}
}
