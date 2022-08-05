package com.tortaza.app.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_usuario;
   
    @NotBlank
    @Size(min = 6, max = 12,message = "este campo solo acepta 6 a 12 digitos")
    private String contrasena;
    
    @NotBlank
    @Size(max=48,message = "este campo solo acepta menos de 48 digitos")
    private String nombre;

    @NotBlank
    @Size(max=48,message = "este campo solo acepta menos de 48 digitos")
    private String apellido;
    
    @NotBlank
    @Size(max=100,message = "este campo solo acepta menos de 100 digitos")
    private String direccion_inicial;
    
    @NotNull 
    @DecimalMax(value="99999999",message="El valor no puede superar los 8 digitos")
    @DecimalMin(value="10000000",message="El valor debe tener 8 digitos")
    @Column(name="dni",unique = true,nullable = false)
    private Integer dni;
    
    @Size(max= 50)
    @NotBlank
    @Pattern(regexp="^[a-z0-9._%-]+@[a-z0-9.-]+\\.[a-z]{2,4}", message = "No se permite mayusculas")
    @Email(message = "El valor debe de ser prueba@example.com como ejemplo")
    @Column(name="correo",unique = true,nullable = false)
    private String correo;
   
    private Boolean estadousuario;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "usuario")
    private List<Pedido> pedido;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_users",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Rol> itemsRoles = new HashSet<>(); //HASHSET evita duplicados

    public Usuario() {
        super();
    }

    public Usuario(Integer id_usuario, String contrasena, String nombre, String apellido,
            String direccion_inicial, Integer dni, String correo, Boolean estadousuario, List<Pedido> pedido) {
        this.id_usuario = id_usuario;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion_inicial = direccion_inicial;
        this.dni = dni;
        this.correo = correo;
        this.estadousuario = estadousuario;
        this.pedido = pedido;
    }
    public void addRole(Rol role) {
        this.itemsRoles.add(role);    
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getId_usuario() {
        return id_usuario;
    }
    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }
    public String getContrasena() {
        return contrasena;
    }
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getDireccion_inicial() {
        return direccion_inicial;
    }
    public void setDireccion_inicial(String direccion_inicial) {
        this.direccion_inicial = direccion_inicial;
    }
    public Integer getDni() {
        return dni;
    }
    public void setDni(Integer dni) {
        this.dni = dni;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public Boolean getEstadousuario() {
        return estadousuario;
    }
    public void setEstadousuario(Boolean estadousuario) {
        this.estadousuario = estadousuario;
    }
    public List<Pedido> getPedido() {
        return pedido;
    }
    public void setPedido(List<Pedido> pedido) {
        this.pedido = pedido;
    }
	public Set<Rol> getItemsRoles() {
		return itemsRoles;
	}
	public void setItemsRoles(Set<Rol> itemsRoles) {
		this.itemsRoles = itemsRoles;
	}
    

}
