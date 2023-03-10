package tech.spring.study.spring.mock;

import org.springframework.stereotype.Component;

@Component
public class SampleBean {
    public String hello() {
        return "hello";
    }
}
