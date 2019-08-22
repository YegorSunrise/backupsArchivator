import addition.Cleaner;
import addition.UnArchive;
import archivator.BackupArchivator;
import generator.FileGenerator;
import model.User;
import org.junit.Before;
import org.junit.Test;
import util.Populate;

import java.util.Map;
import java.util.stream.Collectors;

public class StressTest {

    private Map<String, User> userMap;
    private BackupArchivator archivator = new BackupArchivator();
    private Cleaner cleaner = new Cleaner();
    private UnArchive unArchive = new UnArchive();

    private FileGenerator fileGenerator;

    public StressTest(FileGenerator fileGenerator) {
        this.fileGenerator = fileGenerator;
    }

    @Before
    public void setUp() {
        userMap = Populate.getUsers(200);
        fileGenerator.setUserMap(userMap);
        fileGenerator.createFiles();
    }

    @Test
    public void stressTest() {
        archivator.archive(userMap);
        cleaner.removeInactiveUsers(userMap, 183);
        unArchive.getFilesFromArchive(userMap.values().stream().filter(x -> !x.isActive()).collect(Collectors.toList()));
    }
}
