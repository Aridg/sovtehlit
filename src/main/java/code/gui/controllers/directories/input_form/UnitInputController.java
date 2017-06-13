package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.UnitMeasurementController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.UnitsEntity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.Optional;

/**
 * Created by Asus on 11.06.2017.
 */
public class UnitInputController implements IControllerInput {
    @FXML private Button buttonOne;
    @FXML private TextField nameUnit;
    @FXML private TextField factor;

    private Stage thisStage;
    private UnitMeasurementController parentController;
    private UnitsEntity selectUnit;


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

    @Override
    public void onEditClick(ActionEvent event) {
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        selectUnit.setName(nameUnit.getText());
        selectUnit.setCoefficient(Double.parseDouble(factor.getText()));
        session.update(selectUnit);
        session.getTransaction().commit();
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Единица измерения успешна изменена");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            thisStage.close();
            parentController.Update();
        }
    }


    public void chanhgeForm(){
        nameUnit.setText(selectUnit.getName());
        factor.setText(String.valueOf(selectUnit.getCoefficient()));
        buttonOne.setText("Изменить");
        buttonOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onEditClick(event);
            }
        });
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setParentController(UnitMeasurementController parentController) {
        this.parentController = parentController;
    }

    public void setSelectUnit(UnitsEntity selectUnit) {
        this.selectUnit = selectUnit;
    }
}
