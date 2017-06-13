package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.CustomersController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.CustomersEntity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.Optional;

/**
 * Created by Asus on 11.06.2017.
 */
public class CustomersInputController implements IControllerInput {

    @FXML
    private Button buttonOne;
    @FXML
    private TextField nameCustomer;

    private Stage thisStage;
    private CustomersController parentController;
    private CustomersEntity selectCutomer;

    @Override
    public void onAddClick(ActionEvent event) {
        if(!nameCustomer.getText().equals("")){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            CustomersEntity customersEntity = new CustomersEntity();
            customersEntity.setName(nameCustomer.getText());
            session.save(customersEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Новый заказчик успешно добавлен");
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
        selectCutomer.setName(nameCustomer.getText());
        session.update(selectCutomer);
        session.getTransaction().commit();
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Информация успешна изменена");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            thisStage.close();
            parentController.Update();
        }
    }

    private void chanhgeForm(){
        nameCustomer.setText(selectCutomer.getName());
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
    public void setParentController(CustomersController parentController) {
        this.parentController = parentController;
    }
    public void setSelectCutomer(CustomersEntity selectCutomer) {

        this.selectCutomer = selectCutomer;
        chanhgeForm();
    }
}
