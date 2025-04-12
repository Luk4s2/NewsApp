package eu.newsapp.model

import com.google.gson.annotations.SerializedName

data class NewsResponseModel(
	val status: String?,
	val totalResults: Int?,
	val results: List<Article>?
)

data class Article(
	val title: String?,
	val link: String?,
	val pubDate: String?,
	val description: String?,
	val content: String?,
	@SerializedName("image_url") val imageUrl: String?,
	@SerializedName("source_id") val sourceId: String?,
	val country: List<String>?,
	val category: List<String>?,
	val language: String?
)

