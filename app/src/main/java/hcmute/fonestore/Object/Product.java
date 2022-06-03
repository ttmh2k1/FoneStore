package hcmute.fonestore.Object;

import com.google.firebase.database.Exclude;

public class Product {
    @Exclude
    private String id;

    private String name, image, category, producer, brand, origin, describe;
    private int price;

    public Product() {
    }

    public Product(String name, String image, int price, String category, String producer, String brand, String origin, String describe) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.producer = producer;
        this.brand = brand;
        this.origin = origin;
        this.describe = describe;
        this.price = price;
    }

    public Product(String id, String name, String image, int price, String category, String producer, String brand, String origin, String describe, String seller) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.producer = producer;
        this.brand = brand;
        this.origin = origin;
        this.describe = describe;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
