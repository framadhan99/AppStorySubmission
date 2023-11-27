package com.fajar.storyappsubmission.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.media.session.MediaSession.Token
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.room.Room
import com.fajar.storyappsubmission.core.data.resource.local.room.KeyDao
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDao
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDatabase
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager.S.USER_TOKEN_KEY
import com.fajar.storyappsubmission.core.data.resource.local.store.UserPreferences
import com.fajar.storyappsubmission.core.data.resource.local.store.UserPreferences.Companion.USER_TOKEN
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthServices
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryRepo
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryServices
import com.fajar.storyappsubmission.features.ui.viewmodel.HomeViewModel
import com.fajar.storyappsubmission.features.ui.viewmodel.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    private  val BaseURL = "https://story-api.dicoding.dev/v1/"
//    val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLTB3QlBWdzdYSGJSTmlHcTUiLCJpYXQiOjE3MDA3OTQ4NzF9.JuhELfEIsh-ldvhDNoXZblgHDqwmiL89Uok0JMf-fqY"
    @Provides
//    providesOkHttpClient
    fun provideOkHttpClient(dataStoreManager: DataStoreManager) :OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = runBlocking {
                    dataStoreManager.getToken().first()
                }
                Log.d("cekTokenInRetrofit", "$token")
                requestBuilder.addHeader("Authorization", "Bearer $token")
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun provideStoryDao(appDatabase: StoryDatabase): StoryDao {
        return appDatabase.StoryDao()
    }

    @Provides
    fun provideKeysDao(appDatabase: StoryDatabase): KeyDao {
        return appDatabase.KeysDao()
    }




    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = StoryDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthServices =
        retrofit.create(AuthServices::class.java)

    @Provides
    fun provideStoryService(retrofit: Retrofit): StoryServices =
        retrofit.create(StoryServices::class.java)

    @Provides
    fun provideHomeViewModel(retrofit: Retrofit): HomeViewModel =
        retrofit.create(HomeViewModel::class.java)



    @Provides
    fun provideStoryRepo(retrofit: Retrofit): StoryRepo =
        retrofit.create(StoryRepo::class.java)



    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

}

