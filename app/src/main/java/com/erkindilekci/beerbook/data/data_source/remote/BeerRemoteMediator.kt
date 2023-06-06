package com.erkindilekci.beerbook.data.data_source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.erkindilekci.beerbook.data.data_source.local.BeerDatabase
import com.erkindilekci.beerbook.data.data_source.local.BeerEntity
import com.erkindilekci.beerbook.data.mappers.toBeerEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BeerRemoteMediator(
    private val beerDb: BeerDatabase,
    private val beerApi: BeerApi
) : RemoteMediator<Int, BeerEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BeerEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                REFRESH -> 1
                PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            delay(2000L)
            val beers = beerApi.getBeers(page = loadKey, pageCount = state.config.pageSize)

            beerDb.withTransaction {
                if (loadType == REFRESH) {
                    beerDb.beerDao.clearAll()
                }
                val beerEntities = beers.map { it.toBeerEntity() }
                beerDb.beerDao.upsertAll(beerEntities)
            }

            MediatorResult.Success(endOfPaginationReached = beers.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
