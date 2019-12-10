package Manager.Product;

import Manager.AbstractController;
import Manager.Part.Part;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ProductController extends AbstractController {
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private Button saveButton;
    @FXML
    private TextField id;
    @FXML
    private TextField inventory;
    @FXML
    private TableView<Part> searchableTable;
    @FXML
    private TableColumn<Part, String> searchableId;
    @FXML
    private TableColumn<Part, String> searchableName;
    @FXML
    private TableColumn<Part, String> searchableInvLevel;
    @FXML
    private TableColumn<Part, String> searchableCost;
    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn<Part, String> associatedId;
    @FXML
    private TableColumn<Part, String> associatedName;
    @FXML
    private TableColumn<Part, String> associatedInvLevel;
    @FXML
    private TableColumn<Part, String> associatedCost;

    private String productName;

    private double productPrice;

    private int productMax;

    private int productMin;

    private int productId;

    private int productInventory;

    private Product newProduct;

    @FXML
    public void addProduct() {
        try {
            this.setDefaultValues();
            this.validateDefaultFields();

            this.productId = this.getInventory().generatePartId();
            Runnable task  = this.createProductTask();
            System.out.println(newProduct.getStock());
            new Thread(task).start();
            this.closeAddProductScreen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.displayErrorScreen(e.getMessage());
        }
    }

    @FXML
    public void updateProduct() {
        try {
            this.setDefaultValues();
            this.validateDefaultFields();

            this.productId = Integer.parseInt(this.id.getText());
            Runnable task  = this.modifyProductTask();

            new Thread(task).start();
            this.closeAddProductScreen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.displayErrorScreen(e.getMessage());
        }
    }

    @FXML
    public void deletePart() {
        Part part     = this.associatedPartTable.getSelectionModel().getSelectedItem();
        Runnable task = this.removeProductTask(part);

        new Thread(task).start();
    }

    public void initialize() throws Exception {
        FilteredList<Part> filteredData = new FilteredList<>(this.getInventory().getAllParts(), p -> true);
        SortedList<Part> sortedData     = new SortedList<>(filteredData);

        /**
         * @todo need to separate associated part table and the part list and only set up the main part list here on load
         */
//        this.generateProduct();
        this.setPartColumnHeaders();
        this.addInventoryToTable();
        this.addPartColumnsToTable();
        this.addPartFilterListener(filteredData);
        this.bindPropertyToTable(sortedData);
    }

    private void setDefaultValues() throws Exception {
        this.productName      = this.name.getText();
        this.productPrice     = this.validateDoubleValue(this.price.getText(), "Price");
        this.productMax       = this.validateIntegerValue(this.max.getText(), "Maximum");
        this.productMin       = this.validateIntegerValue(this.min.getText(), "Minimum");
        this.productInventory = this.validateIntegerValue(this.inventory.getText(), "Inventory");
    }

    private void validateDefaultFields() throws Exception {
        if(this.productMax <= this.productMin) {
            throw new Exception("The maximum can't not be less than the minimum");
        } else if(this.productMin < 0 || this.productMax < 0) {
            throw new Exception("The minimum or maximum can't be negative");
        }

        if(this.productInventory < this.productMin) {
            throw new Exception("The inventory can't be less than the minimum");
        }

        if(this.productInventory > this.productMax) {
            throw new Exception("The inventory can't be greater than the maximum");
        }
    }

    private Runnable createProductTask() {
        return new Runnable() {
            @Override
            public void run() {
                generateProduct();
                getInventory().addProduct(newProduct);
            }
        };
    }

    private Runnable removeProductTask(Part part) {
        return new Runnable() {
            @Override
            public void run() {
                newProduct.removeAssociatedPart(part);
            }
        };
    }

    private Runnable modifyProductTask() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    setDefaultValues();
                    getInventory().updateProduct(newProduct);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    displayErrorScreen(e.getMessage());
                }
            }
        };
    }

    private void generateProduct() {
        if(this.newProduct == null) {
            this.newProduct = new Product();
        } else {
            this.productId        = this.newProduct.getId();
            this.productMin       = this.newProduct.getMin();
            this.productMax       = this.newProduct.getMax();
            this.productInventory = this.newProduct.getStock();
            this.productName      = this.newProduct.getName();
            this.productPrice     = this.newProduct.getPrice();
        }
    }

    @FXML
    public void closeAddProductScreen() {
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void exitAddProductWindow() {
        this.closeAddProductScreen();
    }

    /**
     * @todo need to separate associated part table and the part list
     */
    private void setPartColumnHeaders() {
        this.searchableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.searchableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.searchableInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.searchableCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.searchableCost.setCellValueFactory(cellData -> Bindings.format("%.2f", cellData.getValue().getPrice()));
        this.associatedId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.associatedInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.associatedCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.associatedCost.setCellValueFactory(cellData -> Bindings.format("%.2f", cellData.getValue().getPrice()));
    }

    /**
     * @todo need to separate associated part table and the part list
     */
    private void addPartColumnsToTable() {
        this.searchableTable.getColumns().add(searchableId);
        this.searchableTable.getColumns().add(searchableName);
        this.searchableTable.getColumns().add(searchableInvLevel);
        this.searchableTable.getColumns().add(searchableCost);
        this.associatedPartTable.getColumns().add(associatedId);
        this.associatedPartTable.getColumns().add(associatedName);
        this.associatedPartTable.getColumns().add(associatedInvLevel);
        this.associatedPartTable.getColumns().add(associatedCost);
    }

    /**
     * @todo need to separate associated part table and the part list
     */
    private void addInventoryToTable() {
        this.searchableTable.getColumns().clear();
        this.searchableTable.setItems(this.getInventory().getAllParts());
        this.associatedPartTable.getColumns().clear();
        this.associatedPartTable.setItems(this.newProduct.getAssociatedParts());
    }

    /**
     * @todo may want to move this into abstract controller
     * @param filteredData
     */
    private void addPartFilterListener(FilteredList<Part> filteredData) {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(part -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (part.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }

                if(Double.toString(part.getPrice()).contains(lowerCaseFilter)) {
                    return true;
                }

                if(Double.toString(part.getId()).contains(lowerCaseFilter)) {
                    return true;
                }
                if(Double.toString(part.getStock()).contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // Does not match.
            });
        });
    }

    private void bindPropertyToTable(SortedList<Part> sortedData) {
        sortedData.comparatorProperty().bind(this.searchableTable.comparatorProperty());
        this.searchableTable.setItems(sortedData);
    }

    public void addPartToProduct() throws Exception {
        Runnable task = this.createAddPartTask();
        new Thread(task).start();
    }

    public void addPartToProductModify() throws Exception {
        Runnable task = this.createAddPartTask();
        new Thread(task).start();
    }

    private Runnable createAddPartTask() {
        return new Runnable() {
            @Override
            public void run() {
                Part part = searchableTable.getSelectionModel().getSelectedItem();
                newProduct.addAssociatedPart(part);
            }
        };
    }

    public void setProduct(Product product) throws Exception {
        this.newProduct = product;

        this.setFieldValues(product);
        this.generateProduct();
        this.setPartColumnHeaders();
        this.addInventoryToTable();
        this.addPartColumnsToTable();
    }

    private void setFieldValues(Product product)  {
        this.id.setText(Integer.toString(product.getId()));
        this.name.setText(product.getName());
        this.price.setText(Double.toString(product.getPrice()));
        this.min.setText(Integer.toString(product.getMin()));
        this.max.setText(Integer.toString(product.getMax()));
        this.inventory.setText(Integer.toString(product.getStock()));
    }
}
