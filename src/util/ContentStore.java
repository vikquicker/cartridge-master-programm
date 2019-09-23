package util;

import models.Cartridge;
import models.Summary;
import models.Utilized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ContentStore implements Serializable {
    private static ContentStore contentStore;

    public static ContentStore getContentStore() {
        if (contentStore == null) {
            contentStore = new ContentStore();
        }
        return contentStore;
    }

    Map<String, ArrayList<Cartridge>> cartridgesMap = new HashMap<>();
    private ArrayList<Summary> summaryArrayList = new ArrayList<>();
    private ArrayList<Utilized> utilizedArrayList = new ArrayList<>();
    ArrayList<String> tabList;

    public void saveAll() throws IOException {
        save(cartridgesMap, "q_AllTabs");
        save(summaryArrayList, tabList.get(1));
        save(summaryArrayList, tabList.get(2));
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
            tabList = readTabs();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String tab2 : tabList) {
            if (tab2.startsWith("q_")) {
                Map<String, ArrayList<Cartridge>> numerikTables = (Map<String, ArrayList<Cartridge>>) readObjects(tab2);
                if (numerikTables != null && numerikTables.size() > 0) {
                    cartridgesMap.putAll((Map<String, ArrayList<Cartridge>>) readObjects(tab2));
                }
            } else if (tab2.equals("Сводная")) {
                ArrayList<Summary> summaries = (ArrayList<Summary>) readObjects(tab2);
                if (summaries != null && summaries.size() > 0) {
                    summaryArrayList.addAll(new ArrayList<Summary>());
                }
            } else if (tab2.equals("Списанные")) {
                ArrayList<Utilized> utilizeds = (ArrayList<Utilized>) readObjects(tab2);
                if (utilizeds != null && utilizeds.size() > 0) {
                    utilizedArrayList.addAll(new ArrayList<Utilized>());
                }
            }
        }
    }

    public void save(Object list, String fileName) throws IOException {
        FileOutputStream writeInFile = new FileOutputStream("F:\\study\\tabs\\" + fileName + ".res");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(writeInFile);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
    }

    public Object readObjects(String listName) {
        if (!Files.exists(Paths.get("F:\\study\\tabs\\" + listName + ".res"))) {
            return null;
        }
        try {
            FileInputStream readFileTabs = new FileInputStream("F:\\study\\tabs\\" + listName + ".res");
            ObjectInputStream inputStream = new ObjectInputStream(readFileTabs);
            Object list = inputStream.readObject();
            inputStream.close();
            return list;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void writeTabs() throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("F:\\study\\tabs\\tabs.txt"), "windows-1251"));
        writer.write(tabList.get(0));
        for (int i = 1; i < tabList.size(); i++) {
            writer.newLine();
            writer.write(tabList.get(i));
        }
        writer.close();
    }

    public ArrayList<String> readTabs() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("F:\\study\\tabs\\tabs.txt"), "windows-1251"));
        ArrayList<String> cartridgeTabs = new ArrayList<>();
        String str = reader.readLine();
        while (str != null) {
            if (str.startsWith("q_")) {
                readQTabs(str, cartridgeTabs);
            }
            cartridgeTabs.add(str);
            str = reader.readLine();
        }
        reader.close();
        return cartridgeTabs;
    }


    private void readQTabs(String str, ArrayList<String> tabs) {
        if (str.startsWith("q_")) {
            Map<String, ArrayList<Cartridge>> numerikTables = (Map<String, ArrayList<Cartridge>>) readObjects(str);
            Set<String> setQtabs = numerikTables.keySet();
            for (String x : setQtabs) {
                tabList.add(x);
            }
        }
    }
}
