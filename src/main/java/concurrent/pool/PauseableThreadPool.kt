package concurrent.pool

import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock

/**
 * 演示每个任务执行前后放钩子函数
 */
class PauseableThreadPool : ThreadPoolExecutor {
    private val lock = ReentrantLock()
    private val unPaused = lock.newCondition()
    private var isPaused = false

    constructor(corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long,
                unit: TimeUnit, workQueue: BlockingQueue<Runnable>)
            : super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue)

    override fun beforeExecute(t: Thread?, r: Runnable?) {
        super.beforeExecute(t, r)
        lock.lock()
        try {
            while (isPaused) {
                unPaused.await()
            }
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } finally {
            lock.unlock()
        }
    }

    fun pause() {
        lock.lock()
        try {
            isPaused = true
        } finally {
            lock.unlock()
        }
    }

    fun resume() {
        lock.lock()
        try {
            isPaused = false
            unPaused.signalAll()
        } finally {
            lock.unlock()
        }
    }

    class Task : Runnable {
        override fun run() {
            try {
                Thread.sleep(10)
                println("${Thread.currentThread().name}被执行")
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val pool = PauseableThreadPool(
                    10, 10,
                    60L, TimeUnit.SECONDS,
                    LinkedBlockingQueue()
            )
            for (i in 0..9999) {
                pool.execute(Task())
            }

            Thread.sleep(1500)
            pool.pause()
            println("线程池被暂停了")

            Thread.sleep(3000)
            pool.resume()
            println("线程池被恢复了")
        }
    }
}