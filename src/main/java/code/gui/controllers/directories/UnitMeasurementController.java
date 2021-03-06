package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.UnitInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.UnitsEntity;
import javafx.beans.property.DoubleProperty;
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
 * Created by Asus on 10.06.2017.
 */
public class UnitMeasurementController extends IDirectoryController {
    @FXML
    private TableView<UnitsEntity> unitsTable;
    @FXML
    private TableColumn<UnitsEntity, String> nameColumn;
    @FXML
    private TableColumn<UnitsEntity, Double> factorColumn;

    private ObservableList<UnitsEntity> unitsModels = FXCollections.observableArrayList();

    public UnitMeasurementController() {
        Session session = HibernateSessionFactory.getSession();
        unitsModels.addAll(session.createQuery("from UnitsEntity ", UnitsEntity.class)
                .getResultList());
        session.close();
    }


    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        factorColumn.setCellValueFactory(cellData -> cellData.getValue().coefficientPProperty().asObject()); // везде, кроме String полей нужно использовать .asObject()
        unitsTable.setItems(unitsModels);
        unitsTable.setColumnResizePolicy(param -> false);
    }

    @Override
    protected void onAddClick(ActionEvent event) {
        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, UnitInputController> form  = new GuiForm<AnchorPane, UnitInputController>(MenuType.UNITS_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(unitsTable.getScene().getWindow());
        stage.setTitle("Добавление единицы измерения");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        stage.showAndWait();

    }

    @Override
    protected void onEditClick(ActionEvent event) {

        int selectedIndex = unitsTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для изменения");
            alert.showAndWait();
            return;
        }
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        UnitsEntity elem = session.createQuery("from UnitsEntity where id = :id", UnitsEntity.class)
                .setParameter("id", unitsTable.getSelectionModel().getSelectedItem().getId())
                .getSingleResult();
        session.close();

        Stage stage = new Stage (StageStyle.UTILITY);
        GuiForm<AnchorPane, UnitInputController> form  = new GuiForm<AnchorPane, UnitInputController>(MenuType.UNITS_INPUT.getFilePath());
        AnchorPane pane = form.getParent();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(unitsTable.getScene().getWindow());
        stage.setTitle("Изменение едениц измерения");
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        form.getController().setThisStage(stage);
        form.getController().setParentController(this);
        form.getController().setSelectUnit(elem);
        stage.showAndWait();
    }

    @Override
    protected void onDelClick(ActionEvent event) {
        Alert alertApproval = new Alert(Alert.AlertType.WARNING, "Вы точно хотите удалить выбранный объект?");
        alertApproval.setTitle("WARNING!");
        alertApproval.setHeaderText(null);
        Optional<ButtonType> result = alertApproval.showAndWait();
        if(result.get() == ButtonType.OK) {
            int selectedIndex = unitsTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Выберите строку для удаления");
                alert.showAndWait();
                return;
            }
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            UnitsEntity elem = session.createQuery("from UnitsEntity where id = :id", UnitsEntity.class)
                    .setParameter("id", unitsTable.getSelectionModel().getSelectedItem().getId())
                    .getSingleResult();
            session.delete(elem);
            session.getTransaction().commit();
            unitsTable.getItems().remove(unitsTable.getSelectionModel().getSelectedItem());
            session.close();
        }
    }

    @Override
    protected void onUpdateClick(ActionEvent event) {
        unitsModels.clear();
        Session session = HibernateSessionFactory.getSession();
        unitsModels.addAll(session.createQuery("from UnitsEntity ", UnitsEntity.class)
                .getResultList());
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно обновлены");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void Update(){
        unitsModels.clear();
        Session session = HibernateSessionFactory.getSession();
        unitsModels.addAll(session.createQuery("from UnitsEntity ", UnitsEntity.class)
                .getResultList());
        session.close();
    }
}
