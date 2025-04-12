package eu.newsapp.viewmodel.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.newsapp.model.Article
import eu.newsapp.repository.NewsRepository
import eu.newsapp.repository.Result
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

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
			isLoading = true
			errorMessage = null

			when (val result = repository.getNews(apiKey)) {
				is Result.Success -> articles = result.data
				is Result.Error -> errorMessage = result.message
			}

			isLoading = false
		}
	}
}
