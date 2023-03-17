package tech.java.clazz.inner;

class OuterClass {
    int x = 10;
    static int y = 20;

    // Inner Class
    class InnerClass {
        public int myInnerMethod() {
            return x + y; // can access both x and y
        }
    }

    // Static Inner Class
    static class StaticInnerClass {
        public int myStaticInnerMethod() {
            return y; // can only access y directly
            // return x; // compile error
        }
    }
}