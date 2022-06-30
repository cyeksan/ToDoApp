package com.csappgenerator.todoapp.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
    object None : OrderType()
}