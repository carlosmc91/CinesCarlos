package com.example.cinescarlos;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinescarlos.Beans.CinesCarlos;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DescripcionCine extends AppCompatActivity {

    ImageView imagenCine;
    TextView txtHorario, txtTelefono, txtCinesCarlos;
    private CinesCarlos cines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_cine);

        imagenCine = (ImageView)findViewById(R.id.imagenCine);
        txtHorario = (TextView) findViewById(R.id.txtHorario);
        txtTelefono = (TextView)findViewById(R.id.txtTelefono);
        txtCinesCarlos = (TextView)findViewById(R.id.txtCinesCarlos);
        Bundle extras = getIntent().getExtras();
        Long cin = extras.getLong("cin");

        DescripcionCine(cin);


    }

    public void DescripcionCine(Long cin){

        Call<CinesCarlos> call = WebService.
                getInstance().
                createService(WebServiceApi.class)
                .obtenercine(cin);

        call.enqueue(new Callback<CinesCarlos>() {
            @Override
            public void onResponse(Call<CinesCarlos> call,
                                   Response<CinesCarlos> response) {
                if(response.isSuccessful()){
                    CinesCarlos cinesCarlos = response.body();
                   // for (CinesCarlos cinesCarlos : cinesCarlosList) {

                        txtCinesCarlos.setText(cinesCarlos.getNombre());
                        txtHorario.setText("Horario:     " + cinesCarlos.getHorario());
                        txtTelefono.setText("Teléfono de contacto:     " + cinesCarlos.getTelefono());
                        Picasso.get().load(cinesCarlos.getImagen()).into(imagenCine);

                    }

                }
            @Override
            public void onFailure(Call<CinesCarlos> call,
                                  Throwable t) {
                Toast.makeText(DescripcionCine.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
