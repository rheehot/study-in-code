package tech.java.closable;

import org.junit.jupiter.api.Test;

public class TryWithResourcesTest {

    @Test
    void notDeclaredAsVariable() throws Exception {
        try (Outer outer = new Outer(new Inner())) {
            // do something
        }
        // Result :
        // Outer.close()
    }

    @Test
    void declaredAsVariable() throws Exception {
        try (Inner inner = new Inner();
             Outer outer = new Outer(inner)) {
            // do something
        }
        // Result :
        // Outer.close()
        // Inner.close()
    }

    static class Outer implements AutoCloseable {
        Inner inner;

        Outer(Inner inner) {
            this.inner = inner;
        }

        @Override
        public void close() throws Exception {
            System.out.println("Outer.close()");
        }
    }

    static class Inner implements AutoCloseable {
        @Override
        public void close() throws Exception {
            System.out.println("Inner.close()");
        }
    }
}
