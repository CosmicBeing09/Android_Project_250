package com.example.raihan.hobbies;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class normal_post_view_adapter extends RecyclerView.Adapter<normal_post_view_adapter.MyViewHolder> {
    private ItemClickListener clickListener;
    private ArrayList<normal_post_object> obj;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text,user_name;
        ImageView post_image,user_image;

        public MyViewHolder(View itemView)
        {
            super(itemView);


            this.text = itemView.findViewById(R.id.post_text);
            this.user_name = itemView.findViewById(R.id.userName);

            this.post_image = itemView.findViewById(R.id.post_image);
            this.user_image = itemView.findViewById(R.id.userImage);

            itemView.setOnClickListener(this);

            // itemView.setTag(itemView);


        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public normal_post_view_adapter(ArrayList<normal_post_object> obj, Context context)
    {
        this.obj = obj;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.posted_item,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        TextView text = myViewHolder.text;
        TextView user_name = myViewHolder.user_name;
        ImageView imageView = myViewHolder.post_image;
        ImageView userImage = myViewHolder.user_image;

        text.setText(obj.get(i).getPost_text());
        user_name.setText(obj.get(i).getUser());
        Picasso.get().load(obj.get(i).getImgaeUrl()).fit().centerCrop().into(imageView);
        Picasso.get().load(obj.get(i).getUser_imageUri()).fit().centerCrop().into(userImage);

    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


}
