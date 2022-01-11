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
	
	/** Query para buscar no banco uma lista de despesas com a conta entre o período de datas informados
	 */
	List<Despesa> findByContaIdAndDataPagamentoBetween(Long contaId, LocalDate dataIni, LocalDate dataFim);
	
	
	/** Query para buscar no banco uma lista de despesas entre o período de datas informados
	 */
	List<Despesa> findByDataPagamentoBetween(LocalDate dataIni, LocalDate dataFim);
	
	
	/** Query para buscar no banco uma lista de despesas com a conta e o tipo da despesa informados
	 */
	List<Despesa> findByContaIdAndTipoDespesa(Long contaId, TipoDespesa tipoDespesa);

	
	/** Query para buscar no banco uma lista de despesas com o tipo da despesa informado
	 */
	List<Despesa> findByTipoDespesa(TipoDespesa tipo);

	
	/** Query para somar o valor total de todas as despesas
	 */
	@Query("SELECT SUM(d.valor) FROM Despesa d")
	Optional<Double> findValorTotalDespesa();

	
	/** Query para somar o valor total das despesas pelo id da conta
	 */
	@Query("SELECT SUM(d.valor) FROM Despesa d WHERE conta_id = ?1")
	Optional<Double> findValorTotalDespesaConta(Long contaId);
}
