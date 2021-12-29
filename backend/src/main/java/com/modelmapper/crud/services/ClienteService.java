package com.modelmapper.crud.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modelmapper.crud.dto.ClienteCnpjDTO;
import com.modelmapper.crud.dto.ClienteDTO;
import com.modelmapper.crud.entities.Cliente;
import com.modelmapper.crud.entities.ClienteCnpj;
import com.modelmapper.crud.entities.ClienteCpf;
import com.modelmapper.crud.repositories.ClienteRepository;
import com.modelmapper.crud.services.exceptions.DatabaseException;
import com.modelmapper.crud.services.exceptions.ResourceNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	@Autowired
	private ModelMapper modelMapper;

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cliente não existe " + id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Violação de integridade no banco");
		}
	}

	@Transactional
	public ClienteDTO update(Long id, ClienteDTO dto) {
		try {
			Cliente entity = repository.getById(id);
			// tem que passar o id no corpo da requisição
			modelMapper.map(dto, entity);
			entity = repository.save(entity);
			return modelMapper.map(entity, ClienteDTO.class);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Cliente não existe " + id);
		} catch (MappingException e) {
			throw new ResourceNotFoundException("Cliente não atualizado " + id);
		}
	}

	@Transactional
	public ClienteDTO insert(ClienteDTO dto) {
		Cliente entity = new Cliente();
		if (dto instanceof ClienteCnpjDTO) {
			entity = modelMapper.map(dto, ClienteCnpj.class);
		} else {
			entity = modelMapper.map(dto, ClienteCpf.class);
		}
		entity = repository.save(entity);
		return modelMapper.map(entity, ClienteDTO.class);
	}

	@Transactional
	public ClienteDTO findById(Long id) {
		Cliente obj = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado"));
		return modelMapper.map(obj, ClienteDTO.class);
	}

	@Transactional
	public List<ClienteDTO> findAll() {
		List<Cliente> obj = repository.findAll();
		return obj.stream().map(c -> modelMapper.map(c, ClienteDTO.class)).collect(Collectors.toList());
	}
}
