package code.tech.spring.study.junit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class PrototypeBean {
    public PrototypeBean() {
        Statistics.prototypeCount++;
    }
}