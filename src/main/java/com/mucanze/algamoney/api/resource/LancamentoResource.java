package com.mucanze.algamoney.api.resource;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algamoney.api.event.RecursoCriadoEvent;
import com.mucanze.algamoney.api.exceptionHandler.AlgamoneyExceptionHandler.Erro;
import com.mucanze.algamoney.api.model.Lancamento;
import com.mucanze.algamoney.api.repository.LancamentoRepository;
import com.mucanze.algamoney.api.repository.filter.LancamentoFilter;
import com.mucanze.algamoney.api.repository.projection.ResumoLancamento;
import com.mucanze.algamoney.api.service.LancamentoService;
import com.mucanze.algamoney.api.service.exception.PessoaInexistenteOuInativoException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	
	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public Page<Lancamento> getLancamentos(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}
	
	@GetMapping(params = "resumo")
	public Page<ResumoLancamento> getLancamentosResumido(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoRepository.resumir(lancamentoFilter, pageable);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> setLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lancamentoRetornado = lancamentoService.salvar(lancamento);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoRetornado.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoRetornado);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> getLancamento(@PathVariable Long id){
		Lancamento lancamento = lancamentoRepository.findOne(id);
		
		return (lancamento == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(lancamento); 
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeLancamento(@PathVariable Long id){
		lancamentoRepository.delete(id);
	}
	
	@ExceptionHandler({ PessoaInexistenteOuInativoException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativoException(PessoaInexistenteOuInativoException ex){
		
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente_ou_inativa", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
