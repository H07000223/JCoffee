import java.util.*
import java.util.concurrent.LinkedBlockingQueue

// 生产者消费者：BlockingQueue
object BlockingQueueDemo {
    @JvmStatic
    fun main(args: Array<String>) {
        val eventStorage = EventStorage()
        val producer = Producer(eventStorage)
        val consumer = Consumer(eventStorage)
        Thread(producer).start()
        Thread(consumer).start()
    }

    // 生产者
    private class Producer(private val storage: EventStorage) : Runnable {
        override fun run() {
            for (i in 0..66) {
                storage.put()
            }
        }
    }

    // 消费者
    private class Consumer(private val storage: EventStorage) : Runnable {
        override fun run() {
            for (i in 0..66) {
                storage.take()
            }
        }
    }

    private class EventStorage {
        private val random = Random()
        private val queue = LinkedBlockingQueue<Int>(10)
        fun put() {
            queue.put(random.nextInt(100))
            println("【生产者】生产中，仓库里有 ${queue.size} 个产品。")
            Thread.sleep(random.nextInt(100).toLong())
        }

        fun take() {
            val item = queue.take()
            println("【消费者】消费了【产品-$item】，现在仓库还剩下 ${queue.size}")
            Thread.sleep(random.nextInt(100).toLong())
        }
    }
}
