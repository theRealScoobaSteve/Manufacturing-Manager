package Manager.Product;

import Manager.AbstractController;
import Manager.Part.Part;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private Product newProduct;
    // Must hard code this in order to get around static inventory model
    private int lastProductId = 2;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @FXML
    public void addProduct() {
        try {
            this.setDefaultValues();
            this.validateDefaultFields();
            this.newProduct.setId(++lastProductId);
            Runnable task  = this.createProductTask();
            this.associatedParts = this.associatedPartTable.getSelectionModel().getTableView().getItems();
            this.newProduct.setAssociatedParts(this.associatedParts);

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
            Runnable task = this.modifyProductTask();

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

        this.newProduct = new Product();
        this.setAvailablePartColumns();
        this.setAvailableInventoryToTable();
        this.setAvailablePartColumnHeaders();
        this.addPartFilterListener(filteredData);
        this.bindPropertyToTable(sortedData);
        this.setAssociatedPartColumns();
        this.setAssociatedInventoryToTable();
        this.setAssociatedPartColumnHeaders();
    }

    private void setDefaultValues() throws Exception {
        this.newProduct
                .setName(this.name.getText())
                .setPrice(this.validateDoubleValue(this.price.getText(), "Price"))
                .setMax(this.validateIntegerValue(this.max.getText(), "Maximum"))
                .setMin(this.validateIntegerValue(this.min.getText(), "Minimum"))
                .setStock(this.validateIntegerValue(this.inventory.getText(), "Inventory"));
    }

    private void validateDefaultFields() throws Exception {
        if(this.newProduct.getMax() <= this.newProduct.getMin()) {
            throw new Exception("The maximum can't not be less than the minimum");
        }

        if(this.newProduct.getMin() < 0 || this.newProduct.getMax() < 0) {
            throw new Exception("The minimum or maximum can't be negative");
        }

        if(this.newProduct.getStock() < this.newProduct.getMin()) {
            throw new Exception("The inventory can't be less than the minimum");
        }

        if(this.newProduct.getStock() > this.newProduct.getMax()) {
            throw new Exception("The inventory can't be greater than the maximum");
        }
    }

    private Runnable createProductTask() {
        return new Runnable() {
            @Override
            public void run() {
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
                    getInventory().updateProduct(newProduct);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    displayErrorScreen(e.getMessage());
                }
            }
        };
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
    private void setAvailablePartColumns() {
        this.searchableId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.searchableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.searchableInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.searchableCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.searchableCost.setCellValueFactory(cellData -> Bindings.format("%.2f", cellData.getValue().getPrice()));
    }

    private void setAssociatedPartColumns() {
        this.associatedId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.associatedName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.associatedInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.associatedCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.associatedCost.setCellValueFactory(cellData -> Bindings.format("%.2f", cellData.getValue().getPrice()));
    }

    /**
     * @todo need to separate associated part table and the part list
     */
    private void setAvailablePartColumnHeaders() {
        this.searchableTable.getColumns().add(searchableId);
        this.searchableTable.getColumns().add(searchableName);
        this.searchableTable.getColumns().add(searchableInvLevel);
        this.searchableTable.getColumns().add(searchableCost);
    }

    private void setAssociatedPartColumnHeaders() {
        this.associatedPartTable.getColumns().add(associatedId);
        this.associatedPartTable.getColumns().add(associatedName);
        this.associatedPartTable.getColumns().add(associatedInvLevel);
        this.associatedPartTable.getColumns().add(associatedCost);
    }

    /**
     * @todo need to separate associated part table and the part list
     */
    private void setAvailableInventoryToTable() {
        this.searchableTable.getColumns().clear();
        this.searchableTable.setItems(this.getInventory().getAllParts());
    }

    private void setAssociatedInventoryToTable() {
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

    @FXML
    public void addPartToProduct() throws Exception {
        Runnable task = this.createAddPartTask();
        new Thread(task).start();
    }

    @FXML
    public void addPartToProductModify() throws Exception {
        Runnable task = this.createAddPartTask();
        new Thread(task).start();
    }

    private Runnable createAddPartTask() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    Part part = searchableTable.getSelectionModel().getSelectedItem();
                    newProduct.addAssociatedPart(part);
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        };
    }

    public void setProduct(Product product) throws Exception {
        this.newProduct = new Product(product);

        this.setTextFieldValues();
        this.setAssociatedPartColumns();
        this.setAssociatedInventoryToTable();
        this.setAssociatedPartColumnHeaders();
    }

    private void setTextFieldValues() {
        this.id.setText(Integer.toString(this.newProduct.getId()));
        this.name.setText(this.newProduct.getName());
        this.price.setText(Double.toString(this.newProduct.getPrice()));
        this.inventory.setText(Integer.toString(this.newProduct.getStock()));
        this.min.setText(Integer.toString(this.newProduct.getMin()));
        this.max.setText(Integer.toString(this.newProduct.getMax()));
    }
}
