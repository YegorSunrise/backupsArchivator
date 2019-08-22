package archivator;

import addition.UnArchive;
import model.User;
import util.CreatePath;
import util.Volume;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

public class BackupArchivator implements Archivator {

    private String root = CreatePath.getRoot();

    private static Logger logger = Logger.getLogger(UnArchive.class.getName());

    public void archive(Map<String, User> userMap) {
        if (userMap == null) {
            throw new RuntimeException("check parameters: userMap must not be null");
        }
        logger.log(Level.INFO, "archiving...");
        List<File> allUsernameDir = Arrays.asList(Objects.requireNonNull(new File(root).listFiles()));
        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {

            if (!userEntry.getValue().isActive()) {
                String username = userEntry.getKey();

                File userDir = new File(root, username);
                if (allUsernameDir.contains(userDir)) {
                    logger.log(Level.INFO, "backup user: " + username + " size: " + (Volume.getVolume(userDir) / 1024) + "kb");
                    File[] backups = userDir.listFiles();
                    try (FileOutputStream fos = new FileOutputStream(
                            root + File.separator + username + File.separator + username + ".zip");
                         ZipOutputStream zipOut = new ZipOutputStream(fos)) {

                        for (File fileToZip : Objects.requireNonNull(backups)) {
                            zipFile(zipOut, fileToZip);
                            fileToZip.delete();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    logger.log(Level.INFO, "archive user: " + username + " size: " + (Volume.getVolume(userDir) / 1024) + "kb");
                }
            }
        }
        logger.log(Level.INFO, "archiving complete.");
    }


}
