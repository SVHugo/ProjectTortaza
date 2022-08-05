package com.tortaza.app.services;

import com.tortaza.app.models.*;

public class CarritoProducto {
    private static CarritoProducto instance;
    private Carrito carrito = new Carrito();

    public static CarritoProducto getInstance() {
        if (instance == null) {
            instance = new CarritoProducto();
        }
        return instance;
    }

    public static void setInstance(CarritoProducto instance) {
        CarritoProducto.instance = instance;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public void add(Producto p) {
        this.carrito.add(p);
    }

    public void remove(Producto p) {
        this.carrito.remove(p);
    }

}
