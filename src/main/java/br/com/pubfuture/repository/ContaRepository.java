package br.com.pubfuture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.pubfuture.model.Conta;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long>{

}
