package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Cartridge;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        VBox root = new VBox();

        List<String> list = new ArrayList();
        list.add("111");
        list.add("222");
        //TODO change
        primaryStage.setTitle("Cartridge Master 4000");
        Scene scene = new Scene(initialTabPane(list));
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private TabPane initialTabPane(List<String> list){
        ScrollPane scrollPaneLog = new ScrollPane(new Text("log...."));
        TabPane tabPane = new TabPane();

        Tab tableTab;
        ScrollPane scrollPaneTable;

        for (int i = 0; i < list.size() ; i++) {
            tableTab = new Tab(list.get(i));
            scrollPaneTable = new ScrollPane(initiateTable());
            tableTab.setContent(scrollPaneTable);
            tabPane.getTabs().add(tableTab);
        }
        return tabPane;
    }

    private TableView<Cartridge> initiateTable(){
        //TODO reading from file
        List<Cartridge> list = new ArrayList<>();
        list.add(new Cartridge());
        list.add(new Cartridge());
        ObservableList<Cartridge> cartridges = FXCollections.observableArrayList(list);
        TableView<Cartridge> table = new TableView<Cartridge>(cartridges);
        table.setPrefWidth(250);
        table.setPrefHeight(200);

// столбец для вывода имени
        TableColumn<Cartridge, String> numberColumn = new TableColumn<Cartridge, String>("Номер");
        numberColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("number"));
        table.getColumns().add(numberColumn);

        TableColumn<Cartridge, String> statusColumn = new TableColumn<Cartridge, String>("Статус");
        numberColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("status"));
        table.getColumns().add(statusColumn);
        //TODO finish method
        return table;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
