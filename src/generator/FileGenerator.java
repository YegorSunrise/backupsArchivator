package generator;

import model.User;

import java.util.Map;

public interface FileGenerator {

    void createFiles();

    void setUserMap(Map<String,User> userMap);
}
