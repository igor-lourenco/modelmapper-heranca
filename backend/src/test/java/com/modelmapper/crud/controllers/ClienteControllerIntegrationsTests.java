package com.modelmapper.crud.controllers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelmapper.crud.dto.ClienteDTO;
import com.modelmapper.crud.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteControllerIntegrationsTests {

	@Autowired //pra fazer as requisições com o mock
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	
	private long idExistente;
	private long idNaoExistente;
	private ClienteDTO usuarioDTO;
	

	@BeforeEach
	private void setUp() throws Exception {
		idExistente = 1L;
		idNaoExistente = 1000L;
		usuarioDTO = Factory.NovoClienteDTO();
	}
	
	@Test
	public void findByIdDeveriaRetornarUsuarioDTOQuandoIdExistir() throws Exception {
		ResultActions resultado = mockMvc.perform(get("/clientes/{id}", idExistente)
				.accept(MediaType.APPLICATION_JSON)); // tipo de resposta da requisição
		
		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.id").value(idExistente)); // tem que existir um campo id no json
		resultado.andExpect(jsonPath("$.nome").value("Alex")); // tem que existir um campo nome no json
		resultado.andExpect(jsonPath("$.senha").value("123")); // tem que existir um campo nome no json
	}
	
	@Test
	public void findByIdDeveriaRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		ResultActions resultado = mockMvc.perform(get("/clientes/{id}", idNaoExistente)
				.accept(MediaType.APPLICATION_JSON)); // tipo de resposta da requisição
		
		resultado.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertDeveriaRetornarUsuarioDTO() throws Exception {
		String corpoJson = objectMapper.writeValueAsString(usuarioDTO); // converte para json
		
		String nomeEsperado = usuarioDTO.getNome(); // antes de atualizar
		String senhaEsperado = usuarioDTO.getSenha();
		
		ResultActions resultado = mockMvc.perform(post("/clientes")
				.content(corpoJson)  //corpo da requisição
				.contentType(MediaType.APPLICATION_JSON) // tipo do corpo da requisição
				.accept(MediaType.APPLICATION_JSON)); // tipo de resposta da requisição
		
		resultado.andExpect(status().isCreated());
		resultado.andExpect(jsonPath("$.id").value(3)); // tem que existir um campo id no json
		resultado.andExpect(jsonPath("$.nome").value(nomeEsperado)); // tem que existir um campo nome no json
		resultado.andExpect(jsonPath("$.senha").value(senhaEsperado)); // tem que existir um campo nome no json
	}
	
	@Test
	public void updateDeveriaRetornarNotFoundQuandoIdNaoExistir() throws Exception {
		String corpoJson = objectMapper.writeValueAsString(usuarioDTO); // converte para json
		
			
		ResultActions resultado = mockMvc.perform(put("/clientes/{id}", idNaoExistente)
				.content(corpoJson)  //corpo da requisição
				.contentType(MediaType.APPLICATION_JSON) // tipo do corpo da requisição
				.accept(MediaType.APPLICATION_JSON)); // tipo de resposta da requisição
		
		resultado.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateDeveriaRetornarUsuarioDTOQuandoIdExistir() throws Exception {
		String corpoJson = objectMapper.writeValueAsString(usuarioDTO); // converte para json
		
		String nomeEsperado = usuarioDTO.getNome(); // antes de atualizar
		String senhaEsperado = usuarioDTO.getSenha();
		
		ResultActions resultado = mockMvc.perform(put("/clientes/{id}", idExistente)
				.content(corpoJson)  //corpo da requisição
				.contentType(MediaType.APPLICATION_JSON) // tipo do corpo da requisição
				.accept(MediaType.APPLICATION_JSON)); // tipo de resposta da requisição
		
		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.id").value(idExistente)); // tem que existir um campo id no json
		resultado.andExpect(jsonPath("$.nome").value(nomeEsperado)); // tem que existir um campo nome no json
		resultado.andExpect(jsonPath("$.senha").value(senhaEsperado)); // tem que existir um campo nome no json
	}
}
