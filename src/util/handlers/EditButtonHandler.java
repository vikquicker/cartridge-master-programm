package util.handlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EditButtonHandler implements EventHandler<ActionEvent> {
    ContentStore contentStore = ContentStore.getContentStore();
    private int numberOfButton;
    private String tabNameFromEditButtonns;
    private TableView<Cartridge> tabCartridge;
    private TableView<Utilized> tabUtilized;
    private TableView<Summary> tabSummary;

    Cartridge cartridgeFromContent;
    Cartridge cartridgeFromTable;
    private final String utilize = "Списанные";

    //
    public EditButtonHandler(int numberOfButton, String str,
                             TableView<Cartridge> tabCartridge,
                             TableView<Utilized> tabUtilized,
                             TableView<Summary> tabSummary) {
        this.tabCartridge = tabCartridge;
        this.tabUtilized = tabUtilized;
        this.numberOfButton = numberOfButton;
        this.tabSummary = tabSummary;
        tabNameFromEditButtonns = str;
        tabNameFromEditButtonns = "q_" + tabNameFromEditButtonns;
        cartridgeFromContent = contentStore.getCartridgesMap()
                .get(tabNameFromEditButtonns)
                .get(numberOfButton);

        cartridgeFromTable = tabCartridge.getItems().get(numberOfButton);
    }

    @Override
    public void handle(ActionEvent event) {
        Pane pane = new Pane();
        Stage newWindow = new Stage();
        newWindow.setTitle("Модифицировать элемент");

        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);

        //Labels
        Label numberLabel = new Label("Номер");
        Label statusLabel = new Label("Статус");
        Label dataLabel = new Label("Дата");
        Label locationLabel = new Label("Расположение");
        Label locationLabelNew = new Label("Добавить новое");
        Label noticeLabel = new Label("Примечание");

        //Fields
        TextField numberField = new TextField();
        numberField.setText(cartridgeFromContent.getNumber());
        numberField.setMaxWidth(80);

        //TODO replace it
        ObservableList<String> statusList = FXCollections.observableArrayList("На отделении",
                "На заправке 1",
                "На заправке 2",
                "Списан",
                "Заправлен");
        ComboBox<String> statusField = new ComboBox<>(statusList);
        statusField.setValue(cartridgeFromContent.getStatus());

        DatePicker dateField = new DatePicker();
        dateField.setValue(cartridgeFromContent.getDate());
        dateField.setShowWeekNumbers(true);
        dateField.setMaxWidth(100);

        ObservableList<String> locationList = FXCollections.observableArrayList(contentStore.getLocationList());
        ComboBox<String> locationField = new ComboBox<>(locationList);
        locationField.setValue(cartridgeFromContent.getLocation());
        locationField.setMinWidth(100);

        TextField locationFieldNew = new TextField();
        locationFieldNew.setMaxWidth(100);

        TextArea textAreaField = new TextArea();
        textAreaField.setMaxSize(215, 70);
        textAreaField.setText(cartridgeFromContent.getNotice());
        textAreaField.setWrapText(true);

        //Position LabelY
        statusLabel.setLayoutY(5);
        numberLabel.setLayoutY(5);
        dataLabel.setLayoutY(5);
        locationLabel.setLayoutY(5);
        noticeLabel.setLayoutY(5);
        locationLabelNew.setLayoutY(50);

        Button add = new Button("Модифицировать!!!");
        add.setMinSize(80, 60);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cartridgeFromContent.setNumber(numberField.getText());
                cartridgeFromContent.setStatus(statusField.getValue());
                cartridgeFromContent.setDate(dateField.getValue());
                cartridgeFromContent.setLocation(locationField.getValue());
                cartridgeFromContent.setNotice(textAreaField.getText());

                cartridgeFromTable.setNumber(numberField.getText());
                cartridgeFromTable.setStatus(statusField.getValue());
                cartridgeFromTable.setDate(dateField.getValue());
                cartridgeFromTable.setLocation(locationField.getValue());
                cartridgeFromTable.setNotice(textAreaField.getText());

                contentStore.saveContent();

                tabCartridge.refresh();
                tabSummary.refresh();

                newWindow.close();
            }
        });


        //Position FieldY
        numberField.setLayoutY(40);
        statusField.setLayoutY(40);
        dateField.setLayoutY(40);
        textAreaField.setLayoutY(25);
        locationField.setLayoutY(25);
        locationFieldNew.setLayoutY(70);
        add.setLayoutY(29);

        //Position LabelX
        numberLabel.setLayoutX(40);
        statusLabel.setLayoutX(165);
        dataLabel.setLayoutX(298);
        locationLabel.setLayoutX(430);
        noticeLabel.setLayoutX(650);
        locationLabelNew.setLayoutX(425);

        //Position FieldX
        numberField.setLayoutX(20);
        statusField.setLayoutX(120);
        dateField.setLayoutX(262);
        textAreaField.setLayoutX(580);
        locationField.setLayoutX(420);
        locationFieldNew.setLayoutX(420);
        add.setLayoutX(815);

        pane.getChildren().addAll(numberLabel, statusLabel, dataLabel, locationLabel, noticeLabel,
                numberField,
                statusField,
                dateField,
                textAreaField,
                locationField,
                locationFieldNew,
                locationLabelNew,
                add);
        Scene sceneWithLabels1 = new Scene(pane, 945, 90);
        newWindow.setScene(sceneWithLabels1);
        newWindow.show();
    }
}
