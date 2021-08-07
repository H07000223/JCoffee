package jvm.classloader;

/**
 * 被动使用类字段演示二：
 * 通过数组定义来引用类，不会触发此类的初始化
 **/
public class NotInitialization2 {
    public static class SuperClass {
        static {
            System.out.println("SuperClass init!");
        }

        public static int value = 123;
    }

    public static class SubClass extends SuperClass {
        static {
            System.out.println("SubClass init!");
        }
    }

    public static void main(String[] args) {
        SuperClass[] sca = new SuperClass[10];
    }
}