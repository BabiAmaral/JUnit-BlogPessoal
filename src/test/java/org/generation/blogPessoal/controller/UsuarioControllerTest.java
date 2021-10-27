package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.generation.blogPessoal.model.Usuario;
import org.generation.blogPessoal.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTest
{
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private Usuario usuario;
	private Usuario usuarioUpdate;
	private Usuario usuarioAdmin;
	
	@BeforeAll
	void start() {
		Usuario usuarioAdmin = new Usuario(0L, "Administrador", "adm123", "12345", "aaaaaa", "adm");
		
		if(!usuarioRepository.findByUsuario(usuarioAdmin.getUsuario()).isPresent()) {
			
			HttpEntity<Usuario> request = new HttpEntity<Usuario> (usuarioAdmin);
			testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);
		}
		
		 usuario = new Usuario(0L, "Natalia Amaral", "nat123", "12345", "aaaaaa", "normal");

		 usuarioUpdate = new Usuario(2L, "Natalia Oliveira", "natila", "123456", "aaaaaa", "normal");

	}
	
	@Test
	@DisplayName("Cadastrar usuário!!")
	@Order(1)
	public void deveRealizarPostUsuario() {
		
		HttpEntity<Usuario> request = new HttpEntity<Usuario> (usuario);
		ResponseEntity<Usuario> resposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, request, Usuario.class);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		
	}
	
	@Test
	@DisplayName("Listar todos os usuários!!")
	@Order(2)
	public void deveRealizarGetAllUsuario() {
		
	ResponseEntity<String> resposta = testRestTemplate.withBasicAuth("adm123", "12345").exchange("/usuarios", HttpMethod.GET, null , String.class);
	assertEquals(HttpStatus.OK, resposta.getStatusCode());
	
	}
	
	@Test
	@DisplayName("Atualizar usuário!!")
	@Order(3)
	public void deveRealizarPutUsuario() {
		
		HttpEntity<Usuario> request = new HttpEntity<Usuario> (usuarioUpdate);
		ResponseEntity<Usuario> resposta = testRestTemplate.withBasicAuth("adm123", "12345").exchange("/usuarios/", HttpMethod.PUT, request , Usuario.class);
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}
	


}
