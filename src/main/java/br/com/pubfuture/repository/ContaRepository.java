package br.com.pubfuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

	@Query("SELECT SUM(c.saldo) FROM Conta c")
	Double findValorTotalConta();

	@Query("SELECT SUM(c.saldo) FROM Conta c WHERE id = ?1")
	Double findValorTotalContaId(Long id);
}
