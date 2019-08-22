package generator;

import model.User;
import util.CreatePath;
import util.DirectoryCleaner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractFileGenerator implements FileGenerator, DirectoryCleaner {

    Map<String, User> userMap;

    private static Logger logger = Logger.getLogger(AbstractFileGenerator.class.getName());

    public AbstractFileGenerator(Map<String, User> userMap) {
        this.userMap = userMap;
    }

    String getPath() {
        String root = CreatePath.getRoot();
        File creatorDir = new File(root);
        if (!creatorDir.exists()) {
            creatorDir.mkdir();
        } else {
            logger.log(Level.INFO, "clear old directories");
            clearDir(creatorDir);
        }
        return root;
    }

    void createFiles(Map.Entry<String, User> userEntry, int lines, Supplier<String> content, String root) {
        String folder = userEntry.getKey();
        File usersBuf = new File(root, folder);
        if (!usersBuf.exists()) {
            usersBuf.mkdir();
        }
        for (int i = 0; i < 5; i++) {
            Path fullPathToFile = new File(usersBuf, UUID.randomUUID().toString()).toPath();
            try (BufferedWriter bw = Files.newBufferedWriter(fullPathToFile)) {
                for (int j = 0; j < lines; j++) {
                    bw.write(content.get());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.log(Level.INFO, "create file " + fullPathToFile.getFileName() + " size: " + (fullPathToFile.toFile().length() / 1024) + "kb");
        }

    }
}
