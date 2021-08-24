package concurrent.lock

import java.util.concurrent.atomic.AtomicReference

// 使用AtomicReference实现自旋锁
class SpinLock {
    private val sign = AtomicReference<Thread>()

    fun lock() {
        val current = Thread.currentThread()
        while (!sign.compareAndSet(null, current)) {
            println("${Thread.currentThread().name} 自旋获取失败，再次尝试")
        }
    }

    fun unlock() {
        val current = Thread.currentThread()
        sign.compareAndSet(current, null)
    }

    object SpinLockTest {
        @JvmStatic
        fun main(args: Array<String>) {
            val spinLock = SpinLock()
            val runnable = Runnable {
                println("${Thread.currentThread().name} 开始尝试获取自旋锁")
                spinLock.lock()
                println("${Thread.currentThread().name} 获取到了自旋锁")
                try {
                    Thread.sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    spinLock.unlock()
                    println("${Thread.currentThread().name} 释放了自旋锁")
                }
            }
            Thread(runnable).start()
            Thread(runnable).start()
        }
    }
}