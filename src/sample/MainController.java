package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainController {
    public void addPart() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("AddPart.fxml"));
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void modifyPart() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("ModifyPart.fxml"));
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

}
