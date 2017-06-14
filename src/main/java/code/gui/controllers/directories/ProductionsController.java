package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.CustomersInputController;
import code.gui.controllers.directories.input_form.ProductionsInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.CustomersEntity;
import code.hibernate.directories.ProductionEntity;
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

import java.util.Optional;

/**
 * Created by Asus on 14.06.2017.
 */
public class ProductionsController extends IDirectoryController {

    @FXML private TableView<ProductionEntity> productionTable;
    @FXML private TableColumn<ProductionEntity, String> dseColumn;
    @FXML private TableColumn<ProductionEntity, String> nameColumn;
    @FXML private TableColumn<ProductionEntity, Double> netWeightColumn;
    @FXML private TableColumn<ProductionEntity, Double> fillerWeightFormColumn;
    @FXML private TableColumn<ProductionEntity, Integer> countInFormColumn;
    @FXML private TableColumn<ProductionEntity, Double> weightFormColumn;

    private ObservableList<ProductionEntity> productionModels = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        dseColumn.setCellValueFactory(cellData -> cellData.getValue().dsePProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        netWeightColumn.setCellValueFactory(cellData -> cellData.getValue().netWeightPProperty().asObject());
        fillerWeightFormColumn.setCellValueFactory(cellData->cellData.getValue().fillerWeightFormPProperty().asObject());
        countInFormColumn.setCellValueFactory(cellData->cellData.getValue().countInFormPProperty().asObject());
        weightFormColumn.setCellValueFactory(cellData->cellData.getValue().weightFormPProperty().asObject());
        productionTable.setItems(productionModels);
        getProductionModels();
        productionTable.setColumnResizePolicy(param -> false);
    }


    @Override
    protected void onAddClick(ActionEvent event) {
        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, ProductionsInputController> form  = new GuiForm<AnchorPane, ProductionsInputController>(MenuType.PRODUCTION_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(productionTable.getScene().getWindow());
        stage.setTitle("Добавление новой продукции");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        stage.showAndWait();
    }

    @Override
    protected void onEditClick(ActionEvent event) {

        int selectedIndex = productionTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для изменения");
            alert.showAndWait();
            return;
        }
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        ProductionEntity elem = session.createQuery("from ProductionEntity where id = :id", ProductionEntity.class)
                .setParameter("id", productionTable.getSelectionModel().getSelectedItem().getId())
                .getSingleResult();
        session.close();

        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, ProductionsInputController> form  = new GuiForm<AnchorPane, ProductionsInputController>(MenuType.PRODUCTION_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(productionTable.getScene().getWindow());
        stage.setTitle("Изменение информации о заказчике");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        form.getController().setSelectProduction(elem);
        stage.showAndWait();

    }

    @Override
    protected void onDelClick(ActionEvent event) {
        Alert alertApproval = new Alert(Alert.AlertType.WARNING, "Вы точно хотите удалить выбранный объект?");
        alertApproval.setTitle("WARNING!");
        alertApproval.setHeaderText(null);
        Optional<ButtonType> result = alertApproval.showAndWait();
        if(result.get() == ButtonType.OK) {
            int selectedIndex = productionTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для удаления");
                alert.showAndWait();
                return;
            }
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            ProductionEntity elem = session.createQuery("from ProductionEntity where id = :id", ProductionEntity.class)
                    .setParameter("id", productionTable.getSelectionModel().getSelectedItem().getId())
                    .getSingleResult();
            session.delete(elem);
            session.getTransaction().commit();
            productionTable.getItems().remove(productionTable.getSelectionModel().getSelectedItem());
            session.close();
        }

    }

    @Override
    protected void onUpdateClick(ActionEvent event) {
        productionModels.clear();
        Session session = HibernateSessionFactory.getSession();
        productionModels.addAll(session.createQuery("from ProductionEntity", ProductionEntity.class)
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void Update(){
        productionModels.clear();
        Session session = HibernateSessionFactory.getSession();
        productionModels.addAll(session.createQuery("from ProductionEntity", ProductionEntity.class)
                .getResultList());
        session.close();
    }

    private void getProductionModels() {
        Session session = HibernateSessionFactory.getSession();
        productionModels.addAll(session.createQuery("from ProductionEntity", ProductionEntity.class)
                .getResultList());
        session.close();
    }
}
