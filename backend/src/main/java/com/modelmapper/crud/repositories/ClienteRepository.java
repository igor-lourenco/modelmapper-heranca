package com.modelmapper.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.modelmapper.crud.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
