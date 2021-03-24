package com.example.cafeyab.ui.Home.events;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.Event;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.example.cafeyab.ui.Home.events.Adapter_sport_event;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SportEventFragment extends Fragment {


    private RecyclerView recyclerView_sportevent;
    private RecyclerView.LayoutManager layoutManager;
    private List<Event> events;
    private Adapter_sport_event adapterSportEvent;
    private ApiInterface apiInterface;
    Context mcontext;


    public SportEventFragment() {
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
        View view= inflater.inflate(R.layout.fragment_sport_event, container, false);


        //recycler_sportevent
        recyclerView_sportevent=view.findViewById(R.id.recyclerview_sportevent);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_sportevent.setLayoutManager(layoutManager);
        recyclerView_sportevent.setHasFixedSize(true);
//        getEvent("sport");

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getEvent("sport");
    }

    public  void getEvent(String eventkind)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Event>> call = apiInterface.getEvent(eventkind);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                events=response.body();
                //sportevent
                adapterSportEvent = new Adapter_sport_event(events, mcontext,getFragmentManager());
                recyclerView_sportevent.setAdapter(adapterSportEvent);
                adapterSportEvent.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
