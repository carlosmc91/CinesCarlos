package com.example.cinescarlos;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cinescarlos.Beans.CinesCarlos;
import com.example.cinescarlos.Beans.Peliculas;
import com.example.cinescarlos.SharedPreference.SharedPrefManager;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Compra extends AppCompatActivity {
    private static final String CERO = "0";
    private static final String BARRA = "-";
    private Spinner spinnercines;
    private TextView Titulopelicula,fecha;
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    ImageButton Obtenerfecha;
    Button Hora1,Hora2,Hora3;
    public static String cineelegido;
    public static String horaelegida=" ";
    public static String fechaelegida;
    public static String correo;
    public Long id, peliculaid;
    public ArrayList<String> elemento = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra);

        Bundle extras = getIntent().getExtras();
        String id1 = extras.getString("IdPelicula");
        id = Long.valueOf(id1);

        verPelicula();



        horaelegida=" ";
        elemento = new ArrayList<>();
        elemento.add("Seleccione cine");


        ArrayList<Integer> nentradas = new ArrayList<>();
        nentradas.add(1);
        nentradas.add(2);
        nentradas.add(3);
        nentradas.add(4);


        fecha = (TextView) findViewById(R.id.Fecha);
        Obtenerfecha = (ImageButton)findViewById(R.id.imgfecha);
        spinnercines = findViewById(R.id.spinnercines);
        Titulopelicula = findViewById(R.id.Titulopelicula);
        Hora1 = (Button)findViewById(R.id.Hora1);
        Hora2 = (Button)findViewById(R.id.Hora2);
        Hora3 = (Button)findViewById(R.id.Hora3);

        ArrayAdapter adp = new ArrayAdapter(Compra.this, android.R.layout.simple_spinner_dropdown_item, elemento);

        spinnercines.setAdapter(adp);

        spinnercines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String cine = (String) spinnercines.getAdapter().getItem(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void verPelicula(){
        Call<Peliculas> call = WebService.getInstance().createService(WebServiceApi.class).verPelicula(id);
        call.enqueue(new Callback<Peliculas>() {
            @Override
            public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                if(response.isSuccessful()){
                    Peliculas pel = response.body();
                    peliculaid = response.body().getId();
                    Titulopelicula.setText(pel.getNombre());
                    SharedPrefManager.getInstance(getApplicationContext()).savePelicula(pel); //Guardo la pelicula en sharedPreference

                    Call<List<CinesCarlos>> ca = WebService.getInstance().createService(WebServiceApi.class).verCinesPeliculas(pel);

                    ca.enqueue(new Callback<List<CinesCarlos>>() {
                        @Override
                        public void onResponse(Call<List<CinesCarlos>> call, Response<List<CinesCarlos>> response) {
                            if(response.code()==200){
                                for(int i =0; i<response.body().size(); i++){
                                    elemento.add(response.body().get(i).getNombre());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<CinesCarlos>> call, Throwable t) {
                            Log.d("TAG1", "NO HAY CINES");
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Peliculas> call, Throwable t) {

            }
        });


    }


    public void obtenerFecha(View view){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                fecha.setText(year + BARRA + mesFormateado + BARRA + diaFormateado);

            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual

        },dia, mes, anio);
        recogerFecha.getDatePicker().setMinDate(c.getTimeInMillis());
        recogerFecha.show();

    }


    public void Hora1(View view){
        Hora1.setBackgroundResource(R.drawable.botonentradas);
        Hora2.setBackgroundResource((R.drawable.botonhora));
        Hora3.setBackgroundResource((R.drawable.botonhora));
        horaelegida = Hora1.getText().toString();
    }
    public void Hora2(View view){
        Hora1.setBackgroundResource((R.drawable.botonhora));
        Hora2.setBackgroundResource((R.drawable.botonentradas));
        Hora3.setBackgroundResource((R.drawable.botonhora));
        horaelegida = Hora2.getText().toString();
    }
    public void Hora3(View view){
        Hora1.setBackgroundResource((R.drawable.botonhora));
        Hora2.setBackgroundResource((R.drawable.botonhora));
        Hora3.setBackgroundResource((R.drawable.botonentradas));
        horaelegida = Hora3.getText().toString();
    }

    public void Obtenervariables(View view){
        cineelegido = spinnercines.getSelectedItem().toString();
        fechaelegida= fecha.getText().toString();
        try {
            SeleccionButacas();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void SeleccionButacas() throws ParseException {
        String p = "Seleccione cine";
        if(cineelegido.equals(p)){
            Toast.makeText(this, "Debe seleccionar un cine", Toast.LENGTH_SHORT).show();
        }else if(fechaelegida.equals("")){
            Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show();
        }else if(horaelegida.equals(" ")){
            Toast.makeText(this, "Debe seleccionar una sesión", Toast.LENGTH_SHORT).show();
        }else{
            Intent butacas = new Intent(this, Butacas.class);
            butacas.putExtra("cine",cineelegido);
            butacas.putExtra("hora",horaelegida);
            butacas.putExtra("fecha",fechaelegida);
            butacas.putExtra("correo", correo);
            butacas.putExtra("peliculaId", peliculaid );
            startActivity(butacas);

        }
        }




}










