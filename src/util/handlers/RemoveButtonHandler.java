package util.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import models.Cartridge;
import models.Summary;
import models.Utilized;

public class RemoveButtonHandler implements EventHandler<ActionEvent> {
    private String tabName;
    private TableView<Cartridge> tabCartridge;
    private TableView<Utilized> tabUtilized;
    private TableView<Summary> tabSummary;

    public RemoveButtonHandler(String str, TableView<Cartridge> tabCartridge,
                               TableView<Utilized> tabUtilized,
                               TableView<Summary> tabSummary) {
        this.tabCartridge = tabCartridge;
        this.tabUtilized = tabUtilized;
        this.tabSummary = tabSummary;
        tabName = str;
    }

    @Override
    public void handle(ActionEvent event) {

    }
}
