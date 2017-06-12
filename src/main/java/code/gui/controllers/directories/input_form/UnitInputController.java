package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.UnitsEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

/**
 * Created by Asus on 11.06.2017.
 */
public class UnitInputController implements IControllerInput {


    @FXML private TextField nameUnit;
    @FXML private TextField factor;
    private Stage thisStage;

    @Override
    public void onAddClick(ActionEvent event) {
        try{
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            UnitsEntity unitEntity = new UnitsEntity();
            unitEntity.setName(nameUnit.getText());
            unitEntity.setCoefficient(Double.parseDouble(factor.getText()));
            session.save(unitEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Новый договор успешно добавлен");
            alert.showAndWait();
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не все параметры указаны");
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
}
