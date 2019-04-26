package com.example.chatbox.Model;

import java.util.Date;

public class ChatMessage {
    private String messageTo;
    private String messageFrom;
    private String messageText;
    private long messageTime;

    public ChatMessage() {
    }

    public ChatMessage(String messageTo, String messageFrom) {
        this.messageTo = messageTo;
        this.messageFrom = messageFrom;
    }

    public ChatMessage(String messageTo, String messageFrom, String messageText, long messageTime) {
        this.messageTo = messageTo;
        this.messageFrom = messageFrom;
        this.messageText = messageText;
        this.messageTime = messageTime;
    }

    public ChatMessage(String messageTo, String messageFrom, String messageText) {
        this.messageTo = messageTo;
        this.messageFrom = messageFrom;
        this.messageText = messageText;

        messageTime=new Date().getTime();
    }

    public String getMessageTo() {
        return messageTo;
    }

    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    public String getMessageFrom() {
        return messageFrom;
    }

    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
