package code.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by Asus on 11.06.2017.
 */
public abstract class IControllerInput implements IController {
    @FXML
    protected abstract void onAddClick(ActionEvent event);
    @FXML
    protected abstract void onAnnulmentClick(ActionEvent event);
}
