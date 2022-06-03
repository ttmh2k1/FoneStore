package hcmute.fonestore.Notification;

public class Token {
    /* An fcm token , ỏ much commonly known as a registrationTOken
    AN ID issued by GCMconnection servre to the clinet app that allows it to receive message
     */

    String token;

    public Token(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
