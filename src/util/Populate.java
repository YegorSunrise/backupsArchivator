package util;

import model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Populate {

    private static Map<String, User> userMap = new HashMap<>();

    private static Logger logger = Logger.getLogger(Populate.class.getName());


    private Populate() {
    }

    public static Map<String, User> getUsers(int quantity) {
        logger.log(Level.INFO, "generate users... quantity: " + quantity);
        for (int i = 0; i < quantity; i++) {
            User user = new User(generateName(), generateDate());
            user.setActive(!user.getLastActive().isBefore(LocalDate.now().minusDays(91)));
            // user.setActive(false);
            userMap.put(user.getUsername(), user);
        }
        logger.log(Level.INFO, "generate complete.");

        return userMap;
    }

    private static String generateName() {
        return UUID.randomUUID().toString().substring(0, 7).toUpperCase();
    }

    private static LocalDate generateDate() {
        long minDay = LocalDate.of(2018, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }


}
