import addition.Cleaner;
import archivator.BackupArchivator;
import generator.FileGenerator;
import model.User;
import org.junit.Before;
import org.junit.Test;
import util.CreatePath;

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

public class CleanerTest {


    private String root = CreatePath.getRoot();

    private Map<String, User> userMap = new HashMap<>();
    private User user1 = new User("name1", LocalDate.of(2018, 4, 14));
    private User user2 = new User("name2", LocalDate.of(2019, 3, 16));
    private User user3 = new User("name3", LocalDate.of(2017, 5, 2));
    private User user4 = new User("name4", LocalDate.of(2016, 9, 19));
    private BackupArchivator archivator = new BackupArchivator();
    private Cleaner cleaner = new Cleaner();

    private FileGenerator fileGenerator;

    public CleanerTest(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

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
        fileGenerator.setUserMap(userMap);
        fileGenerator.createFiles();

    }


    @Test
    public void removeInactiveUsers() {
        archivator.archive(userMap);
        cleaner.removeInactiveUsers(userMap, 100);
        assertEquals(new File(root).listFiles().length, 1);
    }

    @Test
    public void inactivePeriodNotEnd() {
        user4.setActive(false);
        userMap.put(user4.getUsername(), user4);
        archivator.archive(userMap);
        cleaner.removeInactiveUsers(userMap, 200);
        assertEquals(new File(root).listFiles().length, 1);
    }

    @Test(expected = RuntimeException.class)
    public void negativeInactiveDays() {
        archivator.archive(userMap);
        cleaner.removeInactiveUsers(userMap, -1);
    }

    @Test(expected = RuntimeException.class)
    public void nullMap() {
        archivator.archive(userMap);
        cleaner.removeInactiveUsers(null, 100);
    }

    @Test
    public void emptyMap() throws IOException {
        archivator.archive(userMap);
        cleaner.removeInactiveUsers(new HashMap<>(), 100);
        List<Path> collect = Files.walk(Paths.get(root))
                .filter(Files::isRegularFile)
                .filter(x -> x.toFile().getName().contains(".zip"))
                .collect(Collectors.toList());
        assertEquals(collect.size(), 3);
    }
}
