package tech.spring.study.serialize;

import lombok.*;

@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Car {
    private String name;
    private int age;
    private double velocity;
}
