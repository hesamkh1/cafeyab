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
import android.widget.EditText;
import android.widget.Toast;

import com.example.cafeyab.R;
import com.example.cafeyab.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegistrationFragment extends Fragment {

    private EditText Name,UserName,UserPassword,UsersPassword;
    private Button Btnregister;



    OnRegisterFormActrivityListener registerFormActrivityListener;

    public interface OnRegisterFormActrivityListener
    {
        public  void SuccecRegister();

    }


    public RegistrationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        Name=view.findViewById(R.id.txt_name);
        UserName=view.findViewById(R.id.txt_user_name);
        UserPassword=view.findViewById(R.id.txt_password);
        UsersPassword=view.findViewById(R.id.txt_passwords);
        Btnregister=view.findViewById(R.id.btn_register);

        Btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performRegistration();
            }
        });

        return  view;
    }


    public void performRegistration()
    {
        String name=Name.getText().toString();
        String username= UserName.getText().toString();
        String password=UserPassword.getText().toString();
        String spassword=UsersPassword.getText().toString();
       // Toast.makeText(getActivity(),spassword+"  "+password,Toast.LENGTH_SHORT).show();

        if(spassword.equals(password)){
        Call<User> call= MainActivity.apiInterface.performRegistration(name,username,password);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    //ok error exit vojodn dare samte server

                        if(response.body().getResponse().equals("ok")){

                            MainActivity.prefConfig.displayToast("Registration success....");
                            registerFormActrivityListener.SuccecRegister();


                        }
                        else if(response.body().getResponse().equals("exist")){

                            MainActivity.prefConfig.displayToast("User Already exist....");

                        }
                        else if(response.body().getResponse().equals("error")){

                            MainActivity.prefConfig.displayToast("Something went wrong....");

                        }

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

              //      MainActivity.prefConfig.displayToast("Error\n"+t.toString());

                }
            });

            Name.setText("");
            UserPassword.setText("");
            UsersPassword.setText("");
            UserName.setText("");

    }
        else{
            Toast.makeText(getActivity(),"Password not match !",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity activity=(Activity) context;
        registerFormActrivityListener=(OnRegisterFormActrivityListener) activity;
    }
}
