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

public class manage_sell_post_adapter extends RecyclerView.Adapter<manage_sell_post_adapter.MyViewHolder> {

    private ItemClickListener clickListener;
    private ArrayList<sell_post_object> obj;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView postType,location,price;
        ImageView post_image;

        public MyViewHolder(View itemView)
        {
            super(itemView);


            this.postType = itemView.findViewById(R.id.manage_sell_post_type);
            this.location = itemView.findViewById(R.id.manage_sell_post_location);
            this.price = itemView.findViewById(R.id.manage_sell_post_price);

            this.post_image = itemView.findViewById(R.id.manage_sell_post_image);

            itemView.setOnClickListener(this);

            // itemView.setTag(itemView);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public manage_sell_post_adapter(ArrayList<sell_post_object> obj, Context context)
    {
        this.obj = obj;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manage_sell_post_preview,viewGroup,false);
        manage_sell_post_adapter.MyViewHolder viewHolder = new manage_sell_post_adapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        TextView postType = myViewHolder.postType;
        TextView location = myViewHolder.location;
        TextView price = myViewHolder.price;
        ImageView post_image = myViewHolder.post_image;

        postType.setText(obj.get(i).getPost_type());
        location.setText(obj.get(i).getLocation());
        price.setText(obj.get(i).getPrice());

        try {
            Picasso.get().load(obj.get(i).getImgaeUrl()).fit().centerCrop().into(post_image);
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
