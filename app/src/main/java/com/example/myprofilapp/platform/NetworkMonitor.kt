package com.example.myprofilapp.platform

import kotlinx.coroutines.flow.StateFlow

interface NetworkMonitor {
    val isOnline: StateFlow<Boolean>
}
