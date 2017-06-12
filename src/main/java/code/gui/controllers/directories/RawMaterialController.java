package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.MaterialInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.RowMaterialEntity;
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
public class RawMaterialController extends IDirectoryController {

    @FXML
    private TableView<RowMaterialEntity> customersTable;
    @FXML
    private TableColumn<RowMaterialEntity, Integer> idColumn;
    @FXML
    private TableColumn<RowMaterialEntity, String> nameColumn;

    @FXML
    ObservableList<RowMaterialEntity> rowMaterialModels = FXCollections.observableArrayList();
    private Stage stage = new Stage ();

    public RawMaterialController() {
        Session session = HibernateSessionFactory.getSession();
        rowMaterialModels.addAll(session.createQuery("from RowMaterialEntity ", RowMaterialEntity.class)
                .getResultList());
        session.close();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        customersTable.setItems(rowMaterialModels);
        customersTable.setColumnResizePolicy(param -> false);
    }


    @Override
    protected void onAddClick(ActionEvent event) {

        GuiForm<AnchorPane, RawMaterialController> form  = new GuiForm<AnchorPane, RawMaterialController>(MenuType.RAW_MATERIAL_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление сырья");
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
