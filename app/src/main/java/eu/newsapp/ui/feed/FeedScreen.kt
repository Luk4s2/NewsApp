package eu.newsapp.ui.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.shape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import eu.newsapp.model.Article
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

	when {
		feedViewModel.isLoading -> {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				CircularProgressIndicator()
			}
		}

		feedViewModel.errorMessage != null -> {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				Text("${Constants.FEED_ERROR_PREFIX}${feedViewModel.errorMessage}")
			}
		}

		else -> {
			LazyColumn(modifier = Modifier.padding(16.dp)) {
				items(feedViewModel.articles) { article ->
					NewsItem(article) {
						navController.navigate(Routes.DETAIL)
						navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
					}
				}
			}
		}
	}
}

@Composable
fun NewsItem(article: Article, onClick: () -> Unit) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp)
			.clip(shape)
			.clickable { onClick() },
		elevation = CardDefaults.cardElevation()
	) {
		Column(modifier = Modifier.padding(16.dp)) {
			article.imageUrl?.let {
				AsyncImage(
					model = it,
					contentDescription = null,
					modifier = Modifier
						.fillMaxWidth()
						.height(180.dp)
				)
				Spacer(modifier = Modifier.height(8.dp))
			}
			Text(
				text = article.title ?: Constants.FEED_PLACEHOLDER_TITLE,
				style = MaterialTheme.typography.titleMedium
			)
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = article.pubDate ?: "",
				style = MaterialTheme.typography.bodySmall
			)
		}
	}
}
