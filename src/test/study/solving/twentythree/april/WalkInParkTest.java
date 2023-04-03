package solving.twentythree.april;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WalkInParkTest {
    static Stream<Arguments> testCaseSupplier() {
        return Stream.of(
                Arguments.arguments(new String[]{"SOO", "OOO", "OOO"}, new String[]{"E 2", "S 2", "W 1"}, new int[]{2, 1}),
                Arguments.arguments(new String[]{"SOO", "OXX", "OOO"}, new String[]{"E 2", "S 2", "W 1"}, new int[]{0, 1}),
                Arguments.arguments(new String[]{"OSO", "OOO", "OXO", "OOO"}, new String[]{"E 2", "S 3", "W 1"}, new int[]{0, 0}));
    }

    @ParameterizedTest
    @MethodSource("testCaseSupplier")
    void testSolution(String[] park, String[] routes, int[] expected) {
        int[] result = solution(park, routes);
        Assertions.assertArrayEquals(expected, result);
    }

    public int[] solution(String[] park, String[] routes) {
        // 시작 지점
        Point startPoint = startPoint(park);

        // 움직일 지점
        AtomicReference<Point> movingPoint = new AtomicReference<>(startPoint);
        Arrays.stream(routes)
                // 타입 전환
                .map(route -> Route.of(route))
                // 각 라우팅 명령에 대해
                .forEach(route -> {
                    // 범위 이내 & 열린 경로
                    if (!canRoute(park, movingPoint.get(), route)) {
                        return;
                    }
                    // 이동
                    Point nextPoint = Point.move(movingPoint.get(), route);
                    // 이동 결과 저장
                    movingPoint.set(nextPoint);
                });
        return movingPoint.get().toArray();
    }

    // park에서 "S" 문자의 위치를 찾는다.
    private Point startPoint(String[] park) {
        int startY = IntStream.range(0,  park.length)
                .filter(i -> park[i].contains("S"))
                .findFirst()
                .orElse(-1);
        int startX = park[startY].indexOf('S');
        return Point.of(startX, startY);
    }

    // 현재 위치에서 라우팅 경로가 열려있고, 공원을 넘어가지 않습니다.
    private boolean canRoute(String[] park, Point current, Route route) {
        int height = park.length;
        int width = park[0].length();
        if (!inLimit(current, route, width, height)) {
            return false;
        }
        return opened(park, current, route);
    }

    // 현재 위치에서 라우팅 시 공원을 넘어가지 않습니다.
    private boolean inLimit(Point current, Route route, int width, int height) {
        return switch (route.direct) {
            // 동
            case "E" -> current.x + route.distance < width;
            // 서
            case "W" -> current.x - route.distance >= 0;
            // 남
            case "S" -> current.y + route.distance < height;
            // 북
            case "N" -> current.y - route.distance >= 0;
            // 예외
            default -> throw new IllegalArgumentException();
        };
    }

    // 현재 위치에서 라우팅을 위한 경로가 열려있습니다.
    private boolean opened(String[] park, Point current, Route route) {
        return switch (route.direct) {
            // 동
            case "E" -> openPath(current.x + 1, current.x + route.distance, i -> park[current.y].charAt(i));
            // 서
            case "W" -> openPath(current.x - route.distance, current.x - 1, i -> park[current.y].charAt(i));
            // 남
            case "S" -> openPath(current.y + 1, current.y + route.distance, i -> park[i].charAt(current.x));
            // 북
            case "N" -> openPath(current.y - route.distance, current.y - 1, i -> park[i].charAt(current.x));
            // 예외
            default -> throw new IllegalArgumentException();
        };
    }

    private boolean openPath(int startInclusive, int endInclusive, Function<Integer, Character> eachPointStatus) {
        return IntStream.rangeClosed(startInclusive, endInclusive)
                .filter(i -> eachPointStatus.apply(i) == 'X')
                .findFirst()
                .isEmpty();
    }

    static final class Point {
        int x;
        int y;

        static Point of(int x, int y){
            Point point = new Point();
            point.x = x;
            point.y = y;
            return point;
        }

        static Point move(Point prev, Route route) {
            return switch (route.direct) {
                case "E" -> of(prev.x + route.distance, prev.y);
                case "W" -> of(prev.x - route.distance, prev.y);
                case "S" -> of(prev.x, prev.y + route.distance);
                case "N" -> of(prev.x, prev.y - route.distance);
                default -> throw new IllegalArgumentException();
            };
        }

        int[] toArray() {
            return new int[]{y, x};
        }
    }

    static class Route {
        String direct;
        int distance;

        static Route of(String routeStr) {
            Route route = new Route();
            String[] tokens = routeStr.split(" ");
            route.direct = tokens[0]; // 방향
            route.distance = Integer.parseInt(tokens[1]); // 거리
            return route;
        }
    }

    @Test
    void testInLimit() {
        assertTrue(inLimit(Point.of(1, 1), Route.of("E 1"), 3, 2)); // 동쪽 범위 이내
        assertFalse(inLimit(Point.of(1, 1), Route.of("E 2"), 3, 2)); // 동쪽 범위 초과

        assertTrue(inLimit(Point.of(1, 1), Route.of("W 1"), 3, 2)); // 서쪽 범위 이내
        assertFalse(inLimit(Point.of(1, 1), Route.of("W 2"), 3, 2)); // 서쪽 범위 초과

        assertTrue(inLimit(Point.of(1, 1), Route.of("S 2"), 3, 4)); // 남쪽 범위 이내
        assertFalse(inLimit(Point.of(1, 1), Route.of("S 3"), 3, 4)); // 남쪽 범위 초과

        assertTrue(inLimit(Point.of(1, 2), Route.of("N 2"), 3, 4)); // 북쪽 범위 이내
        assertFalse(inLimit(Point.of(1, 2), Route.of("N 3"), 3, 4)); // 북쪽 범위 초과
    }

    @Test
    void testOpened() {
        String[] park;

        park = Stream.of("XXX", "XSO", "XXX").toArray(String[]::new);
        assertTrue(opened(park, Point.of(1,1), Route.of("E 1"))); // 동쪽 방향 열림
        park = Stream.of("XXX", "XSX", "XXX").toArray(String[]::new);
        assertFalse(opened(park, Point.of(1,1), Route.of("E 1"))); // 동쪽 방향 닫힘

        park = Stream.of("XXX", "XXX", "OSX").toArray(String[]::new);
        assertTrue(opened(park, Point.of(1,2), Route.of("W 1"))); // 서쪽 방향 열림
        park = Stream.of("XXX", "XXX", "XSX").toArray(String[]::new);
        assertFalse(opened(park, Point.of(1,2), Route.of("W 1"))); // 서쪽 방향 닫힘

        park = Stream.of("XXX", "XSX", "XOX").toArray(String[]::new);
        assertTrue(opened(park, Point.of(1,1), Route.of("S 1"))); // 남쪽 방향 열림
        park = Stream.of("XXX", "XSX", "XXX").toArray(String[]::new);
        assertFalse(opened(park, Point.of(1,1), Route.of("S 1"))); // 남쪽 방향 닫힘

        park = Stream.of("XXX", "OXX", "SXX").toArray(String[]::new);
        assertTrue(opened(park, Point.of(0,2), Route.of("N 1"))); // 북쪽 방향 열림
        park = Stream.of("XXX", "XXX", "SXX").toArray(String[]::new);
        assertFalse(opened(park, Point.of(0,2), Route.of("N 1"))); // 북쪽 방향 닫힘
    }
}
