package eu.newsapp.ui.detail

import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import eu.newsapp.model.Article
import androidx.navigation.NavController
import eu.newsapp.ui.detail.components.LabeledTextRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedDetailScreen(
	article: Article,
	navController: NavController
) {
	val context = LocalContext.current

	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Text(
						text = "Article Detail",
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
						style = MaterialTheme.typography.titleLarge
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.primaryContainer,
					titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
					navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
				)
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {
					val sendIntent = Intent(Intent.ACTION_SEND).apply {
						type = "text/plain"
						putExtra(Intent.EXTRA_TEXT, article.link ?: "")
					}
					val shareIntent = Intent.createChooser(sendIntent, "Share article")
					context.startActivity(shareIntent)
				},
				containerColor = MaterialTheme.colorScheme.secondaryContainer
			) {
				Icon(Icons.Default.Share, contentDescription = "Share")
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.verticalScroll(rememberScrollState())
				.padding(16.dp)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			article.imageUrl?.let {
				AsyncImage(
					model = it,
					contentDescription = article.title,
					modifier = Modifier
						.fillMaxWidth()
						.height(220.dp)
				)
			}

			Text(
				text = article.title ?: "No Title",
				style = MaterialTheme.typography.headlineSmall,
				color = MaterialTheme.colorScheme.onBackground
			)

			Text(
				text = article.pubDate ?: "",
				style = MaterialTheme.typography.labelSmall,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)

			HorizontalDivider()

			Text(
				text = article.content ?: article.description ?: "No content available.",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onBackground
			)

			HorizontalDivider()

			LabeledTextRow("Source", article.sourceId ?: "-")
			LabeledTextRow("Language", article.language ?: "-")
			LabeledTextRow("Country", article.country?.joinToString(", ") ?: "-")
			LabeledTextRow("Category", article.category?.joinToString(", ") ?: "-")
		}
	}

	BackHandler {
		navController.popBackStack()
	}
}

