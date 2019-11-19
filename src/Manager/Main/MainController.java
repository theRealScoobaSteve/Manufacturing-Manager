package Manager.Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Manager.AbstractController;

public class MainController extends AbstractController {
    public void addPart() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("../Part/AddPart.fxml").openStream());
            stage.setScene(new Scene(root));

            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

    public void modifyPart() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();

            Pane root = loader.load(getClass().getResource("../Part/ModifyPart.fxml").openStream());
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

}
