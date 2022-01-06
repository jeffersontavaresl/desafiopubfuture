package br.com.pubfuture.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.enums.TipoDespesa;
import br.com.pubfuture.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

	List<Despesa> findByContaIdAndDataPagamentoBetween(Long contaId, LocalDate dataIni, LocalDate dataFim);

	List<Despesa> findByContaIdAndTipoDespesa(Long contaId, TipoDespesa tipoDespesa);

}
