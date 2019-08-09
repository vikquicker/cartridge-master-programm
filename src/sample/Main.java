package sample;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = new VBox();
        ScrollPane scrollPane1 = new ScrollPane();
        ScrollPane scrollPane2 = new ScrollPane(new Text("log...."));

        root.getChildren().addAll(scrollPane1, scrollPane2);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
