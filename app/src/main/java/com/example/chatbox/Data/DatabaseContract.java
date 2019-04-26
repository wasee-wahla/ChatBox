package com.example.chatbox.Data;

import android.provider.BaseColumns;

public final class DatabaseContract {
    private DatabaseContract(){}
    public static final class Users implements BaseColumns{
        public static final String TABLE_NAME="users";
        public static final String USERNAME="username";
        public static final String PASSWORD="password";
        public static final String FULLNAME="fullname";
    }
    public static final class Messages implements BaseColumns{
        public static final String TABLE_NAME="messages";
        public static final String MESSAGE_TO="messageto";
        public static final String MESSAGE_FROM="messagefrom";
        public static final String MESSAGE="message";
        public static final String MESSAGE_CREATED_AT="timestamp";
    }
}
