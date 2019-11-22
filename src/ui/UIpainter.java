package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;
import util.handlers.AddButtonHandler;
import util.handlers.EditButtonHandler;
import util.handlers.RemoveButtonHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UIpainter {
    ContentStore contentStore = ContentStore.getContentStore();
    VBox vBoxForEditAndDeleteButtons;

    private static UIpainter uIpainter;

    public static UIpainter getUIpainter() {
        if (uIpainter == null) {
            uIpainter = new UIpainter();
        }
        return uIpainter;
    }

    private UIpainter(){
    }

    public void createUI(Stage primaryStage){
        VBox root = new VBox();
        List<String> list = contentStore.getTabList();
        ScrollPane scrollPaneLog = new ScrollPane();
        Text logText = new Text("log....");
        root.getChildren().addAll(initialTabPane(list), scrollPaneLog);
        primaryStage.setTitle("Cartridge Master 4000");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public TabPane initialTabPane(List<String> listTabs) {
        TabPane tabPane = new TabPane();
        Tab tableTab;
        Tab tableTabUtilized = new Tab();
        Tab tableTabSummary = new Tab();
        ScrollPane scrollPaneTable;
        TableView<Cartridge> tableViewCartridge = new TableView<>();
        TableView<Utilized> tableViewUtilized = new TableView<>();
        TableView<Summary> tableViewSummary = new TableView<>();
        VBox vBoxForEditAndDeleteButtonsUtilize = new VBox();

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
                    hBox.getChildren().addAll(tableViewCartridge = initiateTable(arrayList, cartridge.getClass()),
                            createButtons(listFromMap.size(), presentedTabName, tableViewCartridge, tableViewUtilized, tableViewSummary));
                } else {
                    hBox.getChildren().addAll(tableViewCartridge = initiateTable(arrayList, cartridge.getClass()),
                            createButtons(0, presentedTabName, tableViewCartridge, tableViewUtilized, tableViewSummary));
                }
                addItem.setOnAction(new AddButtonHandler(presentedTabName, tableViewCartridge, tableViewUtilized,
                        tableViewSummary, vBoxForEditAndDeleteButtons, vBoxForEditAndDeleteButtonsUtilize));

                scrollPaneTable.setContent(hBox);
                vBoxForButtonAndScroll.getChildren().add(scrollPaneTable);
                tableTab.setContent(vBoxForButtonAndScroll);
                tabPane.getTabs().add(tableTab);
                tableTab.setClosable(false);
            } else if (listTabs.get(i).equals("Сводная")) {
                VBox vBoxForButtonAndScroll = new VBox();
                String presentedTabNameSummary = listTabs.get(i);
                tableTab = new Tab(presentedTabNameSummary);

                Button addItem = new Button("Добавить");

                Summary summary = new Summary();
                ArrayList<Summary> summaryArrayList = new ArrayList<>();
                ArrayList<Summary> secondListFromMap = contentStore.getSummaryArrayList();
                scrollPaneTable = new ScrollPane();
                HBox hBox = new HBox();
                if (secondListFromMap != null) {
                    summaryArrayList.addAll(secondListFromMap);
                    hBox.getChildren().addAll(tableViewSummary = initiateTable(summaryArrayList, summary.getClass()));
                } else {
                    hBox.getChildren().addAll(tableViewSummary = initiateTable(summaryArrayList, summary.getClass()));
                }
                addItem.setOnAction(new AddButtonHandler(presentedTabNameSummary, tableViewCartridge, tableViewUtilized,
                        tableViewSummary, vBoxForEditAndDeleteButtons, vBoxForEditAndDeleteButtonsUtilize));

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
                tableViewCartridge = initiateTable(utilizedArrayList, utilizedArrayList.getClass());
                if (thirdListFromMap != null) {
                    utilizedArrayList.addAll(thirdListFromMap);
                    hBox.getChildren().addAll(tableViewUtilized = initiateTable(utilizedArrayList, utilizedArrayList.getClass()),
                            new Pane());
                } else {
                    hBox.getChildren().addAll(tableViewUtilized = initiateTable(utilizedArrayList, utilizedArrayList.getClass()),
                            new Pane());
                }
                vBoxForEditAndDeleteButtonsUtilize = vBoxForEditAndDeleteButtons;

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


    private TableView initiateTable(Object list, Class className) {
        Cartridge cartridge = new Cartridge();
        Summary summary = new Summary();
        if (className.equals(cartridge.getClass())) {
            ObservableList<Cartridge> cartridges = FXCollections.observableArrayList((ArrayList<Cartridge>) list);
            TableView<Cartridge> table = new TableView<Cartridge>(cartridges);
            table.setEditable(true);
            table.setPrefWidth(1200);
            table.setPrefHeight(700);

// столбец для вывода имени
            TableColumn<Cartridge, String> numberColumn = new TableColumn<Cartridge, String>("Номер");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Cartridge, String>("number"));
            table.getColumns().add(numberColumn);
            numberColumn.setMinWidth(100);

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
            noticeColumn.setMinWidth(799);

            return table;
        } else if (className.equals(summary.getClass())) {
            ObservableList<Summary> cartridges = FXCollections.observableArrayList((ArrayList<Summary>) list);
            TableView<Summary> table = new TableView<Summary>(cartridges);
            table.setEditable(true);
            table.setPrefWidth(1200);
            table.setPrefHeight(700);

// столбец для вывода имени
            TableColumn<Summary, String> numberColumn = new TableColumn<>("ОПС");
            numberColumn.setCellValueFactory(new PropertyValueFactory<Summary, String>("opsLocation"));
            table.getColumns().add(numberColumn);
            numberColumn.setMinWidth(100);

            TableColumn<Summary, Integer> locationColumn = new TableColumn<Summary, Integer>("Количество Картриджей");
            locationColumn.setCellValueFactory(new PropertyValueFactory<Summary, Integer>("count"));
            table.getColumns().add(locationColumn);
            locationColumn.setMinWidth(200);

            return table;
        } else {
            ObservableList<Utilized> cartridges = FXCollections.observableArrayList((ArrayList<Utilized>) list);
            TableView<Utilized> table = new TableView<Utilized>(cartridges);
            table.setEditable(true);
            table.setPrefWidth(1200);
            table.setPrefHeight(700);

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
            noticeColumn.setMinWidth(899);

            return table;
        }
    }

    public Node createButtons(int numberOfRows, String str, TableView<Cartridge> tabCartridge,
                              TableView<Utilized> tabUtilized,
                              TableView<Summary> tabSummary) {
        Cartridge cartridge;
        Utilized utilized;
        vBoxForEditAndDeleteButtons = new VBox();
        HBox hBox;
        Button buttonEdit;
        Button buttonDelete;
        InputStream inputEdit = getClass().getResourceAsStream("edit.png");
        InputStream inputDelete = getClass().getResourceAsStream("delete.png");
        Image imageEdit;
        Image imageDelete;
        ImageView imageViewEdit;
        ImageView imageViewDelete;

        InputStream inputBlock = getClass().getResourceAsStream("");
        Image imageBlock = new Image(inputBlock);
        ImageView imageViewBlock = new ImageView(imageBlock);
        hBox = new HBox();
        Button buttonBlock = new Button("", imageViewBlock);
        buttonBlock.setMinSize(58, 10);
        hBox.getChildren().addAll(buttonBlock);
        vBoxForEditAndDeleteButtons.getChildren().addAll(hBox);
        for (int i = 0; i < numberOfRows; i++) {
            cartridge = contentStore.getCartridgesMap().get("q_" + str).get(i);
            utilized = new Utilized();
            utilized.setId(cartridge.getId());
            hBox = new HBox();

            imageEdit = new Image(inputEdit);
            imageDelete = new Image(inputDelete);
            imageViewEdit = new ImageView(imageEdit);
            imageViewDelete = new ImageView(imageDelete);
            buttonEdit = new Button("", imageViewEdit);
            buttonDelete = new Button("", imageViewDelete);
            hBox.getChildren().addAll(buttonEdit, buttonDelete);
            vBoxForEditAndDeleteButtons.getChildren().addAll(hBox);

            buttonEdit.setOnAction(new EditButtonHandler(i, str, tabCartridge, tabUtilized, tabSummary,utilized.getId()));
            buttonDelete.setOnAction(new RemoveButtonHandler(i, str, tabCartridge, tabUtilized, tabSummary,
                    vBoxForEditAndDeleteButtons, cartridge));
        }
        return vBoxForEditAndDeleteButtons;
    }

}
