package code.accessoory;


import code.StartApp;
import code.gui.controllers.IController;
import code.gui.controllers.IDirectoryController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.net.URL;

/**
 * Created by Алексей on 23.04.2017.
 */
public class GuiForm<P extends Parent, C extends IController> {
    private P parent;
    private C controller;

    public GuiForm(String[] filePath) {
        try {
            StringBuilder builder = new StringBuilder("gui");
            for (String pack : filePath){
                builder.append("/");
                builder.append(pack);
            }

            FXMLLoader fxmlLoader = new FXMLLoader();
            URL url = StartApp.class.getClassLoader().getResource(builder.toString());
            if (url == null)
                throw new IllegalArgumentException("FXML file not found!");
            fxmlLoader.setLocation(url);
            parent = fxmlLoader.load();
            controller = fxmlLoader.getController();
        } catch (Exception e) {
            throw new IllegalArgumentException("FXML file load error!");
        }
    }

    public GuiForm(String fileName){
        this(new String[]{fileName});
    }

    public P getParent() {
        return parent;
    }

    public C getController() {
        return controller;
    }
}
