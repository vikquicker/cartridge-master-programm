package util.handlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Cartridge;
import models.Utilized;
import sample.Main;
import util.ContentStore;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddButtonHandler implements EventHandler<ActionEvent> {
    ContentStore contentStore = ContentStore.getContentStore();
    private String tabName;

    public String getTabName() {
        return tabName;
    }

    public AddButtonHandler(String str) {
        tabName = str;
    }

    public void handle(ActionEvent event) {
        Pane pane = new Pane();
        Stage newWindow = new Stage();
        newWindow.setTitle("Добавить элемент");

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
        numberField.setMaxWidth(80);

        ObservableList<String> statusList = FXCollections.observableArrayList("На отделении",
                "На заправке 1",
                "На заправке 2",
                "Списан",
                "Заправлен");
        ComboBox<String> statusField = new ComboBox<>(statusList);
        statusField.setValue("Пустой");

        DatePicker dateField = new DatePicker();
        dateField.setValue(LocalDate.of(2016, 1, 1));
        dateField.setShowWeekNumbers(true);
        dateField.setMaxWidth(100);

        ObservableList<String> locationList = FXCollections.observableArrayList(contentStore.getLocationList());
        ComboBox<String> locationField = new ComboBox<>(locationList);
        locationField.setValue(locationList.get(0));
        locationField.setMinWidth(100);

        TextField locationFieldNew = new TextField();
        locationFieldNew.setMaxWidth(100);

        TextArea textAreaField = new TextArea();
        textAreaField.setMaxSize(215, 70);
        textAreaField.setWrapText(true);

        //Position LabelY
        statusLabel.setLayoutY(5);
        numberLabel.setLayoutY(5);
        dataLabel.setLayoutY(5);
        locationLabel.setLayoutY(5);
        noticeLabel.setLayoutY(5);
        locationLabelNew.setLayoutY(50);

        Button add = new Button("Добавить");
        add.setMinSize(80, 60);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Cartridge cartridgeToAdd = new Cartridge();
                Utilized utilizedToAdd = new Utilized();

                //cartridgeToAdd
                cartridgeToAdd.setNumber(numberField.getText());
                cartridgeToAdd.setStatus(statusField.getValue());
                cartridgeToAdd.setDate(dateField.getValue());
                //TODO Validation
                if (locationFieldNew != null) {
                    cartridgeToAdd.setLocation(locationFieldNew.getText());
                } else {
                    cartridgeToAdd.setLocation(locationField.getValue());
                }
                cartridgeToAdd.setNotice(textAreaField.getText());

                //utilizedToAdd
                utilizedToAdd.setNumber(numberField.getText());
                utilizedToAdd.setStatus(statusField.getValue());
                utilizedToAdd.setDate(dateField.getValue());
                //TODO Validation
                utilizedToAdd.setNotice(textAreaField.getText());

                contentStore.getCartridgesMap().get(tabName).add(cartridgeToAdd);
                ArrayList<Cartridge> arrayList1 = new ArrayList<>();
                arrayList1.add(cartridgeToAdd);
                contentStore.getCartridgesMap().put(tabName, arrayList1);

                if (statusField.equals("Списан")) {
                    contentStore.getUtilizedArrayList().add(utilizedToAdd);
                }

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
        Scene sceneWithLabels1 = new Scene(pane, 900, 90);
        newWindow.setScene(sceneWithLabels1);
        newWindow.show();
    }
}