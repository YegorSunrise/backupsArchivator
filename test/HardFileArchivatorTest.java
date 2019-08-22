import generator.HardFileGenerator;

public class HardFileArchivatorTest extends BackupArchivatorTest {
    public HardFileArchivatorTest() {
        super(new HardFileGenerator());
    }
}
