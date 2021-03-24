package com.example.cafeyab.ui.Home.events;

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
import com.example.cafeyab.model.Event;

import java.util.List;

public class Adapter_game_event extends RecyclerView.Adapter<Adapter_game_event.MyViewHolder>   {

    private List<Event> events;
    private Context context;
    private FragmentManager fragmentManager;

    public Adapter_game_event(List<Event> events, Context context, FragmentManager fragmentManager) {
        this.events = events;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_for_game_event, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.name.setText(events.get(position).getName());
        Glide.with(context)
                .load(events.get(position).getImageurl())
                .centerCrop()
                .error(R.drawable.cafe_csiga)
                .fallback(R.drawable.cafe_csiga)
                .into(holder.img);

        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("id",events.get(position).getId());
                bundle.putString("imageurl",events.get(position).getImageurl());
                bundle.putString("time",events.get(position).getTime());
                bundle.putString("date",events.get(position).getDate());
                bundle.putString("event_category",events.get(position).getEvent_category());
                EventListCafeGameFragment fragment=new EventListCafeGameFragment();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction().add(R.id.fragment_container_home,fragment).addToBackStack("eventpage").commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public static class  MyViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout parentlayout;
        ImageView img;
        TextView name;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.image_gameevent);
            name=itemView.findViewById(R.id.name_gameevent);
            parentlayout=itemView.findViewById(R.id.parent_layout_gameevent);


        }
    }
}
