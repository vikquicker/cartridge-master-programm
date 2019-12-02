package util;

import models.Cartridge;
import models.Summary;
import models.Utilized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContentStore implements Serializable {
    static String START_FILE = System.getProperty("user.dir");
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


    public void saveContent() {
        try {
            String root = Paths.get("").toAbsolutePath().toString();
            FileOutputStream writeInFile = new FileOutputStream(root + "backUp.res");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(writeInFile);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveLog(String text) throws IOException {
        String[] array = text.split("  ");
        String root = Paths.get("").toAbsolutePath().toString();
        FileWriter fileWriter = new FileWriter(root + "log.txt", true);
        for (int i = 0; i < array.length; i++) {
            fileWriter.write(array[i] + "\r\n");
        }
        fileWriter.close();
    }

//    public void protectTwoAplicationSave(String str) throws IOException {
//        String root = Paths.get("").toAbsolutePath().toString();
//        FileWriter fileWriter = new FileWriter(root + "protectTwoAplicationSave.txt");
//        fileWriter.write(str);
//        fileWriter.close();
//    }
//
//    public String protectTwoAplicationread() throws IOException, ClassNotFoundException {
//        String root = Paths.get("" + "protectTwoAplicationSave.txt").toAbsolutePath().toString();
//        FileInputStream readFile = new FileInputStream(root);
//        ObjectInputStream inputStream = new ObjectInputStream(readFile);
//        String list = (String) inputStream.readObject();
//        inputStream.close();
//        return list;
//    }

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

