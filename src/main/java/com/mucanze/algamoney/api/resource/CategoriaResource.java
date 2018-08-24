package com.mucanze.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algamoney.api.event.RecursoCriadoEvent;
import com.mucanze.algamoney.api.model.Categoria;
import com.mucanze.algamoney.api.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public List<Categoria> getCategorias(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
	public ResponseEntity<Categoria> setCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response){
		Categoria categoriaRetornada = categoriaRepository.save(categoria);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaRetornada.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaRetornada);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
	public ResponseEntity<Categoria> getCategoria(@PathVariable Long id){
		Categoria categoria = categoriaRepository.findOne(id);
		return (categoria == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);
	}

}
