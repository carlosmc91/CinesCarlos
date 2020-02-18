package com.example.cinescarlos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinescarlos.Beans.Usuarios;
import com.example.cinescarlos.SharedPreference.SharedPrefManager;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AreaPersonal extends AppCompatActivity {
    int id;
    private EditText creditCard, etnombre, etapellido, etphone;
    private TextView etcorreo;
    private Button btnactualizar;

    private Usuarios usuario;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_personal);
        creditCard = (EditText) findViewById(R.id.CreditCard);
        etnombre = (EditText) findViewById(R.id.etnombre);
        etapellido = (EditText) findViewById(R.id.etapellido);
        etphone = (EditText) findViewById(R.id.etphone);
        btnactualizar = (Button) findViewById(R.id.btnactualizar);
        etcorreo = (TextView) findViewById(R.id.etcorreo);

        recogerUsuario();

    }

    private void recogerUsuario(){
        usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario();
        rellenarUsuario();
    }

    private void rellenarUsuario(){
        creditCard.setText(usuario.getTarjeta());
        etnombre.setText(usuario.getNombre());
        etapellido.setText(usuario.getApellido());
        etphone.setText(usuario.getTelefono().toString());
        etcorreo.setText(usuario.getCorreo());


        btnactualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarusuario();
            }
        });

    }

    private void actualizarusuario(){

        String nombre =  etnombre.getText().toString().trim();
        String apellido = etapellido.getText().toString().trim();
        long telefono = Integer.parseInt(etphone.getText().toString().trim());
        String tarjeta = creditCard.getText().toString().trim();

        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);
        usuario.setTarjeta(tarjeta);

        Call<Usuarios> call = WebService.getInstance().createService(WebServiceApi.class).update(usuario);

        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if(response.code() == 200){
                    Toast.makeText(AreaPersonal.this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(getApplicationContext()).saveUsuario(response.body()); //Guardo el nuevo usuario en Shared
                    startActivity(new Intent(getApplicationContext(), Principal.class));
                }else{
                    Log.d("TAG1", "Error indeterminado");
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(),Principal.class));
        }
    }

    public void onBackPressed(){

        Intent menuPrincipal = new Intent(AreaPersonal.this, Principal.class);
        startActivity(menuPrincipal);

    }
}
