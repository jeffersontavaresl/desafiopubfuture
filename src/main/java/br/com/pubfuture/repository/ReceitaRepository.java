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

	/** Query para buscar no banco uma lista de receitas com a conta entre o período de datas informados
	 */
	List<Receita> findByContaIdAndDataRecebimentoBetween(Long contaId, LocalDate dataIni, LocalDate dataFim);
	
	
	/** Query para buscar no banco uma lista de receitas entre o período de datas informados
	 */
	List<Receita> findByDataRecebimentoBetween(LocalDate dataIni, LocalDate dataFim);
	
	
	/** Query para buscar no banco uma lista de receitas com a conta e o tipo da receita informados
	 */
	List<Receita> findByContaIdAndTipoReceita(Long contaId, TipoReceita tipo);

	
	/** Query para buscar no banco uma lista de receitas com o tipo da receita informado
	 */
	List<Receita> findByTipoReceita(TipoReceita tipo);
	
	
	/** Query para somar o valor total de todas as receitas
	 */
	@Query("SELECT SUM(r.valor) FROM Receita r")
	Optional<Double> findValorTotalReceita();
	
	
	/** Query para somar o valor total das receitas pelo id da conta
	 */
	@Query("SELECT SUM(r.valor) FROM Receita r WHERE conta_id = ?1")
	Optional<Double> findValorTotalReceitaConta(Long contaId);

}
