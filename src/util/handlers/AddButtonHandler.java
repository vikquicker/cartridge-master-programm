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

import java.io.IOException;
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

        if (tabName.equals("111") && contentStore.getLocationListString111().size() == 0) {
            contentStore.getLocationListString111().addAll(Arrays.asList("356500",
                    "356501", "356503", "356504", "356505", "356506", "356507", "356511",
                    "356517", "356518", "356520", "356521", "356522", "356524", "356525",
                    "356526", "356527", "356530", "356531", "356532", "356533", "356534",
                    "356535", "356540", "356542", "356543", "356544", "356545", "356550",
                    "356551", "356552", "356554", "356555", "356556", "356557"));

        } else if (tabName.equals("115") && contentStore.getLocationList115().size() == 0) {
            contentStore.getLocationList115().addAll(Arrays.asList("356523", "356537", "356546", "Учебный"));
        } else if (tabName.equals("226") && contentStore.getLocationList226().size() == 0) {
            contentStore.getLocationList226().addAll(Arrays.asList("Автоколонна", "Начальник", "Зам.начальника", "Бухгалтерия"));
        }
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
                "На заправке 3",
                "До выяснения",
                "Списан",
                "Заправлен");
        ComboBox<String> statusField = new ComboBox<>(statusList);
        statusField.setValue("Пустой");

        DatePicker dateField = new DatePicker();
        dateField.setValue(LocalDate.now());
        dateField.setShowWeekNumbers(true);
        dateField.setMaxWidth(100);

        //location for string cartridge 111
        contentStore.getLocationListString111().add("Коммерция");
        contentStore.getLocationListString111().add("Информ-пункт");
        contentStore.getLocationListString111().add("Эксплуатация");
        contentStore.getLocationListString111().add("IT");

        ObservableList<String> locationListString111 = FXCollections.observableArrayList(contentStore.getLocationListString111());
        ComboBox<String> locationFieldString111 = new ComboBox<>(locationListString111);
        locationFieldString111.setMaxWidth(100);
        locationFieldString111.setMinWidth(100);
        try {
            locationFieldString111.setValue(null);
        } catch (Exception e) {

        }


        //1st location
