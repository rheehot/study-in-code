package code.spring.study.junit;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class MethodOrderTest {

    @Test
    @Order(3)
    public void test1() {
        System.out.println("test1");
    }

    @Test
    @Order(1)
    public void test2() {
        System.out.println("test2");
    }

    @Test
    @Order(2)
    public void test3() {
        System.out.println("test3");
    }
}
