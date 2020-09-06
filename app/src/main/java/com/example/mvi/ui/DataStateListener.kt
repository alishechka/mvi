package com.example.mvi.ui

import com.example.mvi.util.DataState

interface DataStateListener {
    fun onDataStateChanged(dataState: DataState<*>?)
}