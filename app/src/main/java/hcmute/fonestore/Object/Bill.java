package hcmute.fonestore.Object;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Bill {
    @Exclude
    private String id;
    @Exclude
    private String uid;

    private ArrayList<CartItem> orderProducts;
    private int status;
    private String orderTime;

    public Bill() {
    }

    public Bill(ArrayList<CartItem> orderProducts, int status, String orderTime) {
        this.orderProducts = orderProducts;
        this.status = status;
        this.orderTime = orderTime;
    }

    public ArrayList<CartItem> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(ArrayList<CartItem> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStringStatus() {
        if (status == 0) {
            return "Đang chờ xác nhận";
        }
        else if (status == 1) {
            return "Đang giao hàng";
        }
        else if (status == 2) {
            return "Đã giao hàng";
        }
        else
            return "";
    }
}
