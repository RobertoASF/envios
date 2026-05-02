package com.duocuc.envios.service;

import com.duocuc.envios.model.Envio;
import com.duocuc.envios.repository.EnviosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EnviosServiceImplTest {

    @Mock
    private EnviosRepository enviosRepository;

    @InjectMocks
    private EnviosServiceImpl enviosService;

    private Envio envio;

    @BeforeEach
    void setUp() {
        envio = new Envio();
        envio.setId(1L);
        envio.setProductoMascota("Vacunas");
        envio.setDestinatario("Roberto");
        envio.setEstado("TRANSITO");
        envio.setUbicacionActual("Santiago");
    }

    @Test
    void getAllEnviosReturnsRepositoryResults() {
        when(enviosRepository.findAll()).thenReturn(List.of(envio));

        List<Envio> result = enviosService.getAllEnvios();

        assertEquals(1, result.size());
        assertSame(envio, result.getFirst());
        verify(enviosRepository).findAll();
    }

    @Test
    void getEnvioByIdReturnsOptionalFromRepository() {
        when(enviosRepository.findById(1L)).thenReturn(Optional.of(envio));

        Optional<Envio> result = enviosService.getEnvioById(1L);

        assertTrue(result.isPresent());
        assertSame(envio, result.get());
        verify(enviosRepository).findById(1L);
    }

    @Test
    void createEnvioSavesEntity() {
        when(enviosRepository.save(envio)).thenReturn(envio);

        Envio result = enviosService.createEnvio(envio);

        assertSame(envio, result);
        verify(enviosRepository).save(envio);
    }

    @Test
    void updateEnvioSavesWhenIdExists() {
        Envio updatedEnvio = new Envio();
        updatedEnvio.setProductoMascota("Medicamentos");
        updatedEnvio.setDestinatario("Roberto");
        updatedEnvio.setEstado("COMPLETADA");
        updatedEnvio.setUbicacionActual("Valparaiso");

        when(enviosRepository.existsById(1L)).thenReturn(true);
        when(enviosRepository.save(updatedEnvio)).thenReturn(updatedEnvio);

        Envio result = enviosService.updateEnvio(1L, updatedEnvio);

        assertSame(updatedEnvio, result);
        assertEquals(1L, updatedEnvio.getId());
        verify(enviosRepository).existsById(1L);
        verify(enviosRepository).save(updatedEnvio);
    }

    @Test
    void updateEnvioReturnsNullWhenIdDoesNotExist() {
        when(enviosRepository.existsById(99L)).thenReturn(false);

        Envio result = enviosService.updateEnvio(99L, envio);

        assertNull(result);
        verify(enviosRepository).existsById(99L);
        verify(enviosRepository, never()).save(envio);
    }

    @Test
    void deleteEnvioDelegatesToRepository() {
        enviosService.deleteEnvio(1L);

        verify(enviosRepository).deleteById(1L);
    }
}
