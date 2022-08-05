package com.tortaza.app.interfaces;

import com.tortaza.app.models.Detallepedido;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetalle extends CrudRepository<Detallepedido, Integer> {
    
}
