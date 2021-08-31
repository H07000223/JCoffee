package jmm

import kotlin.Throws
import java.lang.InterruptedException
import kotlin.jvm.JvmStatic
import java.lang.Runnable
import jmm.VolatileVisibilityTest

object VolatileVisibilityTest {
    // 此处是否添加volatile，来验证内存模型
    @Volatile
    private var initFlag = false

    @Throws(InterruptedException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        // 线程1：检测initFlag的值，true则跳出循环
        Thread {
            println("waiting data...")
            while (!initFlag) {
            }
            println("==============> success")
        }.start()
        Thread.sleep(3000) // 休眠一段时间，主要确保线程1先跑起来

        // 线程2：修改 initFlag = true
        Thread { prepareData() }.start()
    }

    private fun prepareData() {
        println("data preparing...")
        initFlag = true
        println("initFlag = $initFlag")
        println("data ready...")
    }
}