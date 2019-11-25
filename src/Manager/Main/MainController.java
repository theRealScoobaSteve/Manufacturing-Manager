package Manager.Main;

import Manager.Part.InHouse;
import Manager.Part.Part;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Manager.AbstractController;

import java.util.Iterator;

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
            Stage stage       = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root         = loader.load(getClass().getResource("../Part/ModifyPart.fxml").openStream());

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

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTable.getColumns().clear();
        partTable.setItems(this.getInventory().getAllParts());
        partTable.getColumns().add(partId);
        partTable.getColumns().add(partName);
        partTable.getColumns().add(partInvLevel);
        partTable.getColumns().add(partCost);
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
        SortedList<Part> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(this.partTable.comparatorProperty());
        partTable.setItems(sortedData);
    }

    private void loadInitalData() throws Exception {
        InHouse part1 = new InHouse(this.getInventory().generatePartId(), "Part 1", 20.0, 5, 10, 6, 808);
        InHouse part2 = new InHouse(this.getInventory().generatePartId(), "Part 2", 20.0, 5, 10, 6, 808);
        InHouse part3 = new InHouse(this.getInventory().generatePartId(), "Part 3", 20.0, 5, 10, 6, 808);
        InHouse part4 = new InHouse(this.getInventory().generatePartId(), "Part 4", 20.0, 5, 10, 6, 808);
        InHouse part5 = new InHouse(this.getInventory().generatePartId(), "Part 5", 20.0, 5, 10, 6, 808);
        InHouse part6 = new InHouse(this.getInventory().generatePartId(), "Part 6", 20.0, 5, 10, 6, 808);

        this.getInventory().addPart(part1).addPart(part2).addPart(part3).addPart(part4).addPart(part5).addPart(part6);
    }

    /**
     * @todo break this function down
     * @throws Exception
     */
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
