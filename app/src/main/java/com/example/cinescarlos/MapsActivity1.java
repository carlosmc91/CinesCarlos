package com.example.cinescarlos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.cinescarlos.Beans.CinesCarlos;
import com.example.cinescarlos.api.WebService;
import com.example.cinescarlos.api.WebServiceApi;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity1 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CinesCarlos cines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.logocinescarlos);
        Bitmap b = bitmapdraw.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b,200,200,false);


        Call<List<CinesCarlos>> call = WebService.
                getInstance().
                createService(WebServiceApi.class)
                .getCines();

        call.enqueue(new Callback<List<CinesCarlos>>() {
            @Override
            public void onResponse(Call<List<CinesCarlos>> call, Response<List<CinesCarlos>> response) {
                if(response.code() ==200) {
                    for(int i=0; i<response.body().size(); i++) {
                        LatLng posicion = new LatLng(response.body().get(i).getLatitud(), response.body().get(i).getLongitud());
                        mMap.addMarker(new MarkerOptions().position(posicion).title(response.body().get(i).
                                getNombre()).snippet("CineCarlos").icon(BitmapDescriptorFactory.fromBitmap(smallMarker))).setTag(response.body().get(i).getId());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posicion,10f ));

                    }
                }else if(response.code()==404){
                    Log.d("TAG1", "No hay Cines");
                }
            }

            @Override
            public void onFailure(Call<List<CinesCarlos>> call, Throwable t) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                Intent maps = new Intent(MapsActivity1.this,DescripcionCine.class);
                maps.putExtra("cin", (Long) marker.getTag());
                startActivity(maps);
                return false;
            }
        });
    }
}
