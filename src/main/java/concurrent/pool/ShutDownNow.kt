package concurrent.pool

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ShutDownNow {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val pool = Executors.newFixedThreadPool(10)
            for (i in 0..100) {
                pool.execute(Task())
            }

            Thread.sleep(1500)
            val runnableList = pool.shutdownNow()
            println("runnableList --> ${runnableList.size}")
            println("awaitTermination --> ${pool.awaitTermination(100, TimeUnit.MILLISECONDS)}")
        }
    }

    class Task : Runnable {
        override fun run() {
            try {
                Thread.sleep(500)
                println(Thread.currentThread().name)
            } catch (e: InterruptedException) {
                println("${Thread.currentThread().name}被中断了~")
            }
        }
    }
}