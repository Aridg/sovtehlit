package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.ContractEntity;
import code.hibernate.directories.CustomersEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.sql.Date;


/**
 * Created by Алексей on 10.06.2017.
 */
public class ContractInputController implements IControllerInput {


    @FXML private TextField nameContract;
    @FXML private DatePicker date;

    private Stage thisStage;
    private CustomersEntity selectedCustomer;


    @Override
    public void onAddClick(ActionEvent event) {

        try{
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            ContractEntity contractEntity = new ContractEntity();
            contractEntity.setName(nameContract.getText());
            contractEntity.setDate(Date.valueOf(date.getValue()));
            contractEntity.setCustomerId(selectedCustomer.getId());
            session.save(contractEntity);
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
    public void setSelectedCustomer(CustomersEntity selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
}
