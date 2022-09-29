package spring.study.data.structure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ListTest {

    @Test
    void equals() {
        List<String> list1 = new ArrayList<>();
        list1.add("Joo");
        list1.add("Hi");
        list1.add("Hello");
        List<String> list2 = new ArrayList<>();
        list2.add("Joo");
        list2.add("Hi");
        list2.add("Hello");
        Assertions.assertEquals(list1, list2);
    }

    @Test
    void diff() {
        List<String> list1 = new ArrayList<>();
        list1.add("Joo");
        list1.add("Hi");
        list1.add("Hello");
        List<String> list2 = new ArrayList<>();
        list2.add("Joo");
        list2.add("Hi");
        list2.add("Helloooo");
        Assertions.assertNotEquals(list1, list2);
    }
}
