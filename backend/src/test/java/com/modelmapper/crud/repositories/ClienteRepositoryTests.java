package com.modelmapper.crud.repositories;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.modelmapper.crud.entities.Cliente;
import com.modelmapper.crud.entities.ClienteCpf;

@DataJpaTest
public class ClienteRepositoryTests {

	@Autowired
	private ClienteRepository repository;
	private long idExistente;
	private long idNaoExistente;
	private long totalClientes;

	@BeforeEach
	private void setUp() throws Exception {
		idExistente = 1L;
		idNaoExistente = 1000L;
		totalClientes = 2L;
	}

	@Test
	public void findByIdDeveriaRetornarOptionalEmptyQuandoIdNaoExistir() {
		Optional<Cliente> usuario = repository.findById(idNaoExistente);

		Assertions.assertTrue(usuario.isEmpty());
	}

	@Test
	public void findByIdDeveriaRetornarObjetoQuandoIdExistir() {
		Optional<Cliente> usuario = repository.findById(idExistente);

		Assertions.assertTrue(usuario.isPresent());
		Assertions.assertEquals(usuario.get().getNome(), "Alex");
	}

	@Test
	public void updateDeveriaAtualizarObjeto() {
		Cliente cliente = repository.getById(idExistente);

		cliente.setNome("AlexTeste");
		cliente = repository.save(cliente);

		Assertions.assertEquals(cliente.getNome(), "AlexTeste");
	}

	@Test
	public void updateDeveriaLancarEntityNotFoundExceptionQuandoIdNaoexistir() {
		Cliente cliente = repository.getById(idNaoExistente);

		Assertions.assertThrows(EntityNotFoundException.class, () -> {
			cliente.setNome("AlexTeste");
			repository.save(cliente);
		});
	}

	@Test
	public void insertDeveriaAutoIncrementarObjetoQuandoIdForNulo() {
		Cliente cliente = new ClienteCpf(null, "Igor", "123", "cpf", "163.224.820-41");

		cliente = repository.save(cliente);

		Assertions.assertEquals(cliente.getId(), totalClientes + 1);
		Assertions.assertNotNull(cliente.getId());
		Assertions.assertTrue(cliente.getType().equals("cpf"));
	}

	@Test
	public void deleteDeveriaLançarEmptyResultDataAccessExceptionQuandoIdNaoExistir() {
		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
			repository.deleteById(idNaoExistente);
		});
	}

	@Test
	public void deleteDeveriaDeletarObjetoQuandoIdExistir() {
		repository.deleteById(idExistente);

		Optional<Cliente> resultado = repository.findById(idExistente);
		Assertions.assertFalse(resultado.isPresent());
	}
}
