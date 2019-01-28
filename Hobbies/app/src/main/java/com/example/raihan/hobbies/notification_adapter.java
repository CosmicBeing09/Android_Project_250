package com.example.raihan.hobbies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.MyViewHolder>{

    private ArrayList<notification_object> obj;
    private Context context;
    private OnItemClickListener mListener;


    public void setOnItemCliclListener(OnItemClickListener mListener){
        this.mListener = mListener;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView post_image;
        CircleImageView user_image;
        ImageButton message,delete;

        public MyViewHolder(View itemView, final OnItemClickListener listener)
        {
            super(itemView);

            this.text = itemView.findViewById(R.id.notificationText);

            this.post_image = itemView.findViewById(R.id.notificationPostImage);
            this.user_image = itemView.findViewById(R.id.notificationUserImage);
            this.message = itemView.findViewById(R.id.notificationMessage);
            this.delete = itemView.findViewById(R.id.notificationDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.OnMessageClick(position);
                        }
                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.OnDeleteClick(position);
                        }
                    }
                }
            });
        }

    }

    public notification_adapter(ArrayList<notification_object> obj, Context context)
    {
        this.obj = obj;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_preview,viewGroup,false);
        notification_adapter.MyViewHolder viewHolder = new notification_adapter.MyViewHolder(view,mListener);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        TextView user_name = myViewHolder.text;
        ImageView postImage = myViewHolder.post_image;
        ImageView userImage = myViewHolder.user_image;
        ImageButton message = myViewHolder.message;



        user_name.setText(obj.get(i).getUser_name()+" is interested in your post");

        try {
            Picasso.get().load(obj.get(i).getPost_imageUri()).fit().centerCrop().into(postImage);
            Picasso.get().load(obj.get(i).getUser_imageUri()).fit().centerCrop().into(userImage);
        }catch (Exception e){}



    }

    @Override
    public int getItemCount() {
        return obj.size();
    }


}
