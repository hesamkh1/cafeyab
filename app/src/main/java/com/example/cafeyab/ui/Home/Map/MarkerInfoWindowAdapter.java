package com.example.cafeyab.ui.Home.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    public MarkerInfoWindowAdapter(Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view =  inflater.inflate(R.layout.marker_info_window, null);

//        LatLng latLng = arg0.getPosition();
        TextView name =  view.findViewById(R.id.name_markerinfowindow);
        TextView category =  view.findViewById(R.id.category_markerwindowinfo);
        ImageView imageView= view.findViewById(R.id.cafeimage_markerinfowindow);
//        Glide.with(context)
//                .load(arg0.getTitle())
//                .centerCrop()
//                .error(R.drawable.cafe_csiga)
//                .fallback(R.drawable.cafe_csiga)
//                .into(imageView);
        Picasso.get()
                .load(arg0.getTitle())
                .resize(150,100).into(imageView);

        String [] str=arg0.getSnippet().split("@");
        name.setText(str[0]);
        category.setText(str[1]);
        return view;
    }
}