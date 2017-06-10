package code;

import code.accessoory.GuiForm;
import code.gui.controllers.MainFormController;
import code.hibernate.HibernateSessionFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class StartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Thread(() -> HibernateSessionFactory.init()).start();
        GuiForm<AnchorPane, MainFormController> mainForm = new GuiForm<>("MainForm.fxml");
        AnchorPane root = mainForm.getParent();
        MainFormController controller = mainForm.getController();
        controller.setPrimaryStage(primaryStage);
        primaryStage.setTitle("Работа с БД");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            HibernateSessionFactory.shutdown();
            System.exit(0);
        });
    }
}
