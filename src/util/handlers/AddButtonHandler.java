package util.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddButtonHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
        StackPane secondaryLayout = new StackPane();
        Scene newScene = new Scene(secondaryLayout, 600, 400);
        Stage newWindow = new Stage();
        newWindow.setTitle("Добавить элемент");
        newWindow.setScene(newScene);

        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);

        newWindow.show();
    }
}
