package hcmute.fonestore.Notification;

public class Data {
    public Data(String user, String title, String body, String sent, int icon) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.sent = sent;
        this.icon = icon;
    }

    private String user, title, body, sent;
    private int icon;

    public void setUser(String user) {
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getSent() {
        return sent;
    }

    public int getIcon() {
        return icon;
    }
}
