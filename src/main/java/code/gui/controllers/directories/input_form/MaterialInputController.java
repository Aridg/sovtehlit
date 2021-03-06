package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.MaterialsController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.MaterialTypeEntity;
import code.hibernate.directories.MaterialsEntity;
import code.hibernate.directories.views.MaterialVEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/**
 * Created by Asus on 11.06.2017.
 */
public class MaterialInputController implements IControllerInput {
    @FXML private Button buttonOne;
    @FXML private TextField nameMaterial;
    @FXML private ComboBox<MaterialTypeEntity> materialTypes;

    private ObservableList<MaterialTypeEntity> materialTypeModels = FXCollections.observableArrayList();
    private Stage thisStage;
    private MaterialsController parentController;
    private MaterialsEntity selectMaterial;


    @FXML
    private void initialize() {
        Session session = HibernateSessionFactory.getSession();
        List<MaterialTypeEntity> list = session.createQuery("from MaterialTypeEntity ", MaterialTypeEntity.class)
                .getResultList();
        materialTypeModels.addAll(list);
        materialTypes.setItems(materialTypeModels);
        session.close();
    }


    @Override
    public void onAddClick(ActionEvent event) {
        if(!nameMaterial.getText().equals("") && materialTypes.getSelectionModel().getSelectedItem()!=null){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaterialsEntity materialEntity = new MaterialsEntity();
            materialEntity.setName(nameMaterial.getText());
            materialEntity.setType(materialTypes.getSelectionModel().getSelectedItem().getId());
            session.save(materialEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Новый материал успешно добавлен");
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
        selectMaterial.setName(nameMaterial.getText());
        selectMaterial.setType(materialTypes.getSelectionModel().getSelectedItem().getId());
        session.update(selectMaterial);
        session.getTransaction().commit();
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Материал успешно изменен");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            thisStage.close();
            parentController.Update();
        }
    }

    private void chanhgeForm(){
        Session session = HibernateSessionFactory.getSession();
        MaterialTypeEntity current =  session.createQuery("from MaterialTypeEntity where id=:id",MaterialTypeEntity.class).setParameter("id",selectMaterial.getType()).getSingleResult();
        session.close();
        nameMaterial.setText(selectMaterial.getName());
        materialTypes.getSelectionModel().select(current);
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
    public void setParentController(MaterialsController parentController) {
        this.parentController = parentController;
    }
    public void setSelectMaterial(MaterialsEntity selectMaterial) {

        this.selectMaterial = selectMaterial;
        chanhgeForm();
    }
}
