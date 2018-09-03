package com.mucanze.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mucanze.algamoney.api.model.Lancamento;
import com.mucanze.algamoney.api.model.Pessoa;
import com.mucanze.algamoney.api.repository.LancamentoRepository;
import com.mucanze.algamoney.api.repository.PessoaRepository;
import com.mucanze.algamoney.api.service.exception.PessoaInexistenteOuInativoException;

@Service
public class LancamentoService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	public Lancamento salvar(Lancamento lancamento) {
		 Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		 if(pessoa == null || pessoa.isInativo()){
			 throw new PessoaInexistenteOuInativoException();
		 }
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento actualizar(Long id, Lancamento lancamento){
		Lancamento lancamentoRetornado = buscarLancamentoExistente(id);
		if(!lancamento.getPessoa().equals(lancamentoRetornado.getPessoa())){
			validarPessoa(lancamento);
		}
		
		BeanUtils.copyProperties(lancamento, lancamentoRetornado, "id");
		
		return lancamentoRepository.save(lancamentoRetornado);
	}

	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if(lancamento.getPessoa().getId() != null){
			pessoa = pessoaRepository.findOne(lancamento.getPessoa().getId());
		}
		
		if(pessoa == null || pessoa.isInativo()){
			throw new PessoaInexistenteOuInativoException();
		}
	}

	private Lancamento buscarLancamentoExistente(Long id) {
		Lancamento lancamentoRetornado = lancamentoRepository.findOne(id);
		if(lancamentoRetornado == null){
			throw new IllegalArgumentException();
		}
		return lancamentoRetornado;
	}

}
