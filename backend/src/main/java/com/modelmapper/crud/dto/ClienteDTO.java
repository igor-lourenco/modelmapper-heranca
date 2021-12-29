package com.modelmapper.crud.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.modelmapper.crud.entities.Cliente;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({ @JsonSubTypes.Type(value = ClienteCpfDTO.class, name = "cpf"),
		@JsonSubTypes.Type(value = ClienteCnpjDTO.class, name = "cnpj") })
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private String senha;
	
	public ClienteDTO(Cliente obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
		this.senha = obj.getSenha();
	}
}
