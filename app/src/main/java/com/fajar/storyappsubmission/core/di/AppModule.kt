package com.fajar.storyappsubmission.core.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import com.fajar.storyappsubmission.core.data.resource.local.room.KeyDao
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDao
import com.fajar.storyappsubmission.core.data.resource.local.room.StoryDatabase
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager
import com.fajar.storyappsubmission.core.data.resource.local.store.DataStoreManager.Companion.USER_TOKEN_KEY
import com.fajar.storyappsubmission.core.data.resource.remote.auth.AuthServices
import com.fajar.storyappsubmission.core.data.resource.remote.maps.MapsService
import com.fajar.storyappsubmission.core.data.resource.remote.story.StoryServices
import com.fajar.storyappsubmission.features.hometest.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
//    providesOkHttpClient
    fun providesOkHttpClient(sharedPreferences: SharedPreferences): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                val token = sharedPreferences.getString(USER_TOKEN_KEY.toString(),"")
                Log.d("cekTokenInRetrofit", "$token")
                if (!token.isNullOrEmpty()){
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(" https://story-api.dicoding.dev/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Provides
    fun provideStoryDao(appDatabase: StoryDatabase): StoryDao {
        return appDatabase.storyDao()
    }

    @Provides
    fun provideKeysDao(appDatabase: StoryDatabase): KeyDao {
        return appDatabase.KeysDao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = StoryDatabase.getDatabase(appContext)


    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthServices =
        retrofit.create(AuthServices::class.java)

    @Provides
    fun provideStoryService(retrofit: Retrofit): StoryServices =
        retrofit.create(StoryServices::class.java)

    @Provides
    fun provideHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    @Provides
    fun provideMapsService(retrofit: Retrofit): MapsService =
        retrofit.create(MapsService::class.java)

    @Provides
    @Singleton
    fun dataStoreManager(@ApplicationContext appContext: Context): DataStoreManager =
        DataStoreManager(appContext)

}

@Module
@InstallIn(SingletonComponent::class)
object LocalModule{
    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("userPreferences", Context.MODE_PRIVATE)
    }

}