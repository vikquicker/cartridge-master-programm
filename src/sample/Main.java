package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;

import java.io.IOException;
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
            tableTab = new Tab(listTabs.get(i));
            if (listTabs.get(i).startsWith("q_")) {
                ArrayList<Cartridge> arrayList = new ArrayList<>();
                ArrayList<Cartridge> listFromMap = contentStore.getCartridgesMap().get(listTabs.get(i));
                if (listFromMap != null) {
                    arrayList.addAll(listFromMap);
                }
                scrollPaneTable = new ScrollPane(initiateTable(arrayList, arrayList.getClass()));
                tableTab.setContent(scrollPaneTable);
                tabPane.getTabs().add(tableTab);
            } else if (listTabs.get(i).equals("Сводная")) {
                ArrayList<Summary> summaryArrayList = new ArrayList<>();
                ArrayList<Summary> secondListFromMap = contentStore.getSummaryArrayList();
                if (secondListFromMap != null) {
                    summaryArrayList.addAll(secondListFromMap);
                }
                scrollPaneTable = new ScrollPane(initiateTable(summaryArrayList, summaryArrayList.getClass()));
                tableTab.setContent(scrollPaneTable);
                tabPane.getTabs().add(tableTab);
            } else if (listTabs.get(i).equals("Списанные")) {
                ArrayList<Utilized> utilizedArrayList = new ArrayList<>();
                ArrayList<Utilized> thirdListFromMap = contentStore.getUtilizedArrayList();
                if (thirdListFromMap != null) {
                    utilizedArrayList.addAll(thirdListFromMap);
                }
                scrollPaneTable = new ScrollPane(initiateTable(utilizedArrayList, utilizedArrayList.getClass()));
                tableTab.setContent(scrollPaneTable);
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

//            TableColumn<Cartridge, String> historyColumn = new TableColumn<Cartridge, String>("История");
//            historyColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("history"));
//            table.getColumns().add(historyColumn);
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

//            TableColumn<Summary, String> historyColumn = new TableColumn<Summary, String>("История");
//            historyColumn.setCellValueFactory(new PropertyValueFactory<Summary, String>("history"));
//            table.getColumns().add(historyColumn);
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

//            TableColumn<Utilized, String> historyColumn = new TableColumn<Utilized, String>("История");
//            historyColumn.setCellValueFactory(new PropertyValueFactory<Utilized, String>("history"));
//            table.getColumns().add(historyColumn);
            return table;
        }
    }

    public void stop() throws IOException {
        contentStore.writeTabs();
        contentStore.saveAll();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
