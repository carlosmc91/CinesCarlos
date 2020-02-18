package com.example.cinescarlos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinescarlos.Beans.Entradas;
import com.example.cinescarlos.Beans.Usuarios;
import com.example.cinescarlos.SharedPreference.SharedPrefManager;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Historial extends AppCompatActivity {

    public RecyclerView recyclerView;
    public AdaptadorEntradas adapter;
    private Usuarios usuario;

    public List<Entradas> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        //RecyclerView
        recyclerView = findViewById(R.id.myRecyclerView1);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        CargarEntradas();
    }

    public void CargarEntradas() {
        usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario();

        Call<List<Entradas>> call = WebService.getInstance().createService(WebServiceApi.class).buscarEntradas(usuario);
        call.enqueue(new Callback<List<Entradas>>() {
            @Override
            public void onResponse(Call<List<Entradas>> call, Response<List<Entradas>> response) {
                list = response.body();
                for(Entradas entradas: list){
                    adapter = new AdaptadorEntradas(Historial.this, list);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Entradas>> call, Throwable t) {

            }
        });

    }


    public void onBackPressed(){

        Intent menuPrincipal = new Intent(Historial.this, Principal.class);
        startActivity(menuPrincipal);

    }

}


