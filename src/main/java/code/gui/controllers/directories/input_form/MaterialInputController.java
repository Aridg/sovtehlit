package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.hibernate.directories.MaterialTypeEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * Created by Asus on 11.06.2017.
 */
public class MaterialInputController implements IControllerInput {
    @FXML private TextField nameMaterial;
    @FXML private ChoiceBox<MaterialTypeEntity> materialTypes;

    @Override
    public void onAddClick(ActionEvent event) {

    }

    @Override
    public void onAnnulmentClick(ActionEvent event) {

    }
}
