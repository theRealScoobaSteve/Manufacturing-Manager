package Manager.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Manager.Error.ErrorController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("MainScreen.fxml").openStream());
        primaryStage.setTitle("Manager");
        primaryStage.setScene(new Scene(root, 1200, 500));
//        ((MainController)loader.getController()).initialize();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
