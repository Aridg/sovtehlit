package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.MaterialTypeInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.MaterialTypeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.util.Optional;

/**
 * Created by Алексей on 01.05.2017.
 */
public class MaterialTypeController extends IDirectoryController{
    @FXML private TableView<MaterialTypeEntity> materialTypeTable;
    @FXML private TableColumn<MaterialTypeEntity, String> nameColumn;

    private ObservableList<MaterialTypeEntity> materialTypeModels = FXCollections.observableArrayList();

    public MaterialTypeController() {
        Session session = HibernateSessionFactory.getSession();
        materialTypeModels.addAll(session.createQuery("from MaterialTypeEntity ", MaterialTypeEntity.class)
                .getResultList());
        session.close();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        materialTypeTable.setItems(materialTypeModels);
        materialTypeTable.setColumnResizePolicy(param -> false);
    }

    @Override
    protected void onAddClick(ActionEvent event) {
        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, MaterialTypeInputController> form  = new GuiForm<AnchorPane, MaterialTypeInputController>(MenuType.MATERIAL_TYPE_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(materialTypeTable.getScene().getWindow());
        stage.setTitle("Добавление типа материала");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        stage.showAndWait();

    }

    @Override
    protected void onEditClick(ActionEvent event) {
        int selectedIndex = materialTypeTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для изменения");
            alert.showAndWait();
            return;
        }
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        MaterialTypeEntity elem = session.createQuery("from MaterialTypeEntity where id = :id", MaterialTypeEntity.class)
                .setParameter("id", materialTypeTable.getSelectionModel().getSelectedItem().getId())
                .getSingleResult();
        session.close();

        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, MaterialTypeInputController> form  = new GuiForm<AnchorPane, MaterialTypeInputController>(MenuType.MATERIAL_TYPE_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(materialTypeTable.getScene().getWindow());
        stage.setTitle("Изменение типа материала");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        form.getController().setSelectMaterialType(elem);
        stage.showAndWait();
    }

    @Override
    protected void onDelClick(ActionEvent event) {
        Alert alertApproval = new Alert(Alert.AlertType.WARNING, "Вы точно хотите удалить выбранный объект?");
        alertApproval.setTitle("WARNING!");
        alertApproval.setHeaderText(null);
        Optional<ButtonType> result = alertApproval.showAndWait();
        if(result.get() == ButtonType.OK) {
            int selectedIndex = materialTypeTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для удаления");
                alert.showAndWait();
                return;
            }
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaterialTypeEntity elem = session.createQuery("from MaterialTypeEntity where id = :id", MaterialTypeEntity.class)
                    .setParameter("id", materialTypeTable.getSelectionModel().getSelectedItem().getId())
                    .getSingleResult();
            session.delete(elem);
            session.getTransaction().commit();
            materialTypeTable.getItems().remove(materialTypeTable.getSelectionModel().getSelectedItem());
            session.close();
        }
    }

    @Override
    protected void onUpdateClick(ActionEvent event) {
        materialTypeModels.clear();
        Session session = HibernateSessionFactory.getSession();
        materialTypeModels.addAll(session.createQuery("from MaterialTypeEntity ", MaterialTypeEntity.class)
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    public void Update(){
        materialTypeModels.clear();
        Session session = HibernateSessionFactory.getSession();
        materialTypeModels.addAll(session.createQuery("from MaterialTypeEntity ", MaterialTypeEntity.class)
                .getResultList());
        session.close();
    }

}
