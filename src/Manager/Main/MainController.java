package Manager.Main;

import Manager.Part.InHouse;
import Manager.Part.Part;
import Manager.Part.PartController;
import Manager.Product.Product;
import Manager.Product.ProductController;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Manager.AbstractController;

public class MainController extends AbstractController {
    @FXML
    private Button exitBtn;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, String> partId;
    @FXML
    private TableColumn<Part, String> partName;
    @FXML
    private TableColumn<Part, String> partInvLevel;
    @FXML
    private TableColumn<Part, String> partCost;
    @FXML
    private TextField partFilter;
    @FXML
    private TextField productFilter;
    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, String> productId;
    @FXML
    private TableColumn<Product, String> productName;
    @FXML
    private TableColumn<Product, String> productInvLevel;
    @FXML
    private TableColumn<Product, String> productCost;

    @FXML
    public void addPart() {
        try {
            Stage stage       = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root         = loader.load(getClass().getResource("../Part/AddPart.fxml").openStream());

            stage.setScene(new Scene(root));

            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    public void modifyPart() {
        try {
            Stage stage               = new Stage();
            FXMLLoader loader         = new FXMLLoader();
            Pane root                 = loader.load(getClass().getResource("../Part/ModifyPart.fxml").openStream());
            PartController controller = loader.getController();

            Part part = this.partTable.getSelectionModel().getSelectedItem();
            controller.setPart(part);
            stage.setScene(new Scene(root));

            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void addProduct() {
        try {
            Stage stage       = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root         = loader.load(getClass().getResource("../Product/AddProduct.fxml").openStream());

            stage.setScene(new Scene(root));

            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void modifyProduct() {
        try {
            Stage stage                  = new Stage();
            FXMLLoader loader            = new FXMLLoader();
            Pane root                    = loader.load(getClass().getResource("../Product/ModifyProduct.fxml").openStream());
            ProductController controller = loader.getController();

            Product product = this.productTable.getSelectionModel().getSelectedItem();
            controller.setProduct(product);
            stage.setScene(new Scene(root));

            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void removeProduct() {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run(){
                    Product product = productTable.getSelectionModel().getSelectedItem();

                    getInventory().removeProduct(product);
                }
            };

            new Thread(task).start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.displayErrorScreen(e.getMessage());
        }
    }

    @FXML
    public void initialize() throws Exception {
        this.loadInitalData();
        exitBtn.setOnAction(e -> Platform.exit());
        FilteredList<Part> filteredPartData       = new FilteredList<>(this.getInventory().getAllParts(), p -> true);
        SortedList<Part> sortedPartData           = new SortedList<>(filteredPartData);
        FilteredList<Product> filteredProductData = new FilteredList<>(this.getInventory().getAllProducts(), p -> true);
        SortedList<Product> sortedProductData     = new SortedList<>(filteredProductData);

        this.setPartColumnHeaders();
        this.addInventoryToTable();
        this.addPartColumnsToTable();
        this.addPartFilterListener(filteredPartData);
        this.bindPartPropertyToTable(sortedPartData);
        this.addProductFilterListener(filteredProductData);
        this.bindProductPropertyToTable(sortedProductData);
    }

    private void setPartColumnHeaders() {
        this.partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.partCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.partCost.setCellValueFactory(cellData -> Bindings.format("%.2f", cellData.getValue().getPrice()));
        this.productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.productInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        this.productCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        this.productCost.setCellValueFactory(cellData -> Bindings.format("%.2f", cellData.getValue().getPrice()));
    }

    private void addPartColumnsToTable() {
        this.partTable.getColumns().add(this.partId);
        this.partTable.getColumns().add(this.partName);
        this.partTable.getColumns().add(this.partInvLevel);
        this.partTable.getColumns().add(this.partCost);
        this.productTable.getColumns().add(this.productId);
        this.productTable.getColumns().add(this.productName);
        this.productTable.getColumns().add(this.productInvLevel);
        this.productTable.getColumns().add(this.productCost);
    }

    private void addInventoryToTable() {
        this.partTable.getColumns().clear();
        this.partTable.setItems(this.getInventory().getAllParts());
        this.productTable.getColumns().clear();
        this.productTable.setItems(this.getInventory().getAllProducts());
    }

    private void addPartFilterListener(FilteredList<Part> filteredData) {
        partFilter.textProperty().addListener((observable, oldValue, newValue) -> {
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

    private void addProductFilterListener(FilteredList<Product> filteredData) {
        productFilter.textProperty().addListener((observable, oldValue, newValue) -> {
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

    private void bindPartPropertyToTable(SortedList<Part> sortedData) {
        sortedData.comparatorProperty().bind(this.partTable.comparatorProperty());
        partTable.setItems(sortedData);
    }

    private void bindProductPropertyToTable(SortedList<Product> sortedData) {
        sortedData.comparatorProperty().bind(this.productTable.comparatorProperty());
        productTable.setItems(sortedData);
    }

    private void loadInitalData() throws Exception {
        InHouse part1 = new InHouse(this.getInventory().generatePartId(), "Part 1", 499.99, 50, 500, 373, 304);
        InHouse part2 = new InHouse(this.getInventory().generatePartId(), "Part 2", 36.39, 1000, 5000, 6, 808);
        InHouse part3 = new InHouse(this.getInventory().generatePartId(), "Part 3", 243.6, 2000, 4000, 3000, 206);

        this.getInventory().addPart(part1).addPart(part2).addPart(part3);

        Product product1 = new Product(this.getInventory().generateProductId(), "Product 1", 499.99, 50, 1, 373);
        Product product2 = new Product(this.getInventory().generateProductId(), "Product 2", 36.39, 1000, 6, 5000);
        Product product3 = new Product(this.getInventory().generateProductId(), "Product 3", 243.6, 2000, 3000, 4000);

        product1.addAssociatedPart(part1);

        this.getInventory().addProduct(product1).addProduct(product2).addProduct(product3);
    }

    @FXML
    public void removePart() {
        try {
            Runnable task = new Runnable() {
                @Override
                public void run(){
                    Part part = partTable.getSelectionModel().getSelectedItem();

                    getInventory().deletePart(part);
                }
            };

            new Thread(task).start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.displayErrorScreen(e.getMessage());
        }
    }
}
