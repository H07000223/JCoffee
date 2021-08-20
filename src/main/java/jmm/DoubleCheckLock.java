package jmm;

public class DoubleCheckLock {
    //    private static DoubleCheckLock instance;
    // 禁止指令重排优化
    private volatile static DoubleCheckLock instance;

    private DoubleCheckLock() {
    }

    public static DoubleCheckLock getInstance() {
        // 第一次检测
        if (instance == null) {
            synchronized (DoubleCheckLock.class) { // 同步
                if (instance == null) {
                    // 多线程环境下可能会出现问题的地方
                    instance = new DoubleCheckLock();
                }
            }
        }
        return instance;
    }
}
