package com.example.teacherhelper.utils

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

suspend fun <T> ioContext(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.IO, block)
}

suspend fun <T> mainContext(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main, block)
}

suspend fun <T> immediateMainContext(block: suspend CoroutineScope.() -> T): T {
    return withContext(Dispatchers.Main.immediate, block)
}

suspend inline fun <T> T.write(crossinline block: (T) -> Unit) {
    return ioContext {
        block(this@write)
    }
}

suspend inline fun <T, R> T.read(crossinline block: (T) -> R?): R? {
    return ioContext {
        block(this@read)
    }
}

suspend inline fun <T, R> T.read(default: R, crossinline block: (T) -> R?): R {
    return read(block) ?: default
}

@FlowPreview
@ExperimentalCoroutinesApi
fun <T> Flow<T>.throttleFirst(duration: Long): Flow<T> {
    return flow {
        var lastEmissionTime = 0L
        collect { upstream ->
            val currentTime = System.currentTimeMillis()
            val mayEmit = currentTime - lastEmissionTime > duration
            if (mayEmit) {
                lastEmissionTime = currentTime
                emit(upstream)
            }
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun <T> ConflatedBroadcastChannel<T>.safeOffer(element: T) {
    if (!isClosedForSend) offer(element)
}