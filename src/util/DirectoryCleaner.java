package util;

import java.io.File;
import java.util.Objects;

public interface DirectoryCleaner {

    default void clearDir(File dir) {
        if (dir == null) {
            throw new RuntimeException("directory must not be null");
        }
        for (File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory())
                clearDir(file);
            file.delete();
        }


    }
}
