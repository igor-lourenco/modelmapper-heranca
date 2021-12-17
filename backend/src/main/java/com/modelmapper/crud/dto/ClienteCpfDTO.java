package com.modelmapper.crud.dto;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("cpf")
//@SuperBuilder
public class ClienteCpfDTO extends ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cpf;
}
