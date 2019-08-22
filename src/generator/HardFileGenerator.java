package generator;

import archivator.Archivator;
import model.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;

public class HardFileGenerator extends AbstractFileGenerator implements Archivator {

    private static Logger logger = Logger.getLogger(HardFileGenerator.class.getName());

    public HardFileGenerator(Map<String, User> userMap) {
        super(userMap);
    }

    @Override
    public void createFiles() {
        logger.log(Level.INFO, "generate hard files pack...");
        String root = getPath();

        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            String folder = userEntry.getKey();
            File usersBuf = new File(root, folder);
            createFiles(userEntry, 900, () -> UUID.randomUUID().toString(), root);

            for (File fileToZip : Objects.requireNonNull(usersBuf.listFiles())) {
                File zippedFile = new File(fileToZip + ".a");
                try {
                    FileOutputStream fos = new FileOutputStream(zippedFile);
                    ZipOutputStream zipOut = new ZipOutputStream(fos);
                    zipFile(zipOut, fileToZip);
                    if (!fileToZip.getName().contains(".a")) {
                        fileToZip.delete();
                    }
                    zipOut.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logger.log(Level.INFO, "create zip file " + zippedFile.getName() + " size: " + (zippedFile.length() / 1024) + "kb");
            }
        }
        logger.log(Level.INFO, "generate complete.");
    }

}
