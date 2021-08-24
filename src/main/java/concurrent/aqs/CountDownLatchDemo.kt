package concurrent.aqs

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * 工厂中，质检，5个工人检查，所有人都认为通过，才通过
 */
object CountDownLatchDemo1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val latch = CountDownLatch(5)
        val service = Executors.newFixedThreadPool(5)
        for (i in 0..4) {
            val num = i + 1
            service.submit {
                try {
                    Thread.sleep((Math.random() * 10000).toLong())
                    println("No.${num}完成了检查。")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    latch.countDown()
                }
            }
        }
        println("等待5个人检查完.....")
        latch.await()
        println("所有人都完成了工作，进入下一个环节。")
    }
}

/**
 * 模拟100米跑步，5名选手都准备好了，只等裁判员一声令下，所有人同时开始跑步。
 */
object CountDownLatchDemo2 {
    @JvmStatic
    fun main(args: Array<String>) {
        val latch = CountDownLatch(1)
        val service = Executors.newFixedThreadPool(5)
        for (i in 0..4) {
            val num = i + 1
            service.submit {
                try {
                    println("No.${num}准备完毕，等待发令枪")
                    latch.await()
                    println("No.${num}开始跑步了")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
        // 裁判员倒计时准备发令枪...
        Thread.sleep(5000)
        println("发令枪响，比赛开始！")
        latch.countDown()
    }
}

/**
 * 模拟100米跑步，5名选手都准备好了，只等裁判员一声令下，所有人同时开始跑步。当所有人都到终点后，比赛结束。
 */
object CountDownLatchDemo3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val beginLatch = CountDownLatch(1)
        val endLatch = CountDownLatch(5)
        val service = Executors.newFixedThreadPool(5)
        for (i in 0..4) {
            val num = i + 1
            service.submit {
                try {
                    println("No.${num}准备完毕，等待发令枪")
                    beginLatch.await()
                    println("No.${num}开始跑步了")
                    Thread.sleep((Math.random() * 10000).toLong())
                    println("No.${num}跑到终点了")
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    endLatch.countDown()
                }
            }
        }
        // 裁判员倒计时准备发令枪...
        Thread.sleep(5000)
        println("发令枪响，比赛开始！")
        beginLatch.countDown()

        endLatch.await()
        println("所有人到达终点，比赛结束")
    }
}