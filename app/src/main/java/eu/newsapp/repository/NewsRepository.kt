package eu.newsapp.repository

import eu.newsapp.network.NewsApiService
import eu.newsapp.model.NewsResponseModel

class NewsRepository(private val api: NewsApiService) {

	suspend fun getNews(apiKey: String?): NewsResponseModel {
		return api.getNews(apiKey)
	}
}