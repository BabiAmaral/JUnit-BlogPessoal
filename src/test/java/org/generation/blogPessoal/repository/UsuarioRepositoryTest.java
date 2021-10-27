package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.generation.blogPessoal.model.Usuario;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)//fará pela porta disponível
@TestInstance(TestInstance.Lifecycle.PER_CLASS)// informa de qual maneira o ciclo de vida do teste será executado
public class UsuarioRepositoryTest
{
	@Autowired
	public UsuarioRepository usuarioRepository;
	
	@BeforeAll
	void start() {
		Usuario usuario = new Usuario(0L, "Barbara Amaral", "babi123", "12345", "aaaaaa", "adm");
		
		if(!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) 
			usuarioRepository.save(usuario);
		
		 usuario = new Usuario(0L, "Steve Rogers", "Capitão América", "12345", "aaaaaa", "adm");
		
		if(!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) 
			usuarioRepository.save(usuario);
		
		 usuario = new Usuario(0L, "Natasha Rogers", "Viúva Negra", "12345", "aaaaaa", "adm");
		
		if(!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) 
			usuarioRepository.save(usuario);
		
		 usuario = new Usuario(0L, "Tony Rogers", "Iron Man", "12345", "aaaaaa", "adm");
		
		if(!usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) 
			usuarioRepository.save(usuario);
	}
	
	@Test
	@DisplayName("✨ Retorna Nome")
	public void findByRetornaNome() {
		Usuario usuario = usuarioRepository.findByNome("Barbara Amaral");
		assertTrue(usuario.getNome().equals("Barbara Amaral"));
	}
	
	@Test
	@DisplayName("✨ Retorna Três Usuários")
	public void findAllByNomeContainingIgnoreCaseRetornaTresUsuarios() {
		List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Rogers");
		assertEquals(3, listaDeUsuarios.size());
	}
	
	@AfterAll
	public void end(){
		System.out.println("Teste finalizado!!");
	}

}
