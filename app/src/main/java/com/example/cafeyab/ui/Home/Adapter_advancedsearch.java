package com.example.cafeyab.ui.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.ui.Home.cafepagesinfo.CafePageInfoFragment;

import java.util.List;

public class Adapter_advancedsearch extends RecyclerView.Adapter<Adapter_advancedsearch.MyViewHolder>{

    private List<Cafe> cafes;
    private Context context;
    FragmentManager fragmentManager;
    public Adapter_advancedsearch(List<Cafe> cafes, Context context, FragmentManager fragmentManager) {
        this.cafes = cafes;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_for_advancedsearch, parent, false);
        return new Adapter_advancedsearch.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText(cafes.get(position).getName());
        holder.address.setText(cafes.get(position).getAddress());
        Glide.with(context)
                .load(cafes.get(position).getImageurl())
                .centerCrop()
                .error(R.drawable.cafe_csiga)
                .fallback(R.drawable.cafe_csiga)
                .into(holder.image);
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(context,"click on"+ cafe.get(position).getName().toString(),Toast.LENGTH_SHORT).show();

                Bundle bundle=new Bundle();
                bundle.putString("name",cafes.get(position).getName());
                bundle.putString("latitude",cafes.get(position).getLatitude());
                bundle.putString("longtitude",cafes.get(position).getLongitude());
                bundle.putString("imageurl",cafes.get(position).getImageurl());
                bundle.putString("opentime",cafes.get(position).getOpentime());
                bundle.putString("closetime",cafes.get(position).getClosetime());
                bundle.putString("address",cafes.get(position).getAddress());
                bundle.putString("id",cafes.get(position).getId());
                bundle.putString("phone_number",cafes.get(position).getPhone_number());
                bundle.putString("info",cafes.get(position).getInfo());
                bundle.putString("category",cafes.get(position).getCategory());

                CafePageInfoFragment fragment=new CafePageInfoFragment();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment_container_home,fragment).addToBackStack("HomePage").commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return cafes.size() ;
    }

    public  static  class  MyViewHolder extends RecyclerView.ViewHolder{
            ConstraintLayout parentLayout;
            TextView name,address;
            ImageView image;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                name=itemView.findViewById(R.id.name_advancedsearch);
                address=itemView.findViewById(R.id.address_advancedsearch);
                image=itemView.findViewById(R.id.image_advancedcsearch);
                parentLayout=itemView.findViewById(R.id.parent_layout_advancedsearch);
            }
        }

}
