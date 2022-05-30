package hcmute.fonestore.Object;

import com.google.firebase.database.Exclude;

public class Comment {
    @Exclude
    private String id;
    private String comment, userId, customerName, image;

    public Comment() {
    }

    public Comment(String id, String comment, String userId, String customerName, String image) {
        this.id = id;
        this.comment = comment;
        this.userId = userId;
        this.customerName = customerName;
        this.image = image;
    }

    public Comment(String comment, String userId, String customerName, String image) {
        this.comment = comment;
        this.userId = userId;
        this.customerName = customerName;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
