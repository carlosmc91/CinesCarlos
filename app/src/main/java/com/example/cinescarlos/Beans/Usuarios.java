package com.example.cinescarlos.Beans;

public class Usuarios {

    private String apellido, email, nombre, tarjeta, password;
    private Long telefono, id;

    public Usuarios() {
    }

    public Usuarios(String apellido, String correo, String nombre, String tarjeta, Long telefono, Long id) {
        this.apellido = apellido;
        this.email = correo;
        this.nombre = nombre;
        this.tarjeta = tarjeta;
        this.telefono = telefono;
        this.id = id;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return email;
    }

    public void setCorreo(String correo) {
        this.email = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
