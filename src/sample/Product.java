package sample;

import javafx.collections.ObservableList;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private ObservableList<Part> associatedParts;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public Product setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Product setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public int getMin() {
        return min;
    }

    public Product setMin(int min) {
        this.min = min;
        return this;
    }

    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    public Product setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
        return this;
    }

    public int getMax() {
        return max;
    }

    public Product setMax(int max) {
        this.max = max;
        return this;
    }
}
