package test.junit.library.funtional;

@FunctionalInterface
public interface ThrowingBooleanSupplier {
    boolean get() throws Throwable;
}