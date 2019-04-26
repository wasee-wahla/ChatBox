package com.example.chatbox.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chatbox.Model.ChatMessage;
import com.example.chatbox.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private  String mSCurrentSender ="";
    private ArrayList<ChatMessage> mMessageList;
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    public ChatListAdapter(Context context, ArrayList<ChatMessage> messageList, String sender) {
       this.mContext = context;
        this.mMessageList = messageList;
        this.mSCurrentSender = sender;
    }
    public void updateData(ChatMessage allChat){
        mMessageList.add(allChat);
    }
    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mMessageList.get(position);
        if (chatMessage.getMessageFrom().equals(mSCurrentSender)) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }



    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        if (i == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder(view);
        } else if (i == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_recieved, viewGroup, false);
            return new RecieveMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ChatMessage chatMessage = mMessageList.get(i);
        switch (viewHolder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) viewHolder).bind(chatMessage);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((RecieveMessageHolder) viewHolder).bind(chatMessage);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView textViewMessage, textViewTime;

        public SentMessageHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textview_sent_message);
            textViewTime = itemView.findViewById(R.id.textview_sent_time);
        }

        void bind(ChatMessage chatMessage) {
            textViewMessage.setText(chatMessage.getMessageText());
            textViewTime.setText(DateFormat.format("HH:mm:ss", chatMessage.getMessageTime()));
        }
    }

    private class RecieveMessageHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewMessage, textViewTime;

        public RecieveMessageHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textview_recieved_name);
            textViewMessage = itemView.findViewById(R.id.textview_recieved_message);
            textViewTime = itemView.findViewById(R.id.textview_recieved_time);
        }

        void bind(ChatMessage message) {
            textViewMessage.setText(message.getMessageFrom());
            textViewMessage.setText(message.getMessageText());
            textViewTime.setText(DateFormat.format("HH:mm:ss", message.getMessageTime()));
        }
    }
}
