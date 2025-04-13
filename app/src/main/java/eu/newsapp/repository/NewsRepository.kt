package eu.newsapp.repository

import eu.newsapp.model.Article
import eu.newsapp.data.network.NewsApiService
import eu.newsapp.utils.Constants

sealed class Result<out T> {
	data class Success<out T>(val data: T) : Result<T>()
	data class Error(val message: String) : Result<Nothing>()
}

data class PagedResult(
	val articles: List<Article>,
	val nextPage: String?
)

class NewsRepository(private val api: NewsApiService) {

	private var nextPageToken: String? = null
	private var isRequestInProgress = false

	suspend fun getInitialNews(apiKey: String): Result<PagedResult> {
		nextPageToken = null
		return fetchNews(apiKey)
	}

	suspend fun getNextNews(apiKey: String): Result<PagedResult> {

		if (isRequestInProgress) {
			return Result.Error(Constants.REQUEST_IN_PROGRESS)
		}
		if (nextPageToken == null) {
			return Result.Error(Constants.NO_MORE_PAGES)

		}
		return fetchNews(apiKey)
	}

	private suspend fun fetchNews(apiKey: String): Result<PagedResult> {
		isRequestInProgress = true
		return try {
			val response = api.getNews(
				apiKey = apiKey,
				page = nextPageToken
			)
			nextPageToken = response.nextPage
			Result.Success(
				PagedResult(
					articles = response.results ?: emptyList(),
					nextPage = nextPageToken
				)
			)
		} catch (e: Exception) {
			Result.Error(e.localizedMessage ?: Constants.RESPONSE_ERROR)
		} finally {
			isRequestInProgress = false
		}
	}

	fun hasMorePages(): Boolean = nextPageToken != null
}
