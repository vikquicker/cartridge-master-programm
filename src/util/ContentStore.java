package util;

import models.Cartridge;
import models.Summary;
import models.Utilized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class ContentStore implements Serializable {
    private final static String START_FILE = "start.res";
    private static ContentStore contentStore;

    Map<String, ArrayList<Cartridge>> cartridgesMap;
    private ArrayList<Summary> summaryArrayList;
    private ArrayList<Utilized> utilizedArrayList;
    ArrayList<String> tabList;
    private ArrayList<String> locationList = new ArrayList<>();

    public ArrayList<String> getLocationList() {
        return locationList;
    }

    public static ContentStore getContentStore() {
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
        try {
            readLocationList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cartridgesMap = new HashMap<>();
        summaryArrayList = new ArrayList<>();
        utilizedArrayList = new ArrayList<>();
        tabList = new ArrayList<>();
        tabList.add("q_111");
        tabList.add("q_115");
        tabList.add("q_226");
        tabList.add("Сводная");
        tabList.add("Списанные");
    }

    public void readLocationList() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\study\\tabs\\locationList.txt"), "windows-1251"));
        String str = reader.readLine();
        if (reader != null) {
            while (str != null) {
                getLocationList().add(str);
                str = reader.readLine();
            }
        }
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

