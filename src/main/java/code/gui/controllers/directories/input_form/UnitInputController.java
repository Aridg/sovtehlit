package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created by Asus on 11.06.2017.
 */
public class UnitInputController implements IControllerInput {


    @FXML private TextField nameUnit;
    @FXML private TextField factor;

    @Override
    public void onAddClick(ActionEvent event) {

    }

    @Override
    public void onAnnulmentClick(ActionEvent event) {

    }
}
