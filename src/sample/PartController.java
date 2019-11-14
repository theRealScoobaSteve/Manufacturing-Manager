package sample;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class PartController extends AbstractController {
    @FXML
    private TextField name;
    @FXML
    private TextField price;
    @FXML
    private TextField max;
    @FXML
    private TextField min;
    @FXML
    private TextField companyName;
    @FXML
    private TextField machineId;
    @FXML
    private CheckBox inHouse;
    @FXML
    private CheckBox outSourced;
    @FXML
    private Button saveButton;

    @FXML
    public void addPart() {
        new Thread(this.createPartTask()).start();
        this.closeAddPartScreen();
    }

    @FXML
    public void exitWindow() {
        this.closeAddPartScreen();
    }

    private Runnable createPartTask() {
        return new Runnable() {
            @Override
            public void run() {
                Part newPart;

                if(inHouse.isSelected()) {
                    newPart = new InHouse(
                            getInventory().generatePartId(),
                            name.getText(),
                            Double.parseDouble(price.getText()),
                            Integer.parseInt(max.getText()),
                            Integer.parseInt(min.getText()),
                            Integer.parseInt(machineId.getText())
                    );
                } else {
                    newPart = new Outsourced(
                            getInventory().generatePartId(),
                            name.getText(),
                            Double.parseDouble(price.getText()),
                            Integer.parseInt(max.getText()),
                            Integer.parseInt(min.getText()),
                            companyName.getText()
                    );
                }

                getInventory().addPart(newPart);
            }
        };
    }

    private void closeAddPartScreen() {
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }
}
