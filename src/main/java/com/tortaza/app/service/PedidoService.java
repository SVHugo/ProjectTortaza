package com.tortaza.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tortaza.app.interfaces.IPedido;
import com.tortaza.app.models.Pedido;
import com.tortaza.app.services.IPedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService implements IPedidoService {

    @Autowired	//inyeccion
    private IPedido dato;// tiene : @repository

    @Override
    public List<Pedido> listar() {
        return (List<Pedido>) dato.findAll();
    }

    @Override
    public Optional<Pedido> listarId(int id) {
        return dato.findById(id);
    }

    @Override
    public int guardar(Pedido pedido) {
        int res = 0;
        Pedido pe = dato.save(pedido);
        if (pe != null) {
            res = 1;
        }
        return res;
    }

    @Override
    public void eliminar(int id) {
        // TODO Auto-generated method stub
        
    }

    
    
    
}
