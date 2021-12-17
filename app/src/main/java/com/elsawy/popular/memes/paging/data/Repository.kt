package com.elsawy.popular.memes.paging.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.elsawy.popular.memes.paging.data.model.Meme
import com.elsawy.popular.memes.paging.data.network.MemesApi
import com.elsawy.popular.memes.paging.data.network.MemesPagingSource
import kotlinx.coroutines.flow.Flow


private const val PAGE_SIZE = 10

fun getMemesFromNetwork(): Flow<PagingData<Meme>> {

   val memesApi= MemesApi.getApiClient().create(MemesApi::class.java)

   return Pager(
      config = PagingConfig(
         pageSize = PAGE_SIZE,
         prefetchDistance = 2,
         maxSize = PAGE_SIZE + (PAGE_SIZE * 2),
         enablePlaceholders = false
      ),
      pagingSourceFactory = { MemesPagingSource(memesApi) }
   ).flow
}
