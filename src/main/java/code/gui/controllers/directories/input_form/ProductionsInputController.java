package code.gui.controllers.directories.input_form;

import code.gui.controllers.IControllerInput;
import code.gui.controllers.directories.ProductionsController;
import code.hibernate.HibernateSessionFactory;
import code.hibernate.directories.MaterialsEntity;
import code.hibernate.directories.ProductionEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

/**
 * Created by Asus on 14.06.2017.
 */
public class ProductionsInputController implements IControllerInput {

    @FXML private Button buttonOne;
    @FXML private TextField dce;
    @FXML private TextField name;
    @FXML private TextField netWidth;
    @FXML private TextField fillerWeightForm;
    @FXML private TextField countInForm;
    @FXML private TextField weightForm;
    public ComboBox<MaterialsEntity> materials;

    private Stage thisStage;
    private ProductionsController parentController;
    private ProductionEntity selectProduction;
    private ObservableList<MaterialsEntity> materialsModels = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        Session session = HibernateSessionFactory.getSession();
        List<MaterialsEntity> list = session.createQuery("from MaterialsEntity",MaterialsEntity.class).getResultList();
        materialsModels.addAll(list);
        materials.setItems(materialsModels);
    }

    @Override
    public void onAddClick(ActionEvent event) {
        if(isNull(dce.getText(),name.getText()) && isDouble(netWidth.getText(),fillerWeightForm.getText(),weightForm.getText()) && isInteger(countInForm.getText()) && materials.getSelectionModel().getSelectedItem()!=null){
            Session session = HibernateSessionFactory.getSession();
            session.beginTransaction();
            ProductionEntity productionEntity = new ProductionEntity();
            productionEntity.setDse(dce.getText());
            productionEntity.setName(name.getText());
            productionEntity.setNetWeight(Double.parseDouble(netWidth.getText()));
            productionEntity.setFillerWeightForm(Double.parseDouble(fillerWeightForm.getText()));
            productionEntity.setCountInForm(Integer.parseInt(countInForm.getText()));
            productionEntity.setWeightForm(Double.parseDouble(weightForm.getText()));
            productionEntity.setMaterialId(materials.getSelectionModel().getSelectedItem().getId());
            session.save(productionEntity);
            session.getTransaction().commit();
            session.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Новая продукция успешна добавлена");
            alert.setTitle("OK!");
            alert.setHeaderText(null);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                thisStage.close();
                parentController.Update();
            }
        }
        else if(!isDouble(netWidth.getText(),fillerWeightForm.getText(),weightForm.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Вес формы,заполненной формы и формы  имеют тип вещественного числа (х.х)");
            alert.setHeaderText(null);
            alert.setTitle("ERROR!");
            alert.showAndWait();
        }
        else if(!isInteger(countInForm.getText())){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Кол-во деталей на форме имеет числовое значение");
            alert.setHeaderText(null);
            alert.setTitle("ERROR!");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Не все параметры указаны");
            alert.setHeaderText(null);
            alert.setTitle("ERROR!");
            alert.showAndWait();
        }

    }

    @Override
    public void onAnnulmentClick(ActionEvent event) {

        thisStage.close();

    }

    @Override
    public void onEditClick(ActionEvent event) {
        Session session = HibernateSessionFactory.getSession();
        session.beginTransaction();
        selectProduction.setDse(dce.getText());
        selectProduction.setName(name.getText());
        selectProduction.setNetWeight(Double.parseDouble(netWidth.getText()));
        selectProduction.setFillerWeightForm(Double.parseDouble(fillerWeightForm.getText()));
        selectProduction.setCountInForm(Integer.parseInt(countInForm.getText()));
        selectProduction.setWeightForm(Double.parseDouble(weightForm.getText()));
        selectProduction.setMaterialId(materials.getSelectionModel().getSelectedItem().getId());
        session.update(selectProduction);
        session.getTransaction().commit();
        session.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Продукция успешна изменена");
        alert.setTitle("OK!");
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            thisStage.close();
            parentController.Update();
        }
    }

    private boolean isNull(String... args){
        for (int i = 0; i < args.length; i++) {
           if(args[i].equals("")){
               return   false;
           }
        }
        return true;
    }

    private boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }
        catch(Exception e) {
            return false;
        }
    }

    private boolean isDouble(String... args) {

        for (int i = 0; i < args.length; i++) {
            try {
                Double.parseDouble(args[i]);
            }
            catch (Exception e){
                return false;
            }
        }
        return true;
    }

    private void chanhgeForm(){
        dce.setText(selectProduction.getDse());
        name.setText(selectProduction.getName());
        netWidth.setText(String.valueOf(selectProduction.getNetWeight()));
        fillerWeightForm.setText(String.valueOf(selectProduction.getFillerWeightForm()));
        countInForm.setText(String.valueOf(selectProduction.getCountInForm()));
        weightForm.setText(String.valueOf(selectProduction.getWeightForm()));
        Session session = HibernateSessionFactory.getSession();
        MaterialsEntity current = session.createQuery("from MaterialsEntity where id=:id", MaterialsEntity.class).setParameter("id",selectProduction.getMaterialId()).getSingleResult();
        session.close();
        materials.getSelectionModel().select(current);
        buttonOne.setText("Изменить");
        buttonOne.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                onEditClick(event);
            }
        });
    }

    public void setThisStage(Stage thisStage) {
        this.thisStage = thisStage;
    }

    public void setParentController(ProductionsController parentController) {
        this.parentController = parentController;
    }

    public void setSelectProduction(ProductionEntity selectProduction) {
        this.selectProduction = selectProduction;
        chanhgeForm();
    }
}
