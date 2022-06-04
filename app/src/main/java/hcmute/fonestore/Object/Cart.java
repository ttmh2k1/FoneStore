package hcmute.fonestore.Object;

public class Cart {
    private String id;
    private Long quantity;

    public Cart(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Cart(String id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
