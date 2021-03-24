package com.example.cafeyab.ui.Home.cafepagesinfo;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.model.Comment;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.example.cafeyab.ui.Home.Adapter_pishnahadi;
import com.example.cafeyab.ui.Home.HomeActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CafePageInfoFragment extends Fragment implements OnMapReadyCallback {

    private TextView tv_cafename,tv_opentime,tv_closetime,tv_address,tv_phone,tv_website,tv_info,tv_category,more_comments;
    private ImageView  cafeimage;
    private ImageButton fav;
    private Context mcontext;
    private SupportMapFragment mapFragment;
    private String latitude,longtitude,name,id,favstatus,imgurl;
    private FavDB favDB;
    private FloatingActionButton sharebtn;
    private RecyclerView recyclerView_comment;
    private Adapter_comment adapter_comment;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    private List<Comment> comments;
    private OnCommentspageListener onCommentspageListener;

    public CafePageInfoFragment() {
        // Required empty public constructor
    }

    public interface  OnCommentspageListener
    {
        public void gocommentpage(String cafe_id); //mehtod for going to comments  fragment
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext=context;
        Activity activity=(Activity) context;
        onCommentspageListener=(OnCommentspageListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cafe_page_info, container, false);

        //casting
        tv_cafename=view.findViewById(R.id.cafename_cafepageinfo);
        cafeimage=view.findViewById(R.id.image_cafepageinfo);
        tv_opentime=view.findViewById(R.id.opentime_cafepageinfo);
        tv_closetime=view.findViewById(R.id.closetime_cafepageinfo);
        tv_address=view.findViewById(R.id.address_cafepageinfo);
        tv_phone=view.findViewById(R.id.phone_cafepageinfo);
        tv_website=view.findViewById(R.id.website_cafepageinfo);
        tv_info=view.findViewById(R.id.info_cafepageinfo);
        tv_category=view.findViewById(R.id.category_cafepaginfo);
        sharebtn=view.findViewById(R.id.sharebtn);
        more_comments=view.findViewById(R.id.more_comments_cafepageinfo);


        Bundle bundle=this.getArguments();
        name=bundle.getString("name");
        imgurl=bundle.getString("imageurl");
        latitude=bundle.getString("latitude");
        longtitude=bundle.getString("longtitude");
        String opentime= bundle.getString("opentime");
        String closetime=bundle.getString("closetime");
        String phone_number=bundle.getString("phone_number");
        String info=bundle.getString("info");
        String category=bundle.getString("category");
        final String address=bundle.getString("address");
        id=bundle.getString("id");


        Glide.with(mcontext)
                .load(imgurl)
                .centerCrop()
                .error(R.drawable.cafe_csiga)
                .fallback(R.drawable.cafe_csiga)
                .into(cafeimage);

        tv_cafename.setText(name);
        tv_closetime.setText(closetime);
        tv_opentime.setText(opentime);
        tv_address.setText(address);
        tv_phone.setText(phone_number);
        tv_info.setText(info);
        tv_category.setText(category);

        mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map_cafepageinfo);
        mapFragment.getMapAsync(this);

        //share button
        sharebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent=new Intent(Intent.ACTION_SEND);
                myintent.setType("text/plain");
                myintent.putExtra(Intent.EXTRA_SUBJECT,name);
                myintent.putExtra(Intent.EXTRA_TEXT,"cafe name: "+name +" cafe address: "+address);
                startActivity(Intent.createChooser(myintent,"share cafe "+name));
            }
        });



        //phone call
        tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

//
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tv_phone.getText().toString()));// Initiates the Intent
                    startActivity(intent);

                } catch (ActivityNotFoundException activityException) {

                    Log.e("Calling a Phone Number", "Call failed", activityException);
                }
            }
        });

        //website

        tv_website.setMovementMethod(LinkMovementMethod.getInstance());
        tv_website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse(tv_website.getText().toString()));
                startActivity(browserIntent);
            }
        });


        //favourite
        fav=view.findViewById(R.id.fav_cafepageinfo);
        favDB = new FavDB(mcontext);
        readCursorData(id);
        //create table on first
        SharedPreferences prefs = mcontext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart", true);
        if (firstStart) {

            createTableOnFirstStart();
        }
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(favstatus.equals("0")){
                    favstatus="1";
                    favDB.insertIntoTheDatabase(name,imgurl,id,favstatus);
                    fav.setBackgroundResource(R.drawable.ic_bookmark_orange_24dp);
                    Toast.makeText(mcontext,name +" Add to MyFavourite ",Toast.LENGTH_LONG).show();
                }
                else {
                    favstatus="0";
                    favDB.remove_fav(id);
                    fav.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
                    Toast.makeText(mcontext,name +" Remove from MyFavourite ",Toast.LENGTH_LONG).show();
                }
            }
        });

        //Recycler
        recyclerView_comment=view.findViewById(R.id.recyclerview_cafepageinfo_comments);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_comment.setLayoutManager(layoutManager);
        recyclerView_comment.setHasFixedSize(true);
        getcomments(id);

        //see all comments
        more_comments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCommentspageListener.gocommentpage(id);
            }
        });



        return view;
    }
    public void getcomments(final String Cafe_id)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Comment>> call = apiInterface.getcomments(Cafe_id,"DESC");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                comments=response.body();
                adapter_comment = new Adapter_comment(comments, mcontext,3);
                recyclerView_comment.setAdapter(adapter_comment);
                adapter_comment.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }

    private void createTableOnFirstStart() {

        favDB.insertEmpty();
        SharedPreferences prefs = mcontext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(String id) {

//        Toast.makeText(mcontext,id,Toast.LENGTH_SHORT).show();
        Cursor cursor = favDB.read_all_data(id);
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                String item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS));
//                coffeeItem.setFavStatus(item_fav_status);
                favstatus=item_fav_status;
                //check fav status
                if (item_fav_status != null && item_fav_status.equals("1")) {
                    fav.setBackgroundResource(R.drawable.ic_bookmark_orange_24dp);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    fav.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
                }
            }
        } finally {
            if (cursor != null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latlng = new LatLng(Double.valueOf(latitude), Double.valueOf(longtitude));
        googleMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title(name)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET))
                .alpha(0.7f));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,13));
    }
}
