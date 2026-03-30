package com.duocuc.envios.controller;

import com.duocuc.envios.model.Envio;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/envios")
public class EnvioController {

    private final List<Envio> envios = new ArrayList<>();

    public EnvioController() {
        envios.add(new Envio(1, "Saco Alimento Premium 15kg", "Roberto Sánchez", "En Tránsito", "Centro de Distribución Macul"));
        envios.add(new Envio(2, "Arena Sanitaria Gatos 10kg", "John Dow", "Entregado", "Domicilio Cliente"));
        envios.add(new Envio(3, "Rascador Torre 3 Pisos", "Juan Perez", "Preparando", "Bodega Central"));
        envios.add(new Envio(4, "Correa 5m", "Lindorfo Vergara", "En Tránsito", "Ruta de reparto 4"));
        envios.add(new Envio(5, "Cama para perro XL", "Luis Migueles", "Retrasado", "Ruta de reparto 1"));
        envios.add(new Envio(6, "Juguete hueso Perro", "María Paz", "Entregado", "Domicilio Cliente"));
        envios.add(new Envio(7, "Fuente de Agua Gatos", "Diego Tapia", "En Tránsito", "Centro de Distribución Buin"));
        envios.add(new Envio(8, "Antiparasitario Externo", "Nadalina Franco", "Preparando", "Bodega Central"));
    }

    @GetMapping
    public ResponseEntity<List<Envio>> obtenerTodosLosEnvios() {
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerEnvioPorId(@PathVariable int id) {
        for (Envio envio : envios) {
            if (envio.getId() == id) {
                return ResponseEntity.ok(envio);
            }
        }

        Map<String, String> respuestaError = new HashMap<>();
        respuestaError.put("error", "No existe ningún envío registrado con el ID: " + id);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaError);
    }
}