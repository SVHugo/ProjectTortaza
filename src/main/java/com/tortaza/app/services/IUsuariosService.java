package com.tortaza.app.services;

import java.util.List;
import java.util.Optional;

import com.tortaza.app.models.Usuario;

public interface IUsuariosService {
    public List<Usuario> listar();

    public abstract Usuario findById(Integer id);

    public boolean validU(Usuario u);

    public int guardar(Usuario u);

    public void eliminar(int id);
}
