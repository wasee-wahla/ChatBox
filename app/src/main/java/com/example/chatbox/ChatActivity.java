package com.example.chatbox;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbox.Adapter.ChatListAdapter;
import com.example.chatbox.Data.DatabaseContract;
import com.example.chatbox.Data.DatabaseHelper;
import com.example.chatbox.Model.ChatMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    EditText mEditTextMessage;
    ImageButton mSendMessageButton;
    RecyclerView mChatRecyclerView;
    ArrayList<ChatMessage> arrayList;
    ChatListAdapter chatListAdapter;
    String currentUserName, recieverName, name;
    SharedPreferences mSharedPreferences;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        databaseHelper = DatabaseHelper.getInstance(this);
        recieverName = getIntent().getStringExtra("RECIEVERNAME");
        //currentUserName = getIntent().getStringExtra("USERNAME");
        //Log.d("userName",currentUserName);
        setTitle(recieverName);
        //arrayList=new ArrayList<>();
        mSharedPreferences = getApplicationContext().getSharedPreferences("loginName", MODE_PRIVATE);
        currentUserName = mSharedPreferences.getString("sNAME", "");
        Log.d("currentname", currentUserName);
        Log.d("reciever", recieverName);


        mEditTextMessage = findViewById(R.id.edittext_message);
        mSendMessageButton = findViewById(R.id.button_send_message);
        mChatRecyclerView = findViewById(R.id.chatrecycler);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        Log.d("oncreate", "" + getAllChat());
        //chatListAdapter.notifyDataSetChanged();
        chatListAdapter = new ChatListAdapter(ChatActivity.this, getAllChat(), currentUserName);
        mChatRecyclerView.setLayoutManager(mLayoutManager);

        mChatRecyclerView.setAdapter(chatListAdapter);

        mSendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = mEditTextMessage.getText().toString().trim();

                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(ChatActivity.this, "Message body should't empty...", Toast.LENGTH_SHORT).show();
                    return;
                }
                long messageInsert = insertMessage(new ChatMessage(recieverName, currentUserName, message));
                if (messageInsert == -1) {
                    Toast.makeText(ChatActivity.this, "Message sending Failed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(ChatActivity.this, "" + recieverName, Toast.LENGTH_SHORT).show();
                    chatListAdapter.updateData(new ChatMessage(recieverName,currentUserName,message));
                    chatListAdapter.notifyDataSetChanged();
                    Log.d("chat", "" + getAllChat());
                    mEditTextMessage.setText("");
                }
            }
        });

    }

    private long insertMessage(ChatMessage chatMessage) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.Messages.MESSAGE_TO, chatMessage.getMessageTo());
        cv.put(DatabaseContract.Messages.MESSAGE_FROM, chatMessage.getMessageFrom());
        cv.put(DatabaseContract.Messages.MESSAGE, chatMessage.getMessageText());
        cv.put(DatabaseContract.Messages.MESSAGE_CREATED_AT, chatMessage.getMessageTime());
        long newRowId = db.insert(DatabaseContract.Messages.TABLE_NAME, null, cv);
        return newRowId;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public ArrayList<ChatMessage> getAllChat() {
        arrayList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        //ChatMessage chatMessage = new ChatMessage(recieverName, currentUserName);
        String[] projection = {DatabaseContract.Messages.MESSAGE_TO, DatabaseContract.Messages.MESSAGE_FROM,
                DatabaseContract.Messages.MESSAGE, DatabaseContract.Messages.MESSAGE_CREATED_AT};
        String selection=DatabaseContract.Messages.MESSAGE_TO + " =? AND " + DatabaseContract.Messages.MESSAGE_FROM + " =?";
        //String selection = "("+DatabaseContract.Messages.MESSAGE_TO + " =? AND " + DatabaseContract.Messages.MESSAGE_FROM + " =?)"
         //       + " AND " + "("+ DatabaseContract.Messages.MESSAGE_FROM + " =? AND "+ DatabaseContract.Messages.MESSAGE_TO + " =?)";
        String[] selectionArgs = {recieverName, currentUserName};
        Cursor cursor = db.query(DatabaseContract.Messages.TABLE_NAME, projection,
                selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()){
            do {
                int colTo = cursor.getColumnIndex(DatabaseContract.Messages.MESSAGE_TO);
                int colFrom = cursor.getColumnIndex(DatabaseContract.Messages.MESSAGE_FROM);
                int colMessage = cursor.getColumnIndex(DatabaseContract.Messages.MESSAGE);
                int colTime = cursor.getColumnIndex(DatabaseContract.Messages.MESSAGE_CREATED_AT);

                String messageTo = cursor.getString(colTo);
                String messageFrom = cursor.getString(colFrom);
                String message = cursor.getString(colMessage);
                String messageCreatedAt = cursor.getString(colTime);
                arrayList.add(new ChatMessage(messageTo, messageFrom, message, Long.parseLong(messageCreatedAt)));
            }while (cursor.moveToNext());
        }
        return arrayList;
    }
}
