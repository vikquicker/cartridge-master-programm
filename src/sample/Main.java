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
        primaryStage.setTitle("Cartridge Master 4000");
        Scene scene = new Scene(initialTabPane(list));
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private TabPane initialTabPane(List<String> listTabs) throws IOException {
        ScrollPane scrollPaneLog = new ScrollPane(new Text("log...."));
        TabPane tabPane = new TabPane();

        Tab tableTab;
        ScrollPane scrollPaneTable;

        for (int i = 0; i < listTabs.size(); i++) {
            tableTab = new Tab(listTabs.get(i));
            if (listTabs.get(i).startsWith("q_")){
                ArrayList<Cartridge> arrayList = contentStore.getCartridgesMap().get(listTabs.get(i));
                scrollPaneTable = new ScrollPane(initiateTable(arrayList));
                tableTab.setContent(scrollPaneTable);
                tabPane.getTabs().add(tableTab);
            }
        }
        return tabPane;
    }

    private TableView<Cartridge> initiateTable(ArrayList<Cartridge> list) throws IOException {
        ObservableList<Cartridge> cartridges = FXCollections.observableArrayList(list);
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

        TableColumn<Cartridge, String> historyColumn = new TableColumn<Cartridge, String>("История");
        historyColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("history"));
        table.getColumns().add(historyColumn);
        return table;
    }

    public static void main(String[] args) throws IOException{
        launch(args);
    }
}
