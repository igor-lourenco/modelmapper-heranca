package com.modelmapper.crud.services;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.modelmapper.crud.dto.ClienteDTO;
import com.modelmapper.crud.entities.Cliente;
import com.modelmapper.crud.repositories.ClienteRepository;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional // pra dar rollback no banco depois de cada teste
public class ClienteServiceIntegrationsTests {

	@Autowired
	private ClienteService service;
	@Autowired
	private ClienteRepository repository;

	private long idExistente;
	private long idNaoExistente;
	private long totalUsuarios;
	private Cliente usuarioNovo;
	private Cliente usuario;

	@BeforeEach
	private void setUp() throws Exception {
		idExistente = 1L;
		idNaoExistente = 1000L;
		totalUsuarios = 2L;
		usuarioNovo = new Cliente(null, "Igor", "Lourenço", "123");
		usuario = repository.getById(idExistente);
	}
	
	@Test
	public void findByIdDeveriaRetornarClienteDTOQuandoIdExistir() {	
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(idNaoExistente);
		});
	}

	@Test
	public void updateDeveriaRetornarClienteDTOAtualizadoQuandoIdExistir() {
		ClienteDTO usuarioDTO = new ClienteDTO(usuario);
		usuarioDTO.setNome("AlexTeste");

		ClienteDTO novoUsuarioDTO = service.update(idExistente, usuarioDTO);

		Assertions.assertEquals(totalUsuarios, repository.count());
		Assertions.assertTrue(novoUsuarioDTO.getNome().contains("AlexTeste"));
		Assertions.assertTrue(novoUsuarioDTO.getId() == 1);
	}

	@Test
	public void updateDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {
		ClienteDTO usuarioDTO = new ClienteDTO(usuario);
		usuarioDTO.setNome("AlexTeste");

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(idNaoExistente, usuarioDTO);
		});
	}

	@Test
	public void insertDeveriaRetornarNovoClienteDTO() {
		ClienteDTO dto = new ClienteDTO(usuarioNovo);

		ClienteDTO usuarioDTO = service.insert(dto);

		Assertions.assertEquals(totalUsuarios + 1, repository.count());
		Assertions.assertTrue(usuarioDTO.getNome().contains("Igor"));
		Assertions.assertTrue(usuarioDTO.getId() == 3);
	}


	@Test
	public void findAllDeveriaRetornarLista() {

		List<ClienteDTO> resultado = service.findAll();

		Assertions.assertFalse(resultado.isEmpty());
		Assertions.assertEquals(2, resultado.size());
		Assertions.assertTrue(resultado.get(0).getNome().contains("Alex"));
	}

	@Test
	public void deleteDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idNaoExistente);
		});
	}

	@Test
	public void deleteDeveriaDeletarClienteQuandoIdExistir() {
		service.delete(idExistente);

		Assertions.assertEquals(totalUsuarios - 1, repository.count());
		Assertions.assertFalse(repository.existsById(idExistente));
	}
}
