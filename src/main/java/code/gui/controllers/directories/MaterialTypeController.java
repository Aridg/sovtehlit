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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

/**
 * Created by Алексей on 01.05.2017.
 */
public class MaterialTypeController extends IDirectoryController{
    @FXML private TableView<MaterialTypeEntity> materialTypeTable;
    @FXML private TableColumn<MaterialTypeEntity, Integer> idColumn;
    @FXML private TableColumn<MaterialTypeEntity, String> nameColumn;

    private ObservableList<MaterialTypeEntity> materialTypeModels = FXCollections.observableArrayList();
    private Stage stage = new Stage(StageStyle.UTILITY);

    public MaterialTypeController() {
        Session session = HibernateSessionFactory.getSession();
        materialTypeModels.addAll(session.createQuery("from MaterialTypeEntity ", MaterialTypeEntity.class)
                .getResultList());
        session.close();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        materialTypeTable.setItems(materialTypeModels);
        materialTypeTable.setColumnResizePolicy(param -> false);
    }

    @Override
    protected void onAddClick(ActionEvent event) {

        GuiForm<AnchorPane, MaterialTypeInputController> form  = new GuiForm<AnchorPane, MaterialTypeInputController>(MenuType.MATERIAL_TYPE_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление типа материала");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        stage.showAndWait();

    }

    @Override
    protected void onEditClick(ActionEvent event) {

    }

    @Override
    protected void onDelClick(ActionEvent event) {

        int selectedIndex = materialTypeTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0){
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

    @Override
    protected void onUpdateClick(ActionEvent event) {
        materialTypeModels.clear();
        Session session = HibernateSessionFactory.getSession();
        materialTypeModels.addAll(session.createQuery("from MaterialTypeEntity ", MaterialTypeEntity.class)
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.showAndWait();
    }
}
