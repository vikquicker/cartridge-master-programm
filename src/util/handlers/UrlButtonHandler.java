package util.handlers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.ContentStore;

import java.io.FileWriter;
import java.io.IOException;

public class UrlButtonHandler implements EventHandler {
    ContentStore contentStore = ContentStore.getContentStore();
    javafx.scene.control.TextField URL;

    public UrlButtonHandler(TextField URL) {
        this.URL = URL;
    }

    @Override
    public void handle(Event event) {
        Pane pane = new Pane();
        Stage newWindow = new Stage();

        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);

        Label label = new Label("Сохранено");
        label.setLayoutY(5);
        label.setLayoutX(35);

        Button ok = new Button("OK");
        ok.setLayoutY(27);
        ok.setLayoutX(49);

        //contentStore.setBase(URL.getText());
        try {
            FileWriter fileWriter = new FileWriter("base.txt");
            fileWriter.write(URL.getText());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newWindow.close();
                System.exit(0);
            }
        });

        pane.getChildren().addAll(label, ok);
        Scene sceneWithLabels1 = new Scene(pane, 100, 50);
        newWindow.setScene(sceneWithLabels1);
        newWindow.show();
    }
}
