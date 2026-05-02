package com.duocuc.envios.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnvioTest {

    @Test
    void testGettersAndSetters() {
        Envio envio = new Envio();

        envio.setId(1L);
        envio.setProductoMascota("Vacunas");
        envio.setDestinatario("Roberto");
        envio.setEstado("TRANSITO");
        envio.setUbicacionActual("Santiago");

        assertAll(
                () -> assertEquals(1L, envio.getId()),
                () -> assertEquals("Vacunas", envio.getProductoMascota()),
                () -> assertEquals("Roberto", envio.getDestinatario()),
                () -> assertEquals("TRANSITO", envio.getEstado()),
                () -> assertEquals("Santiago", envio.getUbicacionActual())
        );
    }
}
