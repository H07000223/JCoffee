package jvm.classloader;

/**
 * 被动使用类字段演示一：
 * 通过子类引用父类的静态字段，不会导致子类初始化
 **/
public class NotInitialization {
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
        // 只会输出 SuperClass init！，而不会输出 SubClass init！
        System.out.println(SubClass.value);
    }
}