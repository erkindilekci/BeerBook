package com.erkindilekci.beerbook.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.erkindilekci.beerbook.data.data_source.local.BeerDatabase
import com.erkindilekci.beerbook.data.data_source.local.BeerEntity
import com.erkindilekci.beerbook.data.data_source.remote.BeerApi
import com.erkindilekci.beerbook.data.data_source.remote.BeerRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(
        @ApplicationContext context: Context
    ): BeerDatabase = Room.databaseBuilder(
        context,
        BeerDatabase::class.java,
        "beer_database.db"
    ).build()

    @Provides
    @Singleton
    fun provideBeerApi(): BeerApi = Retrofit.Builder()
        .baseUrl(BeerApi.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideBeerPager(
        beerDb: BeerDatabase,
        beerApi: BeerApi
    ): Pager<Int, BeerEntity> = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = BeerRemoteMediator(beerDb, beerApi),
        pagingSourceFactory = { beerDb.beerDao.pagingSource() }
    )
}
