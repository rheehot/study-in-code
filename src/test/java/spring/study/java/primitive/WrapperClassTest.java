package spring.study.java.primitive;

import org.junit.Test;

@SuppressWarnings({ "UnnecessaryLocalVariable", "ConstantConditions" })
public class WrapperClassTest {
    @Test(expected = NullPointerException.class)
    public void nullFloatToPrimitiveFloat() {
        Float f = null;
        float f2 = f;
    }
}
