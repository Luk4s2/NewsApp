package eu.newsapp.viewmodel.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.newsapp.model.Article
import eu.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import eu.newsapp.utils.Constants

class FeedViewModel(
	private val repository: NewsRepository
) : ViewModel() {

	var articles by mutableStateOf<List<Article>>(emptyList())
		private set

	var isLoading by mutableStateOf(false)
		private set

	var errorMessage by mutableStateOf<String?>(null)
		private set

	fun loadNews(apiKey: String?) {
		viewModelScope.launch {
			try {
				isLoading = true
				errorMessage = null
				val response = repository.getNews(apiKey)
				articles = response.results ?: emptyList()
			} catch (e: Exception) {
				errorMessage = e.localizedMessage ?: Constants.RESPONSE_ERROR
			} finally {
				isLoading = false
			}
		}
	}
}
