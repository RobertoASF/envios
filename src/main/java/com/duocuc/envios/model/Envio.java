package com.duocuc.envios.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.hateoas.RepresentationModel;


@Entity
@Table(name = "ENVIO")
@JsonPropertyOrder({"id", "producto", "destinatario", "estado", "ubicacionActual", "estado"})
public class Envio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "el nombre del producto no pouede ir en blanco")
    @Size(min = 3, max = 100)
    @Column(name = "PRODUCTO")
    @JsonProperty("producto")
    private String productoMascota;

    @NotBlank(message = "El destinbatario no puede ir en blanco")
    @Size(min = 3, max = 100)
    @Column(name = "DESTINATARIO")
    @JsonProperty("destinatario")
    private String destinatario;

    @NotBlank(message = "se debe indicar un estado")
    @Pattern(regexp = "PENDIENTE|COMPLETADA|TRANSITO|CANCELADO", message = "Estado no válido")
    // aqui agregamos una validacion para controlar los datos que entrean
    @Column(name = "ESTADO")
    @JsonProperty("estado")
    private String estado;

    @Column(name = "UBICACION_ACTUAL")
    @JsonProperty("ubicacion_actual")
    private String ubicacionActual;

    public Long getId() {return id;}
    public void setId(Long id) { this.id = id; }

    public String getProductoMascota() { return productoMascota;}
    public void setProductoMascota(String productoMascota) { this.productoMascota = productoMascota; }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacionActual() {
        return ubicacionActual;
    }

    public void setUbicacionActual(String ubicacionActual) {
        this.ubicacionActual = ubicacionActual;
    }
}
