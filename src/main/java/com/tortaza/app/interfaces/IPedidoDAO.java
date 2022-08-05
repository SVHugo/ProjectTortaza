package com.tortaza.app.interfaces;

import com.tortaza.app.models.Pedido;
import com.tortaza.app.models.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IPedidoDAO extends JpaRepository<Usuario,Integer> {
    
}
