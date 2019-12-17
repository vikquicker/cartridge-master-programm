package util.handlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

public class EditButtonHandler implements EventHandler<ActionEvent> {
    ContentStore contentStore = ContentStore.getContentStore();
    private String tabNameFromEditButtonns;
    private TableView<Cartridge> tabCartridge;
    private TableView<Utilized> tabUtilized;
    private TableView<Summary> tabSummary;

    double cartridgeForEdit;
    Cartridge cartridgeFromContent;
    TextArea log;

    public EditButtonHandler(String str, double cartridgeForEdit,
                             TableView<Cartridge> tabCartridge,
                             TableView<Utilized> tabUtilized,
                             TableView<Summary> tabSummary, Cartridge cartridgeFromContent, TextArea log) {
        this.tabCartridge = tabCartridge;
        this.tabUtilized = tabUtilized;
        this.tabSummary = tabSummary;
        this.cartridgeForEdit = cartridgeForEdit;
        tabNameFromEditButtonns = str;
        tabNameFromEditButtonns = "q_" + tabNameFromEditButtonns;
        this.cartridgeFromContent = cartridgeFromContent;
        this.log = log;
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
                "На заправке 3",
                "До выяснения",
                "Списан",
                "Заправлен",
                "Пустой");
        ComboBox<String> statusField = new ComboBox<>(statusList);
        statusField.setValue(cartridgeFromContent.getStatus());

        DatePicker dateField = new DatePicker();
        dateField.setValue(cartridgeFromContent.getDate());
        dateField.setShowWeekNumbers(true);
        dateField.setMaxWidth(100);

        //1st location
        ObservableList<String> locationList111 = FXCollections.observableArrayList(contentStore.getLocationListString111());
        ComboBox<String> locationField111 = new ComboBox<>(locationList111);
        locationField111.setValue(cartridgeFromContent.getLocationString());
        locationField111.setMinWidth(100);
        locationField111.setMaxWidth(100);

        //2rd location
        ObservableList<String> locationList115 = FXCollections.observableArrayList(contentStore.getLocationList115());
        ComboBox<String> locationField115 = new ComboBox<>(locationList115);
        locationField115.setValue(cartridgeFromContent.getLocationString());
        locationField115.setMinWidth(100);
        locationField115.setMaxWidth(100);

        //3rd location
        ObservableList<String> locationList226 = FXCollections.observableArrayList(contentStore.getLocationList226());
        ComboBox<String> locationField226 = new ComboBox<>(locationList226);
        locationField226.setValue(cartridgeFromContent.getLocationString());
        locationField226.setMinWidth(100);
        locationField226.setMaxWidth(100);


        TextField locationFieldNew = new TextField();
        locationFieldNew.setMaxWidth(100);

        ArrayList<String> locationArrayListNull = new ArrayList<>();
        locationArrayListNull.add("Пустое");
        locationArrayListNull.add("Есть");
        ObservableList<String> locationListNull = FXCollections.observableArrayList(locationArrayListNull);
        ComboBox<String> locationFieldNull = new ComboBox<>(locationListNull);
        locationFieldNull.setValue(locationArrayListNull.get(1));
        locationFieldNull.setMaxWidth(85);

        Button deleteLocation = new Button("Удалить");
        deleteLocation.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String cartridgeLocation = cartridgeFromContent.getLocationString();
                if (tabNameFromEditButtonns.equals("q_111")) {
                    contentStore.getLocationListString111().remove(locationField111.getValue());
                    cartridgeFromContent.setLocationString(null);
                } else if (tabNameFromEditButtonns.equals("q_115")) {
                    contentStore.getLocationList115().remove(locationField115.getValue());
                    cartridgeFromContent.setLocationString(null);
                } else if (tabNameFromEditButtonns.equals("q_226")) {
                    contentStore.getLocationList226().remove(locationField226.getValue());
                    cartridgeFromContent.setLocationString(null);
                }

                Date date = new Date();
                DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

