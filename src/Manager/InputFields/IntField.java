package Manager.InputFields;

import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;

public class IntField extends TextField {
    public IntField() {

    }

    @Override
    public void replaceText(IndexRange range, String text) {
        if (text.matches("[0-9]") || text.isEmpty()) {
            super.replaceText(range, text);
        }
    }
}
