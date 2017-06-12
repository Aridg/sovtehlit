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
import javafx.scene.control.Alert;
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

        GuiForm<AnchorPane, ContractInputController> form  = new GuiForm<>(MenuType.CONTRACT_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление контракта");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setSelectedCustomer(customers.getSelectionModel().getSelectedItem());
        stage.showAndWait();
    }

    @Override
    protected void onEditClick(ActionEvent event) {

    }

    @Override
    protected void onDelClick(ActionEvent event) {
        int selectedIndex = customersTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для удаления");
            alert.showAndWait();
            return;
        }
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        ContractEntity elem = session.createQuery("from ContractEntity where id = :id", ContractEntity.class)
                .setParameter("id", customersTable.getSelectionModel().getSelectedItem().getId())
                .getSingleResult();
        session.delete(elem);
        session.getTransaction().commit();
        customersTable.getItems().remove(customersTable.getSelectionModel().getSelectedItem());
        session.close();
    }

    @Override
    protected void onUpdateClick(ActionEvent event) {
        contractModels.clear();
        Session session = HibernateSessionFactory.getSession();
        contractModels.addAll(session.createQuery("from ContractEntity where customerId = :customer", ContractEntity.class)
                .setParameter("customer", customers.getSelectionModel().getSelectedItem().getId())
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.showAndWait();
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
