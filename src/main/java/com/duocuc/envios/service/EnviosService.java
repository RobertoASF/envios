package com.duocuc.envios.service;
import com.duocuc.envios.model.Envio;
import java.util.List;
import java.util.Optional;


public interface EnviosService{
    List<Envio> getAllEnvios();
    Optional<Envio> getEnvioById(Long id);
    Envio createEnvio(Envio Envio);
    Envio updateEnvio(Long id, Envio envio);
    void deleteEnvio(Long id);

}
