package com.example.cafeyab.ui.Home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;






/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HomeFragment extends Fragment  {

       private TextView search_edit_text,addresstv;
       private OnNormalsearchListener onNormalsearchListener;
       private OnNearbyListener onNearbyListener;
        private OnFavouriteListener onFavouriteListener;
       private RecyclerView recyclerView_pishnahadi,recyclerView_takhfifdar;
       private RecyclerView.LayoutManager layoutManager;
       private List<Cafe> cafes;
       private Adapter_pishnahadi adapter_pishnahadi;
       private Adapter_takhfifdar adapter_takhfifdar;
       private ApiInterface apiInterface;
       private ImageView img;
       private ImageButton addressimg,favimg;
       Context mcontext;
       private FusedLocationProviderClient mFusedLocationClient;





    public interface  OnNormalsearchListener
    {
        public void gonormalsearchpage(); //mehtod for going to nearby  fragment
    }

    public interface  OnNearbyListener
    {
        public void gonearbypage(); //mehtod for going to nearby  fragment
    }
    public interface  OnFavouriteListener
    {
        public void gofavouritepage(); //mehtod for going to favourite  fragment
    }


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //click on search for normal search on top of main fragment
        search_edit_text =view.findViewById(R.id.search_edit_text);
        search_edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onNormalsearchListener.gonormalsearchpage();
                }
        });


        //recycler_pishnahadi
        recyclerView_pishnahadi=view.findViewById(R.id.recylerview_pishnahadi);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_pishnahadi.setLayoutManager(layoutManager);
        recyclerView_pishnahadi.setHasFixedSize(true);
        

        //recycler_takhfifdar
        recyclerView_takhfifdar=view.findViewById(R.id.recylerview_takhfifdar);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView_takhfifdar.setLayoutManager(layoutManager);
        recyclerView_takhfifdar.setHasFixedSize(true);

        getcafeproposed("proposed");
        getcafediscounted("discounted");


         img=view.findViewById(R.id.imageView3);
        Glide.with(mcontext)
                .load("https://www.newideafood.com.au/media/18385/cappucino.jpg?width=720&center=0.0,0.0")
                .centerCrop()
                .error(R.drawable.cafe_csiga)
                .fallback(R.drawable.cafe_csiga)
                .into(img);

        //get Address for show in
//        addresstv=view.findViewById(R.id.address_home);
//
//        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(getActivity());
//        if(ActivityCompat.checkSelfPermission(mcontext,
//                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//                getCurrentLocation();
//        }
//        else{
//            //permissopn denied
//            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
//        }

        //go to nearby fragment
        addressimg=view.findViewById(R.id.addressimg_home);
        addressimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNearbyListener.gonearbypage();
            }
        });

        //go to favourite fragment
        favimg=view.findViewById(R.id.favimage_home);
        favimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFavouriteListener.gofavouritepage();
            }
        });




        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext=context;
        Activity activity=(Activity) context;
        onNormalsearchListener=(OnNormalsearchListener)activity;
        onNearbyListener=(OnNearbyListener)activity;
        onFavouriteListener=(OnFavouriteListener)activity;
    }




    public void getcafeproposed(final String cafe_name)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Cafe>> call = apiInterface.getcafeproposed(cafe_name);
        call.enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                cafes=response.body();

                //pishnahadi
                adapter_pishnahadi = new Adapter_pishnahadi(cafes, mcontext,getFragmentManager());
                recyclerView_pishnahadi.setAdapter(adapter_pishnahadi);
                adapter_pishnahadi.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
              //  Log.e("error:",t.toString());
            }
        });

    }
    public void getcafediscounted(String CafeKind){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Cafe>> call = apiInterface.getcafediscounted(CafeKind);
        call.enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {

                //takhfifdar
                cafes=response.body();
                adapter_takhfifdar = new Adapter_takhfifdar(cafes, mcontext,getFragmentManager());
                recyclerView_takhfifdar.setAdapter(adapter_takhfifdar);
                adapter_takhfifdar.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                //  Log.e("error:",t.toString());
            }
        });
    }


//    private String getCityName(LatLng myCoordinates) {
//        String myCity = "";
//        Geocoder geocoder = new Geocoder(mcontext, Locale.getDefault());
//        try {
//            List<Address> addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 1);
//            myCity = addresses.get(0).getAddressLine(0);
//            myCity = myCity.replace(addresses.get(0).getCountryName(),"");//delete country name in address
//            myCity = myCity.replace(addresses.get(0).getFeatureName(),"");
////            myCity = addresses.get(0).getCountryName();
////                Log.e("mylog", "Complete Address: " + addresses.toString());
//            Log.d("mylog", "Address: " + myCity);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return myCity;
//    }



//    private void getCurrentLocation() {
//        Task<Location> task=mFusedLocationClient.getLastLocation();
//        task.addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(final Location location) {
//                if(location !=null){
//                    LatLng latLng=new LatLng(location.getLatitude()
//                            ,location.getLongitude());
//                    String address = getCityName(latLng);
//                    addresstv.setText(address);
//                }
//            }
//
//        });
//
//    }
}
