package com.mucanze.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algamoney.api.event.RecursoCriadoEvent;
import com.mucanze.algamoney.api.model.Pessoa;
import com.mucanze.algamoney.api.repository.PessoaRepository;
import com.mucanze.algamoney.api.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private PessoaService PessoaService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Pessoa> getPessoas(){
		return pessoaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> setPessoa(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		Pessoa pessoaRetornada = pessoaRepository.save(pessoa);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaRetornada.getId()));	
		
		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaRetornada);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id){
		Pessoa pessoa = pessoaRepository.findOne(id);
		
		return (pessoa == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoa);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> updatePessoa(@PathVariable Long id, @Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaRetornada = PessoaService.actualizar(id, pessoa);
		
		return ResponseEntity.status(HttpStatus.OK).body(pessoaRetornada);
	}
	
	@PutMapping("/{id}/activo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePessoaActivoProperties(@PathVariable Long id, @RequestBody Boolean activo){
		PessoaService.actualizarPropriedadeActivo(id, activo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removePessoa(@PathVariable Long id){
		pessoaRepository.delete(id);
	}

}
