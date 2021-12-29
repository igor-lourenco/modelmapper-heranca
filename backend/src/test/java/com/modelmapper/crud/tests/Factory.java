package com.modelmapper.crud.tests;

import com.modelmapper.crud.dto.ClienteCpfDTO;
import com.modelmapper.crud.dto.ClienteDTO;
import com.modelmapper.crud.entities.Cliente;
import com.modelmapper.crud.entities.ClienteCpf;

public class Factory {
	

	public static Cliente CriaClienteCpf() {
		Cliente cliente =  new ClienteCpf(null, "Igor", "123", "cpf", "163.224.820-41");
		return cliente;
	}
	public static Cliente CriaNovoClienteCpf() {
		Cliente cliente =  new ClienteCpf(1L, "Igor", "123", "cpf", "163.224.820-41");
		return cliente;
	}
	
	public static ClienteCpfDTO CriaClienteCpfDTO() {
		ClienteCpfDTO clienteDTO =  new ClienteCpfDTO(null, "Igor", "123","163.224.820-41");
		return clienteDTO;
	}
	
	public static ClienteDTO CriaClienteDTO() {
		Cliente cliente =  new Cliente(1L, "Igor", "123", "cpf");
		return new ClienteDTO(cliente);
	}
	public static ClienteDTO NovoClienteDTO() {
		Cliente cliente =  new Cliente(null, "Igor", "123", "cpf");
		return new ClienteDTO(cliente);
	}
	
	public static ClienteDTO ClienteCpfDTO() {
		Cliente Cliente = CriaClienteCpf();
		return new ClienteDTO(Cliente);
	}
	
}
