package com.example.android.pocketgymbro.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.pocketgymbro.util.LiveDataCallAdapterFactory
import com.example.android.pocketgymbro.util.getValueBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class WorkoutManagerServiceTest {

    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()

    private lateinit var service: WorkoutManagerService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
       mockWebServer = MockWebServer()

        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(WorkoutManagerService::class.java)

    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun get_exercise_search_suggestions() {
        enqueueResponse("search.json")
        val response = service.fetchExerciseSearchSuggestionByName("test search").getValueBlocking() as ApiSuccessResponse

        val numberOfExerciseSuggestionsInFakeFile = 16
        assertThat(response.body.suggestions.size, equalTo(numberOfExerciseSuggestionsInFakeFile))
    }


    private fun enqueueResponse(fileName: String) {
        @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        val inputStream = javaClass.classLoader
            .getResourceAsStream("api-response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockRepsonse = MockResponse()
        mockWebServer.enqueue(mockRepsonse
            .setBody(source.readString(Charsets.UTF_8))
        )
    }

}