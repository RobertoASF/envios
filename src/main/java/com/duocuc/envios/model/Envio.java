package com.duocuc.envios.model;

public class Envio {
    private int id;
    private String productoMascota;
    private String destinatario;
    private String estado;
    private String ubicacionActual;

    public Envio(int id, String productoMascota, String destinatario, String estado, String ubicacionActual) {
        if (id <= 0 || productoMascota == null || productoMascota.trim().isEmpty() || estado == null) {
            throw new IllegalArgumentException("Datos del envio incorrectos");
        }
        this.id = id;
        this.productoMascota = productoMascota;
        this.destinatario = destinatario;
        this.estado = estado;
        this.ubicacionActual = ubicacionActual;
    }

    public int getId() { return id; }
    public String getProductoMascota() { return productoMascota; }
    public String getDestinatario() { return destinatario; }
    public String getEstado() { return estado; }
    public String getUbicacionActual() { return ubicacionActual; }
}