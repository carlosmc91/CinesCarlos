package com.example.cinescarlos.Beans;

public class Sesiones {

    private Long id;

    private String fecha;

    private String hora;

    private String asientos;

    private String cine;

    private Long peliculaId;

    public Sesiones() {
    }

    public Sesiones(Long id, String fecha, String hora, String asientos, String cine, Long peliculaId) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.asientos = asientos;
        this.cine = cine;
        this.peliculaId = peliculaId;
    }

    public Long getId(Long idsesion) {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHora() {
        return hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getAsientos() {
        return asientos;
    }

    public void setAsientos(String asientos) {
        this.asientos = asientos;
    }

    public String getCine() {
        return cine;
    }

    public void setCine(String cine) {
        this.cine = cine;
    }

    public Long getPeliculaId() {
        return peliculaId;
    }

    public void setPeliculaId(Long peliculaId) {
        this.peliculaId = peliculaId;
    }
}
