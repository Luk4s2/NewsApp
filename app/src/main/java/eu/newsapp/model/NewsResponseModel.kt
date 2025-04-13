package eu.newsapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class NewsResponseModel(
	val status: String?,
	val totalResults: Int?,
	val nextPage: String?,
	val results: List<Article>?
)

@Parcelize
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
) : Parcelable