                if (tabNameFromEditButtonns.equals("q_111")) {
                    log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getIdTable() + " расположение "
                            + locationField111.getValue() + " удалено!  ");
                } else if (tabNameFromEditButtonns.equals("q_115")){
                    log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() + " расположение  "
                            + locationField115.getValue() + " удалено!  ");
                }else if (tabNameFromEditButtonns.equals("q_226")){
                    log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() + " расположение  "
                            + locationField226.getValue() + " удалено!  ");
                }

                String str = log.getText();
                try {
                    contentStore.saveLog(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < contentStore.getSummaryArrayList().size(); i++) {
                    if (contentStore.getSummaryArrayList().get(i).getOpsLocation().equals(cartridgeLocation)) {
                        contentStore.getSummaryArrayList().remove(i);
                    }
                }
                tabSummary.getItems().clear();
                HashMap<String, Integer> summaryCount = contentStore.summuryCount();
                Summary summaryNew;
                contentStore.getSummaryArrayList().clear();
                for (Map.Entry<String, Integer> countAndLocation : summaryCount.entrySet()) {
                    summaryNew = new Summary();
                    summaryNew.setOpsLocation(countAndLocation.getKey());
                    summaryNew.setCount(countAndLocation.getValue());
                    contentStore.getSummaryArrayList().add(summaryNew);
                }
                tabSummary.getItems().addAll(contentStore.getSummaryArrayList());

                contentStore.saveContent();

                tabCartridge.refresh();
                tabSummary.refresh();
                tabUtilized.refresh();

                newWindow.close();
            }
        });

        TextArea textAreaField = new TextArea();
        textAreaField.setMaxSize(215, 70);
        textAreaField.setText(cartridgeFromContent.getNotice());
        textAreaField.setWrapText(true);


        Button add = new Button("Модифицировать!!!");
        add.setMinSize(80, 60);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String previosStatus = cartridgeFromContent.getStatus();
                ArrayList<String> previosLocationString = new ArrayList<>();
                if (cartridgeFromContent.getLocationString() == null) {
                    previosLocationString.add("");
                } else {
                    previosLocationString.add(cartridgeFromContent.getLocationString());
                }
                String cartridgeLocation = cartridgeFromContent.getLocationString();

                cartridgeFromContent.setNumber(numberField.getText());
                cartridgeFromContent.setStatus(statusField.getValue());
                cartridgeFromContent.setDate(dateField.getValue());
                if (locationFieldNew.getLength() > 0) {
                    if (tabNameFromEditButtonns.equals("q_111")) {
                        contentStore.getLocationListString111().add(locationFieldNew.getText());
                        cartridgeFromContent.setLocationString(locationFieldNew.getText());
                    } else if (tabNameFromEditButtonns.equals("q_115")) {
                        contentStore.getLocationList115().add(locationFieldNew.getText());
                        cartridgeFromContent.setLocationString(locationFieldNew.getText());
                    } else if (tabNameFromEditButtonns.equals("q_226")) {
                        contentStore.getLocationList226().add(locationFieldNew.getText());
                        cartridgeFromContent.setLocationString(locationFieldNew.getText());
                    }
                } else {
                    if (tabNameFromEditButtonns.equals("q_111")) {
                        cartridgeFromContent.setLocationString(locationField111.getValue());
                    } else if (tabNameFromEditButtonns.equals("q_115")) {
                        cartridgeFromContent.setLocationString(locationField115.getValue());
                    } else if (tabNameFromEditButtonns.equals("q_226")) {
                        cartridgeFromContent.setLocationString(locationField226.getValue());
                    }
                }

                if (locationFieldNull.getValue().equals("Пустое")) {
                    if (tabNameFromEditButtonns.equals("q_111")) {
                        cartridgeFromContent.setLocationString(null);
                    } else if (tabNameFromEditButtonns.equals("q_115")) {
                        cartridgeFromContent.setLocationString(null);
                    } else if (tabNameFromEditButtonns.equals("q_226")) {
                        cartridgeFromContent.setLocationString(null);
                    }
                }
                cartridgeFromContent.setNotice(textAreaField.getText());


                if (cartridgeFromContent.getStatus().equals("На отделении")) {
                    for (int i = 0; i < contentStore.getSummaryArrayList().size(); i++) {
                        if (contentStore.getSummaryArrayList().get(i).getOpsLocation().
                                equals(cartridgeLocation)) {
                            contentStore.getSummaryArrayList().remove(i);
                        }
                    }
                    tabSummary.getItems().clear();
                    HashMap<String, Integer> summaryCount = contentStore.summuryCount();
                    Summary summaryNew;
                    contentStore.getSummaryArrayList().clear();
                    for (Map.Entry<String, Integer> countAndLocation : summaryCount.entrySet()) {
                        summaryNew = new Summary();
                        summaryNew.setOpsLocation(countAndLocation.getKey());
                        summaryNew.setCount(countAndLocation.getValue());
                        contentStore.getSummaryArrayList().add(summaryNew);
                    }
                    tabSummary.getItems().addAll(contentStore.getSummaryArrayList());
                }

                if (previosStatus.equals("На отделении") && !cartridgeFromContent.getStatus().equals("На отделении")) {
                    for (int i = 0; i < contentStore.getSummaryArrayList().size(); i++) {
                        if (contentStore.getSummaryArrayList().get(i).getOpsLocation().
                                equals(cartridgeLocation)) {
                            contentStore.getSummaryArrayList().remove(i);
                        }
                    }
                    tabSummary.getItems().clear();
                    HashMap<String, Integer> summaryCount = contentStore.summuryCount();
                    Summary summaryNew;
                    contentStore.getSummaryArrayList().clear();
                    for (Map.Entry<String, Integer> countAndLocation : summaryCount.entrySet()) {
                        summaryNew = new Summary();
                        summaryNew.setOpsLocation(countAndLocation.getKey());
                        summaryNew.setCount(countAndLocation.getValue());
                        contentStore.getSummaryArrayList().add(summaryNew);
                    }
                    tabSummary.getItems().addAll(contentStore.getSummaryArrayList());
                }

                //TODO think about observer
