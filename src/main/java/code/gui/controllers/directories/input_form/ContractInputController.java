package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.hibernate.directories.CustomersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


/**
 * Created by Алексей on 10.06.2017.
 */
public class ContractInputController implements IControllerInput {


    @FXML private TextField nameContract;
    @FXML private ComboBox<CustomersEntity> customers;
    @FXML private DatePicker date;

    @Override
    public void onAddClick(ActionEvent event) {

    }

    @Override
    public void onAnnulmentClick(ActionEvent event) {

    }
}