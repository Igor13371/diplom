package com.example.marketing

interface Navigable {
    fun navigateTo(id: Int, popToStart: Boolean = false)
}