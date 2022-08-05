package com.tortaza.app.models;

public class ItemCarrito {
	private Producto producto;
	private int cantidad;

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getNombre() {
		return this.getProducto().getNombre();
	}

	public String getImagen() {
		return this.getProducto().getImagen();
	}

	public int getId() {
		return this.getProducto().getId_producto();
	}

	public double getPrecio() {
		return this.getProducto().getPrecio();
	}

	public double getTotal() {
		return this.getPrecio() * this.getCantidad();
	}

}
