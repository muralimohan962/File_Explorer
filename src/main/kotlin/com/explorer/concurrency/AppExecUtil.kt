package com.explorer.concurrency

import java.util.concurrent.Executors

object AppExecUtil {
    private val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    fun getExecutorService() = threadPool
}