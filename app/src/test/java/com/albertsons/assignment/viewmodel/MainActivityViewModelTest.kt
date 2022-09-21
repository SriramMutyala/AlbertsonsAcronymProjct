package com.albertsons.assignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.albertsons.assignment.data.model.Dictionary
import com.albertsons.assignment.data.network.Result
import com.albertsons.assignment.data.repository.Repository
import com.albertsons.assignment.utils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var mainActivityViewModel: MainActivityViewModel

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var meaningsObserver: Observer<Result<List<Dictionary>>>

    @Before
    fun setUp() {
        mainActivityViewModel = MainActivityViewModel(repository, mainCoroutineRule.dispatcher)
    }

    @Test
    fun `get meanings success case`(){
        mainCoroutineRule.dispatcher.runBlockingTest {
            val meaningsResponse = mock(List::class.java)
            mainActivityViewModel.results().observeForever(meaningsObserver)
            `when`(repository.getMeanings(anyString())).thenAnswer {
                Result.success(meaningsResponse)
            }
            val response = repository.getMeanings(anyString())
            assertNotNull(response)
            assertNotNull(response.data)
            assertEquals(response.status, Result.Status.SUCCESS)
        }
    }

    @Test
    fun `get meanings failure case`(){
        mainCoroutineRule.dispatcher.runBlockingTest {
            mainActivityViewModel.results().observeForever(meaningsObserver)
            `when`(repository.getMeanings(anyString())).thenAnswer {
                Result.error("failed",null)
            }
            val response = repository.getMeanings(anyString())
            assertNotNull(response)
            assertNull(response.data)
            assertEquals(response.status, Result.Status.ERROR)
        }
    }

    @After
    fun tearDown() {
        mainActivityViewModel = MainActivityViewModel(repository,mainCoroutineRule.dispatcher)
    }
}