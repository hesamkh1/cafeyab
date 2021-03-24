package com.example.cafeyab.ui.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.example.cafeyab.ui.Home.HomeActivity;
import com.example.cafeyab.utility.PrefConfig;
import com.example.cafeyab.R;

public class MainActivity extends AppCompatActivity  implements LoginFragment.OnLoginFormActrivityListener, WelcomeFragment.OnLogoutListener, RegistrationFragment.OnRegisterFormActrivityListener {

    public static PrefConfig prefConfig;
    public  static ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefConfig=new PrefConfig(this);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);

        if(findViewById(R.id.fragment_container) != null)
        {
            if(savedInstanceState != null)
            {
                return;
            }

            if(prefConfig.readLoginStatus())
            {
                //getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new WelcomeFragment()).commit();
                Intent homeIntent =new Intent(this, HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
            else {
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,new LoginFragment()).commit();
            }
        }

    }

    @Override
    public void performRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container ,
                new RegistrationFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performLogin(String name) {
                prefConfig.writeName(name);
               // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new WelcomeFragment()).commit();
        Intent homeIntent =new Intent(getBaseContext(),HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    @Override
    public void logoutperformed() {
        prefConfig.writeLoginStatus(false);
        prefConfig.writeName("User");
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoginFragment()).commit();
    }

    @Override
    public void SuccecRegister() {
        Toast.makeText(MainActivity.this,"Successfully Register ",Toast.LENGTH_SHORT);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LoginFragment()).commit();
    }
}
