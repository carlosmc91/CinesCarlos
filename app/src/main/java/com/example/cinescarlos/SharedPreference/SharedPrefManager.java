package com.example.cinescarlos.SharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.cinescarlos.Beans.Peliculas;
import com.example.cinescarlos.Beans.Usuarios;

public class SharedPrefManager {
    private static final String SHARED_PREFERENCES = "SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCES_NOMBRE = "SHARED_PREFERENCES_NOMBRE";
    private static final String SHARED_PREFERENCES_APELLIDO = "SHARED_PREFERENCES_APELLIDO";
    private static final String SHARED_PREFERENCES_CORREO = "SHARED_PREFERENCES_CORREO";
    private static final String SHARED_PREFERENCES_TELEFONO = "SHARED_PREFERENCES_TELEFONO";
    private static final String SHARED_PREFERENCES_TARJETA = "SHARED_PREFERENCES_TARJETA";
    private static final String SHARED_PREFERENCES_ID = "SHARED_PREFERENCES_ID";

    private static final String SHARED_PREFERENCES_IDPELICULA = "SHARED_PREFERENCES_IDPELICULA";
    private static final String SHARED_PREFERENCES_NOMBREPELICULA = "SHARED_PREFERENCES_NOMBREPELICULA";


    private static SharedPrefManager instance;
    private Context context;
    private SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE); //Modo privado para que no accedan de terceros
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if(instance== null){
            instance = new SharedPrefManager(context);
        }
        return instance;
    }

    public void saveUsuario(Usuarios usuario){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SHARED_PREFERENCES_ID, usuario.getId());
        editor.putString(SHARED_PREFERENCES_NOMBRE, usuario.getNombre());
        editor.putString(SHARED_PREFERENCES_APELLIDO, usuario.getApellido());
        editor.putString(SHARED_PREFERENCES_CORREO, usuario.getCorreo());
        editor.putString(SHARED_PREFERENCES_TARJETA, usuario.getTarjeta());
        editor.putLong(SHARED_PREFERENCES_TELEFONO, usuario.getTelefono());
        editor.apply();
    }

    public void savePelicula(Peliculas pelicula){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SHARED_PREFERENCES_IDPELICULA, pelicula.getId());
        editor.putString(SHARED_PREFERENCES_NOMBREPELICULA, pelicula.getNombre());
        editor.apply();
    }

    public boolean isLoggedIn(){
        if(sharedPreferences.getLong(SHARED_PREFERENCES_ID, -1)!= -1){
            return true;
        }
        return false;
    }

    public Usuarios getUsuario(){
        Usuarios usuario = new Usuarios(
                sharedPreferences.getString(SHARED_PREFERENCES_APELLIDO, null),
                sharedPreferences.getString(SHARED_PREFERENCES_CORREO, null),
                sharedPreferences.getString(SHARED_PREFERENCES_NOMBRE, null),
                sharedPreferences.getString(SHARED_PREFERENCES_TARJETA, null),
                sharedPreferences.getLong(SHARED_PREFERENCES_TELEFONO, -1),
                sharedPreferences.getLong(SHARED_PREFERENCES_ID, -1)
        );

        return usuario;
    }

    public Peliculas getPelicula(){
        Peliculas pelicula = new Peliculas(
                sharedPreferences.getLong(SHARED_PREFERENCES_IDPELICULA, -1),
                sharedPreferences.getString(SHARED_PREFERENCES_NOMBREPELICULA, null)
        );
        return pelicula;
    }

    public void logOut(){ //Método para salir del Login de Usuario
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
