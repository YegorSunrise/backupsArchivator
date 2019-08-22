package util;

import java.io.File;
import java.util.Objects;

public class Volume {

    private Volume() {
    }

    public static long getVolume(File file) {
        long size;
        size = getSize(file);
        return size;

    }

    private static long getSize(File dir) {
        long size = 0;
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile()) {
                    size += file.length();
                } else
                    size += getSize(file);
            }
        } else {
            size += dir.length();
        }
        return size;
    }
}
