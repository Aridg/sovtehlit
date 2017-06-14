package code.gui.controllers;

import code.accessoory.GuiForm;
import code.accessoory.MenuType;
import code.gui.controllers.directories.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Алексей on 21.04.2017.
 */
public class MainFormController implements IController{

    @FXML private MenuItem rawMaterialsMenu;
    @FXML private MenuItem materialTypesMenu;
    @FXML private MenuItem materialsMenu;
    @FXML private MenuItem contractsMenu;
    @FXML private MenuItem specificationsMenu;
    @FXML private MenuItem productionMenu;
    @FXML private MenuItem unitsMenu;
    @FXML private MenuItem customerMenu;

    private Stage popUpStage = new Stage();
    private Stage primaryStage;

    @FXML
    private void initialize() {
        popUpStage.initModality(Modality.APPLICATION_MODAL);
        popUpStage.initOwner(primaryStage);
        popUpStage.setResizable(false);
    }

    public Stage getPopUpStage() {
        return popUpStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        popUpStage.initOwner(primaryStage);
    }

    public void customerMenuOnClick(ActionEvent actionEvent) throws IOException {
        GuiForm<AnchorPane, CustomersController> form = new GuiForm<>(MenuType.CUSTOMERS.getFilePath());
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Заказчики");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    public void rawMaterialsMenuOnClick(ActionEvent event) {
        GuiForm<AnchorPane, RowMaterialController> form = new GuiForm<>(MenuType.RAW_MATERIALS.getFilePath());
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Сырье");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    public void materialTypesMenuOnClick(ActionEvent event) {
        GuiForm<AnchorPane, MaterialTypeController> form = new GuiForm<>(MenuType.MATERIAL_TYPE.getFilePath());
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Тип материала");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    public void materialsMenuOnClick(ActionEvent event) {
        GuiForm<AnchorPane, MaterialTypeController> form = new GuiForm<>(MenuType.MATERIALS.getFilePath());
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Материала");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    public void contractsMenuOnClick(ActionEvent event) {
        GuiForm<AnchorPane, ContractsController> form = new GuiForm<>(MenuType.CONTRACTS.getFilePath());
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Договоры");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    public void specificationsMenuOnClick(ActionEvent event) {
    }

    public void productionMenuOnClick(ActionEvent event) {

        GuiForm<AnchorPane, ProductionsController> form = new GuiForm<>(MenuType.PRODUCTIONS.getFilePath());
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Продукция");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }

    public void unitsMenuOnClick(ActionEvent event) {

        GuiForm<AnchorPane, UnitMeasurementController> form = new GuiForm<>(MenuType.UNITS.getFilePath());
        AnchorPane pane = form.getParent();

        popUpStage.setTitle("Единицы измерения");
        Scene scene = new Scene(pane);
        popUpStage.setScene(scene);
        popUpStage.showAndWait();
    }
}
