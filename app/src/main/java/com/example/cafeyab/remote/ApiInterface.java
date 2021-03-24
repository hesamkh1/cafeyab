package com.example.cafeyab.remote;

import com.example.cafeyab.model.Cafe;
import com.example.cafeyab.model.Comment;
import com.example.cafeyab.model.Event;
import com.example.cafeyab.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("register.php")
    Call<User> performRegistration(@Query("name") String Name,
                                   @Query("user_name") String UserName,
                                   @Query("user_password") String UserPassword);


    @GET("login.php")
    Call<User> performUserLogin(@Query("user_name") String UserName,
                                @Query("user_password") String UserPassword);


    @GET("cafesearch.php")
    Call<List<Cafe>> getCafe(@Query("key") String CafeName);

    @GET("cafediscounted.php")
    Call<List<Cafe>> getcafediscounted(@Query("key") String CafeKind);

    @GET("cafeproposed.php")
    Call<List<Cafe>> getcafeproposed(@Query("key") String CafeKind);

    @GET("event.php")
    Call<List<Event>> getEvent(@Query("key") String eventkind);

    @GET("advancedsearch.php")
    Call<List<Cafe>> getadvancedCafe(@Query("key") String category);

    @GET("cafevent.php")
    Call<List<Cafe>> getCafeEvent(@Query("key") String eventkind);

    @GET("comments.php")
    Call<List<Comment>> getcomments(@Query("cafe_id") String cafe_id,
                                    @Query("sort_type") String sort);

    @GET("post_comment.php")
    Call<Comment> sendcomments(@Query("name") String name,
                                     @Query("text") String comment_txt,
                                     @Query("cafe_id") String cafe_id);
}
