package com.example.cafeyab.ui.Home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.cafeyab.R;
import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdvancedSearchFragment extends Fragment {
    private Switch smoke,wroof,viproom,hookah;
    private Context mcontext;
    private String str = "";
    private ImageView imagelogo;
    private TextView countofitems;
    private FloatingActionButton search_floating;
    private NestedScrollView nestedScrollView;


    private RecyclerView recyclerView_advancedsearch;
    private RecyclerView.LayoutManager layoutManager;
    private List<Cafe> cafes;
    private Adapter_advancedsearch adapterAdvancedsearch;
    private ApiInterface apiInterface;


    public AdvancedSearchFragment() {
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
       View view = inflater.inflate(R.layout.fragment_advanced_search, container, false);
       smoke=view.findViewById(R.id.smoke_advancesearch);
       wroof=view.findViewById(R.id.wroof_advancesearch);
       viproom=view.findViewById(R.id.viproom_advancesearch);
       hookah=view.findViewById(R.id.hookah_advancesearch);
       imagelogo=view.findViewById(R.id.imagelogo_advancedsearch);
       countofitems=view.findViewById(R.id.countofitems_advancedsearch);
       search_floating=view.findViewById(R.id.search_floating);
       nestedScrollView=view.findViewById(R.id.nestedscroolview_advancesearch);

       search_floating.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               nestedScrollView.scrollTo(0, 0);
           }
       });


       smoke.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked==false)
               {
                   String strNew = str.replace(",smoke", "");
                   str=strNew;

                   if(str.isEmpty()){    recyclerView_advancedsearch.setVisibility(View.GONE); }
                   else{getadvancedCafe(str);}

               }
               else {
                   str=str+",smoke";
                   getadvancedCafe(str);}
           }
       });

        wroof.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==false)
                {

                    String strNew = str.replace(",wroof", "");
                    str=strNew;
                    if(str.isEmpty()){    recyclerView_advancedsearch.setVisibility(View.GONE); }
                    else{getadvancedCafe(str);}

                }
                else {

                    str=str+",wroof";
                    getadvancedCafe(str);

                }
            }
        });

        hookah.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==false)
                {

                    String strNew = str.replace(",hookah", "");
                    str=strNew;
                    if(str.isEmpty()){    recyclerView_advancedsearch.setVisibility(View.GONE); }
                    else{getadvancedCafe(str);}
                }
                else {

                    str=str+",hookah";
                    getadvancedCafe(str);

                }
            }
        });

        viproom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==false)
                {

                    String strNew = str.replace(",viproom", "");
                    str=strNew;

                    if(str.isEmpty()){  recyclerView_advancedsearch.setVisibility(View.GONE); }
                    else{getadvancedCafe(str);}

                }
                else {

                    str=str+",viproom";
                    getadvancedCafe(str);

                }
            }
        });

        //recycler_advancedsearch
        recyclerView_advancedsearch=view.findViewById(R.id.recyclerview_advancedsearch);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_advancedsearch.setLayoutManager(layoutManager);
        recyclerView_advancedsearch.setHasFixedSize(true);



       return view;
    }
    
    
    public void getadvancedCafe(final String category){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Cafe>> call = apiInterface.getadvancedCafe(category);
        call.enqueue(new Callback<List<Cafe>>() {
            @Override
            public void onResponse(Call<List<Cafe>> call, Response<List<Cafe>> response) {
                cafes=response.body();
                countofitems.setText(String.valueOf(cafes.size()));
                Log.e("find",String.valueOf(cafes.size()));
                //advancedsearch
                recyclerView_advancedsearch.setVisibility(View.VISIBLE);
                adapterAdvancedsearch = new Adapter_advancedsearch(cafes, mcontext,getFragmentManager());
                recyclerView_advancedsearch.setAdapter(adapterAdvancedsearch);
                adapterAdvancedsearch.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Cafe>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    } 
}
