package com.example.chatbox;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatbox.Data.DatabaseContract;
import com.example.chatbox.Data.DatabaseHelper;
import com.example.chatbox.Model.User;

public class SignupActivity extends AppCompatActivity {
    EditText mUserName, mFullName, mPassword;
    Button mRegisterButton;
    DatabaseHelper databaseHelper;
    TextView textViewLogin;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textViewLogin = findViewById(R.id.signup_login_text);
        mUserName = findViewById(R.id.signup_username);
        mFullName = findViewById(R.id.signup_fullname);
        mPassword = findViewById(R.id.signup_password);
        mRegisterButton = findViewById(R.id.signup_button);

        databaseHelper = DatabaseHelper.getInstance(this);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sUserName = mUserName.getText().toString().trim();
                String sFullName = mFullName.getText().toString().trim();
                String sPassword = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(sUserName)) {
                    Toast.makeText(SignupActivity.this, "Enter UsrName", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sFullName)) {
                    Toast.makeText(SignupActivity.this, "Enter FullName", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(sPassword) && sPassword.length() < 6) {
                    Toast.makeText(SignupActivity.this, "Password length should be 6 character", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setUsername(sUserName);
                user.setFullname(sFullName);
                user.setPassword(sPassword);
                Log.d("NAME", sUserName);
                Log.d("FNAME", sFullName);
                Log.d("pass", sPassword);

                long result = insertUser(user.getUsername(), user.getFullname(), user.getPassword());
                if (result == -1) {
                    Toast.makeText(SignupActivity.this, "Signup Error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignupActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    mUserName.setText("");
                    mFullName.setText("");
                    mPassword.setText("");
                }
            }
        });

    }


    private long insertUser(String sUserName, String sFullName, String sPassword) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.Users.USERNAME, sUserName);
        cv.put(DatabaseContract.Users.FULLNAME, sFullName);
        cv.put(DatabaseContract.Users.PASSWORD, sPassword);
        long newRowid = db.insert(DatabaseContract.Users.TABLE_NAME, null, cv);
        return newRowid;
    }
}
