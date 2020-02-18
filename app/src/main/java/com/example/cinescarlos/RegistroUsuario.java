package com.example.cinescarlos;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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


public class RegistroUsuario extends AppCompatActivity {

    private EditText txtPass, txtEmail;
    private TextView txtregistrousuarios, txtCuenta;
    private ProgressDialog progressDialog;
    private ObjectAnimator AmimatorLog;
    private long animatorDuration = 3500;
    private AnimatorSet animatorSet;

    private Usuarios usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        Typeface tipo = Typeface.createFromAsset(getAssets(), "fuentes/neon.otf");
        Button registrousuarios = (Button) findViewById(R.id.registroususario);
        registrousuarios.setTypeface(tipo);
        animatorSet = new AnimatorSet();


        txtPass = (EditText)findViewById(R.id.txtPass);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtCuenta = (TextView)findViewById(R.id.txtCuenta);
        txtregistrousuarios = (TextView)findViewById(R.id.txtregistousuarios);
        txtregistrousuarios.setTypeface(tipo);
        AnimacionImagen();

        txtCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }

    public void Registro (View view) {

        AudioPlay.playAudio(this,R.raw.laser);

        String email = txtEmail.getText().toString().trim(); //Con el trim elimino posibles espacios
        String pass = txtPass.getText().toString().trim();

        if(email.isEmpty()){ //Compruebo si está vacío el correo
            txtEmail.setError(getResources().getString(R.string.email_error));
            txtEmail.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            txtPass.setError(getResources().getString(R.string.password_error));
            txtPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){ //Compruebo si es un correo
            txtEmail.setError(getResources().getString(R.string.email_doesnt_match));
            txtEmail.requestFocus();
            return;
        }
        if(pass.length()<4){ //Compruebo si la contraseña es menor de 4 caracteres
            txtPass.setError(getResources().getString(R.string.password_error_less_than));
            txtPass.requestFocus();
            return;
        }

        //Creo el objeto usuario si todo es correcto

        usuario = new Usuarios();
        usuario.setCorreo(email);
        usuario.setPassword(pass);

        //Llamo al método para meter en base de datos
        crearUsuario();
    }

    public void crearUsuario(){

        Call<Void> call = WebService.getInstance().createService(WebServiceApi.class).registrarUsuario(usuario);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 201){
                    Toast.makeText(RegistroUsuario.this, "Usuario guardado correctamente", Toast.LENGTH_SHORT).show();

                }else if(response.code() == 409){
                    Toast.makeText(RegistroUsuario.this, "Usuario ya existente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistroUsuario.this, "Error no definido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("TAG1 Error: ", t.getMessage().toString());

            }
        });


    }

    @Override
    protected void onStart(){ //Si está logueado lo paso directamente a principal
        super.onStart();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            startActivity(new Intent(getApplicationContext(),Principal.class));
        }
    }


    public void AnimacionImagen(){
        AmimatorLog=ObjectAnimator.ofFloat(txtregistrousuarios, View.ALPHA, 0.0f,1.0f);
        AmimatorLog.setDuration(animatorDuration);
        animatorSet.playTogether(AmimatorLog);
        animatorSet.start();
    }


}
