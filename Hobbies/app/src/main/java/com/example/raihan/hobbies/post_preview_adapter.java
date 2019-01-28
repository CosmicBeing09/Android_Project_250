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

public class post_preview_adapter extends RecyclerView.Adapter<post_preview_adapter.MyViewHolder>  {

    private ItemClickListener clickListener;
    private ArrayList<sell_post_object> obj;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView petType,postType;
        ImageView user_image;

        public MyViewHolder(View itemView)
        {
            super(itemView);


            this.petType = itemView.findViewById(R.id.post_preview_petType);
            this.postType = itemView.findViewById(R.id.post_preview_postType);
            this.user_image = itemView.findViewById(R.id.post_preview_image);
            itemView.setOnClickListener(this);

            // itemView.setTag(itemView);


        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public post_preview_adapter(ArrayList<sell_post_object> obj, Context context)
    {
        this.obj = obj;
        this.context = context;
    }


    @NonNull
    @Override
    public post_preview_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_post_preview,viewGroup,false);
        MyViewHolder viewHolder = new MyViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull post_preview_adapter.MyViewHolder holder, int position) {


        TextView petType = holder.petType;
        TextView postType = holder.postType;
        ImageView userImage = holder.user_image;

        petType.setText(obj.get(position).getPet_type());
        postType.setText(obj.get(position).getPost_type());

        try {
            Picasso.get().load(obj.get(position).getImgaeUrl()).fit().centerCrop().into(userImage);
        }catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return obj.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}