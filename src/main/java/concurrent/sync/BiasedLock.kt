package concurrent.sync

import org.openjdk.jol.info.ClassLayout

/**
 * 偏向锁
 * 向锁在Java 6之后是默认启用的，但在应用程序启动几秒钟之后才激活，
 * 可以使用 -XX:BiasedLockingStartupDelay=0 参数关闭延迟，
 * 如果确定应用程序中所有锁通常情况下处于竞争状态，可以通过 XX:-UseBiasedLocking=false 参数关闭偏向锁
 */
class BiasedLock {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MyThread().start()
        }
    }

    class MyThread : Thread() {
        private val lock = Object()
        override fun run() {
            for (i in 0 until 5) {
                synchronized(lock) {
                    println(ClassLayout.parseInstance(lock).toPrintable())
                }
            }
        }
    }
}