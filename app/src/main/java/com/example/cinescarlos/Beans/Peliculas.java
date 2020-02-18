package com.example.cinescarlos.Beans;

public class Peliculas {

    private Long id;
    private String nombre, descripcion, imagen, trailer, tematica;

    public Peliculas(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Peliculas(Long id, String nombre, String descripcion, String imagen, String trailer, String tematica) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.trailer = trailer;
        this.tematica = tematica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getTematica() {
        return tematica;
    }

    public void setTematica(String tematica) {
        this.tematica = tematica;
    }
}

