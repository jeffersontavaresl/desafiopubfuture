package br.com.pubfuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.model.Receita;

@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long>{

}
