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
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.Event;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.example.cafeyab.ui.Home.events.Adapter_concert_event;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcertEventFragment extends Fragment {


    private RecyclerView recyclerView_concertevent;
    private RecyclerView.LayoutManager layoutManager;
    private List<Event> events;
    private Adapter_concert_event adapterConcertEvent;
    private ApiInterface apiInterface;
    Context mcontext;

    public ConcertEventFragment() {
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
        View view = inflater.inflate(R.layout.fragment_concert_event, container, false);

        //recycler_concertevent
        recyclerView_concertevent=view.findViewById(R.id.recyclerview_concertevent);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_concertevent.setLayoutManager(layoutManager);
        recyclerView_concertevent.setHasFixedSize(true);
        getEvent("concert");

        return view;
    }

    public  void getEvent(String eventkind)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Event>> call = apiInterface.getEvent(eventkind);
        call.enqueue(new Callback<List<Event>>() {
            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                events=response.body();
                //concertevent
                adapterConcertEvent = new Adapter_concert_event(events, mcontext,getFragmentManager());
                recyclerView_concertevent.setAdapter(adapterConcertEvent);
                adapterConcertEvent.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
