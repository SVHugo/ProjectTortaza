package com.tortaza.app.models;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<ItemCarrito> items = new ArrayList<>();

    public List<ItemCarrito> getItems() {
        return items;
    }

    public void setItems(List<ItemCarrito> items) {
        this.items = items;
    }

    public void add(Producto p) {
        ItemCarrito item = new ItemCarrito();
        int cantidad = 1;
        if (p != null) {
            try {
                for (ItemCarrito elem : items) {
                    if (elem.getProducto().getId_producto() == p.getId_producto()) {
                        cantidad = elem.getCantidad();
                        cantidad++;
                        items.remove(elem);
                        System.out.println("< Producto > : " + elem.getNombre());
                        System.out.println("< Producto > : " + elem.getImagen());
                        System.out.println("< Cantidad > : " + cantidad);
                    }
                }
            } catch (Exception ex) {

            }
            item.setProducto(p);
            item.setCantidad(cantidad);
            items.add(item);
        }
    }

    public double getTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total = total + item.getTotal();
        }
        return total;
    }

    public void remove(Producto p) {
        if (p != null) {
            try {
                for (ItemCarrito elem : items) {
                    if (elem.getProducto().getId_producto() == p.getId_producto()) {
                        items.remove(elem);
                    }
                }
            } catch (Exception ex) {
            }
        }
    }

}
