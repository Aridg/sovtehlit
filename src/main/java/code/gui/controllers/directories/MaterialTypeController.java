package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.MaterialInputController;
import code.gui.controllers.directories.input_form.MaterialTypeInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.MaterialTypeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;

/**
 * Created by Алексей on 01.05.2017.
 */
public class MaterialTypeController extends IDirectoryController{
    @FXML private TableView<MaterialTypeEntity> customersTable;
    @FXML private TableColumn<MaterialTypeEntity, Integer> idColumn;
    @FXML private TableColumn<MaterialTypeEntity, String> nameColumn;

    @FXML
    ObservableList<MaterialTypeEntity> materialTypeModels = FXCollections.observableArrayList();
    private Stage stage = new Stage();

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
        customersTable.setItems(materialTypeModels);
        customersTable.setColumnResizePolicy(param -> false);
    }

    @Override
    protected void onAddClick(ActionEvent event) {

        GuiForm<AnchorPane, MaterialTypeInputController> form  = new GuiForm<AnchorPane, MaterialTypeInputController>(MenuType.MATERIAL_TYPE_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление типа материала");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @Override
    protected void onEditClick(ActionEvent event) {

    }

    @Override
    protected void onDelClick(ActionEvent event) {

    }
}
