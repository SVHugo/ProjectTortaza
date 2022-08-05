package com.tortaza.app.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rols")
public class Rol implements Serializable{
    //
    private static final long serialVersionUID = 1L;
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rolId;
    @Column
    private String rol;
    
    @ManyToMany(mappedBy = "itemsRoles")
    private Set<Usuario> itemsUsuarios = new HashSet<>();
    
    public Rol() {
        // TODO Auto-generated constructor stub
    }
    public Rol(Integer rolId, String rol) {
        super();
        this.rolId = rolId;
        this.rol = rol;
    }
    public Integer getRolId() {
        return rolId;
    }
    public void setRolId(Integer rolId) {
        this.rolId = rolId;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    
}
