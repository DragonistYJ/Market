package love.dragoinst.market.bean;

import java.util.Objects;

public class Product {
    private int spId;
    private String name;
    private String picURL;
    private int inventory;
    private double price;
    private String description;
    private String size;
    private String brand;
    private String category;
    private String crowed;
    private String img1;
    private String img2;
    private String img3;
    private String img4;

    public int getSpId() {
        return spId;
    }

    public void setSpId(int spId) {
        this.spId = spId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicURL() {
        return picURL;
    }

    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCrowed() {
        return crowed;
    }

    public void setCrowed(String crowed) {
        this.crowed = crowed;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return spId == product.spId &&
                inventory == product.inventory &&
                Objects.equals(name, product.name) &&
                Objects.equals(picURL, product.picURL) &&
                Objects.equals(description, product.description) &&
                Objects.equals(size, product.size) &&
                Objects.equals(brand, product.brand) &&
                Objects.equals(category, product.category) &&
                Objects.equals(crowed, product.crowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spId, name, picURL, inventory, description, size, brand, category, crowed);
    }
}
