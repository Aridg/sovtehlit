package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.CustomersInputController;
import code.gui.controllers.directories.input_form.MaterialInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.ContractEntity;
import code.hibernate.directories.CustomersEntity;
import code.hibernate.directories.MaterialsEntity;
import code.hibernate.directories.views.MaterialVEntity;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.util.Optional;

/**
 * Created by Алексей on 01.05.2017.
 */
public class MaterialsController extends IDirectoryController{
    @FXML private TableView<MaterialVEntity> materialVTable;
    @FXML private TableColumn<MaterialVEntity, Integer> idColumn;
    @FXML private TableColumn<MaterialVEntity, String> nameColumn;
    @FXML private TableColumn<MaterialVEntity, String> typeMaterialColumn;

    private Stage stage = new Stage(StageStyle.UTILITY);
    private ObservableList<MaterialVEntity> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        Session session = HibernateSessionFactory.getSession();
        data.addAll(session.createQuery("from MaterialVEntity ", MaterialVEntity.class)
                .getResultList());
        session.close();
        tableConfiguration();
    }

    @Override
    protected void onAddClick(ActionEvent event) {

        GuiForm<AnchorPane, MaterialInputController> form  = new GuiForm<AnchorPane, MaterialInputController>(MenuType.MATERIAL_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление материла");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        stage.showAndWait();
    }

    @Override
    protected void onEditClick(ActionEvent event) {

    }

    @Override
    protected void onDelClick(ActionEvent event) {
        Alert alertApproval = new Alert(Alert.AlertType.WARNING, "Вы точно хотите удалить выбранный объект?");
        alertApproval.setTitle("WARNING!");
        alertApproval.setHeaderText(null);
        Optional<ButtonType> result = alertApproval.showAndWait();
        if(result.get() == ButtonType.OK) {
            int selectedIndex = materialVTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для удаления");
                alert.showAndWait();
                return;
            }
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            MaterialVEntity elem = session.createQuery("from MaterialVEntity where id = :id", MaterialVEntity.class)
                    .setParameter("id", materialVTable.getSelectionModel().getSelectedItem().getId())
                    .getSingleResult();
            session.delete(elem);
            session.getTransaction().commit();
            materialVTable.getItems().remove(materialVTable.getSelectionModel().getSelectedItem());
            session.close();
        }

    }

    @Override
    protected void onUpdateClick(ActionEvent event) {
        data.clear();
        Session session = HibernateSessionFactory.getSession();
        data.addAll(session.createQuery("from MaterialVEntity ", MaterialVEntity.class)
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void Update() {
        onUpdateClick(new ActionEvent());
    }

    private void tableConfiguration(){
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        typeMaterialColumn.setCellValueFactory(cellData -> cellData.getValue().typePProperty());
        materialVTable.setItems(data);
        materialVTable.setColumnResizePolicy(param -> false);
    }
}
