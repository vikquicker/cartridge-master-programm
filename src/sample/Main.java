package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main extends Application {
    ContentStore contentStore = ContentStore.getContentStore();

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox root = new VBox();

        List<String> list = contentStore.readTabs();
        Text logText = new Text("log....");
        ScrollPane scrollPaneLog = new ScrollPane();
        root.getChildren().addAll(initialTabPane(list), scrollPaneLog);
        primaryStage.setTitle("Cartridge Master 4000");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private TabPane initialTabPane(List<String> listTabs) throws IOException {
        TabPane tabPane = new TabPane();
        Tab tableTab;
        ScrollPane scrollPaneTable;

        for (int i = 0; i < listTabs.size(); i++) {
            VBox vBoxForButtonAndScroll = new VBox();
            Button addItem = new Button("Добавить");
            addItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    StackPane secondaryLayout = new StackPane();
                    Scene newScene = new Scene(secondaryLayout,600,400);
                    Stage newWindow = new Stage();
                    newWindow.setTitle("Добавить элемент");
                    newWindow.setScene(newScene);

                    newWindow.initModality(Modality.APPLICATION_MODAL);
                    newWindow.setResizable(false);

                    newWindow.show();
                }
            });
            vBoxForButtonAndScroll.getChildren().add(addItem);
            tableTab = new Tab(listTabs.get(i));
            if (listTabs.get(i).startsWith("q_")) {
                ArrayList<Cartridge> arrayList = new ArrayList<>();
                ArrayList<Cartridge> listFromMap = contentStore.getCartridgesMap().get(listTabs.get(i));
                scrollPaneTable = new ScrollPane();
                HBox hBox = new HBox();
                if (listFromMap != null) {
                    arrayList.addAll(listFromMap);
                    hBox.getChildren().addAll(initiateTable(arrayList, arrayList.getClass()), createButtons(listFromMap.size()));
                    scrollPaneTable.setContent(hBox);
                }
                vBoxForButtonAndScroll.getChildren().add(scrollPaneTable);
                tableTab.setContent(vBoxForButtonAndScroll);
                tabPane.getTabs().add(tableTab);
            } else if (listTabs.get(i).equals("Сводная")) {
                ArrayList<Summary> summaryArrayList = new ArrayList<>();
                ArrayList<Summary> secondListFromMap = contentStore.getSummaryArrayList();
                scrollPaneTable = new ScrollPane();
                HBox hBox = new HBox();
                if (secondListFromMap != null) {
                    summaryArrayList.addAll(secondListFromMap);
                    hBox.getChildren().addAll(initiateTable(summaryArrayList, summaryArrayList.getClass()), createButtons(secondListFromMap.size()));
                    scrollPaneTable.setContent(hBox);
                }
                vBoxForButtonAndScroll.getChildren().add(scrollPaneTable);
                tableTab.setContent(vBoxForButtonAndScroll);
                tabPane.getTabs().add(tableTab);
            } else if (listTabs.get(i).equals("Списанные")) {
                ArrayList<Utilized> utilizedArrayList = new ArrayList<>();
                ArrayList<Utilized> thirdListFromMap = contentStore.getUtilizedArrayList();
                scrollPaneTable = new ScrollPane();
                HBox hBox = new HBox();
                if (thirdListFromMap != null) {
                    utilizedArrayList.addAll(thirdListFromMap);
                    hBox.getChildren().addAll(initiateTable(utilizedArrayList, utilizedArrayList.getClass()), createButtons(thirdListFromMap.size()));
                    scrollPaneTable.setContent(hBox);
                }
                vBoxForButtonAndScroll.getChildren().add(scrollPaneTable);
                tableTab.setContent(vBoxForButtonAndScroll);
                tabPane.getTabs().add(tableTab);
            }

        }
        return tabPane;
    }

    private TableView initiateTable(Object list, Class className) throws IOException {
        if (className.getName().equals(new ArrayList<Cartridge>().getClass().getName())) {
            ObservableList<Cartridge> cartridges = FXCollections.observableArrayList((ArrayList<Cartridge>) list);
            TableView<Cartridge> table = new TableView<Cartridge>(cartridges);
            table.setPrefWidth(1200);
            table.setPrefHeight(700);

// столбец для вывода имени
            TableColumn<Cartridge, String> numberColumn = new TableColumn<Cartridge, String>("Номер");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("number"));
            table.getColumns().add(numberColumn);

            TableColumn<Cartridge, String> statusColumn = new TableColumn<Cartridge, String>("Статус");
            statusColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("status"));
            table.getColumns().add(statusColumn);

            TableColumn<Cartridge, Date> dateColumn = new TableColumn<Cartridge, Date>("Дата");
            dateColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, Date>("date"));
            table.getColumns().add(dateColumn);

            TableColumn<Cartridge, String> locationColumn = new TableColumn<Cartridge, String>("Расположение");
            locationColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("status"));
            table.getColumns().add(locationColumn);

            TableColumn<Cartridge, String> noticeColumn = new TableColumn<Cartridge, String>("Примечание");
            noticeColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("status"));
            table.getColumns().add(noticeColumn);

            return table;
        } else if (className.getName().equals(new ArrayList<Summary>().getClass().getName())) {
            ObservableList<Summary> cartridges = FXCollections.observableArrayList((ArrayList<Summary>) list);
            TableView<Summary> table = new TableView<Summary>(cartridges);
            table.setPrefWidth(1200);
            table.setPrefHeight(700);

// столбец для вывода имени
            TableColumn<Summary, String> numberColumn = new TableColumn<Summary, String>("Номер");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Summary, String>("number"));
            table.getColumns().add(numberColumn);

            TableColumn<Summary, String> statusColumn = new TableColumn<Summary, String>("Статус");
            statusColumn.setCellValueFactory(new PropertyValueFactory<Summary, String>("status"));
            table.getColumns().add(statusColumn);

            TableColumn<Summary, Date> dateColumn = new TableColumn<Summary, Date>("Дата");
            dateColumn.setCellValueFactory(new PropertyValueFactory<Summary, Date>("date"));
            table.getColumns().add(dateColumn);

            TableColumn<Summary, String> locationColumn = new TableColumn<Summary, String>("Расположение");
            locationColumn.setCellValueFactory(new PropertyValueFactory<Summary, String>("status"));
            table.getColumns().add(locationColumn);

            TableColumn<Summary, String> noticeColumn = new TableColumn<Summary, String>("Примечание");
            noticeColumn.setCellValueFactory(new PropertyValueFactory<Summary, String>("status"));
            table.getColumns().add(noticeColumn);

            return table;
        } else {
            ObservableList<Utilized> cartridges = FXCollections.observableArrayList((ArrayList<Utilized>) list);
            TableView<Utilized> table = new TableView<Utilized>(cartridges);
            table.setPrefWidth(1200);
            table.setPrefHeight(700);

// столбец для вывода имени
            TableColumn<Utilized, String> numberColumn = new TableColumn<Utilized, String>("Номер");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("number"));
            table.getColumns().add(numberColumn);

            TableColumn<Utilized, String> statusColumn = new TableColumn<Utilized, String>("Статус");
            statusColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("status"));
            table.getColumns().add(statusColumn);

            TableColumn<Utilized, Date> dateColumn = new TableColumn<Utilized, Date>("Дата");
            dateColumn.setCellValueFactory(new PropertyValueFactory<Utilized, Date>("date"));
            table.getColumns().add(dateColumn);

            TableColumn<Utilized, String> locationColumn = new TableColumn<Utilized, String>("Расположение");
            locationColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("status"));
            table.getColumns().add(locationColumn);

            TableColumn<Utilized, String> noticeColumn = new TableColumn<Utilized, String>("Примечание");
            noticeColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("status"));
            table.getColumns().add(noticeColumn);

            return table;
        }
    }

    private Node createButtons(int numberOfRows) {
        VBox vBox = new VBox();
        HBox hBox;
        Button buttonEdit;
        Button buttonDelete;
        InputStream inputEdit = getClass().getResourceAsStream("/resources/edit.svg");
        InputStream inputDelete = getClass().getResourceAsStream("/resources/delete.svg");
        Image imageEdit;
        Image imageDelete;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
        for (int i = 0; i < numberOfRows; i++) {
            hBox = new HBox();
            imageEdit = new Image(inputEdit);
            imageDelete = new Image(inputDelete);
            imageViewEdit = new ImageView(imageEdit);
            imageViewDelete = new ImageView(imageDelete);
            buttonEdit = new Button("", imageViewEdit);
            buttonDelete = new Button("", imageViewDelete);
            hBox.getChildren().addAll(buttonEdit, buttonDelete);
            vBox.getChildren().addAll(hBox);
        }
        return vBox;
    }

    public void stop() throws IOException {
        contentStore.writeTabs();
        contentStore.saveAll();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
