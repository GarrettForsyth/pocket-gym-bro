package com.example.android.pocketgymbro.util

import com.example.android.pocketgymbro.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}

