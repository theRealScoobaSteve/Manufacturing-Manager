package Manager.Main;

import Manager.Part.InHouse;
import Manager.Part.Part;
import Manager.Part.PartController;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Manager.AbstractController;

public class MainController extends AbstractController {
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

    public void modifyPart() {
        try {
            Stage stage               = new Stage();
            FXMLLoader loader         = new FXMLLoader();
            Pane root                 = loader.load(getClass().getResource("../Part/ModifyPart.fxml").openStream());
            PartController controller = loader.getController();

            Part part = partTable.getSelectionModel().getSelectedItem();
            controller.setPart(part);
            stage.setScene(new Scene(root));

            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() throws Exception {
        this.loadInitalData();
        FilteredList<Part> filteredData = new FilteredList<>(this.getInventory().getAllParts(), p -> true);
        SortedList<Part> sortedData     = new SortedList<>(filteredData);

        this.setPartColumnHeaders();
        this.addInventoryToTable();
        this.addPartColumnsToTable();
        this.addPartFilterListener(filteredData);
        this.bindPropertyToTable(sortedData);
    }

    private void setPartColumnHeaders() {
        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        partCost.setCellValueFactory(cellData -> Bindings.format("%.2f", cellData.getValue().getPrice()));
    }

    private void addPartColumnsToTable() {
        partTable.getColumns().add(partId);
        partTable.getColumns().add(partName);
        partTable.getColumns().add(partInvLevel);
        partTable.getColumns().add(partCost);
    }

    private void addInventoryToTable() {
        partTable.getColumns().clear();
        partTable.setItems(this.getInventory().getAllParts());
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

    private void bindPropertyToTable(SortedList<Part> sortedData) {
        sortedData.comparatorProperty().bind(this.partTable.comparatorProperty());
        partTable.setItems(sortedData);
    }

    private void loadInitalData() throws Exception {
        InHouse part1 = new InHouse(this.getInventory().generatePartId(), "Part 1", 499.99, 50, 500, 373, 304);
        InHouse part2 = new InHouse(this.getInventory().generatePartId(), "Part 2", 36.39, 1000, 5000, 6, 808);
        InHouse part3 = new InHouse(this.getInventory().generatePartId(), "Part 3", 243.6, 2000, 4000, 6, 206);

        this.getInventory().addPart(part1).addPart(part2).addPart(part3);
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
