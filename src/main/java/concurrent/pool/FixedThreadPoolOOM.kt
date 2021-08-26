package concurrent.pool

import java.util.concurrent.Executors

class FixedThreadPoolOOM {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val executorService = Executors.newFixedThreadPool(1)
            for (i in 0..Int.MAX_VALUE) {
                executorService.execute(Task())
            }
        }
    }

    private class Task : Runnable {
        override fun run() {
            try {
                Thread.sleep(10_0000_0000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            println(Thread.currentThread().name)
        }
    }
}