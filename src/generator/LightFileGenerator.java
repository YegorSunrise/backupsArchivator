package generator;

import model.User;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LightFileGenerator extends AbstractFileGenerator {

    private static Logger logger = Logger.getLogger(LightFileGenerator.class.getName());

    public LightFileGenerator(Map<String, User> userMap) {
        super(userMap);
    }

    @Override
    public void createFiles() {
        logger.log(Level.INFO, "generate light files pack...");
        String root = getPath();
        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            createFiles(userEntry, 17000, () -> "X", root);
        }
        logger.log(Level.INFO,"generate complete.");
    }


}
