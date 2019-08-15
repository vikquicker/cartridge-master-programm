package util;

import models.Cartridge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContentStore implements Serializable {
    public void save(List<String> list) throws IOException {
        FileOutputStream writeInFile = new FileOutputStream("F:\\Учёба\\tabs\\table.res");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(writeInFile);
        for (String x : list) {
            objectOutputStream.writeObject(x);
        }
        objectOutputStream.close();
    }

    public List<String> readTable() throws IOException, ClassNotFoundException {
        FileInputStream readFileTabs = new FileInputStream("F:\\Учёба\\tabs\\table.res");
        ObjectInputStream inputStream = new ObjectInputStream(readFileTabs);
        List<String> List = (List<String>) inputStream.readObject();
        inputStream.close();
        return List;
    }

    public List<String> readTabs() throws IOException, ClassNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("F:\\Учёба\\tabs\\tabs.txt"));
//        FileReader fileReader = new FileReader("F:\\Учёба\\tabs\\tabs.txt");
//        FileInputStream readFileTabs = new FileInputStream("F:\\Учёба\\tabs\\tabs.txt");
//        ObjectInputStream inputStream = new ObjectInputStream(readFileTabs);
//        List<String> cartridgeList = (List<String>) inputStream.readObject();
//        inputStream.close();
        List<String> cartridgeList = new ArrayList<>();
//        while (fileReader.ready()){
//            cartridgeList.add(String.valueOf(fileReader.read()));
//        }
        return cartridgeList;
    }
}
