package id.pawra.ui.screen.pet.activities

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.pawra.data.local.preference.SessionModel
import id.pawra.data.remote.response.ActivitiesResponseItem
import id.pawra.data.remote.retrofit.ApiService
import retrofit2.HttpException

class ActivitiesPagingSource(
    private val keyword: String,
    private val user: SessionModel,
    private val petId: Int,
    private val apiService: ApiService
) : PagingSource<Int, ActivitiesResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ActivitiesResponseItem> {
        return try {
            if (keyword.isEmpty()) return LoadResult.Page(data = emptyList(), null, null)
            val nextPageNumber = params.key ?: 1
            val response = apiService.getSpecificActivities(user.token, petId, keyword, params.loadSize, nextPageNumber)

            LoadResult.Page(
                data = response.items ?: listOf(),
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.items?.isEmpty() == true) null else nextPageNumber + 1
            )
        } catch (e: HttpException) {
            return LoadResult.Error(e)

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ActivitiesResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}