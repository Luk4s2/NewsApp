package eu.newsapp.viewmodel.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.newsapp.model.Article
import eu.newsapp.repository.NewsRepository
import eu.newsapp.repository.Result
import kotlinx.coroutines.delay
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

	private val pagination = PaginationController()

	fun loadInitialNews() {
		isLoading = true
		errorMessage = null
		pagination.reset()

		viewModelScope.launch {
			when (val result = repository.getInitialNews(Constants.API_KEY)) {
				is Result.Success -> {
					articles = result.data.articles.distinctBy { it.title + it.pubDate }
				}
				is Result.Error -> {
					errorMessage = result.message
				}
			}
			isLoading = false
		}
	}

	fun onScrollNearBottom(currentIndex: Int) {
		if (!pagination.shouldLoadNextPage(currentIndex, repository.hasMorePages())) return

		viewModelScope.launch {
			delay(500) // debounce
			when (val result = repository.getNextNews(Constants.API_KEY)) {
				is Result.Success -> {
					val newArticles = result.data.articles
					articles = (articles + newArticles).distinctBy { it.title + it.pubDate }
				}
				is Result.Error -> {
					errorMessage = result.message
				}
			}
			pagination.onRequestFinished()
		}
	}
}
