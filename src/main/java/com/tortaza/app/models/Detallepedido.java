package com.tortaza.app.models;

import javax.persistence.*;

@Entity
public class Detallepedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_detalle;
    @ManyToOne
    @JoinColumn(name="id_pedido")
    private Pedido pedido;

    @OneToOne
    @JoinColumn(name="id_producto")
	private Producto producto;
    private Integer cantidad;
    private double subtotal;

    public Detallepedido() {
    }

    public Detallepedido(Integer id_detalle, Pedido pedido, Producto producto, Integer cantidad, double subtotal) {
        this.id_detalle = id_detalle;
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public Integer getId_detalle() {
        return id_detalle;
    }

    public void setId_detalle(Integer id_detalle) {
        this.id_detalle = id_detalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    
    

   
}
