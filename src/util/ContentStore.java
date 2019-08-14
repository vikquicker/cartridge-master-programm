package util;

import models.Cartridge;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContentStore implements Serializable {
    public void Serializable() throws FileNotFoundException {
        FileOutputStream writeInFile = new FileOutputStream("F:\\Учёба\\tabs\\tabs.res");

    }

    public List<Cartridge> deSerializable() throws IOException, ClassNotFoundException {
        ArrayList<Cartridge> list = new ArrayList<>();
        FileInputStream readFileTabs = new FileInputStream("F:\\Учёба\\tabs\\tabs.txt");
        ObjectInputStream inputStream = new ObjectInputStream(readFileTabs);
        Cartridge cartridge = (Cartridge) inputStream.readObject();

        String str = inputStream.toString();
        String[] massive = str.split(",");

        for (int i = 0; i < massive.length; i++) {
            Cartridge cartridge1 = new Cartridge(massive[i]);
            list.add(cartridge);

        }
        return list;
    }
}
