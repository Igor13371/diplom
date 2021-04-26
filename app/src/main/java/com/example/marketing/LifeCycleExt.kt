package com.example.teacherhelper.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Looper
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.*

val Context.lifecycleOwner: LifecycleOwner?
    get() {
        return if (this is LifecycleOwner)
            this else null
    }

val Context.lifecycle: Lifecycle?
    get() = lifecycleOwner?.lifecycle

val Context.lifecycleCoroutineScope: LifecycleCoroutineScope?
    get() = lifecycle?.coroutineScope

val View.lifecycleOwner: LifecycleOwner?
    get() = context.lifecycleOwner

val View.lifecycle: Lifecycle?
    get() = lifecycleOwner?.lifecycle

val View.lifecycleCoroutineScope: LifecycleCoroutineScope?
    get() = lifecycle?.coroutineScope

val LifecycleOwner.isCreated: Boolean
    get() = lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)

val LifecycleOwner.isStarted: Boolean
    get() = lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)

val LifecycleOwner.isResumed: Boolean
    get() = lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)

fun LifecycleOwner.ensureIsCreated(block: () -> Unit) {
    if (isCreated) {
        block()
    } else lifecycleScope.launchWhenCreated {
        block()
    }
}

fun LifecycleOwner.ensureIsStarted(block: () -> Unit) {
    if (isStarted) {
        block()
    } else lifecycleScope.launchWhenStarted {
        block()
    }
}

fun LifecycleOwner.ensureIsResumed(block: () -> Unit) {
    if (isResumed) {
        block()
    } else lifecycleScope.launchWhenResumed {
        block()
    }
}

inline fun <reified VM: ViewModel> ViewModelStoreOwner.provideViewModel(
    factory: ViewModelProvider.Factory,
    key: String? = null
): VM {
    return ViewModelProvider(this, factory).let {
        if (key == null) {
            it.get(VM::class.java)
        } else {
            it.get(key, VM::class.java)
        }
    }
}

fun <T: Any> LiveData<T>.require(): T {
    return requireNotNull(value)
}

fun <F, S, R> LiveData<F>.mergeWith(
    liveData: LiveData<S>,
    mergeFun: (F?, S?) -> R
): LiveData<R> {
    val result = MediatorLiveData<R>()
    result.addSource(this) {
        result.value = mergeFun(this.value, liveData.value)
    }
    result.addSource(liveData) {
        result.value = mergeFun(this.value, liveData.value)
    }
    return result
}

fun <T> MutableLiveData<T>.sendValue(v: T?) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        value = v
    } else {
        postValue(v)
    }
}

inline fun <reified A: Application> Activity.findApplication(): A? {
    return if (application is A) application as A else null
}

inline fun <reified A: Application> Fragment.findApplication(): A? {
    return if (requireActivity().application is A) requireActivity().application as A else null
}

val Fragment.viewLifecycleScope: LifecycleCoroutineScope
    get() = viewLifecycleOwner.lifecycleScope