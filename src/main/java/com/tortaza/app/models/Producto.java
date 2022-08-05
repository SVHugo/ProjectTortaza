package com.tortaza.app.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;




//creando tabla desde spring

@Entity
public class Producto {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_producto;
    
    
    private Integer id_categoria;
   
    @NotBlank
    private String nombre;
    
    @NotBlank
    @Size(min = 0, max = 255, message="El valor no puede tener mas de 255 caracteres")
    private String descripcion;
    
    @NotNull
    @DecimalMax(value="9999.0",message="El valor no puede superar a 9999")
    @DecimalMin(value="0.0",message="El valor no puede ser menos que 0")
    private double precio;
    @NotNull
    @DecimalMax(value="9999",message="El valor no puede superar a 9999")
    @DecimalMin(value="0",message="El valor no puede ser menos que 0")
    private Integer Stock;
    
    
    private String imagen;
    
    
    private boolean estadoproducto;
    

    public Producto() {
    }

    

    public Producto(Integer id_producto, Integer id_categoria, String nombre, String descripcion,
			double precio, String imagen,Integer Stock, boolean estadoproducto) {
		super();
		this.id_producto = id_producto;
		this.id_categoria = id_categoria;
		
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.imagen = imagen;
		this.Stock=Stock;
		this.estadoproducto = estadoproducto;
	}



	public Integer getId_producto() {
        return id_producto;
    }

    public void setId_producto(Integer id_producto) {
        this.id_producto = id_producto;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }

 

  

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    //getEstadoproducto
    public boolean isEstadoproducto() {
        return estadoproducto;
    }

    public void setEstadoproducto(boolean estadoproducto) {
        this.estadoproducto = estadoproducto;
    }



	public String getImagen() {
		return imagen;
	}

	



	public void setImagen(String imagen) {
		this.imagen = imagen;
	}



	public Integer getStock() {
		return Stock;
	}



	public void setStock(Integer stock) {
		Stock = stock;
	}
	
	

    
    
}
