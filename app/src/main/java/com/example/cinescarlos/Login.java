package com.example.cinescarlos;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Login extends AppCompatActivity {

    private EditText txtPass, txtEmail;
    private TextView txtLogin;
    private ObjectAnimator AmimatorLog;
    private long animatorDuration = 3500;
    private Button botonentradas;
    private AnimatorSet animatorSet;
    public static final long transationDuration = 700;
    private Usuarios usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        animatorSet = new AnimatorSet();

        Typeface tipo = Typeface.createFromAsset(getAssets(), "fuentes/neon.otf");
        botonentradas = (Button)findViewById(R.id.registroususario);
        botonentradas.setTypeface(tipo);

        txtPass = (EditText) findViewById(R.id.txtPass);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtLogin.setTypeface(tipo);
        AnimacionImagen();

    }

    public void Entradas(View view) {

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
        login();

    }

    private void login(){
        Call<List<Usuarios>> call = WebService.getInstance().createService(WebServiceApi.class).login(usuario);
        call.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
                if(response.code() == 200){
                    Toast.makeText(Login.this, "Usuario logueado", Toast.LENGTH_SHORT).show();
                    SharedPrefManager.getInstance(getApplicationContext()).saveUsuario(response.body().get(0)); //Guardo el logueo en sharedPreference
                    startActivity(new Intent(getApplicationContext(), Principal.class));

                }else if(response.code() == 404){
                    Toast.makeText(Login.this, "Error en usuario o contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Login.this, "Error desconocido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                Log.d("TAG1 Error: ", t.getMessage().toString());
            }
        });
    }


        public void AnimacionImagen(){
        AmimatorLog=ObjectAnimator.ofFloat(txtLogin,View.ALPHA, 0.0f,1.0f);
        AmimatorLog.setDuration(animatorDuration);
        animatorSet.playTogether(AmimatorLog);
        animatorSet.start();
    }

}