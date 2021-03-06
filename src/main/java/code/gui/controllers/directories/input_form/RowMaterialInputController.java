package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.RowMaterialController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.RowMaterialEntity;
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
public class RowMaterialInputController implements IControllerInput {
    @FXML private Button buttonOne;
    @FXML private TextField nameRawMaterial;

    private Stage thisStage;
    private RowMaterialController parentController;
    private RowMaterialEntity selectRowMaterial;

    @Override
    public void onAddClick(ActionEvent event) {
        if(!nameRawMaterial.getText().equals("")){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            RowMaterialEntity materialTypeEntity = new RowMaterialEntity();
            materialTypeEntity.setName(nameRawMaterial.getText());
            session.save(materialTypeEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Новое сырье успешно добавлено");
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
        selectRowMaterial.setName(nameRawMaterial.getText());
        session.update(selectRowMaterial);
        session.getTransaction().commit();
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cырье успешно изменено");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            thisStage.close();
            parentController.Update();
        }

    }

    private void chanhgeForm(){
        nameRawMaterial.setText(selectRowMaterial.getName());
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
    public void setParentController(RowMaterialController parentController) {
        this.parentController = parentController;
    }
    public void setSelectRowMaterial(RowMaterialEntity selectRowMaterial) {
        this.selectRowMaterial = selectRowMaterial;
        chanhgeForm();
    }
}
