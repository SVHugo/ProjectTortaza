package com.tortaza.app.interfaces;

import com.tortaza.app.models.Pedido;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedido extends CrudRepository<Pedido, Integer> {
    
}
