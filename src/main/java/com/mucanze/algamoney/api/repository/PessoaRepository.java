package com.mucanze.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mucanze.algamoney.api.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
