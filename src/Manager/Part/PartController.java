package Manager.Part;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import Manager.AbstractController;

import java.awt.event.ActionEvent;
import java.util.regex.Pattern;

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
    private TextField partType;
    @FXML
    private CheckBox inHouse;
    @FXML
    private CheckBox outSourced;
    @FXML
    private Button saveButton;
    @FXML
    private Label partTypeLabel;

    private String partName;

    private double partPrice;

    private int partMax;

    private int partMin;

    private int partMachineId;

    private String partCompanyName;

    private int partId;

    private final static String DOUBLE_PATTERN = "[0-9]+(\\.){0,1}[0-9]*";

    private final static String INTEGER_PATTERN = "\\d+";


    @FXML
    public void addPart() {
        try {
            this.setDefaultValues();

            if(this.inHouse.isSelected()) {
                this.setMachineId();
            }


            Runnable task = this.createPartTask();
            new Thread(task).start();

            this.closeAddPartScreen();
        } catch (Exception e) {
            System.out.print(e.getMessage());
            this.displayErrorScreen(e.getMessage());
        }
    }

    @FXML
    public void exitWindow() {
        this.closeAddPartScreen();
    }

    @FXML
    public void machineIdInput() {
        if(this.inHouse.isSelected()) {
            this.outSourced.setSelected(false);
            this.partType.setText("Machine ID");
            this.partTypeLabel.setText("Machine ID");
        }
    }

    @FXML
    public void companyNameInput() {
        if(this.outSourced.isSelected()) {
            this.inHouse.setSelected(false);
            this.partType.setText("Company Name");
            this.partTypeLabel.setText("Company Name");
        }
    }

    private Runnable createPartTask() {
        return new Runnable() {
            @Override
            public void run() {
                Part newPart;

                if(inHouse.isSelected()) {
                    newPart = new InHouse(
                        partId,
                        partName,
                        partPrice,
                        partMax,
                        partMin,
                        partMachineId
                    );
                } else {
                    newPart = new Outsourced(
                        partId,
                        partName,
                        partPrice,
                        partMax,
                        partMin,
                        partType.getText()
                    );
                }

                getInventory().addPart(newPart);
            }
        };
    }

    private void closeAddPartScreen() throws RuntimeException {
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }

    private void setDefaultValues() throws Exception {
        this.partId = getInventory().generatePartId();
        this.partName = name.getText();

        if(Pattern.matches(DOUBLE_PATTERN, this.price.getText())) {
            this.partPrice = Double.parseDouble(price.getText());
        } else {
            throw new Exception("Please enter a decimal point value for price");
        }

        if(Pattern.matches(INTEGER_PATTERN, this.max.getText())) {
            this.partMax = Integer.parseInt(max.getText());
        } else {
            throw new Exception("Please enter a valid maximum value");
        }

        if(Pattern.matches(INTEGER_PATTERN, this.min.getText())) {
            this.partMin = Integer.parseInt(min.getText());
        } else {
            throw new Exception("Please enter a valid minimum ");
        }

        if(this.partMax <= this.partMin) {
            throw new Exception("The maximum can't not be less than the minimum");
        } else if(this.partMin < 0 || this.partMax < 0) {
            throw new Exception("The minimum or maximum can't be negative");
        }
    }

    private void setMachineId() throws Exception {
        if(Pattern.matches(INTEGER_PATTERN, this.max.getText()) && this.inHouse.isSelected()) {
            this.partMachineId = Integer.parseInt(partType.getText());
        } else {
            throw new Exception("Please enter a valid machine ID ");
        }
    }
}
