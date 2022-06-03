package hcmute.fonestore.Object;

import com.google.firebase.database.PropertyName;

public class Chats {
    private String receiver, sender, message,time;
    boolean isSeen;

    public Chats(){}

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    @PropertyName("isSeen")
    public boolean isSeen() {
        return isSeen;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @PropertyName("isSeen")
    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public Chats(String receiver, String sender, String message, String time, boolean isSeen) {
        this.receiver = receiver;
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.isSeen = isSeen;
    }
}
