import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        LightFileArchivatorTest.class,
        MediumFileArchivatorTest.class,
        HardFileArchivatorTest.class,
        LightFileCleanerTest.class,
        MediumFileCleanerTest.class,
        HardFileCleanerTest.class,
        LightFileUnArchiveTest.class,
        MediumFileUnArchiveTest.class,
        HardFileUnArchiveTest.class,
        LightFileStressTest.class,
        MediumFileStressTest.class,
        HardFileStressTest.class
})


public class SuitTest {
}
