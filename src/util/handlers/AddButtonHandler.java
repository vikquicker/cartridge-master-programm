package util.handlers;

import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.time.LocalDate;
import java.util.*;

public class AddButtonHandler implements EventHandler<ActionEvent> {
    ContentStore contentStore = ContentStore.getContentStore();
    private String tabName;
    private TableView<Cartridge> tabCartridge;
    private TableView<Utilized> tabUtilized;
    private TableView<Summary> tabSummary;
    TextArea log;

    public AddButtonHandler(String str, TableView<Cartridge> tabCartridge,
                            TableView<Utilized> tabUtilized,
                            TableView<Summary> tabSummary, TextArea log) {
        this.tabCartridge = tabCartridge;
        this.tabUtilized = tabUtilized;
        this.tabSummary = tabSummary;
        tabName = str;
        this.log = log;
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

        //TODO replace it
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
        try{
            locationField.setValue(locationList.get(0));
        }catch (Exception e){

        }

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

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!numberField.getText().equals("кошко")) {
                    Cartridge cartridgeToAdd = new Cartridge();
                    Summary summaryNew;

                    double idToCartridgeAndUtil = Math.random();
                    cartridgeToAdd.setId(idToCartridgeAndUtil);
                    cartridgeToAdd.setNumber(numberField.getText());
                    cartridgeToAdd.setStatus(statusField.getValue());
                    cartridgeToAdd.setDate(dateField.getValue());
                    //TODO Validation
                    if (locationFieldNew.getLength() > 0) {
                        contentStore.getLocationList().add(locationFieldNew.getText());
                        cartridgeToAdd.setLocation(locationFieldNew.getText());
                    } else {
                        cartridgeToAdd.setLocation(locationField.getValue());
                    }
                    cartridgeToAdd.setNotice(textAreaField.getText());
                    contentStore.getCartridgesMap().get("q_" + tabName).add(cartridgeToAdd);
                    tabCartridge.getItems().add(cartridgeToAdd);

                    if (statusField.getValue().equals("Списан")) {
                        Utilized utilizedToAdd = new Utilized();

                        utilizedToAdd.setId(idToCartridgeAndUtil);
                        utilizedToAdd.setNumber(numberField.getText());
                        utilizedToAdd.setStatus(statusField.getValue());
                        utilizedToAdd.setDate(dateField.getValue());
                        utilizedToAdd.setNotice(textAreaField.getText());

                        contentStore.getUtilizedArrayList().add(utilizedToAdd);
                        tabUtilized.getItems().add(utilizedToAdd);
                    }

                    if (statusField.getValue().equals("На отделении")) {
                        tabSummary.getItems().clear();
                        HashMap<String, Integer> summaryCount = contentStore.summuryCount();
                        contentStore.getSummaryArrayList().clear();
                        for (Map.Entry<String, Integer> countAndLocation : summaryCount.entrySet()) {
                            summaryNew = new Summary();
                            summaryNew.setOpsLocation(countAndLocation.getKey());
                            summaryNew.setCount(countAndLocation.getValue());
                            contentStore.getSummaryArrayList().add(summaryNew);
                        }
                        tabSummary.getItems().addAll(contentStore.getSummaryArrayList());
                    }

                    Date date = new Date();
                    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));
                    log.appendText("\n" + dateFormat.format(date) + " : Элемент добавлен  ");

                    try {
                        contentStore.saveLog(log.getText().substring(log.getLength() - 51));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    contentStore.saveContent();

                    newWindow.close();
                } else {
                    newWindow.close();
                    Pane pane = new Pane();
                    Stage window = new Stage();
                    window.initModality(Modality.APPLICATION_MODAL);
                    window.setResizable(false);

                    Scene sceneWithLabels1 = new Scene(pane, 307, 230);
                    window.setScene(sceneWithLabels1);
                    window.show();

                    URL str = getClass().getResource("video.mp4");
                    Media media = new Media(str.toString());

                    MediaPlayer mediaPlayer = new MediaPlayer(media);
                    MediaView mediaView = new MediaView(mediaPlayer);
                    pane.getChildren().add(mediaView);
                    mediaPlayer.play();

                    Date date = new Date();
                    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));
                    log.appendText("\n" + dateFormat.format(date) + " : Жизнь за Нерзула!!!  ");

                    try {
                        contentStore.saveLog(log.getText().substring(log.getLength() - 54));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    PauseTransition delay = new PauseTransition(Duration.seconds(6));   // заставка запустится на 3 секунды
                    delay.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            window.close();
                        }
                    });
                    delay.play();
                }
            }
        });
    }
}