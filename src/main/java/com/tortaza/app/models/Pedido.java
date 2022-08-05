package com.tortaza.app.models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    private boolean estadopedido;
    private boolean estadopago;
    private String direccioncliente;
    private Date fecha;
    private double total;
    @OneToMany(mappedBy = "pedido")
	private List<Detallepedido> detalle;

    public Pedido() {
    }
    public Pedido(Integer id_pedido, Usuario usuario, boolean estadopedido, boolean estadopago, String direccioncliente,
            Date fecha, double total, List<Detallepedido> detalle) {
        this.id_pedido = id_pedido;
        this.usuario = usuario;
        this.estadopedido = estadopedido;
        this.estadopago = estadopago;
        this.direccioncliente = direccioncliente;
        this.fecha = fecha;
        this.total = total;
        this.detalle = detalle;
    }
    public Integer getId_pedido() {
        return id_pedido;
    }
    public void setId_pedido(Integer id_pedido) {
        this.id_pedido = id_pedido;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public boolean isEstadopago() {
        return estadopago;
    }
    public void setEstadopago(boolean estadopago) {
        this.estadopago = estadopago;
    }
    public String getDireccioncliente() {
        return direccioncliente;
    }
    public void setDireccioncliente(String direccioncliente) {
        this.direccioncliente = direccioncliente;
    }
    public Date getFecha() {
        return fecha;
    }
    public boolean isEstadopedido() {
        return estadopedido;
    }
    public void setEstadopedido(boolean estadopedido) {
        this.estadopedido = estadopedido;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
    public List<Detallepedido> getDetalle() {
        return detalle;
    }
    public void setDetalle(List<Detallepedido> detalle) {
        this.detalle = detalle;
    }
}
