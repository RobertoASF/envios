package com.duocuc.envios.controller;

import com.duocuc.envios.model.Envio;
import com.duocuc.envios.service.EnviosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EnvioControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EnviosService enviosService;

    @InjectMocks
    private EnvioController envioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(envioController).build();
    }

    @Test
    void getAllEnviosReturnsOk() throws Exception {
        when(enviosService.getAllEnvios()).thenReturn(List.of(buildEnvio(1L, "TRANSITO")));

        mockMvc.perform(get("/envios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].estado").value("TRANSITO"));
    }

    @Test
    void getEnvioByIdReturnsOkWhenFound() throws Exception {
        when(enviosService.getEnvioById(1L)).thenReturn(Optional.of(buildEnvio(1L, "TRANSITO")));

        mockMvc.perform(get("/envios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.destinatario").value("Roberto"));
    }

    @Test
    void getEnvioByIdReturnsNotFoundWhenMissing() throws Exception {
        when(enviosService.getEnvioById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/envios/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createEnvioReturnsCreated() throws Exception {
        when(enviosService.createEnvio(any(Envio.class))).thenReturn(buildEnvio(2L, "PENDIENTE"));

        mockMvc.perform(post("/envios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validEnvioJson("PENDIENTE")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.destinatario").value("Roberto"));
    }

    @Test
    void updateEnvioReturnsOkWhenUpdated() throws Exception {
        when(enviosService.updateEnvio(eq(1L), any(Envio.class))).thenReturn(buildEnvio(1L, "COMPLETADA"));

        mockMvc.perform(put("/envios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validEnvioJson("COMPLETADA")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("COMPLETADA"));
    }

    @Test
    void updateEnvioReturnsNotFoundWhenMissing() throws Exception {
        when(enviosService.updateEnvio(eq(99L), any(Envio.class))).thenReturn(null);

        mockMvc.perform(put("/envios/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validEnvioJson("PENDIENTE")))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteEnvioReturnsNoContentWhenFound() throws Exception {
        when(enviosService.getEnvioById(1L)).thenReturn(Optional.of(buildEnvio(1L, "TRANSITO")));

        mockMvc.perform(delete("/envios/1"))
                .andExpect(status().isNoContent());

        verify(enviosService).deleteEnvio(1L);
    }

    @Test
    void deleteEnvioReturnsNotFoundWhenMissing() throws Exception {
        when(enviosService.getEnvioById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/envios/99"))
                .andExpect(status().isNotFound());

        verify(enviosService, never()).deleteEnvio(99L);
    }

    @Test
    void rastrearEnvioReturnsTrackingDataWhenFound() throws Exception {
        when(enviosService.getEnvioById(1L)).thenReturn(Optional.of(buildEnvio(1L, "TRANSITO")));

        mockMvc.perform(get("/envios/1/rastreo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.estado").value("TRANSITO"))
                .andExpect(jsonPath("$.ubicacionActual").value("Santiago"));
    }

    @Test
    void rastrearEnvioReturnsNotFoundWhenMissing() throws Exception {
        when(enviosService.getEnvioById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/envios/99/rastreo"))
                .andExpect(status().isNotFound());
    }

    private static Envio buildEnvio(Long id, String estado) {
        Envio envio = new Envio();
        envio.setId(id);
        envio.setProductoMascota("Vacunas");
        envio.setDestinatario("Roberto");
        envio.setEstado(estado);
        envio.setUbicacionActual("Santiago");
        return envio;
    }

    private static String validEnvioJson(String estado) {
        return """
                {
                  "producto": "Vacunas",
                  "destinatario": "Roberto",
                  "estado": "%s",
                  "ubicacion_actual": "Santiago"
                }
                """.formatted(estado);
    }
}
