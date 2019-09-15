package util;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import models.Cartridge;
import models.Summary;
import models.Utilized;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
            try {
                if (tab2.startsWith("q_")) {
                    cartridgesMap.putAll((Map<String, ArrayList<Cartridge>>) readObjects(tab2));
                } else if (tab2.equals("Сводная")) {
                    ArrayList<Summary> summaries = (ArrayList<Summary>) readObjects(tab2);
                    if (summaries == null) {
                        summaryArrayList.addAll(new ArrayList<Summary>());
                    } else {
                        summaryArrayList.addAll(summaries);
                    }
                } else if (tab2.equals("Списанные")) {
                    ArrayList<Utilized> utilizeds = (ArrayList<Utilized>) readObjects(tab2);
                    if (utilizeds == null) {
                        utilizedArrayList.addAll(new ArrayList<Utilized>());
                    } else {
                        utilizedArrayList.addAll((utilizeds));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(Object list, String fileName) throws IOException {
        FileOutputStream writeInFile = new FileOutputStream("F:\\study\\tabs\\" + fileName + ".res");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(writeInFile);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
    }

    public Object readObjects(String listName) throws IOException, ClassNotFoundException {
        if (!Files.exists(Paths.get("F:\\study\\tabs\\" + listName + ".res"))) {
            return null;
        }
        FileInputStream readFileTabs = new FileInputStream("F:\\study\\tabs\\" + listName + ".res");
        ObjectInputStream inputStream = new ObjectInputStream(readFileTabs);
        Object list = inputStream.readObject();
        inputStream.close();
        return list;
    }

    public void writeTabs() throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("F:\\study\\tabs\\tabs.txt"), "windows-1251"));
        writer.write(tabList.get(0));
        for (int i = 0; i < tabList.size(); i++) {
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
            cartridgeTabs.add(str);
            str = reader.readLine();
        }
        reader.close();
        return cartridgeTabs;
    }
}
