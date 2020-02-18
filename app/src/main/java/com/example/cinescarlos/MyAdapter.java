package com.example.cinescarlos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cinescarlos.Beans.Peliculas;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Peliculas> peliculas;
    List<Peliculas> peliculasfilter;



    public MyAdapter(Context c, List<Peliculas> p){
    context = c;
    peliculas = p;
    peliculasfilter = new ArrayList<>(peliculas);
    }





    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.raw,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.mdescripcion.setText("Descripción: " + peliculas.get(position).getDescripcion());
        Picasso.get().load(peliculas.get(position).getImagen()).into(holder.mimagen);
        holder.mpelicula.setText(peliculas.get(position).getNombre());
        holder.mtematica.setText("Temática: "+peliculas.get(position).getTematica());
        holder.menlace.setText(peliculas.get(position).getTrailer());
        holder.mid.setText(peliculas.get(position).getId().toString());
        holder.setOnClickListener();

        }



    @Override
    public int getItemCount() { return peliculas.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Peliculas> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(peliculasfilter);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Peliculas pel : peliculasfilter){
                    if(pel.getNombre().toLowerCase().contains(filterPattern)){
                        filteredList.add(pel);
                    }else if(pel.getTematica().toLowerCase().contains(filterPattern)){
                        filteredList.add(pel);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            peliculas.clear();
            peliculas.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Context context;
        TextView mdescripcion;
        ImageView mimagen;
        TextView mpelicula;
        TextView mtematica;
        TextView menlace;
        TextView mid;
        Button btnenlace, btncompra;


        public MyViewHolder(View itemView) {
            super(itemView);
            context =itemView.getContext();
            context = itemView.getContext();


            mpelicula = itemView.findViewById(R.id.txtTitulo);
            mdescripcion = itemView.findViewById(R.id.txtDescripcion);
            mimagen = itemView.findViewById(R.id.Imgpeli);
            mtematica = itemView.findViewById(R.id.txtTematica);
            menlace = itemView.findViewById(R.id.txtEnlace);
            btnenlace = itemView.findViewById(R.id.BtnCardTrailer);
            btncompra = itemView.findViewById(R.id.BtnCardCompra);
            mid = itemView.findViewById(R.id.txtid);
        }

        void setOnClickListener(){
            btncompra.setOnClickListener(this);
            btnenlace.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.BtnCardCompra:
                    Intent intent = new Intent(context,Compra.class);
                    intent.putExtra("Pelicula", mpelicula.getText());
                    intent.putExtra("IdPelicula", mid.getText());
                    context.startActivity(intent);
                    break;
                case R.id.BtnCardTrailer:
                    Intent youtube = new Intent(context,Youtube.class);
                    youtube.putExtra("Enlace", menlace.getText());
                    context.startActivity(youtube);
                    break;
            }


        }
    }



}






