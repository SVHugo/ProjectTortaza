package com.tortaza.app.services;

import java.util.List;
import java.util.Optional;

import com.tortaza.app.models.Detallepedido;
import com.tortaza.app.models.Pedido;

public interface IDetalleService {
    public List<Detallepedido> listar();

    public Optional<Detallepedido> listarId(int id);

    

    public int guardar(List<Detallepedido> detalles);

    public void eliminar(int id);
}
