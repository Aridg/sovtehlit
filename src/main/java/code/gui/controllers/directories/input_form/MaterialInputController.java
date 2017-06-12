package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.MaterialTypeEntity;
import code.hibernate.directories.views.MaterialVEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Asus on 11.06.2017.
 */
public class MaterialInputController implements IControllerInput {
    @FXML private TextField nameMaterial;
    @FXML private ChoiceBox<MaterialTypeEntity> materialTypes;

    private ObservableList<MaterialTypeEntity> materialTypeModels = FXCollections.observableArrayList();
    private Stage thisStage;


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
        try{
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaterialVEntity materialVEntity = new MaterialVEntity();
            materialVEntity.setName(nameMaterial.getText());
            materialVEntity.setType(materialTypes.getSelectionModel().getSelectedItem().getName());
            session.save(materialVEntity);
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
