package code.spring.study.java.clazz;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class InterfaceTest {

    @Nested
    class FinalStaticMember {
        /**
         * Java 8 이후로 인터페이스 정의 시, 디폴트 메서드를 통해 기본 구현을 제공할 수 있습니다. 또한 디폴트 메서드에서 인터페이스
         * 내부에 선언된 static final 멤버 객체의 상태를 바꿀 수도 있습니다. 디폴트 메서드의 주된 용도는 인터페이스에 새로운 메서드를
         * 추가할 때 이전 인터페이스를 구현한 기존 클래스와의 하위 호환성 유지하는 것입니다. 디폴트 메서드가 없다면 인터페이스에 새로운
         * 메서드를 추가하면 기존에 해당 인터페이스를 구현한 클래스는 모두 컴파일 에러가 발생합니다. 인터페이스가 만약 같은 프로젝트에
         * 있지않고 라이브러리로서 외부에 공개되는 것이라면 문제가 될 수 있습니다.
         */
        @Test
        void interface_can_have_final_static_member() {
            SequentialAgeFactory factory = new SequentialAgeFactoryImpl();

            Person person1 = factory.newPerson();
            Person person2 = factory.newPerson();

            Assertions.assertThat(person1.age).isEqualTo(0);
            Assertions.assertThat(person2.age).isEqualTo(1);
            Assertions.assertThat(factory.getNextPersonAge()).isEqualTo(2);
        }

        interface SequentialAgeFactory {
            Person nextPerson = new Person(0, "");

            default int getNextPersonAge() {
                return nextPerson.age;
            }

            default Person newPerson() {
                return new Person(nextPerson.age++, nextPerson.name);
            }
        }

        class SequentialAgeFactoryImpl implements SequentialAgeFactory {
            @Override
            public Person newPerson() {
                Person newPerson = SequentialAgeFactory.super.newPerson();
                newPerson.name += newPerson.age;
                return newPerson;
            }
        }

        @AllArgsConstructor
        @NoArgsConstructor
        static class Person {
            int age;
            String name;
        }
    }
}
