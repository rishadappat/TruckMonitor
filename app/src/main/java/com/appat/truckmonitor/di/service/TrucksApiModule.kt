package com.appat.truckmonitor.di.service

import com.appat.truckmonitor.data.api.TrucksApi
import com.appat.truckmonitor.data.repository.TrucksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrucksApiModule {
    @Singleton
    @Provides
    fun provideTrucksApi(retrofit: Retrofit): TrucksApi {
        return retrofit.create(TrucksApi::class.java)
    }

    @Singleton
    @Provides
    fun provideContactsRepository(apiService: TrucksApi,
                                  defaultDispatcher: CoroutineDispatcher
    ): TrucksRepository {
        return TrucksRepository(apiService, defaultDispatcher)
    }
}