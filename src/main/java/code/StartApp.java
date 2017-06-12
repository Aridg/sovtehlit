package code;

import code.accessoory.GuiForm;
import code.gui.controllers.MainFormController;
import code.hibernate.HibernateSessionFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class StartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
               configPrimary(primaryStage);

        GuiForm loader = new GuiForm("Loader.fxml");
        primaryStage.setScene(new Scene(loader.getParent()));
        primaryStage.show();

        GuiForm<AnchorPane, MainFormController> mainForm = new GuiForm<>("MainForm.fxml");
        AnchorPane root = mainForm.getParent();
        MainFormController controller = mainForm.getController();
        controller.setPrimaryStage(primaryStage);

        new Thread(() -> {
            try {
                HibernateSessionFactory.init();
                Platform.runLater(() -> primaryStage.setScene(new Scene(root, 300, 275)));
            }catch (ExceptionInInitializerError ex){
                ex.printStackTrace();
                Platform.runLater(() -> {
                    new Alert(Alert.AlertType.ERROR, "Ошибка подключения к базе").showAndWait();
                    closeApp();
                });
            }
        }).start();

    }

    private void closeApp() {
        Platform.exit();
        HibernateSessionFactory.shutdown();
        System.exit(0);
    }

    private void configPrimary(Stage primaryStage){
        primaryStage.setTitle("Работа с БД");
        primaryStage.setOnCloseRequest(event -> {
            closeApp();
        });
    }
}
