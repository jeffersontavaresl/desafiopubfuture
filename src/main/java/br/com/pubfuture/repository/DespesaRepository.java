package br.com.pubfuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.model.Despesa;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long>{

}
