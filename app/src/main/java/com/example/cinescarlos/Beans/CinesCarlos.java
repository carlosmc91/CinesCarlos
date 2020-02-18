package com.example.cinescarlos.Beans;

public class CinesCarlos {

    //        "horario": "18:30 - 22:30",
    //        "idCine": 1,
    //        "imagen": "http://josper.com/wp-content/uploads/2018/07/Sambil_5.jpg",
    //        "nombre": "CineCarlos Coslada",
    //        "telefono": "912574742"
    private Long id;
    private String nombre, horario, imagen;
    private float latitud, longitud;
    private int telefono;

    public CinesCarlos() {
    }

    public CinesCarlos(Long id, String nombre, String horario, String imagen, float latitud, float longitud, int telefono) {
        this.id = id;
        this.nombre = nombre;
        this.horario = horario;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.telefono = telefono;
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

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}