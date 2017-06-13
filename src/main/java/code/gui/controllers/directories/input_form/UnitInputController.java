package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.UnitMeasurementController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.UnitsEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.Optional;

/**
 * Created by Asus on 11.06.2017.
 */
public class UnitInputController implements IControllerInput {


    @FXML private TextField nameUnit;
    @FXML private TextField factor;

    private Stage thisStage;
    private UnitMeasurementController parentController;

    @Override
    public void onAddClick(ActionEvent event) {
        if(!nameUnit.getText().equals("") && !factor.getText().equals("")){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            UnitsEntity unitEntity = new UnitsEntity();
            unitEntity.setName(nameUnit.getText());
            unitEntity.setCoefficient(Double.parseDouble(factor.getText()));
            session.save(unitEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Новая единица измерения успешно добавлена");
            alert.setTitle("OK!");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                thisStage.close();
                parentController.Update();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не все параметры указаны");
            alert.setHeaderText(null);
            alert.setTitle("ERROR!");
            alert.showAndWait();
        }
    }

    @Override
    public void onAnnulmentClick(ActionEvent event) {
        thisStage.close();
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setParentController(UnitMeasurementController parentController) {
        this.parentController = parentController;
    }
}
