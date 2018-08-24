package com.mucanze.algamoney.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mucanze.algamoney.api.model.Lancamento;
import com.mucanze.algamoney.api.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery{

}
