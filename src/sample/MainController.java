package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
}
