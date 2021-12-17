package com.modelmapper.crud.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.modelmapper.crud.dto.ClienteCnpjDTO;
import com.modelmapper.crud.dto.ClienteCpfDTO;
import com.modelmapper.crud.dto.ClienteDTO;
import com.modelmapper.crud.entities.Cliente;
import com.modelmapper.crud.entities.ClienteCnpj;
import com.modelmapper.crud.entities.ClienteCpf;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		
		//classe para mapear updates		
		modelMapper.addMappings(new PropertyMap<ClienteDTO, Cliente>() {
			@Override
			protected void configure() {
				skip(destination.getId());
			}
		});
		
		//classe para mapear herança
		modelMapper.createTypeMap(ClienteCpf.class, ClienteDTO.class)
        .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), ClienteCpfDTO.class));

		modelMapper.createTypeMap(ClienteCnpj.class, ClienteDTO.class)
		        .setConverter(mappingContext -> modelMapper.map(mappingContext.getSource(), ClienteCnpjDTO.class));

		return modelMapper;
	}
}
