package tech.java.annotation;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AnnotationTest {
    @Test
    void getAnnotation() {
        MyAnnotation annotation = MyClass.class.getAnnotation(MyAnnotation.class);
        assertEquals("Hello", annotation.value());
    }

    @Test
    void getAnnotations() {
        MyClass obj = new MyClass();
        Annotation[] annotations = obj.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation instanceof MyAnnotation) {
                assertEquals("Hello", ((MyAnnotation) annotation).value());
                return;
            }
        }
        fail();
    }
}
