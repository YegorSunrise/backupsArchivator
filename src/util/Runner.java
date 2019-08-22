package util;

import addition.Cleaner;
import addition.UnZipper;
import generator.FileGenerator;
import generator.LightFileGenerator;
import archivator.BackupArchivator;
import model.User;
import util.Populate;

import java.util.Map;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {


//        Map<String, User> userMap = //Populate.getUsers(1);
//                new LinkedHashMap<>();
//        userMap.put(user1.getUsername(), user1);
//        userMap.put(user2.getUsername(), user2);
//        userMap.put(user3.getUsername(), user3);
//        userMap.put(user4.getUsername(), user4);

        Map<String, User> userMap = Populate.getUsers(10);


      //  userMap.forEach((x, y) -> System.out.println(y));
        FileGenerator fileGen =
                new LightFileGenerator(userMap);
//        new MediumFileGenerator(userMap);
//                new HardFileGenerator(userMap);
        fileGen.createFiles();
        BackupArchivator backupArchivator = new BackupArchivator();
        backupArchivator.archive(userMap);

        Cleaner cleaner = new Cleaner();

        cleaner.removeInactiveUsers(userMap, 200);

        UnZipper unZipper = new UnZipper();
        unZipper.getFilesFromArchive(userMap.values().stream().filter(x -> !x.isActive()).collect(Collectors.toList()));

    }


}


