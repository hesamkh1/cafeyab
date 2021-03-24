package com.example.cafeyab.ui.Home.cafepagesinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeyab.R;

import com.example.cafeyab.model.Comment;

import java.util.List;

public class Adapter_comment extends RecyclerView.Adapter<Adapter_comment.MyViewHolder> {

    private List<Comment> comments;//change to comment
    private Context context;
    private int limit;

    public Adapter_comment(List<Comment> comments, Context context,int limit) {
        this.comments = comments;
        this.context = context;
        this.limit =limit;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_for_comment, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.person_name.setText(comments.get(position).getName());
        holder.date.setText(comments.get(position).getDate());
        holder.comment_msg.setText(comments.get(position).getText());
    }

    @Override
    public int getItemCount() {
        if(comments.size() > limit){
            return limit;
        }
        else
        {
            return comments.size();
        }
    }


    public static class  MyViewHolder extends RecyclerView.ViewHolder{
            TextView person_name,comment_msg,date;
            public MyViewHolder(View itemView)
            {
                super(itemView);
                person_name=itemView.findViewById(R.id.person_name_comment);
                comment_msg=itemView.findViewById(R.id.comment_msg_comment);
                date=itemView.findViewById(R.id.date_comment);

            }

        }
}
