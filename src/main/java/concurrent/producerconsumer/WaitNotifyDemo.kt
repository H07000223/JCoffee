package concurrent.producerconsumer

import java.util.*

object WaitNotifyDemo {
    @JvmStatic
    fun main(args: Array<String>) {
        val eventStorage = EventStorage()
        val producer = Producer(eventStorage)
        val consumer = Consumer(eventStorage)
        Thread(producer).start()
        Thread(consumer).start()
    }
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
    private val maxSize = 10
    private val storage = LinkedList<Int>()
    private val random = Random()
    private val lock = Object()

    fun put() {
        synchronized(lock) {
            while (storage.size == maxSize) { // 库空最大库存
                try {
                    println("【生产者】仓库已满，待销费 ==========")
                    lock.wait()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            storage.add(random.nextInt(100))
            println("【生产者】生产中，仓库里有 ${storage.size} 个产品。")
            lock.notify()
            Thread.sleep(100)
        }
    }

    fun take() {
        synchronized(lock) {
            while (storage.size == 0) { // 库空没有库存
                try {
                    println("【消费者】库存已空，待生产 ==========")
                    lock.wait()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            println("【消费者】消费了【产品-${storage.poll()}】，现在仓库还剩下 ${storage.size}")
            lock.notify()
        }
    }
}