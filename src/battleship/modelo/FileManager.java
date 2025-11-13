package battleship.modelo;

import java.io.*;
import java.util.*;

public class FileManager {
    public static void saveGameLog(List<MoveRecord> records, String file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(records);
        }
    }

    public static List<MoveRecord> loadGameLog(String file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<MoveRecord>) ois.readObject();
        }
    }
}
