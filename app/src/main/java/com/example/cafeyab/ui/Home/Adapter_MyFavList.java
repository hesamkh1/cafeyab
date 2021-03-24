package com.example.cafeyab.ui.Home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.example.cafeyab.ui.Home.cafepagesinfo.CafePageInfoFragment;
import com.example.cafeyab.ui.Home.cafepagesinfo.FavDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Adapter_MyFavList  extends RecyclerView.Adapter<Adapter_MyFavList.ViewHolder> {

    private Context context;
    FragmentManager fragmentManager;
    private List<Cafe> cafe,secondcafe;
    private FavDB favDB;
    private ApiInterface apiInterface;


    public Adapter_MyFavList(List<Cafe> cafe, Context context, FragmentManager fragmentManager) {
        this.cafe = cafe;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_for_favourite,
                parent, false);
        favDB = new FavDB(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.favTextView.setText(cafe.get(position).getName());
        Glide.with(context)
                .load(cafe.get(position).getImageurl())
                .centerCrop()
                .error(R.drawable.cafe_csiga)
                .fallback(R.drawable.cafe_csiga)
                .into(holder.favImageView);
        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCafeinfavourite(cafe.get(position).getName());




            }
        });
    }

    @Override
    public int getItemCount() {
        return cafe.size();
    }



                public class ViewHolder extends RecyclerView.ViewHolder {

                        TextView favTextView;
                        Button favBtn;
                        ImageView favImageView;
                        CardView parentlayout;
                    public ViewHolder(@NonNull View itemView) {
                        super(itemView);
                        favTextView = itemView.findViewById(R.id.favTextView);
                        favBtn = itemView.findViewById(R.id.favBtn);
                        favImageView = itemView.findViewById(R.id.favImageView);
                        parentlayout=itemView.findViewById(R.id.parent_layout_favourite);

                        //remove when unlike
                        favBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int position =getAdapterPosition();
                                final Cafe cafee=cafe.get(position);
                                favDB.remove_fav(cafee.getId());
                                removeItem(position);
                            }
                        });
                }}

    private void removeItem(int position) {
        cafe.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,cafe.size());
    }

    public void getCafeinfavourite(String cafe_name)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Cafe>> call = apiInterface.getCafe(cafe_name);
        call.enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                secondcafe=response.body();
                Bundle bundle=new Bundle();
                bundle.putString("name",secondcafe.get(0).getName());
                bundle.putString("latitude",secondcafe.get(0).getLatitude());
                bundle.putString("longtitude",secondcafe.get(0).getLongitude());
                bundle.putString("imageurl",secondcafe.get(0).getImageurl());
                bundle.putString("opentime",secondcafe.get(0).getOpentime());
                bundle.putString("closetime",secondcafe.get(0).getClosetime());
                bundle.putString("address",secondcafe.get(0).getAddress());
                bundle.putString("id",secondcafe.get(0).getId());
                bundle.putString("phone_number",secondcafe.get(0).getPhone_number());
                bundle.putString("info",secondcafe.get(0).getInfo());
                bundle.putString("category",secondcafe.get(0).getCategory());

                CafePageInfoFragment fragment=new CafePageInfoFragment();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.fragment_container_home,fragment).addToBackStack("HomePage").commit();

            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(context, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                Log.e("error:",t.toString());
            }
        });


    }
}
