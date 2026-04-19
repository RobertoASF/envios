package com.duocuc.envios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duocuc.envios.model.Envio;
import com.duocuc.envios.repository.EnviosRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EnviosServiceImpl implements EnviosService{
    @Autowired
    private EnviosRepository enviosRepository;

    @Override
    public List<Envio> getAllEnvios() {return enviosRepository.findAll();}

    @Override
    public Optional<Envio> getEnvioById(Long id) {return enviosRepository.findById(id);}

    @Override
    public Envio createEnvio(Envio envio) {return enviosRepository.save(envio);}

    @Override
    public Envio updateEnvio(Long id, Envio envio){
        if(enviosRepository.existsById(id)){
            envio.setId(id);
            return enviosRepository.save(envio);
        } else {
            return null;
        }

    }
    @Override
    public void deleteEnvio(Long id){
        enviosRepository.deleteById(id);
    }

}



