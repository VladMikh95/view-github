package ml.vladmikh.projects.view_github.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import ml.vladmikh.projects.view_github.data.network.ApiService
import ml.vladmikh.projects.view_github.data.repository.UserInfoRepository
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesBaseUrl(): String = "https://api.github.com/"

    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit {

        val contentType = "application/json".toMediaType()
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideUserInfoRepository( mainService: ApiService): UserInfoRepository =UserInfoRepository(mainService)

}