package eu.newsapp.repository

import eu.newsapp.model.Article
import eu.newsapp.data.network.NewsApiService
import eu.newsapp.utils.Constants

/**
 * Represents the result of a repository or API operation.
 * Used to wrap both success and error cases in a type-safe manner.
 */
sealed class Result<out T> {

	/**
	 * Represents a successful result.
	 * @param data The result data returned from the operation.
	 */
	data class Success<out T>(val data: T) : Result<T>()

	/**
	 * Represents an error result.
	 * @param message A description of the error.
	 */
	data class Error(val message: String) : Result<Nothing>()
}

/**
 * Data class for holding paginated results.
 *
 * @property articles The list of news articles returned.
 * @property nextPage The token for fetching the next page.
 */
data class PagedResult(
	val articles: List<Article>,
	val nextPage: String?
)

/**
 * Repository responsible for retrieving news data from the News API.
 *
 * It handles both initial fetches and paginated responses, while internally
 * tracking pagination state and concurrent request prevention.
 *
 * @param api The Retrofit service used for network requests.
 */
open class NewsRepository(private val api: NewsApiService) : INewsRepository  {

	/** Token representing the next page to fetch from the API. */
	private var nextPageToken: String? = null

	/** Flag to ensure only one request is processed at a time. */
	private var isRequestInProgress = false

	/**
	 * Fetches the first page of news articles and resets pagination state.
	 *
	 * @param apiKey The API key for authentication.
	 * @return A [Result] containing a [PagedResult] or an error message.
	 */
	override suspend fun getInitialNews(apiKey: String): Result<PagedResult> {
		nextPageToken = null
		return fetchNews(apiKey)
	}

	/**
	 * Fetches the next page of news articles if possible.
	 *
	 * @param apiKey The API key for authentication.
	 * @return A [Result] containing a [PagedResult] or an error message.
	 */
	override suspend fun getNextNews(apiKey: String): Result<PagedResult> {
		if (isRequestInProgress) {
			return Result.Error(Constants.REQUEST_IN_PROGRESS)
		}
		if (nextPageToken == null) {
			return Result.Error(Constants.NO_MORE_PAGES)
		}
		return fetchNews(apiKey)
	}

	/**
	 * Internal helper to execute the API request and parse the result.
	 *
	 * @param apiKey The API key for authentication.
	 * @return A [Result] with paged articles or an error.
	 */
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

	/**
	 * Checks whether more pages of results are available.
	 * @return `true` if there are more pages to load, otherwise `false`.
	 */
	override fun hasMorePages(): Boolean = nextPageToken != null
}
