package com.example.raihan.hobbies;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static com.example.raihan.hobbies.MainActivity.node;

class messege_adapter extends RecyclerView.Adapter<messege_adapter.MyViewHolder> {


    private List<chat_message_object> cmo;
    String id = "";

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView leftText,rightText,name;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.chatName);
            rightText = (TextView) itemView.findViewById(R.id.rightText);
            leftText = (TextView)itemView.findViewById(R.id.leftText);


        }
    }
    public messege_adapter(List<chat_message_object>cmo,String id)
    {
        this.cmo = cmo;
        this.id = id;
    }

    @NonNull
    @Override
    public messege_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull messege_adapter.MyViewHolder holder, int position) {

        chat_message_object cmo1 = cmo.get(position);
        if(cmo1.getMessageUser().equals(node))
        {
            holder.rightText.setText(cmo1.getMessageFriend());
            holder.name.setVisibility(View.GONE);
            holder.rightText.setVisibility(View.VISIBLE);
            holder.leftText.setVisibility(View.GONE);
        }
        else
        {
            holder.leftText.setText(cmo1.getMessageFriend());
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(cmo1.getMessageUser());
            holder.rightText.setVisibility(View.GONE);
            holder.leftText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return cmo.size();
    }
}

