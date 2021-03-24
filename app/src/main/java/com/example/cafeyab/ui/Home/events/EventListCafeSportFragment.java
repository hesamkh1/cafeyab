package com.example.cafeyab.ui.Home.events;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.example.cafeyab.ui.Home.events.Adapter_EventListCafeSport;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventListCafeSportFragment extends Fragment {

    private RecyclerView recyclerView_eventlistcafe;
    private RecyclerView.LayoutManager layoutManager;
    private List<Cafe> cafes;
    private Adapter_EventListCafeSport adapterEventListCafe;
    private ApiInterface apiInterface;
    Context mcontext;
    private ImageView eventimage;
    TextView time,date;
    public EventListCafeSportFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mcontext=context;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_list_cafe_sport, container, false);
        eventimage=view.findViewById(R.id.eventimage_eventlistcafe);
        time=view.findViewById(R.id.time_eventlistcafe);
        date=view.findViewById(R.id.date_eventlistcafe);


        Bundle bundle=this.getArguments();
        String idstr=bundle.getString("id");
        String imgurl=bundle.getString("imageurl");
        String timestr=bundle.getString("time");
        String datestr=bundle.getString("date");



        Glide.with(mcontext)
                .load(imgurl)
                .centerCrop()
                .error(R.drawable.cafe_csiga)
                .fallback(R.drawable.cafe_csiga)
                .into(eventimage);
        time.setText(timestr);
        date.setText(datestr);



        //recycler_eventlistcafe
        recyclerView_eventlistcafe=view.findViewById(R.id.recylerview_eventlistcafe);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_eventlistcafe.setLayoutManager(layoutManager);
        recyclerView_eventlistcafe.setHasFixedSize(true);

        getCafeEvent(idstr);

        return view;

    }


    public  void getCafeEvent(String eventkind)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Cafe>> call = apiInterface.getCafeEvent(eventkind);
        call.enqueue(new Callback<List<Cafe>>() {
            @Override

            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {

                cafes=response.body();
                adapterEventListCafe = new Adapter_EventListCafeSport(cafes, mcontext,getFragmentManager());
                recyclerView_eventlistcafe.setAdapter(adapterEventListCafe);
                adapterEventListCafe.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
