package Manager.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Manager.Part.Part;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    private ObservableList<Part> associatedParts;

    public Product() {
        this.id              = 0;
        this.name            = "";
        this.price           = 0;
        this.stock           = 0;
        this.min             = 0;
        this.max             = 0;
        this.associatedParts = FXCollections.observableArrayList();
    }

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id              = id;
        this.name            = name;
        this.price           = price;
        this.stock           = stock;
        this.min             = min;
        this.max             = max;
        this.associatedParts = FXCollections.observableArrayList();
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

    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    public void removeAssociatedPart(Part part) {
        this.associatedParts.remove(part);
    }

    public int getMax() {
        return max;
    }

    public Product setMax(int max) {
        this.max = max;
        return this;
    }
}
