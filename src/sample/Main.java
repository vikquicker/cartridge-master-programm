package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import ui.UIpainter;
import util.ContentStore;
import util.handlers.AddButtonHandler;
import util.handlers.EditButtonHandler;
import util.handlers.RemoveButtonHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Main extends Application {
    ContentStore contentStore = ContentStore.getContentStore();

    @Override
    public void start(Stage primaryStage) throws Exception {
        UIpainter.getUIpainter().createUI(primaryStage);
    }

    public void stop() throws IOException {
        contentStore.saveContent();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
