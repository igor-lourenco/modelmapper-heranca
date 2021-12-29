package com.modelmapper.crud.entities;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_cliente_cpf")
@DiscriminatorValue("cpf")
@SuperBuilder
public class ClienteCpf extends Cliente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String cpf;

	public ClienteCpf(Long id, String nome, String senha, String type, String cpf) {
		super(id, nome, senha, type);
		this.cpf = cpf;
	}
}
