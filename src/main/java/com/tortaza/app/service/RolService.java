package com.tortaza.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tortaza.app.models.Rol;
import com.tortaza.app.services.IRol;
import com.tortaza.app.services.IRolService;

@Service
public class RolService implements IRolService{
	@Autowired
	private IRol repository;

	@Override
	@Transactional(readOnly = true)
	public Rol findById(Integer Id) {
		// TODO Auto-generated method stub
		return repository.findById(Id).orElse(null);
	}

}
