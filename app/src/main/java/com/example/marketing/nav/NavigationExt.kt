package com.example.marketing.nav

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController

val NavDestination.title: String?
    get() = label?.toString()

fun NavController.popToStart(inclusive: Boolean = false) {
    popBackStack(graph.startDestination, inclusive)
}

fun NavController.navigateDirected(
    actionIds: List<Int>,
    destinationExtras: Bundle? = null
) {
    popToStart()
    actionIds.forEachIndexed { index, id ->
        when (index) {
            actionIds.lastIndex ->
                navigate(id, destinationExtras)
            else -> navigate(id)
        }
    }
}

val Fragment.currentDestination: NavDestination?
    get() = findNavController().currentDestination

fun Fragment.findNavController(@IdRes currDestId: Int): NavController? {
    return if (currentDestination?.id == currDestId) findNavController() else null
}

fun <T> Fragment.setNavigationResult(key: String, result: T) {
    findNavController()
        .previousBackStackEntry
        ?.savedStateHandle
        ?.set(key, result)
}

fun <T> Fragment.getNavigationResult(key: String): LiveData<T>? {
    return findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.let { handle ->
            when {
                handle.contains(key) -> handle.getLiveData(key)
                else -> null
            }
        }
}

fun <T> Fragment.removeNavigationResult(key: String): T? {
    return findNavController()
        .currentBackStackEntry
        ?.savedStateHandle
        ?.remove<T>(key)
}