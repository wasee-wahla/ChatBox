package com.example.chatbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.chatbox.Adapter.UserItemAdapter;
import com.example.chatbox.Data.DatabaseContract;
import com.example.chatbox.Data.DatabaseHelper;
import com.example.chatbox.Interfaces.ItemClickListener;
import com.example.chatbox.Model.ChatMessage;
import com.example.chatbox.Model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerViewUsers;
    DatabaseHelper databaseHelper;
    ArrayList<User> arrayList;
    UserItemAdapter userItemAdapter;
    SharedPreferences sharedPreferences;
    String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper=DatabaseHelper.getInstance(this);
        SharedPreferences getsharedPreferences=getSharedPreferences("CURRENTUSERNAME",MODE_PRIVATE);
        String curUserName=getsharedPreferences.getString("name","");

        currentUserName=getIntent().getStringExtra("mCURRENTUSERNAME");
        sharedPreferences= getApplicationContext().getSharedPreferences("CURRENTUSERNAME",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("name",currentUserName);
        editor.apply();

        mRecyclerViewUsers=findViewById(R.id.recyclerview_user_items);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        mRecyclerViewUsers.setLayoutManager(layoutManager);
        //Log.d("list",""+getAllUser());


        Log.d("name",curUserName);
        userItemAdapter=new UserItemAdapter(getAllUser(),this,curUserName);
        //Log.d("list",""+getAllUser());
        mRecyclerViewUsers.addItemDecoration(new DividerItemDecoration(this,LinearLayout.VERTICAL));
        mRecyclerViewUsers.setAdapter(userItemAdapter);
    }
    public ArrayList<User> getAllUser(){
        arrayList=new ArrayList<>();
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("loginName",MODE_PRIVATE);
        String name=sharedPreferences.getString("sNAME","");

        String[] projection={DatabaseContract.Users.USERNAME};
        String selection= DatabaseContract.Users.USERNAME + " !=?";
        String[] selectionArgs={name};
        SQLiteDatabase db=databaseHelper.getReadableDatabase();
        Cursor cursor=db.query(DatabaseContract.Users.TABLE_NAME,projection,
                selection,selectionArgs,null,null,null);
        while (cursor.moveToNext()){
            int colName=cursor.getColumnIndex(DatabaseContract.Users.USERNAME);
            String userName=cursor.getString(colName);
            arrayList.add(new User(userName));
        }
        return arrayList;
    }
}
