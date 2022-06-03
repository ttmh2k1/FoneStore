package hcmute.fonestore.Object;

import com.google.firebase.database.Exclude;

public class Product {
    @Exclude
    private String id;

    private String name, image, category, producer, brand, origin, describe, creator, active;
    private Long price;

    public Product() {
    }

    public Product(String name, String image, Long price, String category, String producer, String brand, String origin, String describe, String creator, String active) {
        this.name = name;
        this.image = image;
        this.category = category;
        this.producer = producer;
        this.brand = brand;
        this.origin = origin;
        this.describe = describe;
        this.price = price;
        this.creator = creator;
        this.active = active;
    }

    public Product(String id, String name, String image, Long price, String category, String producer, String brand, String origin, String describe, String creator, String active) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.category = category;
        this.producer = producer;
        this.brand = brand;
        this.origin = origin;
        this.describe = describe;
        this.price = price;
        this.creator = creator;
        this.active = active;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getFormattedPrice() {
        return String.format("%,d vnđ", price);
    }
}
