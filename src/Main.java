import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        GameProgress game1 = new GameProgress(6, 2, 2, 22.0);
        GameProgress game2 = new GameProgress(4, 3, 7, 20.0);
        GameProgress game3 = new GameProgress(5, 4, 3, 35.0);

        saveGame("/Users/silent27/Games/savegames/game1.dat", game1);
        saveGame("/Users/silent27/Games/savegames/game2.dat", game2);
        saveGame("/Users/silent27/Games/savegames/game3.dat", game3);

        zipFiles(new File("/Users/silent27/Games/savegames"));

        deleteSaveGame("/Users/silent27/Games/savegames/game1.dat");
        deleteSaveGame("/Users/silent27/Games/savegames/game2.dat");
        deleteSaveGame("/Users/silent27/Games/savegames/game3.dat");
    }

    public static void saveGame(String name, GameProgress game) {
        try (FileOutputStream fileOut = new FileOutputStream(name);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(game);
            System.out.println("Файл создан");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(File path) throws IOException {
        String zip = "/Users/silent27/Games/savegames/zip.zip";
        File zipFile = new File(zip);
        if (zipFile.exists()) {
            System.out.println(zipFile.delete() + " Удаляем старый архив");
        }
        File[] files = path.listFiles();
        if (files.length == 0)
            throw new IllegalArgumentException(path.getAbsolutePath() + " Файлы не существуют");
        FileOutputStream fileOut = new FileOutputStream("/Users/silent27/Games/savegames/zip.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fileOut);
        for (File zipZip : files) {
            FileInputStream fileIn = new FileInputStream(zipZip);
            ZipEntry entry = new ZipEntry(zipZip.getName());
            zipOut.putNextEntry(entry);
            byte[] buffer = new byte[fileIn.available()];
            int length;
            while ((length = fileIn.read(buffer)) >= 0) {
                zipOut.write(buffer, 0, length);
            }
            fileIn.close();
        }
        zipOut.close();
        fileOut.close();
    }

    public static void deleteSaveGame(String name) {
        File file = new File(name);
        if (file.exists()) {
            System.out.println(file.delete() + " Файлы удалены");
        } else {
            System.out.println("Файл не существует");
        }
    }
}
