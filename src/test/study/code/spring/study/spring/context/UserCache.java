package code.spring.study.spring.context;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;

public class UserCache {
    @Getter
    private Set<String> userList = new HashSet<>();

    public boolean addUser(String user) {
        return userList.add(user);
    }

    public void printUserList(String message) {
        System.out.println(message + ": " + userList);
    }
}
