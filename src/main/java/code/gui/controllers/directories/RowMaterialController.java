package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.RowMaterialInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.RowMaterialEntity;
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
public class RowMaterialController extends IDirectoryController {

    @FXML
    private TableView<RowMaterialEntity> rowMaterialTable;
    @FXML
    private TableColumn<RowMaterialEntity, Integer> idColumn;
    @FXML
    private TableColumn<RowMaterialEntity, String> nameColumn;

    private ObservableList<RowMaterialEntity> rowMaterialModels = FXCollections.observableArrayList();
    private Stage stage = new Stage (StageStyle.UTILITY);

    public RowMaterialController() {
        Session session = HibernateSessionFactory.getSession();
        rowMaterialModels.addAll(session.createQuery("from RowMaterialEntity ", RowMaterialEntity.class)
                .getResultList());
        session.close();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        rowMaterialTable.setItems(rowMaterialModels);
        rowMaterialTable.setColumnResizePolicy(param -> false);
    }


    @Override
    protected void onAddClick(ActionEvent event) {

        GuiForm<AnchorPane, RowMaterialInputController> form  = new GuiForm<AnchorPane, RowMaterialInputController>(MenuType.RAW_MATERIAL_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление сырья");
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

        int selectedIndex = rowMaterialTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для удаления");
            alert.showAndWait();
            return;
        }
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        RowMaterialEntity elem = session.createQuery("from MaterialTypeEntity where id = :id", RowMaterialEntity.class)
                .setParameter("id", rowMaterialTable.getSelectionModel().getSelectedItem().getId())
                .getSingleResult();
        session.delete(elem);
        session.getTransaction().commit();
        rowMaterialTable.getItems().remove(rowMaterialTable.getSelectionModel().getSelectedItem());
        session.close();
    }

    @Override
    protected void onUpdateClick(ActionEvent event) {
        rowMaterialModels.clear();
        Session session = HibernateSessionFactory.getSession();
        rowMaterialModels.addAll(session.createQuery("from RowMaterialEntity ", RowMaterialEntity.class)
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.showAndWait();
    }
}
