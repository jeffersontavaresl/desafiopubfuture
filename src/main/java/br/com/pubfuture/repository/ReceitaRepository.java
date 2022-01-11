package br.com.pubfuture.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{

	List<Receita> findByContaIdAndDataRecebimentoBetween(Long contaId, LocalDate dataIni, LocalDate dataFim);

	List<Receita> findByContaIdAndTipoReceita(Long contaId, TipoReceita tipo);

	List<Receita> findByTipoReceita(TipoReceita tipo);
	
	@Query("SELECT SUM(r.valor) FROM Receita r")
	Optional<Double> findValorTotalReceita();
	
	@Query("SELECT SUM(r.valor) FROM Receita r WHERE conta_id = ?1")
	Optional<Double> findValorTotalReceitaConta(Long contaId);
}
