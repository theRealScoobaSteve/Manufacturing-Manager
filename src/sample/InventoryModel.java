package sample;

import javafx.collections.ObservableList;

public class InventoryModel {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    public InventoryModel addPart(Part part) {
        allParts.add(part);
        return this;
    }

    public InventoryModel addProduct (Product product) {
        allProducts.add(product);
        return this;
    }

    public InventoryModel lookupPart(int id) {
        Part part;
        allParts.indexOf(Part)
    }
}
