package Manager.Part;

import javafx.application.Platform;
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
    private TextField partType;
    @FXML
    private CheckBox inHouse;
    @FXML
    private CheckBox outSourced;
    @FXML
    private Button saveButton;
    @FXML
    private Button modifyButton;
    @FXML
    private Label partTypeLabel;
    @FXML
    private TextField id;
    @FXML
    private TextField inventory;

    private String partName;

    private double partPrice;

    private int partMax;

    private int partMin;

    private int partMachineId;

    private String partCompanyName;

    private int partId;

    private Part newPart;

    private int partInventory;

    @FXML
    public void addPart() {
        try {
            this.setDefaultValues();
            this.validateDefaultFields();

            this.partId   = this.getInventory().generatePartId();
            Runnable task = this.createPartTask();

            new Thread(task).start();
            this.closeAddPartScreen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.displayErrorScreen(e.getMessage());
        }
    }

    @FXML
    public void modifyPart() {
        try {
            this.setDefaultValues();
            this.validateDefaultFields();

            this.partId   = Integer.parseInt(this.id.getText());
            Runnable task = this.modifyPartTask();

            new Thread(task).start();
            this.closeModifyPartScreen();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.displayErrorScreen(e.getMessage());
        }
    }

    @FXML
    public void exitAddPartWindow() {
        this.closeAddPartScreen();
    }

    @FXML
    public void exitModifyPartWindow() {
        this.closeModifyPartScreen();
    }

    @FXML
    public void machineIdInput() {
        if(this.inHouse.isSelected()) {
            this.outSourced.setSelected(false);
            this.inHouse.setDisable(true);
            this.outSourced.setDisable(false);
            this.partType.setText("Machine ID");
            this.partTypeLabel.setText("Machine ID");
        }
    }

    @FXML
    public void companyNameInput() {
        if(this.outSourced.isSelected()) {
            this.inHouse.setSelected(false);
            this.outSourced.setDisable(true);
            this.inHouse.setDisable(false);
            this.partType.setText("Company Name");
            this.partTypeLabel.setText("Company Name");
        }
    }

    private Runnable createPartTask() {
        return new Runnable() {
            @Override
            public void run() {
                generatePart();
                getInventory().addPart(newPart);
            }
        };
    }

    private Runnable modifyPartTask() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    generatePart();
                    getInventory().updatePart(newPart);

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    displayErrorScreen(e.getMessage());
                }
            }
        };
    }

    private void closeAddPartScreen() {
        Stage stage = (Stage) this.saveButton.getScene().getWindow();
        stage.close();
    }

    private void closeModifyPartScreen() {
        Stage stage = (Stage) this.modifyButton.getScene().getWindow();
        stage.close();
    }

    private void generatePart() {
        if(inHouse.isSelected()) {
            newPart = new InHouse(
                    partId,
                    partName,
                    partPrice,
                    partMax,
                    partMin,
                    partInventory,
                    partMachineId
            );
        } else {
            newPart = new Outsourced(
                    partId,
                    partName,
                    partPrice,
                    partMax,
                    partMin,
                    partInventory,
                    partType.getText()
            );
        }
    }
    private void validateDefaultFields() throws Exception {
        if(this.partMax <= this.partMin) {
            throw new Exception("The maximum can't not be less than the minimum");
        } else if(this.partMin < 0 || this.partMax < 0) {
            throw new Exception("The minimum or maximum can't be negative");
        }

        if(this.partInventory < this.partMin) {
            throw new Exception("The inventory can't be less than the minimum");
        }

        if(this.partInventory > this.partMax) {
            throw new Exception("The inventory can't be greater than the maximum");
        }

        if(this.inHouse.isSelected()) {
            this.partMachineId = this.validateIntegerValue(this.partType.getText(), "Machine ID");
        }
    }

    private void setDefaultValues() throws Exception {
        this.partName      = this.name.getText();
        this.partPrice     = this.validateDoubleValue(this.price.getText(), "Price");
        this.partMax       = this.validateIntegerValue(this.max.getText(), "Maximum");
        this.partMin       = this.validateIntegerValue(this.min.getText(), "Minimum");
        this.partInventory = this.validateIntegerValue(this.inventory.getText(), "Inventory");
    }

    public void setPart(Part part) throws Exception {
        this.newPart = part;
        this.setTextFieldValues(part);
    }

    private void setTextFieldValues(Part part) throws Exception {
        this.id.setText(Integer.toString(part.getId()));
        this.name.setText(part.getName());
        this.price.setText(Double.toString(part.getPrice()));
        this.inventory.setText(Integer.toString(part.getStock()));
        this.min.setText(Integer.toString(part.getMin()));
        this.max.setText(Integer.toString(part.getMax()));

        if(part instanceof InHouse) {
            this.partType.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else {
            this.partType.setText(((Outsourced) part).getCompanyName());
        }
    }

}
