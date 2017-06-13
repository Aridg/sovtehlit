package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.MaterialTypeController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.CustomersEntity;
import code.hibernate.directories.MaterialTypeEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.Optional;

/**
 * Created by Asus on 11.06.2017.
 */
public class MaterialTypeInputController implements IControllerInput {

    @FXML private TextField nameMaterialType;

    private Stage thisStage;
    private MaterialTypeController parentController;

    @Override
    public void onAddClick(ActionEvent event) {
        if(!nameMaterialType.getText().equals("")){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaterialTypeEntity materialTypeEntity = new MaterialTypeEntity();
            materialTypeEntity.setName(nameMaterialType.getText());
            session.save(materialTypeEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Новый тип материала успешно добавлен");
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

    public void setParentController(MaterialTypeController parentController) {
        this.parentController = parentController;
    }
}
