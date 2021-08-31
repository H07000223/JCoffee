package concurrent.sync;

public class SynchronizedDemo {
    public void codeBlock() {
        synchronized (this) {
            System.out.println("Synchronized Block");
        }
    }

    public synchronized void method() {
        System.out.println("Synchronized Method");
    }

    public synchronized static void staticMethod() {
        System.out.println("Synchronized Static Method");
    }
}