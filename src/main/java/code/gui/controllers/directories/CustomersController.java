package code.gui.controllers.directories;


import code.gui.controllers.IDirectoryController;
import code.hibernate.directories.CustomersEntity;
import code.hibernate.HibernateSessionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Session;

import java.util.List;

public class CustomersController extends IDirectoryController {
    @FXML private TableView<CustomersEntity> customersTable;
    @FXML private TableColumn<CustomersEntity, Integer> idColumn;
    @FXML private TableColumn<CustomersEntity, String> nameColumn;


    private ObservableList<CustomersEntity> customerModels = FXCollections.observableArrayList();

    public CustomersController() {
        customerModels.addAll(getData(CustomersEntity.class));
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        customersTable.setItems(customerModels);
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
