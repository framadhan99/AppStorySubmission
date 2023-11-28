package com.fajar.storyappsubmission.features.hometest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.fajar.storyappsubmission.core.data.resource.remote.home.HomeRepository
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponseItems
import com.fajar.storyappsubmission.features.ui.adapter.HomeAdapter
import com.fajar.storyappsubmission.features.ui.viewmodel.HomeVM
import com.fajar.storyappsubmission.utils.DataDummy
import com.fajar.storyappsubmission.utils.MainDispatcherRule
import com.fajar.storyappsubmission.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
//import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeVMTest{
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var homeRepository: HomeRepository

    @Test
    fun `when Get Quote Should Not Null and Return Data`() = runTest {
        val dummyStory = DataDummy.generateDummyQuoteResponse()
        val data: PagingData<StoryResponseItems> = QuotePagingSource.snapshot(dummyStory)
        val expectedStory = MutableLiveData<PagingData<StoryResponseItems>>()
        expectedStory.value = data
        Mockito.`when`(homeRepository.getStory()).thenReturn(expectedStory)

        val mainViewModel = HomeVM(homeRepository)
        val actualQuote: PagingData<StoryResponseItems> = mainViewModel.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
//        Assert.assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)
        Assert.assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)
        Assert.assertEquals(dummyStory[0].description, differ.snapshot()[0]?.description)
    }
    @Test
    fun `when Get Quote Empty Should Return No Data`() = runTest {
        val data: PagingData<StoryResponseItems> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<StoryResponseItems>>()
        expectedStory.value = data
        Mockito.`when`(homeRepository.getStory()).thenReturn(expectedStory)
        val mainViewModel = HomeVM(homeRepository)
        val actualQuote: PagingData<StoryResponseItems> = mainViewModel.story.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)
        Assert.assertEquals(0, differ.snapshot().size)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
class QuotePagingSource : PagingSource<Int, LiveData<List<StoryResponseItems>>>() {
    companion object {
        fun snapshot(items: List<StoryResponseItems>): PagingData<StoryResponseItems> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<StoryResponseItems>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<StoryResponseItems>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

