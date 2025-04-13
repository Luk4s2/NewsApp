package eu.newsapp.ui.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.newsapp.ui.feed.components.NewsItem
import eu.newsapp.ui.navigation.NavUtils
import eu.newsapp.utils.Constants
import eu.newsapp.viewmodel.feed.FeedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedScreen(
	navController: NavController,
	feedViewModel: FeedViewModel = koinViewModel(),
) {
	val articles = feedViewModel.articles
	val isLoading = feedViewModel.isLoading
	val errorMessage = feedViewModel.errorMessage
	val listState = rememberLazyListState()

	LaunchedEffect(Unit) {
		feedViewModel.loadInitialNews()
	}


	LaunchedEffect(listState) {
		// Trigger pagination on scroll
		snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
			.map { it ?: -1 }
			.distinctUntilChanged()
			.collectLatest { index ->
				feedViewModel.onScrollNearBottom(index)
			}
	}

	Surface(
		modifier = Modifier.fillMaxSize(),
		color = MaterialTheme.colorScheme.background
	) {
		when {
			errorMessage != null -> {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text(
						text = "${Constants.FEED_ERROR_PREFIX}$errorMessage",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.error
					)
				}
			}

			isLoading && articles.isEmpty() -> {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					CircularProgressIndicator()
				}
			}

			else -> {
				val uniqueArticles = remember(articles) {
					articles.distinctBy { it.link }
				}

				LazyColumn(
					state = listState,
					contentPadding = PaddingValues(
						horizontal = Constants.FEED_HORIZONTAL_PADDING.dp,
						vertical = Constants.FEED_VERTICAL_PADDING.dp
					),
					verticalArrangement = Arrangement.spacedBy(Constants.FEED_ITEM_SPACING.dp)
				) {
					itemsIndexed(uniqueArticles) { _, article ->
						NewsItem(article) {
							NavUtils.navigateToDetail(navController, article)
						}
					}

					if (isLoading && uniqueArticles.isNotEmpty()) {
						item {
							Box(
								modifier = Modifier
									.fillMaxWidth()
									.padding(Constants.FEED_HORIZONTAL_PADDING.dp),
								contentAlignment = Alignment.Center
							) {
								CircularProgressIndicator()
							}
						}
					}
				}
			}
		}
	}
}
