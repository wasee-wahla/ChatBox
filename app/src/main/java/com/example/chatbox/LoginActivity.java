package com.example.chatbox;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbox.Data.DatabaseContract;
import com.example.chatbox.Data.DatabaseHelper;
import com.example.chatbox.Model.User;

public class LoginActivity extends AppCompatActivity {
    private EditText mLoginName, mLoginPassword;
    private Button mLoginButton;
    DatabaseHelper databaseHelper;
    TextView mSignupTextView;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mLoginName = findViewById(R.id.login_name);
        mLoginPassword = findViewById(R.id.login_password);
        mLoginButton = findViewById(R.id.login_button);
        databaseHelper = DatabaseHelper.getInstance(this);
        mSignupTextView=findViewById(R.id.login_textview_signup);
        mSignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loginName, loginPassword;
                db = databaseHelper.getReadableDatabase();
                loginName = mLoginName.getText().toString();
                loginPassword = mLoginPassword.getText().toString();
                if (TextUtils.isEmpty(loginName)) {
                    Toast.makeText(LoginActivity.this, "Enter Valid Name..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(loginPassword) && loginPassword.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Password should be 6 characters long..", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User(loginName, loginPassword);
                String[] projection = {BaseColumns._ID, DatabaseContract.Users.USERNAME, DatabaseContract.Users.PASSWORD};
                String selection = DatabaseContract.Users.USERNAME + " =? AND " + DatabaseContract.Users.PASSWORD + " =?";
                String[] selectionArgs = {user.getUsername(), user.getPassword()};
                Cursor cursor = db.query(DatabaseContract.Users.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
                if (cursor.moveToFirst()) {
                    String name = cursor.getString(1);
                    SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("loginName",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("sNAME",name);
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Welcome " + name, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("mCURRENTUSERNAME",name);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Username or Password doesn't Match", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
