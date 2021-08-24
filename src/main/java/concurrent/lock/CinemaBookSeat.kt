package concurrent.lock

import java.util.concurrent.locks.ReentrantLock

/**
 * 演示多线程预定电影院座位
 */
object CinemaBookSeat {
    private val lock = ReentrantLock()

    @JvmStatic
    fun main(args: Array<String>) {
        Thread { bookSeat() }.start()
        Thread { bookSeat() }.start()
        Thread { bookSeat() }.start()
        Thread { bookSeat() }.start()
    }

    private fun bookSeat() {
        lock.lock()
        try {
            println("${Thread.currentThread().name} 开始预定座位")
            Thread.sleep(1000)
            println("${Thread.currentThread().name} 完成预定座位")
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }
}