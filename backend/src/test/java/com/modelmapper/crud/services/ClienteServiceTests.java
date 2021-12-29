package com.modelmapper.crud.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.modelmapper.crud.dto.ClienteDTO;
import com.modelmapper.crud.entities.Cliente;
import com.modelmapper.crud.repositories.ClienteRepository;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;
import com.modelmapper.crud.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ClienteServiceTests {


	@InjectMocks // pra simular uma injeção da classe
	private ClienteService service;

	@Mock // pra simular o comportamento do modelmapper
	private ModelMapper modelMapper;

	@Mock // pra simular o comportamento dos métodos do repository
	private ClienteRepository repository;

	private long idExistente;
	private long idNaoExistente;
	private List<Cliente> lista = new ArrayList<>();
	private Cliente cliente;
	private ClienteDTO clienteDTO;

	@BeforeEach
	private void setUp() throws Exception {
		idExistente = 1L;
		idNaoExistente = 1000L;
		cliente = Factory.CriaClienteCpf();
		clienteDTO = Factory.CriaClienteCpfDTO();

		when(modelMapper.map(any(), any())).thenReturn(clienteDTO);

		when(repository.findAll()).thenReturn(lista);

		when(repository.findById(idExistente)).thenReturn(Optional.of(cliente));
		when(repository.findById(idNaoExistente)).thenReturn(Optional.empty());

		when(repository.getById(idExistente)).thenReturn(cliente);
		when(repository.getById(idNaoExistente)).thenThrow(EntityNotFoundException.class);

		when(repository.save(any())).thenReturn(cliente);

		doNothing().when(repository).deleteById(idExistente);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(idNaoExistente);
	}
	

	@Test
	public void updateDeveriaRetornarUsuarioDTOAtualizadoQuandoIdExistir() {
		ClienteDTO dto = service.update(idExistente, clienteDTO);

		Assertions.assertNotNull(dto);
		verify(repository, times(1)).getById(idExistente);
	}

	@Test
	public void updateDeveriaLancarEntityNotFoundExceptionQuandoIdNaoExistir() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(idNaoExistente, clienteDTO);
		});
		verify(repository, times(1)).getById(idNaoExistente);
	}

	@Test
	public void findByidDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(idNaoExistente);
		});

		verify(repository, times(1)).findById(idNaoExistente);
	}

	@Test
	public void findByIdDeveriaRetornarUsuarioDTOQuandoIdExistir() {
		ClienteDTO dto = service.findById(idExistente);

		Assertions.assertTrue(dto.getNome() == "Igor");
		verify(repository, times(1)).findById(idExistente);
	}

	@Test
	public void findAllDeveriaRetornarListaUsuario() {
		List<ClienteDTO> dto = service.findAll();

		Assertions.assertNotNull(dto);
		verify(repository, times(1)).findAll();
	}

	@Test
	public void deleteDeveriaLancarResourceNotFoundExceptionQuandoIdNaoExistir() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idNaoExistente);
		});
		verify(repository, times(1)).deleteById(idNaoExistente);
	}

	@Test
	public void deleteDeveriaFazerNadaQuandoIdExistir() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(idExistente);
		});
		verify(repository, times(1)).deleteById(idExistente);
	}
}
