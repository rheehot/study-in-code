package code.tech.netty.message;

import code.tech.netty.handler.LogSource;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class User implements LogSource {
    private final String name;
    private final int age;

    @Override
    public String toLog() {
        return "name = %s, age = %d".formatted(name, age);
    }
}
