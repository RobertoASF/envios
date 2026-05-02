package com.duocuc.envios.repository;

import com.duocuc.envios.model.Envio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EnviosRepositoryTest {

    @Autowired
    private EnviosRepository enviosRepository;

    @Test
    void testSaveAndFindById() {
        Envio envio = new Envio();
        envio.setProductoMascota("Vacunas");
        envio.setDestinatario("Roberto");
        envio.setEstado("TRANSITO");
        envio.setUbicacionActual("Santiago");

        Envio savedEnvio = enviosRepository.save(envio);
        Optional<Envio> foundEnvio = enviosRepository.findById(savedEnvio.getId());

        assertTrue(foundEnvio.isPresent());
        assertEquals("Roberto", foundEnvio.get().getDestinatario());
        assertEquals("TRANSITO", foundEnvio.get().getEstado());
    }
}
