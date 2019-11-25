package Manager.Main;

import Manager.Part.InHouse;
import Manager.Part.Part;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
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

    public void initialize() throws Exception {
        this.loadInitalData();
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

    public void removePart() throws Exception {
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
