package util.handlers;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class RemoveButtonHandler implements EventHandler<ActionEvent> {
    ContentStore contentStore = ContentStore.getContentStore();
    private Cartridge cartridgeForRemove;
    private String tabName;
    private TableView<Cartridge> tabCartridge;
    private TableView<Utilized> tabUtilized;
    private TableView<Summary> tabSummary;
    TextArea log;

    public RemoveButtonHandler(String str, Cartridge cartridgeForRemove, TableView<Cartridge> tabCartridge,
                               TableView<Utilized> tabUtilized, TableView<Summary> tabSummary, TextArea log) {
        this.tabCartridge = tabCartridge;
        this.tabUtilized = tabUtilized;
        this.tabSummary = tabSummary;
        tabName = str;
        this.cartridgeForRemove = cartridgeForRemove;
        this.log = log;
    }

    @Override
    public void handle(ActionEvent event) {
        Pane pane = new Pane();
        Stage newWindow = new Stage();
        newWindow.setTitle("Удалить элемент");

        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);

        //Labels
        Label numberLabel = new Label("Сударь! Вы уверенны что хотите удалить этот афигенный элемент???");
        numberLabel.setLayoutY(5);
        numberLabel.setLayoutX(10);

        Button add = new Button("Аннигилировать!!!");
        add.setMinSize(50, 20);
        add.setLayoutY(30);
        add.setLayoutX(135);
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String cartridgeStatus = cartridgeForRemove.getStatus();
                Integer cartridgeLocation = cartridgeForRemove.getLocation();
                contentStore.getCartridgesMap().get("q_" + tabName).remove(cartridgeForRemove);

                if(tabName.equals("111")){
                    for (int i = 0; i < contentStore.getCartridgesMap().get("q_111").size(); i++) {
                        contentStore.getCartridgesMap().get("q_111").get(i).setIdTable(i+1);
                    }
                }

                tabCartridge.getItems().remove(cartridgeForRemove);
                tabCartridge.refresh();
                if (cartridgeStatus.equals("На отделении")) {
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

                int x = contentStore.getUtilizedArrayList().size();
                if (cartridgeStatus.equals("Списан")) {
                    for (int i = 0; i < x; i++) {
                        if (cartridgeForRemove.getId() == contentStore.getUtilizedArrayList().get(i).getId()) {
                            contentStore.getUtilizedArrayList().remove(i);
                            tabUtilized.getItems().remove(i);
                            break;
                        }
                    }
                }

                Date date = new Date();
                DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

                if (tabName.equals("111")){
                    log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeForRemove.getIdTable() + " аннигилирован на атомы  ");
                }else{
                    log.appendText("\n" + dateFormat.format(date) + " : Картридж " + cartridgeForRemove.getNumber() + " аннигилирован на атомы  ");
                }

                String str = log.getText();
                try {
                    contentStore.saveLog(str);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                contentStore.saveContent();
                newWindow.close();
            }
        });
        pane.getChildren().addAll(numberLabel, add);
        Scene sceneWithLabels1 = new Scene(pane, 390, 55);
        newWindow.setScene(sceneWithLabels1);
        newWindow.show();
    }
}
