package hcmute.fonestore.object;

public class product {

    private String Name, Image, Category, Producer, Brand, Origin, Describe, Price, Seller;

    public product() {
    }

    public product(String name, String image, String price, String category, String producer, String brand, String origin, String describe, String seller) {
        Name = name;
        Image = image;
        Category = category;
        Producer = producer;
        Brand = brand;
        Origin = origin;
        Describe = describe;
        Price = price;
        Seller = seller;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getProducer() {
        return Producer;
    }

    public void setProducer(String producer) {
        Producer = producer;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getOrigin() {
        return Origin;
    }

    public void setOrigin(String origin) {
        Origin = origin;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSeller() {
        return Seller;
    }

    public void setSeller(String seller) {
        Seller = seller;
    }
}
