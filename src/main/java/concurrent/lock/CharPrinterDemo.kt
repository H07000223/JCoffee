package concurrent.lock

import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * 演示ReentrantLock的基本用法，演示被打断
 */
class CharPrinterDemo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            CharPrinterDemo().init()
        }
    }

    private fun init() {
        val charPrinter = CharPrinter()
        Thread {
            while (true) {
                try {
                    Thread.sleep(5)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                charPrinter.output("悟空")
            }
        }.start()

        Thread {
            while (true) {
                try {
                    Thread.sleep(5)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                charPrinter.output("大师兄")
            }
        }.start()
    }

    internal class CharPrinter {
        var lock: Lock = ReentrantLock()

        // 字符串打印方法，一个个字符的打印
        fun output(name: String) {
            val len = name.length
            lock.lock()
            try {
                for (i in 0 until len) {
                    print(name[i])
                }
                println("")
            } finally {
                lock.unlock()
            }
        }
    }
}