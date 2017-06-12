package code.gui.controllers.directories;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.IDirectoryController;
import code.gui.controllers.directories.input_form.CustomersInputController;
import code.gui.controllers.directories.input_form.MaterialInputController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.ContractEntity;
import code.hibernate.directories.CustomersEntity;
import code.hibernate.directories.MaterialsEntity;
import code.hibernate.directories.views.MaterialVEntity;
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

/**
 * Created by Алексей on 01.05.2017.
 */
public class MaterialsController extends IDirectoryController{
    public TableView<MaterialVEntity> customersTable;
    public TableColumn<MaterialVEntity, Integer> idColumn;
    public TableColumn<MaterialVEntity, String> nameColumn;
    public TableColumn<MaterialVEntity, String> typeMaterialColumn;

    private Stage stage = new Stage();
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

        GuiForm<AnchorPane, MaterialInputController> form  = new GuiForm<AnchorPane, MaterialInputController>(MenuType.MATERIAL_INPUT.getFilePath());
        AnchorPane pane = form.getParent();

        stage.setTitle("Добавление материла");
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

    private void tableConfiguration(){
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idPProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().namePProperty());
        typeMaterialColumn.setCellValueFactory(cellData -> cellData.getValue().typePProperty());
        customersTable.setItems(data);
        customersTable.setColumnResizePolicy(param -> false);
    }
}
