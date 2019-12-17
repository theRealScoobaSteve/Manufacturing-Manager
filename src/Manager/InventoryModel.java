package Manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Manager.Part.Part;
import Manager.Product.Product;

import java.util.Iterator;

public class InventoryModel {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static void addProduct (Product product) {
        allProducts.add(product);
    }

    public static Part lookupPart(int id) {
        Iterator<Part> iterator = allParts.iterator();

        while(iterator.hasNext()) {
            Part part = iterator.next();

            if(part.getId() == id) {
                return part;
            }
        }

        return null;
    }

    public static Part lookupPart(String partName) {
        Iterator<Part> iterator = allParts.iterator();

        while(iterator.hasNext()) {
            Part part = iterator.next();

            if(part.getName() == partName) {
                return part;
            }
        }

        return null;
    }

    public static void updatePart(Part selectedPart) {
        Part oldPart = lookupPart(selectedPart.getId());

        int index = allParts.indexOf(oldPart);
        allParts.set(index, selectedPart);
    }

    public static void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static Product lookupProduct(int id) {
        Iterator<Product> iterator = allProducts.iterator();

        while(iterator.hasNext()) {
            Product product = iterator.next();

            if(product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public static Product lookupProduct(String partName) {
        Iterator<Product> iterator = allProducts.iterator();

        while(iterator.hasNext()) {
            Product product = iterator.next();

            if(product.getName() == partName) {
                return product;
            }
        }

        return null;
    }

    public static void updateProduct(Product selectedProduct) {
        Product oldProduct = lookupProduct(selectedProduct.getId());
        int id             = allProducts.indexOf(oldProduct);

        allProducts.set(id, selectedProduct);
    }

    public static void removeProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
