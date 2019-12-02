package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.UIpainter;
import util.ContentStore;

import java.io.IOException;

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
