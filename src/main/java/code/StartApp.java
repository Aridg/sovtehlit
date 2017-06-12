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
import javafx.stage.StageStyle;
import javafx.stage.Window;


public class StartApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        configPrimary(primaryStage);

        Stage loaderStage = new Stage(StageStyle.UNDECORATED);

        GuiForm loader = new GuiForm("Loader.fxml");
        loaderStage.setScene(new Scene(loader.getParent()));
        loaderStage.centerOnScreen();
        loaderStage.show();


        GuiForm<AnchorPane, MainFormController> mainForm = new GuiForm<>("MainForm.fxml");
        AnchorPane root = mainForm.getParent();
        MainFormController controller = mainForm.getController();
        controller.setPrimaryStage(primaryStage);

        new Thread(() -> {
            try {
                HibernateSessionFactory.init();
                Platform.runLater(() -> {
                    loaderStage.close();
                    primaryStage.setScene(new Scene(root, 300, 275));
                    primaryStage.show();
                });
            } catch (ExceptionInInitializerError ex) {
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

    private void configPrimary(Stage primaryStage) {
        primaryStage.setTitle("Управление производством");
        primaryStage.initStyle(StageStyle.UTILITY);
        primaryStage.setOnCloseRequest(event -> {
            closeApp();
        });
    }
}
