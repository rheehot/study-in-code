package solving.twentythree.mar;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class PracticeBilliardsTest {
    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(
                Arguments.arguments(10, 10, 3, 7, new int[][]{{7, 7}, {2, 7}, {7, 3}}, new int[]{52, 37, 116})
        );
    }

    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void testSolution(int m, int n, int startX, int startY, int[][] balls, int[] expected) {
        int[] result = solution(m, n, startX, startY, balls);
        Assertions.assertArrayEquals(expected, result);
    }

    public int[] solution(int m, int n, int startX, int startY, int[][] balls) {
        return Arrays.stream(balls)
                .mapToInt(target -> minDistance(new Point(startX, startY), new Point(target[0], target[1]), m, n))
                .toArray();
    }

    public int minDistance(Point start, Point target, int width, int height) {
        return Stream.of(
                        distanceBounceLeft(start, target, width, height),
                        distanceBounceRight(start, target, width, height),
                        distanceBounceBottom(start, target, width, height),
                        distanceBounceTop(start, target, width, height))
                .filter(Optional::isPresent)
                .mapToInt(Optional::get)
                .min()
                .orElse(-1);
    }

    Optional<Integer> distanceBounceLeft(Point start, Point target, int width, int height) {
        if (blockedToLeft(start, target)) {
            return Optional.empty();
        }

        int xSum = sumOfDistanceXFromBase(start, target, 0);
        int ySum = distanceY(start, target);
        int distance = sumOfPowerEachDistance(xSum, ySum);
        return Optional.of(distance);
    }

    Optional<Integer> distanceBounceRight(Point start, Point target, int width, int height) {
        if (blockedToRight(start, target)) {
            return Optional.empty();
        }

        int xSum = sumOfDistanceXFromBase(start, target, width);
        int ySum = distanceY(start, target);
        int distance = sumOfPowerEachDistance(xSum, ySum);
        return Optional.of(distance);
    }

    Optional<Integer> distanceBounceBottom(Point start, Point target, int width, int height) {
        if (blockedToBottom(start, target)) {
            return Optional.empty();
        }

        int xSum = distanceX(start, target);
        int ySum = sumOfDistanceYFromBase(start, target, 0);
        int distance = sumOfPowerEachDistance(xSum, ySum);
        return Optional.of(distance);
    }

    Optional<Integer> distanceBounceTop(Point start, Point target, int width, int height) {
        if (blockedToTop(start, target)) {
            return Optional.empty();
        }

        int xSum = distanceX(start, target);
        int ySum = sumOfDistanceYFromBase(start, target, height);
        int distance = sumOfPowerEachDistance(xSum, ySum);
        return Optional.of(distance);
    }

    private boolean blockedToLeft(Point start, Point target) {
        return start.sameY(target) && start.greaterThanX(target);
    }

    private boolean blockedToRight(Point start, Point target) {
        return start.sameY(target) && target.greaterThanX(start);
    }

    private boolean blockedToTop(Point start, Point target) {
        return start.sameX(target) && target.greaterThanY(start);
    }

    private boolean blockedToBottom(Point start, Point target) {
        return start.sameX(target) && start.greaterThanY(target);
    }

    private int sumOfDistanceXFromBase(Point start, Point target, int base) {
        return Math.abs(start.x - base) + Math.abs(target.x - base);
    }

    private int sumOfDistanceYFromBase(Point start, Point target, int base) {
        return Math.abs(start.y - base) + Math.abs(target.y - base);
    }

    private int distanceX(Point start, Point target) {
        return Math.abs(start.x - target.x);
    }

    private int distanceY(Point start, Point target) {
        return Math.abs(start.y - target.y);
    }

    private int sumOfPowerEachDistance(int xDistance, int yDistance) {
        return (int) Math.pow(xDistance, 2) + (int) Math.pow(yDistance, 2);
    }

    class Point {
        int x;
        int y;
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        boolean sameX(Point other) {
            return x == other.x;
        }

        boolean sameY(Point other) {
            return y == other.y;
        }

        boolean greaterThanX(Point other) {
            return x > other.x;
        }

        boolean greaterThanY(Point other) {
            return y > other.y;
        }
    }
}