//                if (previosStatus.equals("Списан") && cartridgeFromContent.getStatus().equals("Списан")) {
//                    Utilized utilized = new Utilized();
//                    utilized.setId(cartridgeForEdit);
//                    if (tabNameFromEditButtonns.equals("q_111")) {
//                        utilized.setNumber(String.valueOf(cartridgeFromContent.getIdTable()));
//                    } else {
//                        utilized.setNumber(numberField.getText());
//                    }
//                    utilized.setStatus(statusField.getValue());
//                    utilized.setDate(dateField.getValue());
//                    utilized.setNotice(textAreaField.getText());
//
//                    for (int i = 0; i < contentStore.getUtilizedArrayList().size(); i++) {
//                        if (cartridgeFromContent.getId() == contentStore.getUtilizedArrayList().get(i).getId()) {
//                            contentStore.getUtilizedArrayList().set(i, utilized);
//                        }
//                    }
//                    tabUtilized.getItems().clear();
//                    tabUtilized.getItems().addAll(contentStore.getUtilizedArrayList());
//                }
                if (!previosStatus.equals("Списан") && cartridgeFromContent.getStatus().equals("Списан")) {
                    Utilized utilized = new Utilized();
                    utilized.setId(cartridgeForEdit);
                    if (tabNameFromEditButtonns.equals("q_111")) {
                        utilized.setNumber(String.valueOf(cartridgeFromContent.getIdTable()));
                    } else {
                        utilized.setNumber(numberField.getText());
                    }
                    utilized.setStatus(statusField.getValue());
                    utilized.setDate(dateField.getValue());
                    utilized.setNotice(textAreaField.getText());
                    tabUtilized.getItems().add(utilized);

                    contentStore.getUtilizedArrayList().add(utilized);
                }

                //LOG!!!
                if (tabNameFromEditButtonns.equals("q_111")) {
                    if (previosLocationString.get(0) != cartridgeFromContent.getLocationString() && !previosStatus.equals(cartridgeFromContent.getStatus())) {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

                        if (tabNameFromEditButtonns.equals("q_111")) {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getIdTable() +
                                    " Переехал с: " + previosLocationString + " на: " + cartridgeFromContent.getLocationString() + " статус: " +
                                    previosStatus + " изменён на: " + cartridgeFromContent.getStatus() + "  ");
                        } else {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() +
                                    " Переехал с: " + previosLocationString + " на: " + cartridgeFromContent.getLocationString() + " статус: " +
                                    previosStatus + " изменён на: " + cartridgeFromContent.getStatus() + "  ");
                        }

                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (!previosStatus.equals(cartridgeFromContent.getStatus())) {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

                        if (tabNameFromEditButtonns.equals("q_111")) {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getIdTable() +
                                    " Статус: " + previosStatus + " изменён на: " + cartridgeFromContent.getStatus() + " Расположение: " +
                                    cartridgeFromContent.getLocationString() + "  ");
                        } else {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() +
                                    " Статус: " + previosStatus + " изменён на: " + cartridgeFromContent.getStatus() + " Расположение: " +
                                    cartridgeFromContent.getLocationString() + "  ");
                        }

                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (previosLocationString.get(0) != cartridgeFromContent.getLocationString()) {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

                        if (tabNameFromEditButtonns.equals("q_111")) {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getIdTable() +
                                    " Переехал с: " + previosLocationString + " на: " + cartridgeFromContent.getLocationString() + " статус: " +
                                    cartridgeFromContent.getStatus() + "  ");
                        } else {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() +
                                    " Переехал с: " + previosLocationString + " на: " + cartridgeFromContent.getLocationString() + " статус: " +
                                    cartridgeFromContent.getStatus() + "  ");
                        }

                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

                        if (tabNameFromEditButtonns.equals("q_111")) {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getIdTable() + " изменён  ");
                        } else {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() + " изменён  ");
                        }

                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (previosLocationString.get(0) != cartridgeFromContent.getLocationString() && !previosStatus.equals(cartridgeFromContent.getStatus())) {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));
                        log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() +
                                " Переехал с: " + previosLocationString + " на: " + cartridgeFromContent.getLocationString() + " статус: " +
                                previosStatus + " изменён на: " + cartridgeFromContent.getStatus() + "  ");

                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (!previosStatus.equals(cartridgeFromContent.getStatus())) {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));
                        log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() +
                                " Статус: " + previosStatus + " изменён на: " + cartridgeFromContent.getStatus() + " Расположение: " +
                                cartridgeFromContent.getLocationString() + "  ");

                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (!previosLocationString.get(0).equals(cartridgeFromContent.getLocationString())) {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));
                        log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() +
                                " Переехал с: " + previosLocationString + " на: " + cartridgeFromContent.getLocationString() + " статус: " +
                                cartridgeFromContent.getStatus() + "  ");


                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Date date = new Date();
                        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

                        if (tabNameFromEditButtonns.equals("q_111")) {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getIdTable() + " изменён  ");
                        } else {
                            log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeFromContent.getNumber() + " изменён  ");
                        }

                        String str = log.getText();
                        try {
                            contentStore.saveLog(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                contentStore.saveContent();

                tabCartridge.refresh();
                tabSummary.refresh();
                tabUtilized.refresh();

                newWindow.close();
            }
        });


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
        locationLabel.setLayoutX(390);
        noticeLabel.setLayoutX(650);
        locationLabelNew.setLayoutX(385);

        //Position FieldY
        numberField.setLayoutY(40);
        statusField.setLayoutY(40);
        dateField.setLayoutY(40);
        textAreaField.setLayoutY(25);
        locationField111.setLayoutY(25);
        deleteLocation.setLayoutY(25);
        locationFieldNew.setLayoutY(70);
        add.setLayoutY(29);
        locationFieldNull.setLayoutY(70);
        locationField115.setLayoutY(25);
        locationField226.setLayoutY(25);

        //Position FieldX
        numberField.setLayoutX(20);
        statusField.setLayoutX(120);
        dateField.setLayoutX(262);
        textAreaField.setLayoutX(580);
        locationField111.setLayoutX(380);
        deleteLocation.setLayoutX(500);
        locationFieldNew.setLayoutX(380);
        add.setLayoutX(815);
        locationFieldNull.setLayoutX(488);
        locationField115.setLayoutX(380);
        locationField226.setLayoutX(380);

        if (tabNameFromEditButtonns.equals("q_111")) {
            pane.getChildren().addAll(numberLabel, statusLabel, dataLabel, locationLabel, noticeLabel,
                    numberField,
                    statusField,
                    dateField,
                    textAreaField,
                    locationField111,
                    locationFieldNew,
                    locationLabelNew,
                    add,
                    deleteLocation,
                    locationFieldNull);
        } else if (tabNameFromEditButtonns.equals("q_115")) {
            pane.getChildren().addAll(numberLabel, statusLabel, dataLabel, locationLabel, noticeLabel,
                    numberField,
                    statusField,
                    dateField,
                    textAreaField,
                    locationField115,
                    locationFieldNew,
                    locationLabelNew,
                    add,
                    deleteLocation,
                    locationFieldNull);
        } else if (tabNameFromEditButtonns.equals("q_226")) {
            pane.getChildren().addAll(numberLabel, statusLabel, dataLabel, locationLabel, noticeLabel,
                    numberField,
                    statusField,
                    dateField,
                    textAreaField,
                    locationField226,
                    locationFieldNew,
                    locationLabelNew,
                    add,
                    deleteLocation,
                    locationFieldNull);
        }

        Scene sceneWithLabels1 = new Scene(pane, 945, 90);
        newWindow.setScene(sceneWithLabels1);
        newWindow.show();
    }
}
