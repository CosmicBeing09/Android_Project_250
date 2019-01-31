package com.example.raihan.hobbies;

public class chat_message_object {

    private String messageFriend;
    private String messageUser;
    private String friendImageUri;

    public chat_message_object(String messageFriend,String messageUser,String friendImageUri)
    {
        this.messageFriend = messageFriend;
        this.messageUser = messageUser;
        this.friendImageUri = friendImageUri;
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

    public String getFriendImageUri() {
        return friendImageUri;
    }

    public void setFriendImageUri(String friendImageUri) {
        this.friendImageUri = friendImageUri;
    }
}
