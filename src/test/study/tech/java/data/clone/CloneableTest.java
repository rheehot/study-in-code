package tech.java.data.clone;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * - 깊은 복사와 얕은 복사
 * - 배열의 clone()
 */
public class CloneableTest {
    @Test
    void basic() {
        Info info = new Info(1, new Member(10, "name"), new Mutable(10));
        Info clone = info.clone();
        assertEquals(info.id, clone.id);
        assertEquals(info.member.age, clone.member.age);
        assertEquals(info.member.name, clone.member.name);
        assertEquals(info.mutable.size, clone.mutable.size);
        assertArrayEquals(info.mutable.array, clone.mutable.array);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    @AllArgsConstructor
    class Info implements Cloneable {
        int id;
        Member member;
        Mutable mutable;

        @Override
        public Info clone() {
            try {
                Info clone = (Info) super.clone();
                // recommand: copy mutable state here, so the clone can't change the internals of the original
                return clone;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    class Mutable {
        int size;
        int[] array;
        Mutable(int size) {
            this.size = size;
            array = new int[size];
        }
    }

    @AllArgsConstructor
    class Member {
        int age;
        String name;
    }
}
