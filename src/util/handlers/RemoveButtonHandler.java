package util.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.ContentStore;
import java.util.HashMap;
import java.util.Map;


public class RemoveButtonHandler implements EventHandler<ActionEvent> {
    ContentStore contentStore = ContentStore.getContentStore();
    int numberOfButton;
    private Cartridge cartridgeForRemove;
    private String tabNameFromCreateButtonns;
    private TableView<Cartridge> tabCartridge;
    private TableView<Utilized> tabUtilized;
    private TableView<Summary> tabSummary;
    private VBox vBoxForEditAndDelete;

    public RemoveButtonHandler(int numberOfButton, String str,
                               TableView<Cartridge> tabCartridge,
                               TableView<Utilized> tabUtilized,
                               TableView<Summary> tabSummary,
                               VBox vBoxForEditAndDelete,Cartridge cartridgeForRemove) {
        this.tabCartridge = tabCartridge;
        this.tabUtilized = tabUtilized;
        this.numberOfButton = numberOfButton;
        this.tabSummary = tabSummary;
        this.vBoxForEditAndDelete = vBoxForEditAndDelete;
        tabNameFromCreateButtonns = str;
        this.cartridgeForRemove = cartridgeForRemove;
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
                if (tabNameFromCreateButtonns.equals("111")) {
                    String cartridgeStatus = cartridgeForRemove.getStatus();
                    String cartridgeLocation = cartridgeForRemove.getLocation();
                    contentStore.getCartridgesMap().get("q_" + tabNameFromCreateButtonns).remove(cartridgeForRemove);
                    tabCartridge.getItems().remove(cartridgeForRemove);
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
                } else if (tabNameFromCreateButtonns.equals("115")) {
                    String cartridgeStatus = cartridgeForRemove.getStatus();
                    String cartridgeLocation = cartridgeForRemove.getLocation();
                    contentStore.getCartridgesMap().get("q_" + tabNameFromCreateButtonns).remove(cartridgeForRemove);
                    tabCartridge.getItems().remove(cartridgeForRemove);
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
                } else if (tabNameFromCreateButtonns.equals("226")) {
                    String cartridgeStatus = cartridgeForRemove.getStatus();
                    String cartridgeLocation = cartridgeForRemove.getLocation();
                    contentStore.getCartridgesMap().get("q_" + tabNameFromCreateButtonns).remove(cartridgeForRemove);
                    tabCartridge.getItems().remove(cartridgeForRemove);
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
                }

                if (tabNameFromCreateButtonns.equals("111")) {
                    int rowNumber = contentStore.getCartridgesMap().get("q_" + tabNameFromCreateButtonns).size() - 1;
                    vBoxForEditAndDelete.getChildren().remove(rowNumber);
                } else if (tabNameFromCreateButtonns.equals("115")) {
                    int rowNumber = contentStore.getCartridgesMap().get("q_" + tabNameFromCreateButtonns).size() - 1;
                    vBoxForEditAndDelete.getChildren().remove(rowNumber);
                } else if (tabNameFromCreateButtonns.equals("226")) {
                    int rowNumber = contentStore.getCartridgesMap().get("q_" + tabNameFromCreateButtonns).size() - 1;
                    vBoxForEditAndDelete.getChildren().remove(rowNumber);
                } else {
                    int rowNumber = contentStore.getUtilizedArrayList().size() - 1;
                    vBoxForEditAndDelete.getChildren().remove(rowNumber);
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
