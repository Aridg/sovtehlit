package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.CustomersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

/**
 * Created by Asus on 11.06.2017.
 */
public class CustomersInputController implements IControllerInput {

    @FXML
    private TextField nameCustomer;

    private Stage thisStage;

    @Override
    public void onAddClick(ActionEvent event) {
        try{
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            CustomersEntity customersEntity = new CustomersEntity();
            customersEntity.setName(nameCustomer.getText());
            session.save(customersEntity);
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
