import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(100, 3, 1, 100.0);
        GameProgress progress2 = new GameProgress(80, 2, 1, 80.0);
        GameProgress progress3 = new GameProgress(50, 1, 2, 50.0);

        String saveDir = "C:\\My projects\\Java\\Netology\\Games\\savegames";

        saveGame(saveDir + "//save1.dat", progress1);
        saveGame(saveDir + "//save2.dat", progress2);
        saveGame(saveDir + "//save3.dat", progress3);

        List<String> saves = new ArrayList<>();

        saves.add(saveDir + "//save1.dat");
        saves.add(saveDir + "//save2.dat");
        saves.add(saveDir + "//save3.dat");

        zipFiles(saveDir + "//zip.zip", saves);

        for (String save : saves) {
            new File(save).delete();
        }
    }

    public static void saveGame(String saveFile, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(saveFile);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipFile, List<String> fileList) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (String fileName : fileList) {
                try (FileInputStream fis = new FileInputStream(fileName)) {
                    ZipEntry entry = new ZipEntry(fileName.replace("C:\\My projects\\Java\\Netology\\Games\\savegames", ""));
                    zos.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zos.write(buffer);
                    zos.closeEntry();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}