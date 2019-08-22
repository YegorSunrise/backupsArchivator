import archivator.BackupArchivator;
import generator.FileGenerator;
import generator.LightFileGenerator;
import model.User;
import org.junit.Before;
import org.junit.Test;
import util.CreatePath;
import util.Volume;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BackupArchivatorTest {

    private String root = CreatePath.getRoot();

    private Map<String, User> userMap = new HashMap<>();
    private User user1 = new User("name1", LocalDate.of(2018, 4, 14));
    private User user2 = new User("name2", LocalDate.of(2019, 3, 16));
    private User user3 = new User("name3", LocalDate.of(2017, 5, 2));
    private User user4 = new User("name4", LocalDate.of(2016, 9, 19));

    private BackupArchivator archivator = new BackupArchivator();
    private FileGenerator fileGenerator;

    @Before
    public void setUp() {
        user1.setActive(false);
        user2.setActive(false);
        user3.setActive(false);
        user4.setActive(true);
        userMap.put(user1.getUsername(), user1);
        userMap.put(user2.getUsername(), user2);
        userMap.put(user3.getUsername(), user3);
        userMap.put(user4.getUsername(), user4);
        fileGenerator = new LightFileGenerator(userMap);
        fileGenerator.createFiles();
    }

    @Test
    public void archive() throws IOException {
        archivator.archive(userMap);
        List<Path> collect = Files.walk(Paths.get(root))
                .filter(Files::isRegularFile)
                .filter(x -> x.toFile().getName().contains(".zip"))
                .collect(Collectors.toList());
        assertEquals(collect.size(), 3);
    }

    @Test
    public void folderSizeChanged() {
        File allBackups = new File(root);
        long volumeBeforeArchive = Volume.getVolume(allBackups);
        archivator.archive(userMap);
        long volumeAfterArchive = Volume.getVolume(allBackups);
        assertNotEquals(volumeAfterArchive, volumeBeforeArchive);
    }

    @Test
    public void notArchive() throws IOException {
        archivator.archive(userMap);
        File notArchive = new File(root, user4.getUsername());
        List<Path> collect = Files.walk(Paths.get(notArchive.toString()))
                .filter(Files::isRegularFile)
                .filter(x -> x.toFile().getName().contains(".zip"))
                .collect(Collectors.toList());
        assertEquals(collect.size(), 0);
    }
}