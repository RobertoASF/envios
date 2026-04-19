package com.duocuc.envios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.duocuc.envios.model.Envio;


public interface EnviosRepository extends JpaRepository<Envio, Long>{
}