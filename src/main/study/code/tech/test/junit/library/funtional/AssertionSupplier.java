package code.tech.test.junit.library.funtional;

@FunctionalInterface
public interface AssertionSupplier {
    boolean get() throws Throwable;
}
