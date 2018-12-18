package com.example.android.pocketgymbro.util

import androidx.lifecycle.LiveData
import com.example.android.pocketgymbro.api.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData::class.java) {
            return null
        }

        val observableType = CallAdapter.Factory.getParameterUpperBound(0, returnType as ParameterizedType)
        require(observableType is ParameterizedType) { "resource must be parameterized" }

        val rawObservableType = CallAdapter.Factory.getRawType(observableType)
        require(rawObservableType == ApiResponse::class.java) { "type must be a resource"}

        val bodyType = CallAdapter.Factory.getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}