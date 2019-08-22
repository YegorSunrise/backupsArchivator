package addition;

import model.User;
import util.CreatePath;
import util.DirectoryCleaner;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cleaner implements DirectoryCleaner {

    private String root = CreatePath.getRoot();

    private static Logger logger = Logger.getLogger(Cleaner.class.getName());

    public void removeInactiveUsers(Map<String, User> userMap, int inactiveDays) {

        logger.log(Level.INFO, "inactive users remove...");
        if (userMap == null || inactiveDays < 0) {
            throw new RuntimeException("check parameters: userMap must not be null and inactiveDays can't be < 0");
        }

        int count = 0;
        List<String> usersForRemove = new ArrayList<>();

        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            User user = userEntry.getValue();

            if (!user.isActive() && user.getLastActive().isBefore(LocalDate.now().minusDays(inactiveDays))) {
                String folderForDelete = user.getUsername();
                usersForRemove.add(folderForDelete);
                count++;
                clearDir(new File(root, folderForDelete));
            }
        }

        usersForRemove.forEach(x -> {
            userMap.remove(x);
            new File(root, x).delete();
        });
        logger.log(Level.INFO, "remove complete. users delete quantity: " + count);
    }
}
