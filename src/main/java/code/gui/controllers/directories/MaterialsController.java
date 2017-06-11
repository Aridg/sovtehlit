package code.gui.controllers.directories;

import code.gui.controllers.IDirectoryController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.ContractEntity;
import code.hibernate.directories.CustomersEntity;
import code.hibernate.directories.MaterialsEntity;
import code.hibernate.directories.views.MaterialVEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.hibernate.Session;

/**
 * Created by Алексей on 01.05.2017.
 */
public class MaterialsController extends IDirectoryController{
    public TableView<MaterialVEntity> customersTable;
    public TableColumn<MaterialVEntity, Integer> idColumn;
    public TableColumn<MaterialVEntity, String> nameColumn;
    public TableColumn<MaterialVEntity, String> typeMaterialColumn;


    private ObservableList<MaterialVEntity> data = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        Session session = HibernateSessionFactory.getSession();
        data.addAll(session.createQuery("from MaterialVEntity ", MaterialVEntity.class)
                .getResultList());
        session.close();
        tableConfiguration();
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

    private void tableConfiguration(){
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        typeMaterialColumn.setCellValueFactory(cellData -> cellData.getValue().typePProperty());
        customersTable.setItems(data);
        customersTable.setColumnResizePolicy(param -> false);
    }
}
