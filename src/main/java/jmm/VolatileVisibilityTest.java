package jmm;

public class VolatileVisibilityTest {
    //  此处是否添加volatile,来验证内存模型
//    private static boolean initFlag = false;
    private static volatile boolean initFlag = false;

    public static void main(String[] args) throws InterruptedException {
        // 线程1：检测initFlag的值，true则跳出循环
        new Thread(() -> {
            System.out.println("waiting data...");
            while (!initFlag) {

            }
            System.out.println("==============> success");
        }).start();

        Thread.sleep(3000); // 休眠一段时间，主要确保线程1先跑起来

        // 线程2：修改 initFlag = true
        new Thread(() -> {
            prepareData();
        }).start();
    }

    private static void prepareData() {
        System.out.println("data preparing...");
        initFlag = true; //此处为第30行代码
        System.out.println("initFlag = " + initFlag);
        System.out.println("data ready...");
    }
}