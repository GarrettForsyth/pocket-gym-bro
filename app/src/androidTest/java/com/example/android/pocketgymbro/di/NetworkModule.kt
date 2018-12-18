package com.example.android.pocketgymbro.di

import com.example.android.pocketgymbro.api.WorkoutManagerService
import com.example.android.pocketgymbro.testing.api.MockWorkoutManagerService
import com.example.android.pocketgymbro.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.mock.BehaviorDelegate
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module(includes = [NetworkModule::class])
class PerfectNetworkModule {

    @Singleton
    @Provides
    fun provideNetworkBehaviour(): NetworkBehavior {
        val behavior = NetworkBehavior.create()
        behavior.setDelay(0, TimeUnit.SECONDS)
        behavior.setErrorPercent(0)
        behavior.setFailurePercent(0)
        behavior.setVariancePercent(0)
        return behavior
    }
}

@Module(includes = [NetworkModule::class])
class NetworkFailureModule {
    @Singleton
    @Provides
    fun provideNetworkBehaviour(): NetworkBehavior {
        val behavior = NetworkBehavior.create()
        behavior.setDelay(0, TimeUnit.SECONDS)
        behavior.setErrorPercent(0)
        behavior.setFailurePercent(100)
        behavior.setVariancePercent(0)
        return behavior
    }
}

@Module(includes = [NetworkModule::class])
class NetworkErrorModule {
    @Singleton
    @Provides
    fun provideNetworkBehaviour(): NetworkBehavior {
        val behavior = NetworkBehavior.create()
        behavior.setDelay(0, TimeUnit.SECONDS)
        behavior.setErrorPercent(100)
        behavior.setFailurePercent(0)
        behavior.setVariancePercent(0)
        return behavior
    }
}

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://wger.de/api/v2/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideMockRetrofit(
        retrofit: Retrofit,
        behavior: NetworkBehavior
    ): MockRetrofit {
        return MockRetrofit.Builder(retrofit)
            .networkBehavior(behavior)
            .build()
    }

    @Singleton
    @Provides
    fun provideBehaviourDelegate(
        mockRetrofit: MockRetrofit
    ): BehaviorDelegate<WorkoutManagerService> {
        return mockRetrofit.create(WorkoutManagerService::class.java)
    }

    @Singleton
    @Provides
    fun provideWorkoutManagerService(
        delegate: BehaviorDelegate<WorkoutManagerService>
    ): WorkoutManagerService {
        return MockWorkoutManagerService(delegate)
    }
}
