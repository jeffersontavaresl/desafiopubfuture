package br.com.pubfuture.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	List<Despesa> findByContaIdAndDataPagamentoBetween(Long contaId, LocalDate dataIni, LocalDate dataFim);
	
	List<Despesa> findByDataPagamentoBetween(LocalDate dataIni, LocalDate dataFim);

	List<Despesa> findByContaIdAndTipoDespesa(Long contaId, TipoDespesa tipoDespesa);

	List<Despesa> findByTipoDespesa(TipoDespesa tipo);

	@Query("SELECT SUM(d.valor) FROM Despesa d")
	Optional<Double> findValorTotalDespesa();

	@Query("SELECT SUM(d.valor) FROM Despesa d WHERE conta_id = ?1")
	Optional<Double> findValorTotalDespesaConta(Long contaId);
}
