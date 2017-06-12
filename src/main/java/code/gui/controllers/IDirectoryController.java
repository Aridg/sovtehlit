package code.gui.controllers;

import code.hibernate.HibernateSessionFactory;
import code.hibernate.IModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by Алексей on 22.04.2017.
 */
public abstract class IDirectoryController implements IController{
    @FXML protected abstract void onAddClick(ActionEvent event);
    @FXML protected abstract void onEditClick(ActionEvent event);
    @FXML protected abstract void onDelClick(ActionEvent event);
    @FXML protected  abstract  void onUpdateClick(ActionEvent event);
}
