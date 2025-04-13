package eu.newsapp.data.network

import eu.newsapp.model.NewsResponseModel
import eu.newsapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Retrofit interface for the NewsData.io API.
 *
 * Defines the structure for HTTP requests to fetch news data.
 */
interface NewsApiService {

	/**
	 * Fetches a list of news articles from NewsData.io.
	 *
	 * @param apiKey The API key required to authenticate the request. Sent via the `X-ACCESS-KEY` header.
	 * @param language Optional parameter to filter news by language (default defined in [Constants.LANGUAGE]).
	 * @param query Optional query keyword to search for specific topics (default defined in [Constants.QUERY]).
	 * @param page The next page token (optional) used for paginated results.
	 * @return A [NewsResponseModel] object containing the list of news articles and pagination info.
	 */
	@GET("news")
	suspend fun getNews(
		@Header("X-ACCESS-KEY") apiKey: String,
		@Query("language") language: String = Constants.LANGUAGE,
		@Query("q") query: String = Constants.QUERY,
		@Query("page") page: String? = null
	): NewsResponseModel
}
