package com.duocuc.envios.controller;

import com.duocuc.envios.model.Envio;
import com.duocuc.envios.service.EnviosService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/envios")
@CrossOrigin(origins = "*")
public class EnvioController {

    private static final Logger log = LoggerFactory.getLogger(EnvioController.class);


    @Autowired
    private EnviosService enviosService;


    @GetMapping
    public ResponseEntity<List<Envio>> getAllEnvios() {
        List<Envio> envios = enviosService.getAllEnvios();
        log.info("GET /envios");
        log.info("Retornando todas los envios registrados");
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envio> getEnvioById(@PathVariable Long id) {
        log.info("Iniciando búsqueda del envío con ID: {}", id);

        return enviosService.getEnvioById(id)
                .map(envio -> {
                    log.info("Envío encontrado exitosamente para el destinatario: {}", envio.getDestinatario());
                    return ResponseEntity.ok(envio);
                })
                .orElseGet(() -> {
                    log.warn("No se encontró ningún envío con el ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Envio> createEnvio(@Valid @RequestBody Envio envio){
        log.info("Recibida solicitud para registrar un nuevo envío a nombre de: {}", envio.getDestinatario());
        Envio nuevoEnvio = enviosService.createEnvio(envio);
        log.info("Envío creado exitosamente con ID: {}", nuevoEnvio.getId());
        return new ResponseEntity<>(nuevoEnvio, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envio> updateEnvio(@PathVariable Long id, @Valid @RequestBody Envio envio) {
        log.info("Iniciando actualización del envío con ID: {}", id);
        Envio actualizado = enviosService.updateEnvio(id, envio);

        if (actualizado != null){
            log.info("Envío con ID: {} actualizado correctamente al estado: {}", id, actualizado.getEstado());
            return ResponseEntity.ok(actualizado);
        } else {
            log.warn("No se pudo actualizar: El envío con ID: {} no existe en la base de datos", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnvio(@PathVariable Long id) {
        log.info("Solicitud recibida para eliminar el envío con ID: {}", id);

        if (enviosService.getEnvioById(id).isPresent()) {
            enviosService.deleteEnvio(id);
            log.info("Envío con ID: {} eliminado exitosamente", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Fallo al eliminar: No se encontró el envío con ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/rastreo")
    public ResponseEntity<Map<String, String>> rastrearEnvio(@PathVariable Long id) {
        log.info("Iniciando rastreo detallado para el envio con ID: {}", id);

        return enviosService.getEnvioById(id)
                .map(envio -> {
                    log.info("Envío ID: {} encontrado. Estado: {}. Ubicación actual: {}",
                            id, envio.getEstado(), envio.getUbicacionActual());

                    Map<String, String> rastreo = new HashMap<>();
                    rastreo.put("id", envio.getId().toString());
                    rastreo.put("estado", envio.getEstado());
                    rastreo.put("ubicacionActual", envio.getUbicacionActual());

                    return ResponseEntity.ok(rastreo);
                })
                .orElseGet(() -> {
                    log.warn("No se pudo realizar el rastreo: El envío con ID {}, no existe en la base de datos", id);
                    return ResponseEntity.notFound().build();
                });
    }
}