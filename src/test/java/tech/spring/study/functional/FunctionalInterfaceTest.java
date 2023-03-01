package tech.spring.study.functional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class FunctionalInterfaceTest {
    @Nested
    class Parameterize{
        Person person = new Person();
        String name = "JooSing";

        @Test
        void lambdaTest() {
            Assertions.assertEquals("Hello! JooSing", person.hello(() -> name));
        }

        @Test
        void anonymousObjectTest() {
            Assertions.assertEquals("Hello! JooSing", person.hello(new Introduce() {
                @Override
                public String apply() {
                    return name;
                }
            }));
        }

        @Test
        void objectTest() {
            Assertions.assertEquals("Hello! JooSing", person.hello(new IntroduceMyself()));
        }

        @Test
        void functionReferenceTest() {
            IntroduceMyself introduceMyself = new IntroduceMyself();
            Assertions.assertEquals("Hello! JooSing", person.hello(introduceMyself::apply));
        }
    }
}
