package com.tortaza.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.tortaza.app.interfaces.IDetalle;
import com.tortaza.app.models.Detallepedido;
import com.tortaza.app.models.Pedido;
import com.tortaza.app.services.IDetalleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleService implements IDetalleService {

    @Autowired
    private IDetalle dato;

    @Override
    public List<Detallepedido> listar() {
        return (List<Detallepedido>) dato.findAll();
    }

    @Override
    public Optional<Detallepedido> listarId(int id) {
        
        return dato.findById(id);
    }

    @Override
    public int guardar(List<Detallepedido> detalles) {
        int res = 0;

        for (var de : detalles) {
            Detallepedido deta = dato.save(de);
            if (deta != null) {
                res = 1;
            }
        }

        return res;
    }

    @Override
    public void eliminar(int id) {
        // TODO Auto-generated method stub

    }

    

}
