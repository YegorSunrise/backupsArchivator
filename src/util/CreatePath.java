package util;

import java.io.File;
import java.nio.file.Paths;

public class CreatePath {
    private static String root = Paths.get("").toAbsolutePath().toString() + File.separator + "usersBackup";

    private CreatePath() {
    }

    public static String getRoot() {
        return root;
    }
}
