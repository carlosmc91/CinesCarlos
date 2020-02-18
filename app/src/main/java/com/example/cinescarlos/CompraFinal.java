package com.example.cinescarlos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinescarlos.Beans.Entradas;
import com.example.cinescarlos.Beans.Sesiones;
import com.example.cinescarlos.SharedPreference.SharedPrefManager;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompraFinal extends AppCompatActivity {

    String cine;
    String hora;
    String fecha;
    String butacas;
    TextView txCine, txPeli, txFecha, txPrecio, txButacas, txTarjeta, txHora;
    Long idsesion;
    String cadena;
    String cines;
    Button BtnQR;
    String precio;
    String nuevacadena;
    private Sesiones sesion;
    private Entradas entrada;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_final);

        cine = getIntent().getStringExtra("Cine");
        hora = getIntent().getStringExtra("Hora");
        fecha = getIntent().getStringExtra("Fecha");
        String butaca = getIntent().getStringExtra("Butacas");
        precio = getIntent().getStringExtra("Precio");
        cadena = getIntent().getStringExtra("Cadena");
        idsesion = getIntent().getLongExtra("Idsesion", 0);
        butacas = butaca.substring(0, butaca.length()-1);



        cines = cine.trim();

        txCine = (TextView) findViewById(R.id.txtCine);
        txPeli = (TextView) findViewById(R.id.txPeli);
        txFecha = (TextView) findViewById(R.id.txFecha);
        txPrecio = (TextView) findViewById(R.id.txtPrecio);
        txButacas = (TextView) findViewById(R.id.txButacas);
        txHora = (TextView) findViewById(R.id.txHora);
        txTarjeta = (TextView) findViewById(R.id.txTarjeta);
        BtnQR = (Button)findViewById(R.id.btnGenPDF);

        txCine.setText("Cine: " + cine);

        txHora.setText(hora);
        txFecha.setText(fecha);
        txPrecio.setText(precio);
        txTarjeta.setText("Número de tarjeta: " + SharedPrefManager.getInstance(CompraFinal.this).getUsuario().getTarjeta());
        txButacas.setText("Butacas: " + butacas);
        txPeli.setText(SharedPrefManager.getInstance(CompraFinal.this).getPelicula().getNombre());

        nuevacadena = cadena.replace('p','U');
    }

    public void Comprobardatos(View view){
        if(SharedPrefManager.getInstance(CompraFinal.this).getUsuario().getTarjeta() != null){
            ConfirmarCompra();
        }else{
            Toast.makeText(CompraFinal.this, "Debe actualizar los datos en Area Personal",Toast.LENGTH_SHORT).show();
            Intent areaPersonal = new Intent(CompraFinal.this, AreaPersonal.class);
            startActivity(areaPersonal);
        }
    }

    private void ConfirmarCompra(){

        sesion = new Sesiones();

        sesion.setPeliculaId(SharedPrefManager.getInstance(CompraFinal.this).getPelicula().getId());
        sesion.setCine(cine);
        sesion.setId(idsesion);
        sesion.setFecha(txFecha.getText().toString());
        sesion.setHora((String) txHora.getText());
        sesion.setAsientos(nuevacadena);

        if(idsesion== null || idsesion ==0){
            Call<Void> call = WebService.getInstance().createService(WebServiceApi.class).crearSesion(sesion);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(response.code() == 201){
                        Toast.makeText(CompraFinal.this, "Sesion creada correctamente", Toast.LENGTH_SHORT).show();
                        CrearEntrada();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CompraFinal.this, "Algo falla", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Call<Sesiones> call = WebService.getInstance().createService(WebServiceApi.class).update(idsesion, sesion);

            call.enqueue(new Callback<Sesiones>() {
                @Override
                public void onResponse(Call<Sesiones> call, Response<Sesiones> response) {
                    if(response.code() == 200){
                        CrearEntrada();
                    }else{
                        Log.d("TAG1", "Error al actualizar sesión");
                    }
                }

                @Override
                public void onFailure(Call<Sesiones> call, Throwable t) {
                    Log.d("TAG1", "Error indeterminado");
                }
            });

        }
    }

    private void CrearEntrada(){

        entrada = new Entradas();

        entrada.setAsientos(butacas);
        entrada.setCine(cine);
        entrada.setPeliculaId(SharedPrefManager.getInstance(CompraFinal.this).getPelicula().getId());
        entrada.setHora(hora);
        entrada.setUsuarioId(SharedPrefManager.getInstance(CompraFinal.this).getUsuario().getId());
        entrada.setFecha(fecha);

        Call<Void> call = WebService.getInstance().createService(WebServiceApi.class).crearEntrada(entrada);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.code() == 200){
                    Toast.makeText(CompraFinal.this, "Entrada Registrada", Toast.LENGTH_SHORT).show();
                    Intent historial = new Intent(CompraFinal.this, Historial.class);
                    startActivity(historial);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("TAG1", "Error indeterminado");
            }
        });

    }

    public void onBackPressed(){

        Intent menuPrincipal = new Intent(CompraFinal.this, Principal.class);
        startActivity(menuPrincipal);

    }




}


