package generator;

import model.User;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MediumFileGenerator extends AbstractFileGenerator {

    private static Logger logger = Logger.getLogger(MediumFileGenerator.class.getName());

    public MediumFileGenerator(Map<String, User> userMap) {
        super(userMap);
    }

    @Override
    public void createFiles() {
        logger.log(Level.INFO, "generate medium files pack...");
        String root = getPath();
        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            createFiles(userEntry, 500, () -> UUID.randomUUID().toString(), root);
        }
        logger.log(Level.INFO,"generate complete.");
    }
}
