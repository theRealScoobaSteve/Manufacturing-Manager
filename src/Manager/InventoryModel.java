package Manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import Manager.Part.Part;
import Manager.Product.Product;

import java.util.Iterator;

public class InventoryModel {
    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;
    private static InventoryModel instance;
    private int lastPartId;
    private int lastProductId;

    public InventoryModel() {
        this.allParts      = FXCollections.observableArrayList();
        this.allParts      = FXCollections.observableArrayList();
        this.lastPartId    = 0;
        this.lastProductId = 0;
    }



    public InventoryModel addPart(Part part) {
        allParts.add(part);
        return this;
    }

    public InventoryModel addProduct (Product product) {
        allProducts.add(product);
        return this;
    }

    public Part lookupPart(int id) {
        Iterator<Part> iterator = allParts.iterator();

        while(iterator.hasNext()) {
            Part part = iterator.next();

            if(part.getId() == id) {
                return part;
            }
        }

        throw null;
    }

    public Part lookupPart(String partName) {
        Iterator<Part> iterator = allParts.iterator();

        while(iterator.hasNext()) {
            Part part = iterator.next();

            if(part.getName() == partName) {
                return part;
            }
        }

        return null;
    }

    public void updatePart(Part selectedPart) {
        int id = allParts.indexOf(selectedPart);

        allParts.set(id, selectedPart);
    }

    public void deletePart(Part selectedPart) {
        allParts.remove(selectedPart);
    }

    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    public Product lookupProduct(int id) {
        Iterator<Product> iterator = allProducts.iterator();

        while(iterator.hasNext()) {
            Product product = iterator.next();

            if(product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    public Product lookupProduct(String partName) {
        Iterator<Product> iterator = allProducts.iterator();

        while(iterator.hasNext()) {
            Product product = iterator.next();

            if(product.getName() == partName) {
                return product;
            }
        }

        return null;
    }

    public void updateProduct(Product selectedProduct) {
        int id = allProducts.indexOf(selectedProduct);

        allProducts.set(id, selectedProduct);
    }

    public void removeProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
    }

    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public int getPartStock() {
        return this.allParts.size();
    }

    public int getProductStock() {
        return this.allProducts.size();
    }

    public int getLastPartId() {
        return lastPartId;
    }

    public int getLastProductId() {
        return lastProductId;
    }

    public int generatePartId() {
      return ++this.lastPartId;
    }

    public static InventoryModel getInstance() {
        if(instance ==  null) {
            instance = new InventoryModel();
        }

        return instance;
    }
}
