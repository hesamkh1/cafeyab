package com.example.cafeyab.ui.Home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NormalSearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Cafe> cafes;
    private Adapter_nsearch adapter;
    private ApiInterface apiInterface;
    private EditText nsearch_edit_text;

    Context mcontext;



    public NormalSearchFragment() {
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
        View view = inflater.inflate(R.layout.fragment_normal_search, container, false);

        recyclerView=view.findViewById(R.id.recyclerview_nsearch);
        layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
      //  getCafe("");

        nsearch_edit_text=view.findViewById(R.id.search_edit_text_normal);
        nsearch_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                getCafe(nsearch_edit_text.getText().toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getCafe(nsearch_edit_text.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                getCafe(nsearch_edit_text.getText().toString());
            }
        });



        return view;
    }

    public void getCafe(String cafe_name)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Cafe>> call = apiInterface.getCafe(cafe_name);
        call.enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                cafes=response.body();
                adapter = new Adapter_nsearch(cafes, mcontext,getFragmentManager());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                Log.e("error:",t.toString());
            }
        });


    }


}
