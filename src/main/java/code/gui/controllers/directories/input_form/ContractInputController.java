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
public class ContractInputController extends IControllerInput {


    @FXML private TextField nameContract;
    @FXML private ComboBox<CustomersEntity> customers;
    @FXML private DatePicker date;

    @Override
    protected void onAddClick(ActionEvent event) {

    }

    @Override
    protected void onAnnulmentClick(ActionEvent event) {

    }
}
