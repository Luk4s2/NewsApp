package eu.newsapp.repository

import eu.newsapp.model.Article
import eu.newsapp.data.network.NewsApiService
import eu.newsapp.utils.Constants

sealed class Result<out T> {
	data class Success<out T>(val data: T) : Result<T>()
	data class Error(val message: String) : Result<Nothing>()
}

class NewsRepository(private val api: NewsApiService) {

	suspend fun getNews(apiKey: String?): Result<List<Article>> {
		return try {
			val response = api.getNews(apiKey)
			Result.Success(response.results ?: emptyList())
		} catch (e: Exception) {
			Result.Error(e.localizedMessage ?: Constants.RESPONSE_ERROR)
		}
	}
}
