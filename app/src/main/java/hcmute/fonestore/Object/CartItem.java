package hcmute.fonestore.Object;

import com.google.firebase.database.Exclude;

public class CartItem {
    @Exclude
    private String key;
    @Exclude
    private String uid;

    private Product product;
    private int qty;

    public CartItem() {
    }

    public CartItem(Product product, int qty) {
        this.qty = qty;
        this.product = product;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
