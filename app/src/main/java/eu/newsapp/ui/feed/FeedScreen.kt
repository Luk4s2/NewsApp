package eu.newsapp.ui.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.newsapp.ui.feed.components.NewsItem
import eu.newsapp.ui.navigation.Routes
import eu.newsapp.utils.Constants
import eu.newsapp.viewmodel.feed.FeedViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedScreen(
	navController: NavController,
	feedViewModel: FeedViewModel = koinViewModel(),
) {
	LaunchedEffect(Unit) {
		feedViewModel.loadNews(apiKey = Constants.API_KEY)
	}

	Surface(
		modifier = Modifier.fillMaxSize(),
		color = MaterialTheme.colorScheme.background
	) {
		when {
			feedViewModel.isLoading -> {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					CircularProgressIndicator()
				}
			}

			feedViewModel.errorMessage != null -> {
				Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
					Text(
						text = "${Constants.FEED_ERROR_PREFIX}${feedViewModel.errorMessage}",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.error
					)
				}
			}

			else -> {
				LazyColumn(
					contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
					verticalArrangement = Arrangement.spacedBy(8.dp)
				) {
					items(feedViewModel.articles) { article ->
						NewsItem(article) {
							navController.navigate(Routes.DETAIL)
							navController.currentBackStackEntry
								?.savedStateHandle
								?.set("article", article)
						}
					}
				}
			}
		}
	}
}
