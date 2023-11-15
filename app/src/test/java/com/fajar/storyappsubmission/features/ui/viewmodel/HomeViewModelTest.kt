package com.fajar.storyappsubmission.features.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.fajar.storyappsubmission.core.data.model.Story
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRepo
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryResponse
import com.fajar.storyappsubmission.core.data.resource.remote.story.StorySource
import com.fajar.storyappsubmission.features.ui.adapter.HomeStoryAdapter
import com.fajar.storyappsubmission.utils.DataDummy
import com.fajar.storyappsubmission.utils.MainDispatcherRule
import com.fajar.storyappsubmission.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest{

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository: StoryRepo
    private lateinit var dataStoreManager: DataStoreManager
    private var dummyStory = DataDummy.generateDummyQuoteResponse()

    private lateinit var homeRepository: StorySource

    @Test
    fun `when Get Quote Should Not Null and Return Data`() = runTest {
        val dummyStory = DataDummy.generateDummyQuoteResponse()
        val data: PagingData<Story> = QuotePagingSource.snapshot(dummyStory.listStory)
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = data
        `when`(homeRepository.loadData("")).thenReturn(expectedStory.asFlow())

        val mainViewModel = HomeViewModel(storyRepository, dataStoreManager)
        val actualQuote: PagingData<Story> = mainViewModel.pagingStory("").getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)
        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory.listStory.size, differ.snapshot().size)
//        Assert.assertEquals(dummyStory[0].id, differ.snapshot()[0]?.id)
        Assert.assertEquals(dummyStory.listStory[0].name, differ.snapshot()[0]?.name)
        Assert.assertEquals(dummyStory.listStory[0].description, differ.snapshot()[0]?.description)
    }
    @Test
    fun `when Get Quote Empty Should Return No Data`() = runTest {
        val data: PagingData<Story> = PagingData.from(emptyList())
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = data
        `when`(homeRepository.loadData("")).thenReturn(expectedStory.asFlow())
        val mainViewModel = HomeViewModel(storyRepository, dataStoreManager)
        val actualQuote: PagingData<Story> = mainViewModel.pagingStory("").getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = HomeStoryAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)
        Assert.assertEquals(0, differ.snapshot().size)
    }
}



class QuotePagingSource : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}




