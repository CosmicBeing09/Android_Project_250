package com.example.raihan.hobbies;

import android.content.Context;
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

public class id_previewAdapter extends RecyclerView.Adapter<id_previewAdapter.MyViewHolder>  {

    private ItemClickListener clickListener;
    private ArrayList<global_profile_info> obj;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView user_name,location;
        CircleImageView user_image;

        public MyViewHolder(View itemView)
        {
            super(itemView);


            this.user_name = itemView.findViewById(R.id.id_previewName);
            this.location = itemView.findViewById(R.id.id_previewLocation);
            this.user_image = itemView.findViewById(R.id.id_PreviewUserImage);
            itemView.setOnClickListener(this);

            // itemView.setTag(itemView);


        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.id_preview,viewGroup,false);
        id_previewAdapter.MyViewHolder viewHolder = new id_previewAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        TextView user_name = holder.user_name;
        TextView location = holder.location;
        CircleImageView userImage = holder.user_image;

        user_name.setText(obj.get(position).getName());
        location.setText(obj.get(position).getLocation());
        Picasso.get().load(obj.get(position).getGlobal_imageUri()).fit().centerCrop().into(userImage);

    }

    @Override
    public int getItemCount() {
        return obj.size();
    }

    public id_previewAdapter(ArrayList<global_profile_info> obj, Context context)
    {
        this.obj = obj;
        this.context = context;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }



}
