package com.example.cafeyab.ui.Home.cafepagesinfo;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cafeyab.R;
import com.example.cafeyab.model.Comment;
import com.example.cafeyab.remote.ApiClient;
import com.example.cafeyab.remote.ApiInterface;
import com.example.cafeyab.ui.Login.MainActivity;
import com.example.cafeyab.utility.PrefConfig;

import java.util.List;
import java.util.PriorityQueue;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopUpClass{

    private ApiInterface apiInterface;
    private Button send_btn;
    private EditText cm_box;
    private String strmsg;
    private PrefConfig prefConfig;

    //PopupWindow display method

    public void showPopupWindow(final View view,final String cafe_id) {

        //Create a View object yourself through inflater
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.add_comment_popup, null);
        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;
        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, Gravity.CENTER, 0);


        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();

                return true;
            }
        });

        send_btn=popupView.findViewById(R.id.submit_comment_addcommentpopup);
        cm_box=popupView.findViewById(R.id.comment_txt_addcommentpopup);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strmsg=cm_box.getText().toString();
                sendcomments(cafe_id,view);
                popupWindow.dismiss();
            }
        });
    }


    public void sendcomments(String cafe_id,final View v) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        //Log.e("ssss",MainActivity.prefConfig.readName()+"   "+cafe_id+"  "+strmsg);
        Call<Comment> call = apiInterface.sendcomments( MainActivity.prefConfig.readName(),strmsg , cafe_id);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                    if(response.body().getResponse().equals("ok") ){
                    Toast.makeText(v.getContext(), " send comment succesful", Toast.LENGTH_SHORT).show();

                }
                 else if(response.body().getResponse().equals("error"))   {
                        Toast.makeText(v.getContext(), " send comment unsuccesful", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(v.getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}