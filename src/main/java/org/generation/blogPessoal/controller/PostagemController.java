package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Postagem;
import org.generation.blogPessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*") //a classe aceita requisições de qualquer origem
public class PostagemController
{
	@Autowired //garante que todos serviços da interface repository seja acessado a partir do  controller
	private PostagemRepository repository;

	@GetMapping 
	public ResponseEntity<List<Postagem>> GetAll() {
		return ResponseEntity.ok(repository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> GetById(@PathVariable long id){ 
		return repository.findById(id) 
		     .map(resp -> ResponseEntity.ok(resp)) //.map tem dentro do objeto optional/retorna o ok
			 .orElse(ResponseEntity.notFound().build());//orElse, outro metodo dentro de optional/ retorna caso não exista ou dê erro
	}
	
	
	@GetMapping("/titulo/{titulo}") //precisamos colocar algo que não seja atributo na frente para não confundir o caminho.
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo){
		
		return ResponseEntity.ok(repository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping 
	public ResponseEntity<Postagem> post (@RequestBody Postagem postagem){ //como é algo grande, notação pata pegar o corpo
		return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(postagem));
	}
	
	@PutMapping  //atualização //no postman passa o id e faz a atualização
	public ResponseEntity<Postagem> put (@RequestBody Postagem postagem){ //como é algo grande, notação pata pegar o corpo
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(postagem)); //devolvendo o objeto salvo	
	}
	
	@DeleteMapping ("/deletar/{id}")
	public void delete(@PathVariable long id) {
		repository.deleteById(id);
	}
		
	
}        




