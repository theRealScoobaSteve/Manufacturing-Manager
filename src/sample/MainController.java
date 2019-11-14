package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController extends AbstractController {
    public void addPart() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("AddPart.fxml").openStream());
//            PartController controller = (PartController) loader.getController();
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

            Pane root = loader.load(getClass().getResource("ModifyPart.fxml").openStream());
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }

}
