package tech.spring.study.spring.context;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

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
