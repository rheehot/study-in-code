package code.tech.spring.study.java.primitive;

import org.junit.Test;

@SuppressWarnings({ "UnnecessaryLocalVariable", "ConstantConditions" })
public class WrapperClassTest {
    @Test(expected = NullPointerException.class)
    public void assignNullValueBoolean() {
        Boolean flag = null;
        boolean flag2 = flag;
    }
}
