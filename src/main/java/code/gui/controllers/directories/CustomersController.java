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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class CustomersController extends IDirectoryController {
    @FXML private TableView<CustomersEntity> customersTable;
    @FXML private TableColumn<CustomersEntity, String> nameColumn;

    private ObservableList<CustomersEntity> customerModels = FXCollections.observableArrayList();

    public CustomersController() {
        Session session = HibernateSessionFactory.getSession();
        customerModels.addAll(session.createQuery("from CustomersEntity ", CustomersEntity.class)
                .getResultList());
        session.close();
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        customersTable.setItems(customerModels);
        customersTable.setColumnResizePolicy(param -> false);
    }


    @Override
    protected void onAddClick(ActionEvent event) {
        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, CustomersInputController> form  = new GuiForm<AnchorPane, CustomersInputController>(MenuType.COSTOMER_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(customersTable.getScene().getWindow());
        stage.setTitle("Добавление заказчика");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        stage.showAndWait();

    }

    @Override
    protected void onEditClick(ActionEvent event) {

        int selectedIndex = customersTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для изменения");
            alert.showAndWait();
            return;
        }
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        CustomersEntity elem = session.createQuery("from CustomersEntity where id = :id", CustomersEntity.class)
                .setParameter("id", customersTable.getSelectionModel().getSelectedItem().getId())
                .getSingleResult();
        session.close();

        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, CustomersInputController> form  = new GuiForm<AnchorPane, CustomersInputController>(MenuType.COSTOMER_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(customersTable.getScene().getWindow());
        stage.setTitle("Изменение информации о заказчике");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        form.getController().setSelectCutomer(elem);
        stage.showAndWait();
    }

    @Override
    protected void onDelClick(ActionEvent event) {
        Alert alertApproval = new Alert(Alert.AlertType.WARNING, "Вы точно хотите удалить выбранный объект?");
        alertApproval.setTitle("WARNING!");
        alertApproval.setHeaderText(null);
        Optional<ButtonType> result = alertApproval.showAndWait();
        if(result.get() == ButtonType.OK) {
            int selectedIndex = customersTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для удаления");
                alert.showAndWait();
                return;
            }
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            CustomersEntity elem = session.createQuery("from CustomersEntity where id = :id", CustomersEntity.class)
                    .setParameter("id", customersTable.getSelectionModel().getSelectedItem().getId())
                    .getSingleResult();
            session.delete(elem);
            session.getTransaction().commit();
            customersTable.getItems().remove(customersTable.getSelectionModel().getSelectedItem());
            session.close();
        }
    }

    @Override
    protected void onUpdateClick(ActionEvent event) {
        customerModels.clear();
        Session session = HibernateSessionFactory.getSession();
        customerModels.addAll(session.createQuery("from CustomersEntity ", CustomersEntity.class)
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void Update() {
        customerModels.clear();
        Session session = HibernateSessionFactory.getSession();
        customerModels.addAll(session.createQuery("from CustomersEntity ", CustomersEntity.class)
                .getResultList());
        session.close();
    }
}
