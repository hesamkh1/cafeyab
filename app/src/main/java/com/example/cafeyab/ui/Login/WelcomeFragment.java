package com.example.cafeyab.ui.Login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cafeyab.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment {

    private TextView textView;
    private Button BtnLogout;
    OnLogoutListener logoutListener;
    public interface  OnLogoutListener
    {
        public void logoutperformed();
    }

    public WelcomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_welcome, container, false);

       textView=view.findViewById(R.id.txt_name_info);
       BtnLogout=view.findViewById(R.id.btn_logout);

       textView.setText("Welcome"+ MainActivity.prefConfig.readName());
       BtnLogout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                logoutListener.logoutperformed();
           }
       });

       return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity=(Activity) context;
        logoutListener = (OnLogoutListener)activity;
    }
}
