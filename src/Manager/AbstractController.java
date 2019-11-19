package Manager;

import Manager.Error.ErrorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractController {
    private InventoryModel inventory;

    protected InventoryModel getInventory() {
        return inventory.getInstance();
    }

    protected void displayErrorScreen(String text) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("../Error/ErrorScreen.fxml").openStream());

            ((ErrorController)loader.getController()).setErrorText(text);
            stage.setScene(new Scene(root));

            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
