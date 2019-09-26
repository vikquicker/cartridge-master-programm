package util.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

        VBox vBox = new VBox();
        HBox numberBox = new HBox();
        HBox statusBox = new HBox();
        HBox dataBox = new HBox();
        HBox locationBox = new HBox();
        HBox noticeBox = new HBox();

        Label numberLabel = new Label("Номер");
        Label statusLabel = new Label("Статус");
        Label dataLabel = new Label("Дата");
        Label locationLabel = new Label("Расположение");
        Label noticeLabel = new Label("Примечание");


        newWindow.show();
    }
}
