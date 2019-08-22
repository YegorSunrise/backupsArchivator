package addition;

import model.User;
import util.CreatePath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnArchive {

    private String root = CreatePath.getRoot();

    private static Logger logger = Logger.getLogger(UnArchive.class.getName());

    public void getFilesFromArchive(Collection<User> users) {
        for (User user : users) {
            getFilesFromArchive(user);
        }
    }

    public void getFilesFromArchive(User user) {
        if (user == null) {
            throw new RuntimeException("user must not be null");
        }

        if (user.isActive()) {
            throw new RuntimeException("user is not in archive user: " + user.getUsername());
        }
        logger.log(Level.INFO, "unarchive for user " + user.getUsername());

        StringBuilder archive =
                new StringBuilder()
                        .append(root)
                        .append(File.separator)
                        .append(user.getUsername())
                        .append(File.separator)
                        .append(user.getUsername())
                        .append(".zip");
        File destDir = new File(root, user.getUsername());
        byte[] buffer = new byte[1024];

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(archive.toString()))) {
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File newFile = new File(destDir, zipEntry.getName());
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                zipEntry = zis.getNextEntry();
            }
            zis.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new File(archive.toString()).delete();
        logger.log(Level.INFO, "unarchive complete.");
    }
}
