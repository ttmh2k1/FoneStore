package hcmute.fonestore.Object;

public class Comment {
    String comment, id, customerName, image;

    public Comment() {
    }

    public Comment(String comment, String id, String customerName, String image) {
        this.comment = comment;
        this.id = id;
        this.customerName = customerName;
        this.image = image;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
