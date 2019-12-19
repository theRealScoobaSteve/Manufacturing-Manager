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

    private Part newPart;

    // Must hard code this in order to get around static inventory model
    private int lastPartId = 2;

    @FXML
    public void addPart() {
        try {
            this.generatePart();
            this.setDefaultValues();
            this.validateDefaultFields();

            this.newPart.setId(++lastPartId);
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
            this.generatePart();
            this.setDefaultValues();
            this.validateDefaultFields();

            this.newPart.setId(Integer.parseInt(this.id.getText()));
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
                getInventory().addPart(newPart);
            }
        };
    }

    private Runnable modifyPartTask() {
        return new Runnable() {
            @Override
            public void run() {
                try {
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
            newPart = new InHouse();
        } else {
            newPart = new Outsourced();
        }
    }

    private void validateDefaultFields() throws Exception {
        if(this.newPart.getMax() <= this.newPart.getMin()) {
            throw new Exception("The maximum can't not be less than the minimum");
        }

        if(this.newPart.getMin() < 0 || this.newPart.getMax() < 0) {
            throw new Exception("The minimum or maximum can't be negative");
        }

        if(this.newPart.getStock() < this.newPart.getMin()) {
            throw new Exception("The inventory can't be less than the minimum");
        }

        if(this.newPart.getStock() > this.newPart.getMax()) {
            throw new Exception("The inventory can't be greater than the maximum");
        }
    }

    private void setDefaultValues() throws Exception {
        this.newPart
                .setName(this.name.getText())
                .setPrice(this.validateDoubleValue(this.price.getText(), "Price"))
                .setMax(this.validateIntegerValue(this.max.getText(), "Maximum"))
                .setMin(this.validateIntegerValue(this.min.getText(), "Minimum"))
                .setStock(this.validateIntegerValue(this.inventory.getText(), "Inventory"));

        if(this.inHouse.isSelected()) {
            ((InHouse) this.newPart).setMachineId(this.validateIntegerValue(this.partType.getText(), "Machine ID"));
        } else {
            ((Outsourced) this.newPart).setCompanyName(this.partType.getText());
        }
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
