package util;

import models.Cartridge;

import java.io.*;
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
    ArrayList<Cartridge> cartridges = new ArrayList<>();
    ArrayList<String> tabList;

    public ArrayList<Cartridge> getCartridges() {
        return cartridges;
    }

    public void setCartridges(ArrayList<Cartridge> cartridges) {
        this.cartridges = cartridges;
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

        cartridges.add(new Cartridge());

        for (String tab2 : tabList) {
            try {
                if (tab2.startsWith("q_")) {
                    cartridgesMap.put(tab2, readCartidges(tab2));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(ArrayList<Cartridge> list, String fileName) throws IOException {
        FileOutputStream writeInFile = new FileOutputStream("F:\\study\\tabs\\" + fileName + ".res");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(writeInFile);
        objectOutputStream.writeObject(list);
        objectOutputStream.close();
    }

    public ArrayList<Cartridge> readCartidges(String listName) throws IOException, ClassNotFoundException {
        FileInputStream readFileTabs = new FileInputStream("F:\\study\\tabs\\" + listName + ".res");
        ObjectInputStream inputStream = new ObjectInputStream(readFileTabs);
        ArrayList<Cartridge> list = (ArrayList<Cartridge>) inputStream.readObject();
        inputStream.close();
        return list;
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
