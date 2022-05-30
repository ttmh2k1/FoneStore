package hcmute.fonestore.Object;

import com.google.firebase.database.Exclude;

public class Notification {
    @Exclude
    private String id;
    private String title, productId, customerName, productImageLink;

    public Notification() {
    }

    public Notification(String title, String productId, String customerName, String productImageLink) {
        this.title = title;
        this.productId = productId;
        this.customerName = customerName;
        this.productImageLink = productImageLink;
    }

    public Notification(String id, String title, String productId, String customerName, String productImageLink) {
        this.id = id;
        this.title = title;
        this.productId = productId;
        this.customerName = customerName;
        this.productImageLink = productImageLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
    }
}
