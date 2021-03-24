package com.example.cafeyab.ui.Home.cafepagesinfo;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.Comment;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentsFragment extends Fragment {

    public String cafe_id,sort="DESC";
    private int counter=10;
    private RecyclerView recyclerView_comment;
    private Adapter_comment adapter_comment;
    private RecyclerView.LayoutManager layoutManager;
    private ApiInterface apiInterface;
    private List<Comment> comments;
    private Context mcontext;
    String[] sortby = { "Latest","Earliest" };
    String [] count ={"+10","+50","+100","+200"};


    public CommentsFragment() {
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
        View view=inflater.inflate(R.layout.fragment_comments, container, false);
        TextView textView=view.findViewById(R.id.text);

        Bundle bundle=this.getArguments();
        cafe_id = bundle.getString("cafe_id");

        //Recycler
        recyclerView_comment=view.findViewById(R.id.recyclerview_fragmentcomments_comments);
        layoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_comment.setLayoutManager(layoutManager);
        recyclerView_comment.setHasFixedSize(true);
        getcomments(cafe_id,sort);


        //sortby
        Spinner spin1 = view.findViewById(R.id.sortby_spinner_comments);
        spin1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
//                if (item != null) {
//                    Toast.makeText(getContext(), item.toString(), Toast.LENGTH_SHORT).show();
//                }
                if (item.equals("Latest")){
                    sort="DESC";
                    getcomments(cafe_id,sort);
                }
                else if (item.equals("Earliest")){
                    sort="ASC";
                    getcomments(cafe_id,sort);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub

            }
        });
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter arrayAdapter = new ArrayAdapter(mcontext,R.layout.spinner_style,sortby);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(arrayAdapter);


        //count of showing
        Spinner spin2 = view.findViewById(R.id.count_spinner_comments);
        spin2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
//                if (item != null) {
//                    Toast.makeText(getContext(), item.toString(), Toast.LENGTH_SHORT).show();
//                }
                if (item.equals("+10")){

                    counter=10;
                    adapter_comment = new Adapter_comment(comments, mcontext,counter);
                    recyclerView_comment.setAdapter(adapter_comment);
                    adapter_comment.notifyDataSetChanged();
                }
                else if (item.equals("+50")){

                    counter=50;
                    adapter_comment = new Adapter_comment(comments, mcontext,counter);
                    recyclerView_comment.setAdapter(adapter_comment);
                    adapter_comment.notifyDataSetChanged();
                }
                else if (item.equals("+100")){
                    counter=100;
                    adapter_comment = new Adapter_comment(comments, mcontext,counter);
                    recyclerView_comment.setAdapter(adapter_comment);
                    adapter_comment.notifyDataSetChanged();
                }
                else if (item.equals("+200")){
                    counter=200;
                    adapter_comment = new Adapter_comment(comments, mcontext,counter);
                    recyclerView_comment.setAdapter(adapter_comment);
                    adapter_comment.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter arrayAdapter1 = new ArrayAdapter(mcontext,R.layout.spinner_style,count);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin2.setAdapter(arrayAdapter1);


        FloatingActionButton popupButton = view.findViewById(R.id.add_comment_floating);
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v,cafe_id);

            }

        });

        return view;
    }





    public void getcomments(final String Cafe_id,String sort_type)
    {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Comment>> call = apiInterface.getcomments(Cafe_id,sort_type);

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                comments=response.body();
                adapter_comment = new Adapter_comment(comments, mcontext,counter);
                recyclerView_comment.setAdapter(adapter_comment);
                adapter_comment.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(mcontext, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
