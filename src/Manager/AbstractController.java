package Manager;

import Manager.Error.ErrorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.regex.Pattern;

public abstract class AbstractController {
    private static InventoryModel inventory;

    private final static String DOUBLE_PATTERN = "[0-9]+(\\.){0,1}[0-9]*";

    private final static String INTEGER_PATTERN = "\\d+";

    protected InventoryModel getInventory() {
        return inventory;
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

    protected double validateDoubleValue(String input, String label) throws Exception {
        if(Pattern.matches(DOUBLE_PATTERN, input)) {
            return Double.parseDouble(input);
        }

        throw new Exception(label + ": is not a valid decimal point value");
    }

    protected int validateIntegerValue(String input, String label) throws Exception {
        if(Pattern.matches(INTEGER_PATTERN, input)) {
            return Integer.parseInt(input);
        }

        throw new Exception(label + ": is not a valid integer value");
    }
}
