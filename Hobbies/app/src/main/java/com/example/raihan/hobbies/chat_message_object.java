package com.example.raihan.hobbies;

public class chat_message_object {

    private String messageFriend;
    private String messageUser;

    public chat_message_object(String messageFriend,String messageUser)
    {
        this.messageFriend = messageFriend;
        this.messageUser = messageUser;
    }

    public chat_message_object(){}

    public String getMessageFriend() {
        return messageFriend;
    }

    public void setMessageFriend(String messageFriend) {
        this.messageFriend = messageFriend;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
}
