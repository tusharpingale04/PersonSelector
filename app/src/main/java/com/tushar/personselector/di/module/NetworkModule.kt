package com.tushar.personselector.di.module

import androidx.annotation.NonNull
import com.tushar.personselector.BuildConfig
import com.tushar.personselector.network.ProfilesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providePersonService(@NonNull retrofit: Retrofit): ProfilesApiService {
        return retrofit.create(ProfilesApiService::class.java)
    }

}