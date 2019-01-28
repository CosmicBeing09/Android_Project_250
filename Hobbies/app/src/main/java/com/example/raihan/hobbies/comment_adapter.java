package com.example.raihan.hobbies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class comment_adapter extends RecyclerView.Adapter<comment_adapter.MyViewHolder> {

    private ArrayList<comment_object> obj;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text, name;
        CircleImageView user_image;

        public MyViewHolder(View itemView) {
            super(itemView);


            this.text = itemView.findViewById(R.id.commentText);
            this.name = itemView.findViewById(R.id.commentorName);
            this.user_image = itemView.findViewById(R.id.commentorImage);

        }


    }

    public comment_adapter(ArrayList<comment_object> obj, Context context)
    {
        this.obj = obj;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_sample,viewGroup,false);
        comment_adapter.MyViewHolder viewHolder = new comment_adapter.MyViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView name = holder.name;
        TextView text = holder.text;
        CircleImageView userImage = holder.user_image;

        name.setText(obj.get(position).getCommentor()+": ");
        text.setText(obj.get(position).getText());
        try {
            Picasso.get().load(obj.get(position).getUri()).fit().centerCrop().into(userImage);
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return obj.size();
    }


}


