package com.example.cafeyab.ui.Home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.ui.Home.cafepagesinfo.CafePageInfoFragment;

import java.util.List;

public class Adapter_pishnahadi  extends RecyclerView.Adapter<Adapter_pishnahadi.MyViewHolder> {

    private List<Cafe> cafe;
    private Context context;
    FragmentManager fragmentManager;

    public Adapter_pishnahadi(List<Cafe> cafe, Context context, FragmentManager fragmentManager) {
        this.cafe = cafe;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_for_pishnahadi, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

            holder.name.setText(cafe.get(position).getName());
            holder.address.setText(cafe.get(position).getAddress());

            Glide.with(context)
                .load(cafe.get(position).getImageurl())
                .autoClone()
                .error(R.drawable.cafe_csiga)
                .fallback(R.drawable.cafe_csiga)
                .centerCrop()
                .into(holder.img);

            holder.parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  // Toast.makeText(context,"click on"+ cafe.get(position).getName().toString(),Toast.LENGTH_SHORT).show();

                    Bundle bundle=new Bundle();
                    bundle.putString("name",cafe.get(position).getName());
                    bundle.putString("latitude",cafe.get(position).getLatitude());
                    bundle.putString("longtitude",cafe.get(position).getLongitude());
                    bundle.putString("imageurl",cafe.get(position).getImageurl());
                    bundle.putString("opentime",cafe.get(position).getOpentime());
                    bundle.putString("closetime",cafe.get(position).getClosetime());
                    bundle.putString("address",cafe.get(position).getAddress());
                    bundle.putString("id",cafe.get(position).getId());
                    bundle.putString("phone_number",cafe.get(position).getPhone_number());
                    bundle.putString("info",cafe.get(position).getInfo());
                    bundle.putString("category",cafe.get(position).getCategory());

                    CafePageInfoFragment fragment=new CafePageInfoFragment();
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.fragment_container_home,fragment).addToBackStack("HomePage").commit();

                }
            });



    }

    @Override
    public int getItemCount() {
        return cafe.size();
    }

                public static class  MyViewHolder extends RecyclerView.ViewHolder{
                    TextView name,address;
                    ImageView img;
                    LinearLayout parentLayout;
                    public MyViewHolder(View itemView)
                    {
                        super(itemView);
                        name=itemView.findViewById(R.id.cafe_name);

                        parentLayout = itemView.findViewById(R.id.parent_layout_pishnahadi);
                        img=itemView.findViewById(R.id.imageView_pishnahadi);
                        address=itemView.findViewById(R.id.cafe_address);
                    }

    }


}
