package concurrent.sync

import org.openjdk.jol.info.ClassLayout

/**
 * 在64位系统中，Mark Word = 8 bytes，类型指针 = 8 bytes，对象头 = 16 bytes
 * idea默认开启指针压缩-XX:+UseCompressedOops，需要在配置设置关闭 -XX:-UseCompressedOops
 */
class JolDemo {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val obj = LockObj()

            // parseInstance 解析实例对象，toPrintable进行打印其解析的实例对象信息
            println(ClassLayout.parseInstance(obj).toPrintable())
        }
    }

    class LockObj {
        private val x = 0
        private val b = false
    }
}