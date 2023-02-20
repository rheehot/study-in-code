package code.spring.study.data.structure;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListTest {

    @Test
    @DisplayName("List.of() 메서드에 의해 생성된 List는 불변입니다.")
    void listOfMakeImmutableList() {
        List<String> list = List.of("a", "b", "c");
        //noinspection ConstantConditions
        Assertions.assertThrows(UnsupportedOperationException.class, () -> list.add("d"));
    }

    @Test
    @DisplayName("불변리스트이지만 각 항목이 가지는 값은 변경할 수 있습니다.")
    void immutableListButItemChange() {
        List<Position> list = List.of(new Position(11, 22), new Position(33, 44));
        Assertions.assertDoesNotThrow(() -> list.get(0).setAzimuth(55));
        Assertions.assertEquals(55, list.get(0).getAzimuth());
    }

    @Test
    @DisplayName("List.copyOf() 메서드에 Immutable List를 넘기면 같은 인스턴스 List를 반환합니다.")
    void copyOfImmutableList() {
        List<Position> origin = List.of(new Position(11, 22), new Position(33, 44));
        List<Position> copied = List.copyOf(origin);

        Assertions.assertEquals(origin, copied);
    }

    @SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
    @Test
    @DisplayName("addAll() 메서드를 사용하면 shadow copy가 일어납니다.")
    void shadowCopy() {
        // Given
        List<Position> origin = new ArrayList<>();
        origin.add(new Position(11, 22));
        origin.add(new Position(33, 44));
        List<Position> copied = new ArrayList<>();

        // When
        copied.addAll(origin);
        // List<Position> copied = new ArrayList<>(origin); 동일한 결과
        copied.get(0).setAzimuth(55);

        // Then
        Assertions.assertEquals(55, origin.get(0).getAzimuth());
    }

    @Test
    @DisplayName("List deep copy를 직접 수행합니다.")
    void deepCopy() {
        // Given
        List<Position> origin = new ArrayList<>();
        origin.add(new Position(11, 22));
        origin.add(new Position(33, 44));
        List<Position> copied = new ArrayList<>();

        // When
        for (Position pos : origin) {
            copied.add(new Position(pos));
        }
        copied.get(0).setAzimuth(55);

        // Then
        Assertions.assertNotEquals(55, origin.get(0).getAzimuth());
        Assertions.assertEquals(11, origin.get(0).getAzimuth());
    }

    @Getter
    @Setter
    static class Position implements Comparable<Position> {
        int azimuth;
        int elevation;

        public Position(int azimuth, int elevation) {
            this.azimuth = azimuth;
            this.elevation = elevation;
        }

        public Position(Position pos) {
            this.azimuth = pos.getAzimuth();
            this.elevation = pos.getElevation();
        }
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
