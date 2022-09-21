package com.albertsons.assignment.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.albertsons.assignment.data.network.Api
import com.albertsons.assignment.data.network.Result
import com.albertsons.assignment.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var api: Api


    private lateinit var repository: Repository


    @Before
    fun setUp() {
        repository = Repository(api)
    }

    @Test
    fun `when fetch meanings and status success`() {
        mainCoroutineRule.dispatcher.runBlockingTest {
            val mockResponseBody = mock(List::class.java)
            val mockResponse = Response.success(mockResponseBody)

            `when`(api.getMeanings(anyString())).thenAnswer {
                mockResponse
            }
            val response = repository.getMeanings(anyString())
            assertNotNull(response)
            assertNotNull(response.data)
            assertEquals(response.status, Result.Status.SUCCESS)
        }
    }

    @Test
    fun `when fetch meanings and status failure`() {
        mainCoroutineRule.dispatcher.runBlockingTest {
            val errorResponse =
                "{\n" +
                        "  \"type\": \"error\",\n" +
                        "  \"message\": \"something went wrong.\"\n}"
            val errorResponseBody =
                errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
            val mockResponse = Response.error<String>(400, errorResponseBody)
            `when`(api.getMeanings(anyString())).thenAnswer {
                mockResponse
            }
            val response = repository.getMeanings(anyString())
            assertNotNull(response)
            assertNull(response.data)
            assertEquals(response.status, Result.Status.ERROR)
        }
    }

    @After
    fun tearDown() {
    }
}