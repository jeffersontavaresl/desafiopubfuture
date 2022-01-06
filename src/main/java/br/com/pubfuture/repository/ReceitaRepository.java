package br.com.pubfuture.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.enums.TipoReceita;
import br.com.pubfuture.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{

	List<Receita> findByContaIdAndDataRecebimentoBetween(Long contaId, LocalDate dataIni, LocalDate dataFim);

	List<Receita> findByContaIdAndTipoReceita(Long contaId, TipoReceita tipo);
}
