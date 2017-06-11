package code.gui.controllers.directories;


import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.ContractInputController;
import code.gui.controllers.directories.input_form.CustomersInputController;
import code.hibernate.directories.CustomersEntity;
import code.hibernate.HibernateSessionFactory;
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

import java.util.List;

public class CustomersController extends IDirectoryController {
    @FXML private TableView<CustomersEntity> customersTable;
    @FXML private TableColumn<CustomersEntity, Integer> idColumn;
    @FXML private TableColumn<CustomersEntity, String> nameColumn;


    private Stage stage = new Stage();
    private ObservableList<CustomersEntity> customerModels = FXCollections.observableArrayList();

    public CustomersController() {
        Session session = HibernateSessionFactory.getSession();
        customerModels.addAll(session.createQuery("from CustomersEntity ", CustomersEntity.class)
                .getResultList());
        session.close();
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

        GuiForm<AnchorPane, CustomersInputController> form  = new GuiForm<AnchorPane, CustomersInputController>(MenuType.COSTOMER_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление заказчика");
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
