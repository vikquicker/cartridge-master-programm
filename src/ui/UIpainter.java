package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;
import util.handlers.AddButtonHandler;
import util.handlers.EditButtonHandler;
import util.handlers.RemoveButtonHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UIpainter {
    ContentStore contentStore = ContentStore.getContentStore();
    private static UIpainter uIpainter;
    Socket socket = new Socket();

    public static UIpainter getUIpainter() {
        if (uIpainter == null) {
            uIpainter = new UIpainter();
        }
        return uIpainter;
    }

    private UIpainter() {
    }


    public void createUI(Stage primaryStage) {

        InputStream inputStreamIco = getClass().getResourceAsStream("cat.png");
        primaryStage.getIcons().add(new Image(inputStreamIco));

        VBox root = new VBox();
        List<String> list = contentStore.getTabList();
        String logText = ("Добро пожаловать в программу - Cartridge Master 4000  ");
        TextArea log = new TextArea(logText + "\r\n");
        log.setEditable(false);
        log.setMaxHeight(100);
        log.setMinHeight(100);
        root.getChildren().addAll(initialTabPane(list, log), log);
        primaryStage.setTitle("Cartridge Master 4000");
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/resources/css/main.css");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMaxHeight(720);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public TabPane initialTabPane(List<String> listTabs, TextArea log) {
        try {
            contentStore.saveLog(log.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
        TabPane tabPane = new TabPane();
        Tab tableTab;
        Tab tableTabUtilized = new Tab();
        Tab tableTabSummary = new Tab();
        ScrollPane scrollPaneTable;
        TableView<Cartridge> tableViewCartridge = new TableView<>();
        TableView<Utilized> tableViewUtilized = new TableView<>();
        TableView<Summary> tableViewSummary = new TableView<>();

        for (int i = 0; i < listTabs.size(); i++) {
            if (listTabs.get(i).startsWith("q_")) {
                Cartridge cartridge = new Cartridge();
                VBox vBoxForButtonAndScroll = new VBox();
                String presentedTabName = listTabs.get(i)
                        .substring("q_".length());
                tableTab = new Tab(presentedTabName);

                Button addItem = new Button("Добавить");
                vBoxForButtonAndScroll.getChildren().add(addItem);

                ArrayList<Cartridge> arrayList = new ArrayList<>();
                ArrayList<Cartridge> listFromMap = contentStore.getCartridgesMap().get(listTabs.get(i));
                scrollPaneTable = new ScrollPane();
                HBox hBox = new HBox();
                if (listFromMap != null) {
                    arrayList.addAll(listFromMap);
                    hBox.getChildren().addAll(tableViewCartridge = initiateTable(arrayList, cartridge.getClass(), presentedTabName,
                            tableViewCartridge, tableViewUtilized, tableViewSummary, log));
                } else {
                    hBox.getChildren().addAll(tableViewCartridge = initiateTable(arrayList, cartridge.getClass(), presentedTabName,
                            tableViewCartridge, tableViewUtilized, tableViewSummary, log));
                }
                addItem.setOnAction(new AddButtonHandler(presentedTabName, tableViewCartridge, tableViewUtilized,
                        tableViewSummary, log));

                scrollPaneTable.setContent(hBox);
                vBoxForButtonAndScroll.getChildren().add(scrollPaneTable);
                tableTab.setContent(vBoxForButtonAndScroll);
                tabPane.getTabs().add(tableTab);
                tableTab.setClosable(false);
            } else if (listTabs.get(i).equals("Сводная")) {
                VBox vBoxForButtonAndScroll = new VBox();
                String presentedTabNameSummary = listTabs.get(i);
                tableTab = new Tab(presentedTabNameSummary);

                Summary summary = new Summary();
                ArrayList<Summary> summaryArrayList = new ArrayList<>();
                ArrayList<Summary> secondListFromMap = contentStore.getSummaryArrayList();
                scrollPaneTable = new ScrollPane();
                HBox hBox = new HBox();
                if (secondListFromMap != null) {
                    summaryArrayList.addAll(secondListFromMap);
                    hBox.getChildren().addAll(tableViewSummary = initiateTable(summaryArrayList, summary.getClass(), presentedTabNameSummary,
                            tableViewCartridge, tableViewUtilized, tableViewSummary, log));
                } else {
                    hBox.getChildren().addAll(tableViewSummary = initiateTable(summaryArrayList, summary.getClass(), presentedTabNameSummary,
                            tableViewCartridge, tableViewUtilized, tableViewSummary, log));
                }

                scrollPaneTable.setContent(hBox);
                vBoxForButtonAndScroll.getChildren().add(scrollPaneTable);
                tableTab.setContent(vBoxForButtonAndScroll);
                tableTabSummary = tableTab;
                tableTab.setClosable(false);
            } else if (listTabs.get(i).equals("Списанные")) {
                VBox vBoxForButtonAndScroll = new VBox();
                String presentedTabNameUtilized = listTabs.get(i);
                tableTab = new Tab(presentedTabNameUtilized);

                ArrayList<Utilized> utilizedArrayList = new ArrayList<>();
                ArrayList<Utilized> thirdListFromMap = contentStore.getUtilizedArrayList();
                scrollPaneTable = new ScrollPane();
                HBox hBox = new HBox();
                tableViewCartridge = initiateTable(utilizedArrayList, utilizedArrayList.getClass(), presentedTabNameUtilized,
                        tableViewCartridge, tableViewUtilized, tableViewSummary, log);
                if (thirdListFromMap != null) {
                    utilizedArrayList.addAll(thirdListFromMap);
                    hBox.getChildren().addAll(tableViewUtilized = initiateTable(utilizedArrayList, utilizedArrayList.getClass(), presentedTabNameUtilized,
                            tableViewCartridge, tableViewUtilized, tableViewSummary, log),
                            new Pane());
                } else {
                    hBox.getChildren().addAll(tableViewUtilized = initiateTable(utilizedArrayList, utilizedArrayList.getClass(), presentedTabNameUtilized,
                            tableViewCartridge, tableViewUtilized, tableViewSummary, log),
                            new Pane());
                }

                scrollPaneTable.setContent(hBox);
                vBoxForButtonAndScroll.getChildren().add(scrollPaneTable);
                tableTab.setContent(vBoxForButtonAndScroll);
                tableTabUtilized = tableTab;
                tableTab.setClosable(false);
            }
        }
        tabPane.getTabs().add(tableTabSummary);
        tabPane.getTabs().add(tableTabUtilized);
        return tabPane;
    }


    private TableView initiateTable(Object list, Class className, String tabName,
                                    TableView<Cartridge> cartridgeTable, TableView<Utilized> utilizedTable, TableView<Summary> summaryTable,
                                    TextArea log) {
        Cartridge cartridge = new Cartridge();
        Summary summary = new Summary();
        if (className.equals(cartridge.getClass())) {
            ObservableList<Cartridge> cartridges = FXCollections.observableArrayList((ArrayList<Cartridge>) list);
            TableView<Cartridge> table = new TableView<Cartridge>(cartridges);
            table.setEditable(true);
            table.setPrefWidth(1200);
            table.setPrefHeight(440);

// столбец для вывода имени
            TableColumn<Cartridge, String> numberColumn = new TableColumn<Cartridge, String>("Номер");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("number"));
            table.getColumns().add(numberColumn);
            numberColumn.setMinWidth(100);

            TableColumn<Cartridge, String> colorColumn = new TableColumn<Cartridge, String>("Col");
            colorColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("color"));
            table.getColumns().add(colorColumn);
            colorColumn.setMinWidth(30);
            colorColumn.setMaxWidth(30);

            TableColumn<Cartridge, String> statusColumn = new TableColumn<Cartridge, String>("Статус");
            statusColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("status"));
            table.getColumns().add(statusColumn);
            statusColumn.setMinWidth(100);

            TableColumn<Cartridge, Date> dateColumn = new TableColumn<Cartridge, Date>("Дата");
            dateColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, Date>("date"));
            table.getColumns().add(dateColumn);
            dateColumn.setMinWidth(100);

            TableColumn<Cartridge, String> locationColumn = new TableColumn<Cartridge, String>("Расположение");
            locationColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("location"));
            table.getColumns().add(locationColumn);
            locationColumn.setMinWidth(100);


            TableColumn<Cartridge, String> noticeColumn = new TableColumn<Cartridge, String>("Примечание");
            noticeColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("notice"));
            table.getColumns().add(noticeColumn);
            noticeColumn.setMinWidth(683);


            TableColumn actionColEdit = new TableColumn("Edit");
            actionColEdit.setMaxWidth(35);

            TableColumn actionColDelete = new TableColumn("Delete");
            actionColDelete.setMaxWidth(50);
            actionColDelete.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

            colorColumn.setCellFactory(column -> {
                return new TableCell<Cartridge, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            Cartridge cartridge = getTableView().getItems().get(getIndex());
                            if (cartridge.getStatus().equals("Заправлен")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: green");
                            } else if (cartridge.getStatus().equals("Пустой")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: yellow");
                            } else if (cartridge.getStatus().equals("Списан")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: red");
                            } else if (cartridge.getStatus().equals("На заправке 1")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: blue");
                            } else if (cartridge.getStatus().equals("На отделении")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: orange");
                            } else if (cartridge.getStatus().equals("До выяснения")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: grey");
                            } else if (cartridge.getStatus().equals("На заправке 2")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: turquoise");
                            } else if (cartridge.getStatus().equals("На заправке 3")) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: purple");
                            }
                        }
                    }
                };
            });

            Callback<TableColumn<Cartridge, String>, TableCell<Cartridge, String>> cellFactoryEdit
                    =
                    new Callback<TableColumn<Cartridge, String>, TableCell<Cartridge, String>>() {
                        @Override
                        public TableCell call(final TableColumn<Cartridge, String> param) {
                            final TableCell<Cartridge, String> cell = new TableCell<Cartridge, String>() {
                                InputStream inputEdit = getClass().getResourceAsStream("edit.png");
                                Image imageEdit = new Image(inputEdit);
                                ImageView imageViewEdit = new ImageView(imageEdit);
                                final Button edit = new Button("", imageViewEdit);

                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        Cartridge cartridge = getTableView().getItems().get(getIndex());
                                        TableView<Cartridge> cartridgeTableView = getTableView();
                                        edit.setOnAction(new EditButtonHandler(tabName, cartridge.getId(), cartridgeTableView,
                                                utilizedTable, summaryTable, cartridge, log));
                                        setGraphic(edit);
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    };


            Callback<TableColumn<Cartridge, String>, TableCell<Cartridge, String>> cellFactoryDelete
                    =
                    new Callback<TableColumn<Cartridge, String>, TableCell<Cartridge, String>>() {
                        @Override
                        public TableCell call(final TableColumn<Cartridge, String> param) {
                            final TableCell<Cartridge, String> cell = new TableCell<Cartridge, String>() {

                                InputStream inputDelete = getClass().getResourceAsStream("delete.png");
                                Image imageDelete = new Image(inputDelete);
                                ImageView imageViewDelete = new ImageView(imageDelete);
                                final Button delete = new Button("", imageViewDelete);


                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (empty) {
                                        setGraphic(null);
                                        setText(null);
                                    } else {
                                        delete.setMinSize(43, 25);
                                        Cartridge cartridge = getTableView().getItems().get(getIndex());
                                        TableView<Cartridge> cartridgeTableView = getTableView();
                                        delete.setOnAction(new RemoveButtonHandler(tabName, cartridge, cartridgeTableView,
                                                utilizedTable, summaryTable, log));
                                        setGraphic(delete);
                                        setText(null);
                                    }
                                }
                            };
                            return cell;
                        }
                    };

            table.setEditable(false);
            Callback<TableColumn<Cartridge, String>, TableCell<Cartridge, String>> showNoticeFactory =
                    new Callback<TableColumn<Cartridge, String>, TableCell<Cartridge, String>>() {
                        public TableCell call(TableColumn p) {
                            TableCell cell = new TableCell<Cartridge, String>() {
                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setText(empty ? null : getString());
                                    setGraphic(null);
                                }

                                private String getString() {
                                    return getItem() == null ? "" : getItem().toString();
                                }
                            };

                            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    textToShow(cell.getText());
                                }
                            });
                            return cell;
                        }
                    };

            noticeColumn.setCellFactory(showNoticeFactory);

            actionColEdit.setCellFactory(cellFactoryEdit);
            table.getColumns().add(actionColEdit);

            actionColDelete.setCellFactory(cellFactoryDelete);
            table.getColumns().add(actionColDelete);
            return table;
        } else if (className.equals(summary.getClass())) {
            ObservableList<Summary> cartridges = FXCollections.observableArrayList((ArrayList<Summary>) list);
            TableView<Summary> table = new TableView<Summary>(cartridges);
            table.setEditable(true);
            table.setPrefWidth(1200);
            table.setPrefHeight(465);

// столбец для вывода имени
            TableColumn<Summary, String> numberColumn = new TableColumn<>("ОПС");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Summary, String>("opsLocation"));
            table.getColumns().add(numberColumn);
            numberColumn.setMinWidth(100);

            TableColumn<Summary, Integer> locationColumn = new TableColumn<Summary, Integer>("Количество Картриджей");
            locationColumn.setCellValueFactory(new PropertyValueFactory<Summary, Integer>("count"));
            table.getColumns().add(locationColumn);
            locationColumn.setMinWidth(200);

            TableColumn<Summary, Integer> colorColumn = new TableColumn<Summary, Integer>("Col");
            colorColumn.setCellValueFactory(new PropertyValueFactory<Summary, Integer>("color"));
            table.getColumns().add(colorColumn);
            colorColumn.setMaxWidth(30);

            colorColumn.setCellFactory(column -> {
                return new TableCell<Summary, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item == null || empty) {
                            setText(null);
                            setStyle("");
                        } else {
                            Summary summary = getTableView().getItems().get(getIndex());
                            if (summary.getCount() <= 3) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: green");
                            } else if (summary.getCount() > 3 && summary.getCount() < 9) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: yellow");
                            } else if (summary.getCount() >= 9) {
                                setTextFill(Color.CHOCOLATE);
                                setStyle("-fx-background-color: red");
                            }
                        }
                    }
                };
            });

            return table;
        } else {
            ObservableList<Utilized> cartridges = FXCollections.observableArrayList((ArrayList<Utilized>) list);
            TableView<Utilized> table = new TableView<Utilized>(cartridges);
            table.setEditable(true);
            table.setPrefWidth(1200);
            table.setPrefHeight(465);

// столбец для вывода имени
            TableColumn<Utilized, String> numberColumn = new TableColumn<Utilized, String>("Номер");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("number"));
            table.getColumns().add(numberColumn);
            numberColumn.setMinWidth(100);

            TableColumn<Utilized, String> statusColumn = new TableColumn<Utilized, String>("Статус");
            statusColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("status"));
            table.getColumns().add(statusColumn);
            statusColumn.setMinWidth(100);

            TableColumn<Utilized, Date> dateColumn = new TableColumn<Utilized, Date>("Дата");
            dateColumn.setCellValueFactory(new PropertyValueFactory<Utilized, Date>("date"));
            table.getColumns().add(dateColumn);
            dateColumn.setMinWidth(100);

            TableColumn<Utilized, String> noticeColumn = new TableColumn<Utilized, String>("Примечание");
            noticeColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("notice"));
            table.getColumns().add(noticeColumn);
            noticeColumn.setMinWidth(897);

            table.setEditable(false);
            Callback<TableColumn<Utilized, String>, TableCell<Utilized, String>> showNoticeFactory =
                    new Callback<TableColumn<Utilized, String>, TableCell<Utilized, String>>() {
                        public TableCell call(TableColumn p) {
                            TableCell cell = new TableCell<Cartridge, String>() {
                                @Override
                                public void updateItem(String item, boolean empty) {
                                    super.updateItem(item, empty);
                                    setText(empty ? null : getString());
                                    setGraphic(null);
                                }

                                private String getString() {
                                    return getItem() == null ? "" : getItem().toString();
                                }
                            };

                            cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    textToShow(cell.getText());
                                }
                            });
                            return cell;
                        }
                    };

            noticeColumn.setCellFactory(showNoticeFactory);

            return table;
        }
    }

    public String textToShow(String str) {
        Pane pane = new Pane();
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);

        Scene sceneWithLabels1 = new Scene(pane, 600, 215);
        TextArea textArea = new TextArea();
        textArea.setMinWidth(610);
        textArea.setText(str);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        Button ok = new Button("Ok");
        ok.getStyleClass().add("ok");
        ok.setLayoutX(280);
        ok.setLayoutY(190);
        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                newWindow.close();
            }
        });
        pane.getChildren().addAll(textArea, ok);

        newWindow.setScene(sceneWithLabels1);
        newWindow.show();
        return str;
    }

}
