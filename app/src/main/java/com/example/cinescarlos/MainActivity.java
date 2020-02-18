package com.example.cinescarlos;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



import androidx.appcompat.app.AppCompatActivity;

import com.example.cinescarlos.SharedPreference.SharedPrefManager;


public class MainActivity extends AppCompatActivity {

    private ImageView ivRotation;
    private Button boton1,boton2,boton3;
    private ObjectAnimator animatorAll;
    private ObjectAnimator animatorX;
    private ObjectAnimator animatorRotation;
    private ObjectAnimator animatorBot1;
    private ObjectAnimator animatorBot2;
    private ObjectAnimator animatorBot3;


    private long animatorDuration = 3000;

    private AnimatorSet animatorSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animatorSet = new AnimatorSet();

        SharedPrefManager.getInstance(MainActivity.this).logOut();

        Typeface tipo = Typeface.createFromAsset(getAssets(), "fuentes/neon.otf");
        boton1 = (Button) findViewById(R.id.boton1);
        boton2 = (Button) findViewById(R.id.boton2);
        boton3 = (Button) findViewById(R.id.boton3);
        ivRotation = (ImageView) findViewById(R.id.imlogo);
        boton1.setTypeface(tipo);
        boton2.setTypeface(tipo);
        boton3.setTypeface(tipo);
        AudioPlay.Sonidoinicio(this,R.raw.transicion);

        AnimacionImagen();
        AnimacionBotones();


    }

    public void Mapa (View view){
        AudioPlay.Volumen(this,R.raw.laser);
        Intent mapa = new Intent(this,MapsActivity1.class);
        startActivity(mapa);
    }

    public void Registro (View view){
        AudioPlay.Volumen(this,R.raw.laser);
        Intent registro = new Intent(this,RegistroUsuario.class);
        startActivity(registro);

    }

    public void Login (View view){
        AudioPlay.Volumen(this,R.raw.laser);
        Intent login = new Intent(this,Login.class);
        startActivity(login);

    }

    public void AnimacionImagen(){
        animatorAll = ObjectAnimator.ofFloat(ivRotation, View.ALPHA, 0.0f,1.0f);
        animatorAll.setDuration(animatorDuration);
        animatorX = ObjectAnimator.ofFloat(ivRotation, "x", 500f);
        animatorX.setDuration(animatorDuration);
        animatorRotation=ObjectAnimator.ofFloat(ivRotation,"rotation",0f,360f);
        animatorRotation.setDuration(animatorDuration);
        animatorSet.playTogether(animatorRotation,animatorX,animatorAll);
        animatorSet.start();
    }

    public void AnimacionBotones(){
        animatorBot1 = ObjectAnimator.ofFloat(boton1, View.ALPHA, 0.0f,1.0f);
        animatorBot1.setDuration(animatorDuration);
        animatorBot2 = ObjectAnimator.ofFloat(boton2, View.ALPHA, 0.0f,1.0f);
        animatorBot2.setDuration(animatorDuration);
        animatorBot3 = ObjectAnimator.ofFloat(boton3, View.ALPHA, 0.0f,1.0f);
        animatorBot3.setDuration(animatorDuration);
        animatorSet.playTogether(animatorBot1,animatorBot2,animatorBot3);
        animatorSet.start();
    }



}








