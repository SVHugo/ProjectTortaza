package com.tortaza.app.services;

import java.util.List;
import java.util.Optional;

import com.tortaza.app.models.Pedido;

public interface IPedidoService {

    public List<Pedido> listar();

    public Optional<Pedido> listarId(int id);

    public int guardar(Pedido pedido);

    public void eliminar(int id);
}
