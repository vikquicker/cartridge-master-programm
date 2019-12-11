package util.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import util.ContentStore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;


public class PrintHandler implements EventHandler<ActionEvent> {
    ContentStore contentStore = ContentStore.getContentStore();
    private TableView<Cartridge> tabCartridge;
    private TableView<Utilized> tabUtilized;
    private TableView<Summary> tabSummary;
    private String tabName;
    TextArea log;

    public PrintHandler(TableView<Cartridge> tabCartridge,
                        TableView<Utilized> tabUtilized,
                        TableView<Summary> tabSummary,
                        String tabName,
                        TextArea log) {
        this.tabCartridge = tabCartridge;
        this.tabUtilized = tabUtilized;
        this.tabSummary = tabSummary;
        this.tabName = tabName;
        this.log = log;
    }

    @Override
    public void handle(ActionEvent event) {
        //Export to XML
        if (tabName.equals("111")){
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < tabCartridge.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tabCartridge.getColumns().get(j).getText());
            }

            for (int i = 0; i < tabCartridge.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabCartridge.getColumns().size(); j++) {
                    if (tabCartridge.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabCartridge.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            String root = Paths.get("").toAbsolutePath().toString();
            String saveContent = root + "workbook.xls";
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(saveContent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(tabName.equals("115")){
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < tabCartridge.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tabCartridge.getColumns().get(j).getText());
            }

            for (int i = 0; i < tabCartridge.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabCartridge.getColumns().size(); j++) {
                    if (tabCartridge.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabCartridge.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            String root = Paths.get("").toAbsolutePath().toString();
            String saveContent = root + "workbook.xls";
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(saveContent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(tabName.equals("226")){
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < tabCartridge.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tabCartridge.getColumns().get(j).getText());
            }

            for (int i = 0; i < tabCartridge.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabCartridge.getColumns().size(); j++) {
                    if (tabCartridge.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabCartridge.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            String root = Paths.get("").toAbsolutePath().toString();
            String saveContent = root + "workbook.xls";
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(saveContent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(tabName.equals("Сводная")){
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < tabSummary.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tabSummary.getColumns().get(j).getText());
            }

            for (int i = 0; i < tabSummary.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabSummary.getColumns().size(); j++) {
                    if (tabSummary.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabSummary.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            String root = Paths.get("").toAbsolutePath().toString();
            String saveContent = root + "workbook.xls";
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(saveContent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(tabName.equals("Списанные")){
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("sample");
            Row row = spreadsheet.createRow(0);

            for (int j = 0; j < tabUtilized.getColumns().size(); j++) {
                row.createCell(j).setCellValue(tabUtilized.getColumns().get(j).getText());
            }

            for (int i = 0; i < tabUtilized.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < tabUtilized.getColumns().size(); j++) {
                    if (tabUtilized.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(tabUtilized.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                    }
                }
            }
            String root = Paths.get("").toAbsolutePath().toString();
            String saveContent = root + "workbook.xls";
            FileOutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(saveContent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                workbook.write(fileOut);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Pane pane = new Pane();
        Stage newWindow = new Stage();
        newWindow.initModality(Modality.APPLICATION_MODAL);
        newWindow.setResizable(false);

        Label fileLabel = new Label("Файл сохранён!");
        fileLabel.setLayoutY(5);
        fileLabel.setLayoutX(20);

        Button ok = new Button("OK");
        ok.setMinSize(50, 20);
        ok.setLayoutY(30);
        ok.setLayoutX(40);

        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, new Locale("ru"));

        if (tabName.equals("111")){
            log.appendText("\n" + dateFormat.format(date) + " Файл сохранён в exel  ");
        }

        String str = log.getText();
        try {
            contentStore.saveLog(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ok.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                contentStore.saveContent();
                newWindow.close();
            }
        });

        pane.getChildren().addAll(fileLabel,ok);
        Scene scene = new Scene(pane, 100, 55);
        newWindow.setScene(scene);
        newWindow.show();
    }
}
