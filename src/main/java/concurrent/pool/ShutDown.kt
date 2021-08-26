package concurrent.pool

import java.util.concurrent.Executors

class ShutDown {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val pool = Executors.newFixedThreadPool(10)
//            for (i in 0..999) {
            for (i in 0..100) {
                pool.execute(Task())
            }

            Thread.sleep(1500)
            println("isShutdown --> ${pool.isShutdown}")
            pool.shutdown()
            println("isShutdown --> ${pool.isShutdown}")
            println("isTerminated --> ${pool.isTerminated}")
//            pool.execute(Task())

            // 将task缩减至100，延时10秒，查看线程池是否完全终止
            Thread.sleep(10_000)
            println("isTerminated --> ${pool.isTerminated}")
//            println("awaitTermination --> ${pool.awaitTermination(10, TimeUnit.SECONDS)}")
        }
    }

    class Task : Runnable {
        override fun run() {
            try {
                Thread.sleep(500)
                println(Thread.currentThread().name)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}