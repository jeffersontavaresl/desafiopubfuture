package br.com.pubfuture.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ContaRepositoryTest {

	@Autowired
	ContaRepository contaRepository;
	
	@Test 
	void deveriaSomarOSaldoTotalDeTodasAsContas() {
		contaRepository.findValorTotalConta();
	}
	
	@Test
	void deveriaSomarOSaldoTotalPorConta() {
		contaRepository.findValorTotalContaId(1L);
	}
}
