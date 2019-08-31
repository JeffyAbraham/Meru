package com.example.meru;

import java.util.Date;

public class ModelChatMessage {
    private String messageText;
    private String messageUser;
    private long messageTime;

    public ModelChatMessage(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;

        // Initialize to current time

    }

    public ModelChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }


    public long getMessageTime() {
        return  messageTime;
    }
}