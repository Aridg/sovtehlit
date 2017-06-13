package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.ContractsController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.ContractEntity;
import code.hibernate.directories.CustomersEntity;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.sql.Date;
import java.util.Optional;


/**
 * Created by Алексей on 10.06.2017.
 */
public class ContractInputController implements IControllerInput {
    @FXML private Button buttonOne;
    @FXML private TextField nameContract;
    @FXML private DatePicker date;

    private Stage thisStage;
    private CustomersEntity selectedCustomer;
    private ContractsController parentController;
    private ContractEntity selectContract;


    @Override
    public void onAddClick(ActionEvent event) {

        if(!nameContract.getText().equals("") && date!=null){
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
        selectContract.setDate(Date.valueOf(date.getValue()));
        selectContract.setName(nameContract.getText());
        session.update(selectContract);
        session.getTransaction().commit();
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Договор успешно изменен");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            thisStage.close();
            parentController.Update();
        }
    }

    public void chanhgeForm(){
        nameContract.setText(selectContract.getName());
        date.setValue(selectContract.getDateP());
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
    public void setSelectedCustomer(CustomersEntity selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }
    public void setParentController(ContractsController parent) {
        this.parentController = parent;
    }
    public void setSelectContract(ContractEntity selectContract) {
        this.selectContract = selectContract;
    }
}
