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
import eu.newsapp.repository.INewsRepository
import eu.newsapp.utils.Constants

/**
 * ViewModel responsible for managing the feed screen's data and state.
 *
 * Handles initial data loading, pagination on scroll,
 * and error management. It communicates with [INewsRepository]
 * to fetch paginated articles from the API.
 *
 * @property repository The repository used to fetch news data.
 */
open class FeedViewModel(
	private val repository: INewsRepository
) : ViewModel() {

	/** Holds the list of currently loaded articles. */
	private var _articles by mutableStateOf<List<Article>>(emptyList())
	open val articles: List<Article>
		get() = _articles

	/** Flag to indicate whether data is currently being loaded. */
	private var _isLoading by mutableStateOf(false)
	open val isLoading: Boolean
		get() = _isLoading

	/** Holds the error message, if any occurred during data fetching. */
	private var _errorMessage by mutableStateOf<String?>(null)
	open val errorMessage: String?
		get() = _errorMessage

	/** Pagination controller to avoid duplicate loads and maintain scroll state. */
	private val pagination = PaginationController()

	/**
	 * Loads the first page of articles when the feed screen opens.
	 * Resets pagination controller and error state.
	 */
	fun loadInitialNews() {
		_isLoading = true
		_errorMessage = null
		pagination.reset()

		viewModelScope.launch {
			when (val result = repository.getInitialNews(Constants.API_KEY)) {
				is Result.Success -> {
					_articles = result.data.articles.distinctBy { it.title + it.pubDate }
				}
				is Result.Error -> {
					_errorMessage = result.message
				}
			}
			_isLoading = false
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
					_articles = (articles + newArticles).distinctBy { it.title + it.pubDate }
				}
				is Result.Error -> {
					_errorMessage = result.message
				}
			}
			pagination.onRequestFinished()
		}
	}
	fun hasMorePages(): Boolean {
		return repository.hasMorePages()
	}
}