//        ObservableList<Integer> locationList111 = FXCollections.observableArrayList(contentStore.getLocationList111());
//        ComboBox<Integer> locationField111 = new ComboBox<>(locationList111);
//        locationField111.setMaxWidth(100);
//        locationField111.setMinWidth(100);
//        try {
//            locationField111.setValue(null);
//        } catch (Exception e) {
//
//        }

        //2rd location
        ObservableList<String> locationList115 = FXCollections.observableArrayList(contentStore.getLocationList115());
        ComboBox<String> locationField115 = new ComboBox<>(locationList115);
        locationField115.setMaxWidth(100);
        locationField115.setMinWidth(100);
        try {
            locationField115.setValue(null);
        } catch (Exception e) {

        }

        //3rd location
        ObservableList<String> locationList226 = FXCollections.observableArrayList(contentStore.getLocationList226());
        ComboBox<String> locationField226 = new ComboBox<>(locationList226);
        locationField226.setMaxWidth(100);
        locationField226.setMinWidth(100);
        try {
            locationField226.setValue(null);
        } catch (Exception e) {

        }

        TextField locationFieldNew = new TextField();
        locationFieldNew.setMaxWidth(100);

        TextArea textAreaField = new TextArea();
        textAreaField.setMaxSize(215, 70);
        textAreaField.setWrapText(true);

        Button add = new Button("Добавить");
        add.setMinSize(80, 60);

            //Position LabelY
            statusLabel.setLayoutY(5);
            numberLabel.setLayoutY(5);
            dataLabel.setLayoutY(5);
            locationLabel.setLayoutY(5);
            noticeLabel.setLayoutY(5);
            locationLabelNew.setLayoutY(50);

            //Position LabelX
            numberLabel.setLayoutX(40);
            statusLabel.setLayoutX(165);
            dataLabel.setLayoutX(298);
            locationLabel.setLayoutX(430);
            noticeLabel.setLayoutX(650);
            locationLabelNew.setLayoutX(425);


            //Position FieldY
            numberField.setLayoutY(40);
            statusField.setLayoutY(40);
            dateField.setLayoutY(40);
            textAreaField.setLayoutY(25);
            locationFieldString111.setLayoutY(25);
            locationField115.setLayoutY(25);
            locationField226.setLayoutY(25);
            locationFieldNew.setLayoutY(70);
            add.setLayoutY(29);


            //Position FieldX
            numberField.setLayoutX(20);
            statusField.setLayoutX(120);
            dateField.setLayoutX(262);
            textAreaField.setLayoutX(580);
            locationFieldString111.setLayoutX(420);
            locationField115.setLayoutX(420);
            locationField226.setLayoutX(420);
            locationFieldNew.setLayoutX(420);
            add.setLayoutX(815);


        if (tabName.equals("111")) {
            pane.getChildren().addAll(numberLabel, statusLabel, dataLabel, locationLabel, noticeLabel,
                    numberField,
                    statusField,
                    dateField,
                    locationFieldString111,
                    textAreaField,
                    locationFieldNew,
                    locationLabelNew,
                    add);
        } else if (tabName.equals("115")) {
            pane.getChildren().addAll(numberLabel, statusLabel, dataLabel, locationLabel, noticeLabel,
                    numberField,
                    statusField,
                    dateField,
                    textAreaField,
                    locationField115,
                    locationFieldNew,
                    locationLabelNew,
                    add);
        } else if (tabName.equals("226")) {
            pane.getChildren().addAll(numberLabel, statusLabel, dataLabel, locationLabel, noticeLabel,
                    numberField,
                    statusField,
                    dateField,
                    textAreaField,
                    locationField226,
                    locationFieldNew,
                    locationLabelNew,
                    add);
        }
        Scene sceneWithLabels1 = new Scene(pane, 900, 90);
        newWindow.setScene(sceneWithLabels1);
        newWindow.show();

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (!numberField.getText().equals("кошко")) {
                    Cartridge cartridgeToAdd = new Cartridge();
                    Summary summaryNew;

                    if (contentStore.getCartridgesMap().get("q_111").size() == 0) {
                        cartridgeToAdd.setIdTable(1);
                    } else {
                        cartridgeToAdd.setIdTable(contentStore.getCartridgesMap().get("q_111").
                                get(contentStore.getCartridgesMap().get("q_111").size() - 1).getIdTable() + 1);
                    }
                    double idToCartridgeAndUtil = Math.random();
                    cartridgeToAdd.setId(idToCartridgeAndUtil);
                    cartridgeToAdd.setNumber(numberField.getText());
                    cartridgeToAdd.setStatus(statusField.getValue());
                    cartridgeToAdd.setDate(dateField.getValue());
                    //TODO Validation
//                    if (tabName.equals("111")) {
//                        if (locationFieldNew.getLength() > 0 && locationFieldNewString.getLength() == 0 &&
//                                locationField111.getValue() == null && locationFieldString111.getValue() == null) {
//                            contentStore.getLocationList111().add(Integer.valueOf(locationFieldNew.getText()));
//                            cartridgeToAdd.setLocation(Integer.valueOf(locationFieldNew.getText()));
//                        } else if (locationFieldNewString.getLength() > 0 && locationFieldNew.getLength() == 0 &&
//                                locationField111.getValue() == null && locationFieldString111.getValue() == null) {
//                            contentStore.getLocationListString111().add(locationFieldNewString.getText());
//                            cartridgeToAdd.setLocationString(locationFieldNewString.getText());
//                        } else if (locationFieldNewString.getLength() == 0 && locationFieldNew.getLength() == 0 &&
//                                locationField111.getValue() != null && locationFieldString111.getValue() == null) {
//                            cartridgeToAdd.setLocation(Integer.valueOf(locationField111.getValue()));
//                        } else if (locationFieldNewString.getLength() == 0 && locationFieldNew.getLength() == 0 &&
//                                locationField111.getValue() == null && locationFieldString111.getValue() != null) {
//                            cartridgeToAdd.setLocationString(locationFieldString111.getValue());
//                        }
//                    } else if (tabName.equals("115")) {
//
//                    } else if (tabName.equals("226")) {
//
//                    }
                    if (locationFieldNew.getLength() > 0) {
                        if (tabName.equals("111")){
                            contentStore.getLocationListString111().add(locationFieldNew.getText());
                            cartridgeToAdd.setLocationString(locationFieldNew.getText());
                        }else if(tabName.equals("115")){
                            contentStore.getLocationList115().add(locationFieldNew.getText());
                            cartridgeToAdd.setLocationString(locationFieldNew.getText());
                        }else if(tabName.equals("226")){
                            contentStore.getLocationList226().add(locationFieldNew.getText());
                            cartridgeToAdd.setLocationString(locationFieldNew.getText());
                        }
                    } else {
                        if (tabName.equals("111")){
                            cartridgeToAdd.setLocationString(String.valueOf(locationFieldString111.getValue()));
                        }else if(tabName.equals("115")){
                            cartridgeToAdd.setLocationString(locationField115.getValue());
                        }else if(tabName.equals("226")){
                            cartridgeToAdd.setLocationString(locationField226.getValue());
                        }

                    }

                    cartridgeToAdd.setNotice(textAreaField.getText());
                    contentStore.getCartridgesMap().get("q_" + tabName).add(cartridgeToAdd);
                    tabCartridge.getItems().add(cartridgeToAdd);

//                    if (statusField.getValue().equals("Списан")) {
//                        Utilized utilizedToAdd = new Utilized();
//
//                        utilizedToAdd.setId(idToCartridgeAndUtil);
//                        if (tabName.equals("111")) {
//                            utilizedToAdd.setNumber(String.valueOf(cartridgeToAdd.getIdTable()));
//                        } else {
//                            utilizedToAdd.setNumber(numberField.getText());
//                        }
//                        utilizedToAdd.setStatus(statusField.getValue());
//                        utilizedToAdd.setDate(dateField.getValue());
//                        utilizedToAdd.setNotice(textAreaField.getText());
//
//                        contentStore.getUtilizedArrayList().add(utilizedToAdd);
//                        tabUtilized.getItems().add(utilizedToAdd);
//                    }

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

                    if (tabName.equals("111")) {
                        log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeToAdd.getIdTable() + " добавлен на отделение " +
                                cartridgeToAdd.getLocationString() + " статус : " + cartridgeToAdd.getStatus() + "  ");
                    } else if (tabName.equals("115")) {
                        log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeToAdd.getNumber() + " добавлен на отделение " +
                                cartridgeToAdd.getLocationString() + " статус : " + cartridgeToAdd.getStatus() + "  ");
                    } else if (tabName.equals("226")) {
                        log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeToAdd.getNumber() + " добавлен на отделение " +
                                cartridgeToAdd.getLocationString() + " статус : " + cartridgeToAdd.getStatus() + "  ");
                    }


                    String str = log.getText();
                    try {
                        contentStore.saveLog(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    contentStore.saveContent();


//                    Printer printer = Printer.getDefaultPrinter();
//                    PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.DEFAULT);
//                    double scaleX = pageLayout.getPrintableWidth() / tabCartridge.getBoundsInParent().getWidth();
//                    double scaleY = pageLayout.getPrintableHeight() / tabCartridge.getBoundsInParent().getHeight();
//                    //tabCartridge.getTransforms().add(new Scale(scaleX, scaleY));
//
//                    javafx.print.PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
//                    printerJob.printPage(pageLayout, (Node) tabCartridge.getItems());
//                    printerJob.endJob();

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

                    String str1 = log.getText();
                    try {
                        contentStore.saveLog(str1);
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