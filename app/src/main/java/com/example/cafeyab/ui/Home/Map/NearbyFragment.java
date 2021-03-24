package com.example.cafeyab.ui.Home.Map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NearbyFragment extends Fragment implements OnMapReadyCallback {


    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient client;
    private Context mcontext;
    private ApiInterface apiInterface;
    private List<Cafe> cafes;
    private int size=0;
//    List<LatLng> sydney ;
   private ImageButton mylocation;
   private GoogleMap mMap;




    public NearbyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nearby, container, false);
//        mylocation=view.findViewById(R.id.mylocation_nearby);

        //Markers
        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);


        //Current location
        client= LocationServices.getFusedLocationProviderClient(getActivity());
        if(ActivityCompat.checkSelfPermission(mcontext,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();

        }
        else{
            //permissopn denied
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }

//        mylocation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                        client= LocationServices.getFusedLocationProviderClient(getActivity());
//                        if(ActivityCompat.checkSelfPermission(mcontext,
//                                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//                                 getCurrentLocation();
//
//                        }
//                        else{
//                            //permissopn denied
//                            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
//                        }
//            }
//        });



        return view;
    }




    //@Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap=googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setPadding(0,0,0,200);

        //get Data
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Cafe>> call = apiInterface.getCafe("all");
        call.enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                cafes=response.body();
                size=cafes.size();

                for(int i=0;i<size;i++){
//                    Log.e("Arrrrrraaaaaay", String.valueOf(cafes.size()));
//                     Log.e("latitude",cafes.get(2).getName());
                    double sLat = Double.valueOf(cafes.get(i).getLatitude());
                    double sLng = Double.valueOf(cafes.get(i).getLongitude());
                    LatLng latlngg = new LatLng(sLat, sLng);
                    mMap.addMarker(new MarkerOptions()
                            .position(latlngg)
                            .title(cafes.get(i).getImageurl())
                            .snippet(cafes.get(i).getName()+"@"+cafes.get(i).getCategory())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                            .alpha(0.7f));

                    // Setting a custom info window adapter for the google map
                    MarkerInfoWindowAdapter markerInfoWindowAdapter = new MarkerInfoWindowAdapter(mcontext);
                    googleMap.setInfoWindowAdapter(markerInfoWindowAdapter);

                    // Adding and showing marker when the map is touched
//                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                        @Override
//                        public void onMapClick(LatLng arg0) {
//                            mMap.clear();
//                            MarkerOptions markerOptions = new MarkerOptions();
//                            markerOptions.position(arg0);
//                            mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0));
//                            Marker marker = mMap.addMarker(markerOptions);
//                            marker.showInfoWindow();
//                        }
//                    });

                }
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                //  Log.e("error:",t.toString());
            }
        });

//        LatLng sydney = new LatLng(35.775210, 51.364792);
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(sydney)
//                .title("Marker in Cafe Deyhok"));
//
//        LatLng tehran = new LatLng(35.775310, 51.364592);
//
//        googleMap.addMarker(new MarkerOptions()
//                .position(tehran)
//                .title("Marker in Cafe tehran"));
         //  googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,14));

    }

    private void getCurrentLocation() {
        Task<Location> task=client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location !=null){
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            LatLng latLng=new LatLng(location.getLatitude()
                                    ,location.getLongitude());


                            MarkerOptions options=new MarkerOptions().position(latLng)
                                    .title("Man injam dawshie Man")
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.mylocation));

                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14));
                            googleMap.addMarker(options);



                        }
                    });
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 44)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
        }
    }




    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext=context;
    }
}
