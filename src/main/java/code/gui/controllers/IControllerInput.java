package code.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 * Created by Asus on 11.06.2017.
 */
public interface IControllerInput extends IController {
    @FXML
    void onAddClick(ActionEvent event);
    @FXML
    void onAnnulmentClick(ActionEvent event);
    @FXML
    void onEditClick(ActionEvent event);
}
