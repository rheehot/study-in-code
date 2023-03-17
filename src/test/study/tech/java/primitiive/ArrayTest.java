package tech.java.primitiive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArrayTest {

    @Test
    void twoDimensionalArray() {
        int[][] array = {  // = int [2][3]
                {1, 2, 3},
                {4, 5, 6}
        };

        Assertions.assertEquals(1, array[0][0]);
        Assertions.assertEquals(2, array[0][1]);
        Assertions.assertEquals(3, array[0][2]);
        Assertions.assertEquals(4, array[1][0]);
        Assertions.assertEquals(5, array[1][1]);
        Assertions.assertEquals(6, array[1][2]);
    }
}
