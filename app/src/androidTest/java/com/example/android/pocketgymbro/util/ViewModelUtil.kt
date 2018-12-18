@file:Suppress("UNCHECKED_CAST")

package com.example.android.pocketgymbro.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.mockito.Mockito.mock

/**
 * Creates a view model factory for a given instance.
 */
object ViewModelUtil {
    fun <T : ViewModel> createFor(model: T): ViewModelProvider.Factory {
        return object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(model.javaClass)) {
                    return model as T
                }
                throw IllegalArgumentException("unexpected model class $modelClass")
            }

        }
    }

    fun <T : ViewModel> createMockFor(model: T): ViewModelProvider.Factory {
        return object: ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(model.javaClass)) {
                    return mock(modelClass)
                }
                throw IllegalArgumentException("unexpected model class $modelClass")
            }

        }
    }
}
