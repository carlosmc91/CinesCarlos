 package com.example.cinescarlos;

 import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinescarlos.Beans.Peliculas;
import com.example.cinescarlos.Beans.Sesiones;
import com.example.cinescarlos.Beans.Usuarios;
import com.example.cinescarlos.SharedPreference.SharedPrefManager;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class Butacas extends AppCompatActivity implements View.OnClickListener {

     TextView txPeli, txCine, txPrecio, txHora, txFecha;
     ViewGroup layout;
     public String asientos;
     List<TextView> ArrayAsientos = new ArrayList<>();
     int tamañoasientos = 80;
     int espacioentreasientos = 10;
     int ASIENTO_DISPONIBLE = 1;
     int ASIENTO_OCUPADO = 2;
     String selectedIds = "";
     String numero="";
     String nu="";
     String cine;
     String hora;
     String fecha;
     String titulo;
     String cines;
     Long peliculaId;
     Long idsesion;
     int n= 0;
     int nentradas = 0;
     StringBuilder variable;
     private Sesiones sesion;
     private Peliculas peliculas;
     private Usuarios usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butacas);

        cine = getIntent().getStringExtra("cine");
        hora = getIntent().getStringExtra("hora");
        fecha = getIntent().getStringExtra("fecha");
        titulo = getIntent().getStringExtra("titulo");
        peliculaId = getIntent().getLongExtra("peliculaId", -1);
        cines = cine.trim();

        txCine = (TextView)findViewById(R.id.txtCine);
        txPeli = (TextView)findViewById(R.id.txPeli);
        txFecha = (TextView)findViewById(R.id.txtFecha);
        txPrecio = (TextView)findViewById(R.id.txtPrecio);
        txHora = (TextView)findViewById(R.id.txtCorreo);

        txCine.setText("Cine: " + cine);
        txPeli.setText(titulo);
        txHora.setText("Sesión: "+ hora);
        txFecha.setText("Fecha: " + fecha);

        sesion = new Sesiones();
        sesion.setCine(cine);
        sesion.setHora(hora);
        sesion.setPeliculaId(SharedPrefManager.getInstance(Butacas.this).getPelicula().getId());
        sesion.setFecha(fecha);

        setUpPelicula();


        ComprobarButacas();
    }

    private void setUpPelicula(){
        peliculas = SharedPrefManager.getInstance(getApplicationContext()).getPelicula();
        usuario = SharedPrefManager.getInstance(getApplicationContext()).getUsuario();
        txPeli.setText(peliculas.getNombre());
    }

    public void ComprobarButacas(){
        Call<Sesiones> call = WebService.getInstance().createService(WebServiceApi.class).buscarSesion(sesion);
        call.enqueue(new Callback<Sesiones>() {
            @Override
            public void onResponse(Call<Sesiones> call, Response<Sesiones> response) {
                if(response.code() == 200){
                    Sesiones sesion = response.body();
                    asientos = sesion.getAsientos();
                    idsesion = sesion.getId(idsesion);
                    CargarAsientos(asientos);

                }else if(response.code() == 404){
                    asientos= "_AAAAAAAAAAAAAAA_/"
                            + "_________________/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAAAAAAA__AA/"
                            + "AA__AAAA_AAAA__AA/"
                            + "AA__AAAA_AAAA__AA/"
                            + "AA__AAAA_AAAA__AA/"
                            + "AA__AAAA_AAAA__AA/";
                    CargarAsientos(asientos);
                }else{
                    Toast.makeText(Butacas.this, "Error desconocido", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sesiones> call, Throwable t) {
                Log.d("TAG1 Error: ", t.getMessage());
            }
        });
    }

    public void CargarAsientos(String asientos){

        layout = findViewById(R.id.layoutSeat);
        asientos = "/" + asientos;

        LinearLayout layoutSeat = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutSeat.setOrientation(LinearLayout.VERTICAL);
        layoutSeat.setLayoutParams(params);
        layoutSeat.setPadding(8 * espacioentreasientos, 8 * espacioentreasientos, 8 * espacioentreasientos, 8 * espacioentreasientos);
        layout.addView(layoutSeat);

        LinearLayout layout = null;

        int count = 0;
        int posi = 0;

        for (int index = 0; index < asientos.length(); index++) {
            if (asientos.charAt(index) == '/') {
                posi++;
                layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);
                layoutSeat.addView(layout);
            } else if (asientos.charAt(index) == 'U') {
                posi++;
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamañoasientos, tamañoasientos);
                layoutParams.setMargins(espacioentreasientos, espacioentreasientos, espacioentreasientos, espacioentreasientos);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * espacioentreasientos);
                view.setId(count);
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_b);
                view.setTextColor(Color.WHITE);
                view.setContentDescription(posi+"");
                view.setTag(ASIENTO_OCUPADO);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                layout.addView(view);
                ArrayAsientos.add(view);
                view.setOnClickListener(this);
            } else if (asientos.charAt(index) == 'A') {
                posi++;
                count++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamañoasientos, tamañoasientos);
                layoutParams.setMargins(espacioentreasientos, espacioentreasientos, espacioentreasientos, espacioentreasientos);
                view.setLayoutParams(layoutParams);
                view.setPadding(0, 0, 0, 2 * espacioentreasientos);
                view.setId(count);
                view.setContentDescription(posi+"");
                view.setGravity(Gravity.CENTER);
                view.setBackgroundResource(R.drawable.ic_seats_booked);
                view.setText(count + "");
                view.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 9);
                view.setTextColor(Color.BLACK);
                view.setTag(ASIENTO_DISPONIBLE);
                layout.addView(view);
                ArrayAsientos.add(view);
                view.setOnClickListener(this);
            } else if (asientos.charAt(index) == '_') {
                posi++;
                TextView view = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(tamañoasientos, tamañoasientos);
                layoutParams.setMargins(espacioentreasientos, espacioentreasientos, espacioentreasientos, espacioentreasientos);
                view.setLayoutParams(layoutParams);
                view.setBackgroundColor(Color.TRANSPARENT);
                view.setText("");
                layout.addView(view);
            }
            }
        }


     @Override
     public void onClick(View view) {

        variable = new StringBuilder(asientos);

         if ((int) view.getTag() == ASIENTO_DISPONIBLE) {
             if (selectedIds.contains(view.getId() + ",")) {
                 selectedIds = selectedIds.replace(+view.getId() + ",", "");
                 view.setBackgroundResource(R.drawable.ic_seats_booked);
                 nentradas = selectedIds.split(",").length;
                 txPrecio.setText("Precio: " +nentradas*6+ "€");
                 if(selectedIds.equals("")){
                     txPrecio.setText("Precio: " +0+ "€");
                 }
                 nu = (String) view.getContentDescription();
                 n=Integer.parseInt(nu);
                 variable.setCharAt(n-2, 'A');
                 asientos = variable.toString();
                 nu= "";
                 n=0;

             } else {

                 selectedIds = selectedIds + view.getId() + ",";
                 view.setBackgroundResource(R.drawable.ic_seats_book);
                 nentradas = selectedIds.split(",").length;
                 numero = (String) view.getContentDescription();
                 txPrecio.setText("Precio: " +nentradas*6+ "€");
                 n = Integer.parseInt(numero);
                 variable.setCharAt(n-2, 'p');
                 asientos = variable.toString();
                 numero= "";
                 n=0;

             }
         } else if ((int) view.getTag() == ASIENTO_OCUPADO) {
             Toast.makeText(this, "Asiento " + view.getId() + " ocupado", Toast.LENGTH_SHORT).show();
         }
     }

     @Override
     public void onPointerCaptureChanged(boolean hasCapture) {

     }

     public void Confirmarbutacas(View view){
        if(selectedIds.equals("")){
            Toast.makeText(this,"Debe seleccionar alguna butaca", Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this,CompraFinal.class);
            intent.putExtra("Cine", cine);
            intent.putExtra("Hora", hora);
            intent.putExtra("Fecha", fecha);
            intent.putExtra("Butacas", selectedIds);
            intent.putExtra("Cadena", (Serializable) variable);
            intent.putExtra("Precio", txPrecio.getText().toString());
            intent.putExtra("Idsesion", idsesion);
            this.startActivity(intent);
        }
     }


 }


