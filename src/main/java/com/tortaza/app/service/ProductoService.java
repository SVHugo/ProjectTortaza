package com.tortaza.app.service;

import java.util.List;
import java.util.Optional;

import com.tortaza.app.models.Producto;
import com.tortaza.app.services.IProductos;
import com.tortaza.app.services.IProductosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service		//servicio
public class ProductoService implements IProductosService { //implementa el IproductosService(tiene el crud)

    @Autowired	//inyeccion
    private IProductos dato;// tiene : @repository

    @Override
    public List<Producto> listar() {
        return (List<Producto>) dato.findAll();
      
    }

    @Override
    public Optional<Producto> listarId(int id) {
    	
        return dato.findById(id);
    }

    @Override
    public int guardar(Producto p) {
        int res = 0;
        Producto producto = dato.save(p);
        if (!producto.equals(null)) {
            res = 1;
        }
        return res;
    }

    @Override
    public void eliminar(int id) {
        // TODO Auto-generated method stub
    	
    }

	@Override
	public List<Producto> findAll() {
		return (List<Producto>) dato.findAll();
	}

	

	

}
