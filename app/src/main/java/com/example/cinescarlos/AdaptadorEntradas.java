package com.example.cinescarlos;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinescarlos.Beans.Entradas;
import com.example.cinescarlos.Beans.Peliculas;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdaptadorEntradas extends RecyclerView.Adapter<AdaptadorEntradas.MyViewHolder> implements View.OnClickListener {

    Context context;
    List<Entradas> entradas;
    ImageView Bidi;
    String nombrepeli;



    public AdaptadorEntradas(Context c, List<Entradas> e){
    context = c;
    entradas = e;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.rawentradas,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {



        final Call<Peliculas> calle = WebService.getInstance().createService(WebServiceApi.class).verPelicula(entradas.get(position).getPeliculaId());

        calle.enqueue(new Callback<Peliculas>() {
            @Override
            public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                nombrepeli = response.body().getNombre();
                holder.EnPeli.setText(nombrepeli);
            }

            @Override
            public void onFailure(Call<Peliculas> call, Throwable t) {

            }
        });


        holder.EnFecha.setText("Fecha: " + entradas.get(position).getFecha());
        holder.EnCine.setText(entradas.get(position).getCine());
        holder.Enhora.setText("Hora: "+entradas.get(position).getHora());
        holder.Enbutacas.setText("Butacas: "+ entradas.get(position).getAsientos());

        String bidi = (entradas.get(position).getAsientos() + entradas.get(position).getFecha() + entradas.get(position).getCine()
        + entradas.get(position).getHora() + entradas.get(position).getPeliculaId());

        GenerarQR(bidi);


        }
        private void GenerarQR(String bidi){
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

            try{
                BitMatrix bitMatrix = multiFormatWriter.encode(bidi, BarcodeFormat.QR_CODE, 500, 500 );
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                Bidi.setImageBitmap(bitmap);
            }catch(WriterException e){
                e.printStackTrace();
            }
        }

    @Override
    public int getItemCount() { return entradas.size();
    }

    @Override
    public void onClick(View v) {

    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{

        Context context;
        TextView EnPeli;
        TextView EnCine;
        ImageView Bidir;
        TextView Enhora;
        TextView Enbutacas;
        TextView EnFecha;


        public MyViewHolder(View itemView) {
            super(itemView);
            context =itemView.getContext();
            context = itemView.getContext();


            EnPeli = itemView.findViewById(R.id.EnPeli);
            EnCine = itemView.findViewById(R.id.EnCine);
            Bidi = itemView.findViewById(R.id.Bidi);
            Enhora = itemView.findViewById(R.id.Enhora);
            Enbutacas = itemView.findViewById(R.id.Enbutacas);
            EnFecha = itemView.findViewById(R.id.EnFecha);

        }


        }


}






