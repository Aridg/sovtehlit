package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.ContractInputController;
import code.gui.controllers.directories.input_form.CustomersInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.ContractEntity;
import code.hibernate.directories.CustomersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.Session;


import java.time.LocalDate;
import java.util.List;

/**
 * Created by Алексей on 01.05.2017.
 */
public class ContractsController extends IDirectoryController{
    @FXML private TableColumn<ContractEntity, LocalDate> dateColumn;
    @FXML private TableView<ContractEntity> customersTable;
    @FXML private TableColumn<ContractEntity, Integer> idColumn;
    @FXML private TableColumn<ContractEntity, String> nameColumn;
    @FXML private ComboBox<CustomersEntity> customers;

    private Stage stage = new Stage();

    ObservableList<ContractEntity> contractModels = FXCollections.observableArrayList();
    ObservableList<CustomersEntity> dataCustomers = FXCollections.observableArrayList();

    public ContractsController() {
        Session session = HibernateSessionFactory.getSession();
        contractModels.addAll(session.createQuery("from ContractEntity ", ContractEntity.class)
                .getResultList());
        session.close();
    }


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().datePProperty());
        customersTable.setItems(contractModels);
        getCustomers();
        customersTable.setColumnResizePolicy(param -> false);
    }

    @Override
    protected void onAddClick(ActionEvent event) {

        GuiForm<AnchorPane, ContractInputController> form  = new GuiForm<AnchorPane, ContractInputController>(MenuType.CONTRACT_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление контракта");
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

    private void getCustomers()
    {
        Session session = HibernateSessionFactory.getSession();
        List<CustomersEntity> list = session.createQuery("from CustomersEntity ", CustomersEntity.class)
                .getResultList();
        dataCustomers.addAll(list);
        customers.setItems(dataCustomers);
        session.close();
    }
}
