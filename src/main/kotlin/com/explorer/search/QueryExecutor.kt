package com.explorer.search

interface QueryExecutor<Result, Params> {
    fun execute(p: Params, consumer: (Result) -> Boolean)
}