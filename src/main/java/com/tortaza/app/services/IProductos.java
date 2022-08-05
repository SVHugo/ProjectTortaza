package com.tortaza.app.services;

import com.tortaza.app.models.Producto;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductos extends CrudRepository<Producto, Integer> {

}
