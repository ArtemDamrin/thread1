import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void saveGame(GameProgress save, String way) {
        try (FileOutputStream fos = new FileOutputStream(way);
             ObjectOutputStream cos = new ObjectOutputStream(fos)) {
            cos.writeObject(save);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void newFile(File file) {
        try {
            if (file.createNewFile()) {
                System.out.println("Файл " + file.getName() + " создан");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String wayToZip, String[] arr) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(wayToZip))) {
            for (String wayToFile : arr) {
                try (FileInputStream fis = new FileInputStream(wayToFile)) {
                    ZipEntry entry = new ZipEntry(wayToFile);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {

        GameProgress save1 = new GameProgress(30, 2, 10, 100);
        GameProgress save2 = new GameProgress(31, 3, 20, 200);
        GameProgress save3 = new GameProgress(32, 4, 30, 300);

        File save1File = new File("C:\\Games\\saveGames", "save1.dat");
        newFile(save1File);

        File save2File = new File("C:\\Games\\saveGames", "save2.dat");
        newFile(save2File);

        File save3File = new File("C:\\Games\\saveGames", "save3.dat");
        newFile(save3File);

        saveGame(save1, "C:\\Games\\saveGames\\save1.dat");
        saveGame(save2, "C:\\Games\\saveGames\\save2.dat");
        saveGame(save3, "C:\\Games\\saveGames\\save3.dat");

        String[] savesList = new String[3];
        savesList[0] = "C:\\Games\\saveGames\\save1.dat";
        savesList[1] = "C:\\Games\\saveGames\\save2.dat";
        savesList[2] = "C:\\Games\\saveGames\\save3.dat";

        zipFiles("C:\\Games\\saveGames\\zip.zip", savesList);

        save1File.delete();
        save2File.delete();
        save3File.delete();
    }
}