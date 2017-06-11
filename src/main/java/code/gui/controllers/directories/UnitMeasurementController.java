package code.gui.controllers.directories;

import code.gui.controllers.IDirectoryController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.UnitsEntity;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Session;

/**
 * Created by Asus on 10.06.2017.
 */
public class UnitMeasurementController extends IDirectoryController {
    @FXML
    private TableView<UnitsEntity> unitsTable;
    @FXML
    private TableColumn<UnitsEntity, Integer> idColumn;
    @FXML
    private TableColumn<UnitsEntity, String> nameColumn;
    @FXML
    private TableColumn<UnitsEntity, Double> factorColumn;

    ObservableList<UnitsEntity> unitsModels = FXCollections.observableArrayList();

    public UnitMeasurementController() {
        Session session = HibernateSessionFactory.getSession();
        unitsModels.addAll(session.createQuery("from UnitsEntity ", UnitsEntity.class)
                .getResultList());
        session.close();
    }


    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        factorColumn.setCellValueFactory(cellData -> cellData.getValue().coefficientPProperty().asObject()); // везде, кроме String полей нужно использовать .asObject()
        unitsTable.setItems(unitsModels);
        unitsTable.setColumnResizePolicy(param -> false);
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
