package eu.newsapp.network

import eu.newsapp.model.NewsResponseModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {
	@GET("news")
	suspend fun getNews(
		@Header("X-ACCESS-KEY") apiKey: String?,
		@Query("language") language: String = "en"
	): NewsResponseModel
}