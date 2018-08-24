package com.mucanze.algamoney.api.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.mucanze.algamoney.api.model.TipoLancamento;

public class ResumoLancamento {
	
	private Long id;	
	private String descricao;
	private TipoLancamento tipo;
	private LocalDate dataVencimento;
	private LocalDate dataPagamento;
	private BigDecimal valor;
	private String pessoa;
	private String categoria;
	
	public ResumoLancamento(Long id, String descricao, TipoLancamento tipo, LocalDate dataVencimento,
			LocalDate dataPagamento, BigDecimal valor, String pessoa, String categoria) {
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		this.valor = valor;
		this.pessoa = pessoa;
		this.categoria = categoria;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public TipoLancamento getTipo() {
		return tipo;
	}
	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}
	public LocalDate getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public LocalDate getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getPessoa() {
		return pessoa;
	}
	public void setPessoa(String pessoa) {
		this.pessoa = pessoa;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
