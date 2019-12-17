package util;

import models.Cartridge;
import models.Summary;
import models.Utilized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContentStore implements Serializable {
    private static final long serialVersionUID = 1L;
    String root = Paths.get("").toAbsolutePath().toString();
    String saveContent = root + "backUp.res";
    String saveLog= root + "log.txt";

    static String START_FILE = System.getProperty("user.dir");
    private static ContentStore contentStore;

    Map<String, ArrayList<Cartridge>> cartridgesMap;
    private ArrayList<Summary> summaryArrayList;
    private ArrayList<Utilized> utilizedArrayList;
    ArrayList<String> tabList;
    private SortedSet<Integer> locationList111;
    private SortedSet<String> locationList115;
    private SortedSet<String> locationList226;
    private SortedSet<String>  locationListString111;
    public SortedSet<String> getLocationListString111() {
        return locationListString111;
    }
    public SortedSet<Integer> getLocationList111() {
        return locationList111;
    }
    public SortedSet<String> getLocationList115() {
        return locationList115;
    }
    public SortedSet<String> getLocationList226() {
        return locationList226;
    }




    public static ContentStore getContentStore() {
        if (contentStore != null) {
            return contentStore;
        }
        contentStore = (ContentStore) readContent(START_FILE + "backUp.res");
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

    private ContentStore() {
        cartridgesMap = new HashMap<>();
        summaryArrayList = new ArrayList<>();
        utilizedArrayList = new ArrayList<>();
        tabList = new ArrayList<>();
        locationListString111 =  new TreeSet<>();
        locationList111 = new TreeSet<>();
        locationList115 = new TreeSet<>();
        locationList226 = new TreeSet<>();
        this.getCartridgesMap().put("q_111", new ArrayList<Cartridge>());
        this.getCartridgesMap().put("q_115", new ArrayList<Cartridge>());
        this.getCartridgesMap().put("q_226", new ArrayList<Cartridge>());
        tabList.add("Путь к базе");
        tabList.add("Списанные");
        tabList.add("Сводная");
        tabList.add("q_111");
        tabList.add("q_115");
        tabList.add("q_226");
    }


    public HashMap<String, Integer> summuryCount() {
        ArrayList<String> arrayListCount = new ArrayList<>();
        HashMap<String, Integer> numberOfCartridges = new HashMap<>();
        for (int i = 0; i < contentStore.getCartridgesMap().get("q_111").size(); i++) {
            Integer location111 = contentStore.getCartridgesMap().get("q_111").get(i).getLocation();
            String status111 = contentStore.getCartridgesMap().get("q_111").get(i).getStatus();
            if (status111.equals("На отделении")) {
                arrayListCount.add(String.valueOf(location111));
            }
        }
        for (int i = 0; i < contentStore.getCartridgesMap().get("q_115").size(); i++) {
            String location115 = contentStore.getCartridgesMap().get("q_115").get(i).getLocationString();
            String status115 = contentStore.getCartridgesMap().get("q_115").get(i).getStatus();
            if (status115.equals("На отделении")) {
                arrayListCount.add(String.valueOf(location115));
            }
        }
        for (int i = 0; i < contentStore.getCartridgesMap().get("q_226").size(); i++) {
            String location226 = contentStore.getCartridgesMap().get("q_226").get(i).getLocationString();
            String status226 = contentStore.getCartridgesMap().get("q_226").get(i).getStatus();
            if (status226.equals("На отделении")) {
                arrayListCount.add(String.valueOf(location226));
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


    public void saveContent() {
        try {
            FileOutputStream writeInFile = new FileOutputStream(saveContent);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(writeInFile);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLog(String text) throws IOException {
        String[] array = text.split("  ");
        FileWriter fileWriter = new FileWriter(saveLog, true);
        for (int i = 0; i < array.length; i++) {
            fileWriter.write(array[i] + "\r\n");
        }
        fileWriter.close();
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

