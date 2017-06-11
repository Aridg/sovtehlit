package code.gui.controllers.directories;

import code.gui.controllers.IDirectoryController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.ContractEntity;
import code.hibernate.directories.CustomersEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    ObservableList<ContractEntity> contractModels = FXCollections.observableArrayList();
    ObservableList<CustomersEntity> dataCustomers = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().datePProperty());
        customersTable.setItems(contractModels);
        getCustomers();
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

    private void getCustomers()
{
        Session session = HibernateSessionFactory.getSession();
        List<CustomersEntity> list = session.createQuery("from CustomersEntity ", CustomersEntity.class)
                .getResultList();
        dataCustomers.clear();
        dataCustomers.addAll(list);
        customers.setItems(dataCustomers);
        session.close();
        customers.getSelectionModel().selectFirst();
        onCustomerChange(null);
    }

    @FXML
    private void onCustomerChange(ActionEvent event) {
        if(customers.getItems().size() == 0)
            return;
        contractModels.clear();
        Session session = HibernateSessionFactory.getSession();
        contractModels.addAll(session.createQuery("from ContractEntity where customerId = :customer", ContractEntity.class)
                .setParameter("customer", customers.getSelectionModel().getSelectedItem().getId())
                .getResultList());
        session.close();
    }
}
