package spring.study.data.structure;


import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class ListTest {

    @AllArgsConstructor
    static
    class Position implements Comparable<Position> {
        int azimuth;
        int elevation;

        @Override
        public int compareTo(Position o) {
            if (o == null) {
                return -1;
            }

            if ((o.azimuth == this.azimuth) && (o.elevation == this.elevation)) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    @Test
    void objectEquals() {
        Position p1 = new Position(1, 2);
        Position p2 = new Position(1, 2);
        Assertions.assertEquals(0, p1.compareTo(p2));
    }

    @Test
    void equals() {
        List<Position> list1 = new ArrayList<>();
        list1.add(new Position(1, 2));
        list1.add(new Position(3, 4));
        list1.add(new Position(5, 6));
        List<Position> list2 = new ArrayList<>();
        list2.add(new Position(1, 2));
        list2.add(new Position(3, 4));
        list2.add(new Position(5, 6));

        for (int i = 0; i < list1.size(); i++) {
            Assertions.assertEquals(0, list1.get(i).compareTo(list2.get(i)));
        }
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
