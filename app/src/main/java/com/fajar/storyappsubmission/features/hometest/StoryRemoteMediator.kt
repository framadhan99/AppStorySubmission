package com.fajar.storyappsubmission.features.hometest

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.fajar.storyappsubmission.core.data.resource.local.room.KeyEntity
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDatabase

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator (
    private val database: StoryDatabase,
    private val homeService: HomeService
): RemoteMediator<Int, StoryResponseItems>(){
    private companion object{
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryResponseItems>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosisiton(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }
        return try {
            Log.d("CekMediator", "ilang")
            val responseData = homeService.fetchStory(page, state.config.pageSize).listStory
            val endOfPaginationReached = responseData.isEmpty()

            database.withTransaction{
                if (loadType == LoadType.REFRESH){
                    database.KeysDao().deleteKeys()
                    database.StoryDao().deleteAll()
                }
                val prevKey = if(page==1 ) null else page -1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = responseData.map {
                    KeyEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                database.KeysDao().insertAll(keys)
                database.StoryDao().insertStory(responseData)
            }
            MediatorResult.Success(endOfPaginationReached)
        }catch (exception:Exception){
            Log.d("CekMediator", exception.toString())
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, StoryResponseItems>) : KeyEntity? {
        return state.pages.lastOrNull(){
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let {data ->
            database.KeysDao().getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, StoryResponseItems>) : KeyEntity?{
        return state.pages.firstOrNull{
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let {
            database.KeysDao().getRemoteKeysId(it.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosisiton(state : PagingState<Int, StoryResponseItems>): KeyEntity?{
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.id?.let { id->
                database.KeysDao().getRemoteKeysId(id)
            }
        }
    }
}