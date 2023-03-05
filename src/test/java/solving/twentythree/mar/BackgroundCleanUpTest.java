package solving.twentythree.mar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BackgroundCleanUpTest {
    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(
                Arguments.arguments(new String[]{".#...", "..#..", "...#."}, new int[]{0, 1, 3, 4}),
                Arguments.arguments(new String[]{"..........", ".....#....", "......##..", "...##.....", "....#....."}, new int[]{1, 3, 5, 8}),
                Arguments.arguments(new String[]{".##...##.", "#..#.#..#", "#...#...#", ".#.....#.", "..#...#..", "...#.#...", "....#...."}, new int[]{0, 0, 7, 9}),
                Arguments.arguments(new String[]{"..", "#."}, new int[]{1, 0, 2, 1})
        );
    }

    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void testSolution(String[] wallpaper, int[] expected) {
        int[] result = solution(wallpaper);
        Assertions.assertArrayEquals(expected, result);
    }

    public int[] solution(String[] wallpaper) {

        // Top
        int top = IntStream.range(0, wallpaper.length)
                .filter(i -> wallpaper[i].contains("#"))
                .findFirst()
                .orElse(-1);
        assert top != -1;

        // Left
        int left = Arrays.stream(wallpaper)
                .mapToInt(line -> line.indexOf('#'))
                .filter(find -> find != -1)
                .min()
                .orElse(-1);
        assert left != -1;

        // Bottom
        int bottom = IntStream.iterate(wallpaper.length - 1, i -> i - 1).limit(wallpaper.length)
                .filter(i -> wallpaper[i].contains("#"))
                .findFirst()
                .orElse(-1);
        assert bottom != -1;

        // Right
        int right = Arrays.stream(wallpaper)
                .mapToInt(line -> line.lastIndexOf('#'))
                .filter(find -> find != -1)
                .max()
                .orElse(-1);
        assert right != -1;

        // 드래그할 영역 반환
        return new int[]{top, left, bottom + 1, right + 1};
    }
}

