package Manager.Part;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import Manager.AbstractController;

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
    private Label errorMessage;


    @FXML
    public void addPart() throws Exception {
//        this.validateInput();
        try {
//            Runnable task = this.createPartTask();
//            new Thread(task).start();
            throw new Exception("Test");
        } catch(RuntimeException e) {
            System.out.print("HERE 2");

        }

        this.closeAddPartScreen();
    }

    @FXML
    public void exitWindow() {
        this.closeAddPartScreen();
    }

    private Runnable createPartTask() throws RuntimeException {
        return new Runnable() {
            @Override
            public void run() throws RuntimeException {
                try {
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
                } catch(Exception e)  {
                    System.out.print("HERE");
                    throw new RuntimeException(e.getMessage());
                }

            }
        };
    }

    private void closeAddPartScreen() throws RuntimeException {
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }

//    private void validateInput() {
//        if(name.getText() != null) {
//            throw new RuntimeException("Name can not be empty");
//        } else if(price.)
//    }
}
