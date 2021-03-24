package com.example.cafeyab.ui.Login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    public Button RegText;
    private EditText UserName,UserPassword;
    private Button  BtnLogin;

    OnLoginFormActrivityListener loginFormActrivityListener;

    public interface OnLoginFormActrivityListener
    {
        public  void performRegister();
        public  void performLogin(String name);
    }



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view  = inflater.inflate(R.layout.fragment_login, container, false);

        RegText=view.findViewById(R.id.regtext);
        UserName=view.findViewById(R.id.user_name);
        UserPassword=view.findViewById(R.id.user_pass);
        BtnLogin=view.findViewById(R.id.btn_login);


        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performLogin();

            }
        });

        RegText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginFormActrivityListener.performRegister();
            }
        });


        return view;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        loginFormActrivityListener = (OnLoginFormActrivityListener) activity;
    }

    private void performLogin()
        {
            String username=UserName.getText().toString();
            String password=UserPassword.getText().toString();

        Call<User> call = MainActivity.apiInterface.performUserLogin(username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                    if(response.body().getResponse().equals("ok"))
                    {
                        MainActivity.prefConfig.writeLoginStatus(true);
                        loginFormActrivityListener.performLogin(response.body().getName());
                    }

                    else if(response.body().getResponse().equals("failed"))
                    {
                        MainActivity.prefConfig.displayToast("Login Failed! Please Try Again...");

                    }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


               Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
//                Log.e("Error",t.toString());

            }
        });

        UserPassword.setText("");
        UserName.setText("");

    }
}
