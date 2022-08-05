package com.tortaza.app.service;

import java.util.List;
import java.util.Optional;

import com.tortaza.app.models.Usuario;

import com.tortaza.app.services.IUsuarios;
import com.tortaza.app.services.IUsuariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements IUsuariosService {

    @Autowired
    private IUsuarios dato;

    @Override
    public List<Usuario> listar() {
        return (List<Usuario>) dato.findAll();
    }

    @Override
    public boolean validU(Usuario u) {
        boolean ins = false;
        for (var us : dato.findAll()) {
            if (u.getCorreo().equals(us.getCorreo()) && u.getContrasena().equals(us.getContrasena())) {
                return true;
            }
        }
        return ins;
    }

    @Override
    public int guardar(Usuario u) {
    	int res =0;
        Usuario usuario = dato.save(u);// aqui se guarda al usuario
        if (!usuario.equals(null)) {
            res = 1;
        }
        return res;

    }

    @Override
    public void eliminar(int id) {
        // TODO Auto-generated method stub

    }

	@Override
	@Transactional(readOnly = true)
	public Usuario findById(Integer id) {
		// TODO Auto-generated method stub
		return dato.findById(id).orElse(null);
	}

}
