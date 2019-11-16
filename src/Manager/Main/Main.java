package Manager.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Manager.Error.ErrorController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(Main::showError);

        Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        primaryStage.setTitle("Manager");
        primaryStage.setScene(new Scene(root, 1200, 500));
        primaryStage.show();
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

    private static void showError(Thread thread, Throwable e) {
        System.err.println("***Default exception handler***");

        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in " + thread);

        }
    }

    private static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();

        e.printStackTrace(new PrintWriter(errorMsg));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("../Error/ErrorScreen.fxml"));
        try {
            Parent root = loader.load();
            ((ErrorController)loader.getController()).setErrorText(e.getMessage());
            dialog.setScene(new Scene(root, 600, 200));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
