package com.mucanze.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mucanze.algamoney.api.model.Pessoa;
import com.mucanze.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa actualizar(Long id, Pessoa pessoa){
		Pessoa pessoaRetornada = buscarPessoaPeloId(id);
		BeanUtils.copyProperties(pessoa, pessoaRetornada, "id");
		
		return pessoaRepository.save(pessoaRetornada);
	}

	public void actualizarPropriedadeActivo(Long id, Boolean activo) {
		Pessoa pessoaRetornada = buscarPessoaPeloId(id);
		pessoaRetornada.setActivo(activo);
		
		pessoaRepository.save(pessoaRetornada);
	}
	
	private Pessoa buscarPessoaPeloId(Long id) {
		Pessoa pessoaRetornada = pessoaRepository.findOne(id);
		if(pessoaRetornada == null){
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaRetornada;
	}

	

}
