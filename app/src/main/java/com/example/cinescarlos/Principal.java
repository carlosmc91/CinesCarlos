package com.example.cinescarlos;


import android.content.Intent;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.app.AppCompatActivity;


public class Principal extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Slide fadeIn = new Slide();
        fadeIn.setDuration(Login.transationDuration);
        fadeIn.setInterpolator(new DecelerateInterpolator());

        getWindow().setEnterTransition(fadeIn);


    }

    public void ecartelera (View view){
        Intent principal = new Intent(Principal.this,Cartelera.class);
        startActivity(principal);
    }

    public void eareapersonal (View view){
        Intent area = new Intent(Principal.this,AreaPersonal.class);
        startActivity(area);
    }



    public void historial (View view){
        Intent historial = new Intent(Principal.this,Historial.class);
        startActivity(historial);
    }

    public void onBackPressed(){

    }
}
