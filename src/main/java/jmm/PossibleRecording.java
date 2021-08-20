package jmm;

public class PossibleRecording {
    static int x, y = 0;
    static int a, b = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread one = new Thread(() -> {
            a = 1;
            x = b;
        });
        Thread two = new Thread(() -> {
            b = 1;
            y = a;
        });

        one.start();
        two.start();
        one.join();
        two.join();
        System.out.println("x = " + x + ", y = " + y);
    }
}
