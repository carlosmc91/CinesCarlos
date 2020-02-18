package com.example.cinescarlos.api;

import com.example.cinescarlos.Beans.CinesCarlos;
import com.example.cinescarlos.Beans.Entradas;
import com.example.cinescarlos.Beans.Peliculas;
import com.example.cinescarlos.Beans.Sesiones;
import com.example.cinescarlos.Beans.Usuarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceApi {

    //USUARIOS

    @POST("crearusuario")
    Call<Void> registrarUsuario(@Body Usuarios usuario);

    @POST("login")
    Call<List<Usuarios>> login(@Body Usuarios usuario);

    @DELETE("entity.usuarios/{id}") //Método para borrar, no le voy a usar pero le dejo para información
    Call<Void> deleteById(@Path("id") Long id );

    @PUT("actualizarusuario")
    Call<Usuarios> update(@Body Usuarios usuario);

    //CINES

    @GET("cines")
    Call<List<CinesCarlos>> getCines();

    @GET("descripcioncine/{id}")
    Call<CinesCarlos> obtenercine(@Path("id") Long cin);

    //PELICULAS
    @GET("peliculas")
    Call<List<Peliculas>> obtenerPeliculas();

    //CINESPELICULAS
    @POST("peliculas_cines")
    Call<List<CinesCarlos>> verCinesPeliculas(@Body Peliculas peliculas);

    @GET("descripcionpelicula/{id}")
    Call<Peliculas> verPelicula(@Path("id") Long id);

    //SESIONES
    @POST("comprobarasientos")
    Call<Sesiones> buscarSesion(@Body Sesiones sesiones);

    @POST("crear_sesion")
    Call<Void> crearSesion(@Body Sesiones sesiones);

    @PUT("actualizarsesion/{id}")
    Call<Sesiones> update(@Path("id") Long id,
            @Body Sesiones sesiones);

    //ENTRADAS
    @POST("crear_entrada")
    Call<Void> crearEntrada(@Body Entradas entrada);

    @POST("entradas_usuario")
    Call<List<Entradas>> buscarEntradas(@Body Usuarios usuarios);

}
