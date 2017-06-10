package code.gui.controllers.directories;

import code.gui.controllers.IDirectoryController;
import code.hibernate.directories.RowMaterialEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * Created by Алексей on 01.05.2017.
 */
public class RawMaterialController extends IDirectoryController{

    @FXML private TableView<RowMaterialEntity> customersTable;
    @FXML private TableColumn<RowMaterialEntity, Integer> idColumn;
    @FXML private TableColumn<RowMaterialEntity, String> nameColumn;

    @FXML
    ObservableList<RowMaterialEntity> rowMaterialModels = FXCollections.observableArrayList();

    public RawMaterialController() {
        rowMaterialModels.addAll(getData(RowMaterialEntity.class));
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

    }

    @Override
    protected void onEditClick(ActionEvent event) {

    }

    @Override
    protected void onDelClick(ActionEvent event) {

    }
}
