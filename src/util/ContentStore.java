package util;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Cartridge;
import models.Summary;
import models.Utilized;
import util.handlers.RemoveButtonHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContentStore implements Serializable {
    private final static String START_FILE = "start.res";
    private static ContentStore contentStore;

    Map<String, ArrayList<Cartridge>> cartridgesMap;
    private ArrayList<Summary> summaryArrayList;
    private ArrayList<Utilized> utilizedArrayList;
    ArrayList<String> tabList;
    private Set<String> locationList;

    public Set<String> getLocationList() {
        return locationList;
    }

    public static ContentStore getContentStore() {
        if (contentStore != null) {
            return contentStore;
        }
        contentStore = (ContentStore) readContent(START_FILE);
        if (contentStore == null) {
            contentStore = new ContentStore();
        }
        return contentStore;
    }

    public Map<String, ArrayList<Cartridge>> getCartridgesMap() {
        return cartridgesMap;
    }

    public ArrayList<Summary> getSummaryArrayList() {
        return summaryArrayList;
    }

    public ArrayList<Utilized> getUtilizedArrayList() {
        return utilizedArrayList;
    }

    public ArrayList<String> getTabList() {
        return tabList;
    }

    public void setTabList(ArrayList<String> tabList) {
        this.tabList = tabList;
    }

    public ContentStore() {
        cartridgesMap = new HashMap<>();
        summaryArrayList = new ArrayList<>();
        utilizedArrayList = new ArrayList<>();
        tabList = new ArrayList<>();
        locationList = new HashSet<String>();
        this.getCartridgesMap().put("q_111", new ArrayList<Cartridge>());
        this.getCartridgesMap().put("q_115", new ArrayList<Cartridge>());
        this.getCartridgesMap().put("q_226", new ArrayList<Cartridge>());
        tabList.add("Списанные");
        tabList.add("Сводная");
        tabList.add("q_111");
        tabList.add("q_115");
        tabList.add("q_226");
        locationList.add("356542");
        locationList.add("356532");
    }

    public Node createButtons(int numberOfRows, String str, TableView<Cartridge> tabCartridge,
                              TableView<Utilized> tabUtilized,
                              TableView<Summary> tabSummary) {
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

        imageDelete = new Image(inputDelete);
        imageViewDelete = new ImageView(imageDelete);
        hBox = new HBox();
        buttonDelete = new Button("", imageViewDelete);
        buttonDelete.setMinSize(33,10);
        hBox.getChildren().addAll(buttonDelete);
        vBox.getChildren().addAll(hBox);
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

            buttonDelete.setOnAction(new RemoveButtonHandler(i, str,tabCartridge,tabUtilized,tabSummary));
        }
        return vBox;
    }

    public HashMap<String, Integer> summuryCount() {
        ArrayList<String> arrayListCount = new ArrayList<>();
        HashMap<String, Integer> numberOfCartridges = new HashMap<>();
        for (int i = 0; i < contentStore.getCartridgesMap().get("q_111").size(); i++) {
            String location111 = contentStore.getCartridgesMap().get("q_111").get(i).getLocation();
            String status111 = contentStore.getCartridgesMap().get("q_111").get(i).getStatus();
            if (status111.equals("На отделении")) {
                arrayListCount.add(location111);
            }
        }
        for (int i = 0; i < contentStore.getCartridgesMap().get("q_115").size(); i++) {
            String location115 = contentStore.getCartridgesMap().get("q_115").get(i).getLocation();
            String status115 = contentStore.getCartridgesMap().get("q_115").get(i).getStatus();
            if (status115.equals("На отделении")) {
                arrayListCount.add(location115);
            }
        }
        for (int i = 0; i < contentStore.getCartridgesMap().get("q_226").size(); i++) {
            String location226 = contentStore.getCartridgesMap().get("q_226").get(i).getLocation();
            String status226 = contentStore.getCartridgesMap().get("q_226").get(i).getStatus();
            if (status226.equals("На отделении")) {
                arrayListCount.add(location226);
            }
        }

        for (String str : arrayListCount) {
            numberOfCartridges.put(str, 0);
        }

        for (Map.Entry<String, Integer> map : numberOfCartridges.entrySet()) {
            for (int i = 0; i < arrayListCount.size(); i++) {
                if (map.getKey().equals(arrayListCount.get(i))) {
                    int x = map.getValue();
                    x++;
                    map.setValue(x);
                }
            }
        }
        return numberOfCartridges;
    }

    public void saveContent() throws IOException {
        FileOutputStream writeInFile = new FileOutputStream(START_FILE);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(writeInFile);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    public static Object readContent(String listName) {
        if (!Files.exists(Paths.get(listName))) {
            return null;
        }
        try {
            FileInputStream readFileTabs = new FileInputStream(listName);
            ObjectInputStream inputStream = new ObjectInputStream(readFileTabs);
            Object list = inputStream.readObject();
            inputStream.close();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}

