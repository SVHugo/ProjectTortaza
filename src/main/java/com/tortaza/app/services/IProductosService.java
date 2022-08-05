package com.tortaza.app.services;

import java.util.List;
import java.util.Optional;


import com.tortaza.app.models.Producto;

public interface IProductosService {
    public List<Producto> listar();

    public Optional<Producto> listarId(int id);
    

    public int guardar(Producto p);

    public void eliminar(int id);
    
    
    public List<Producto> findAll();
    
    
    
}
