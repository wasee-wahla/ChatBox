package com.example.chatbox.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbox.ChatActivity;
import com.example.chatbox.Interfaces.ItemClickListener;
import com.example.chatbox.Model.ChatMessage;
import com.example.chatbox.Model.User;
import com.example.chatbox.R;

import java.util.ArrayList;
import java.util.Random;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder>{
    private ArrayList<User> items;
    private Context mContext;
    private String userName;

    public UserItemAdapter(ArrayList<User> items, Context context,String userName){
        this.items = items;
        this.mContext = context;
        this.userName=userName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final User user = items.get(position);

        holder.alphaBet.setBackgroundColor(getRandomColor());
        holder.alphaBet.setText(user.getUsername().substring(0,1));
        holder.userName.setText(user.getUsername());
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                //Toast.makeText(mContext, ""+user.getUsername(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(mContext,ChatActivity.class);
                intent.putExtra("USERNAME",userName);
                intent.putExtra("RECIEVERNAME",user.getUsername());
                //Toast.makeText(mContext, ""+userName+user.getUsername(), Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });
        //All the thing you gonna show in the item
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView alphaBet;
        public TextView userName;
        private ItemClickListener itemClickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            alphaBet = (TextView) itemView.findViewById(R.id.user_item_aplhabet);
            userName = (TextView) itemView.findViewById(R.id.user_item_username);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener){
            this.itemClickListener=itemClickListener;
        }
        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition());
        }
    }
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }
}
