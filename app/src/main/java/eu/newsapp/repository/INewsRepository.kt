package eu.newsapp.repository

/**
 * Interface defining the contract for news data operations.
 */
interface INewsRepository {

	/**
	 * Fetches the initial list of news articles.
	 *
	 * @param apiKey The API key for authentication.
	 * @return A [Result] containing either a [PagedResult] or an error.
	 */
	suspend fun getInitialNews(apiKey: String): Result<PagedResult>

	/**
	 * Fetches the next page of news articles.
	 *
	 * @param apiKey The API key for authentication.
	 * @return A [Result] containing either a [PagedResult] or an error.
	 */
	suspend fun getNextNews(apiKey: String): Result<PagedResult>

	/**
	 * Indicates whether more pages are available for loading.
	 */
	fun hasMorePages(): Boolean
}
