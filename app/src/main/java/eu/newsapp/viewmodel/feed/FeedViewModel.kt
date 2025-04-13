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

/**
 * ViewModel responsible for managing the feed screen's data and state.
 *
 * Handles initial data loading, pagination on scroll,
 * and error management. It communicates with [NewsRepository]
 * to fetch paginated articles from the API.
 *
 * @property repository The repository used to fetch news data.
 */
class FeedViewModel(
	private val repository: NewsRepository
) : ViewModel() {

	/** Holds the list of currently loaded articles. */
	var articles by mutableStateOf<List<Article>>(emptyList())
		private set

	/** Flag to indicate whether data is currently being loaded. */
	var isLoading by mutableStateOf(false)
		private set

	/** Holds the error message, if any occurred during data fetching. */
	var errorMessage by mutableStateOf<String?>(null)
		private set

	/** Pagination controller to avoid duplicate loads and maintain scroll state. */
	private val pagination = PaginationController()

	/**
	 * Loads the first page of articles when the feed screen opens.
	 * Resets pagination controller and error state.
	 */
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

	/**
	 * Triggered when the user scrolls near the bottom of the feed.
	 * Attempts to load the next page of articles if conditions allow.
	 *
	 * @param currentIndex The index of the currently visible last item in the list.
	 */
	fun onScrollNearBottom(currentIndex: Int) {
		if (!pagination.shouldLoadNextPage(currentIndex, repository.hasMorePages())) return

		viewModelScope.launch {
			delay(500)
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
