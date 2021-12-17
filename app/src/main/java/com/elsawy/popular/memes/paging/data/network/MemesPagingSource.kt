package com.elsawy.popular.memes.paging.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.elsawy.popular.memes.paging.data.model.Meme
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX = 1
const val PAGE_SIZE = 10

class MemesPagingSource(private val service: MemesApi) : PagingSource<Int, Meme>() {

   override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Meme> {
      val page = params.key ?: STARTING_PAGE_INDEX
      return try {
         val startPageIndex = (page - 1) * PAGE_SIZE

         val response = service.getMemes().data.memes

         val currentPage = if (startPageIndex < response.size)
            response.subList(startPageIndex, startPageIndex + PAGE_SIZE)
         else
            emptyList()


         LoadResult.Page(
            data = currentPage,
            prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
            nextKey = if (response.isEmpty()) null else page + 1
         )
      } catch (exception: IOException) {
         return LoadResult.Error(exception)
      } catch (exception: HttpException) {
         return LoadResult.Error(exception)
      }
   }

   override fun getRefreshKey(state: PagingState<Int, Meme>): Int? {
      return state.anchorPosition?.let { anchorPosition ->
         state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
            ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
      }
   }

}