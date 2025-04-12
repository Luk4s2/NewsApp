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
import eu.newsapp.utils.Constants

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
						text = Constants.DETAIL_TITLE,
						maxLines = 1,
						overflow = TextOverflow.Ellipsis,
						style = MaterialTheme.typography.titleLarge
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = Constants.BACK_BUTTON_DESCRIPTION)
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
					val shareIntent = Intent.createChooser(sendIntent, Constants.SHARE_ARTICLE_TITLE)
					context.startActivity(shareIntent)
				},
				containerColor = MaterialTheme.colorScheme.secondaryContainer
			) {
				Icon(Icons.Default.Share, contentDescription = Constants.SHARE_BUTTON_DESCRIPTION)
			}
		}
	) { padding ->
		Column(
			modifier = Modifier
				.padding(padding)
				.verticalScroll(rememberScrollState())
				.padding(Constants.CARD_PADDING.dp)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(Constants.FEED_ITEM_SPACING.dp)
		) {
			article.imageUrl?.let {
				AsyncImage(
					model = it,
					contentDescription = article.title,
					modifier = Modifier
						.fillMaxWidth()
						.height(Constants.IMAGE_HEIGHT.dp)
				)
			}

			Text(
				text = article.title ?: Constants.FEED_PLACEHOLDER_TITLE,
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
				text = article.content ?: article.description ?: Constants.FEED_PLACEHOLDER_CONTENT,
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onBackground
			)

			HorizontalDivider()

			LabeledTextRow(Constants.LABEL_SOURCE, article.sourceId ?: "-")
			LabeledTextRow(Constants.LABEL_LANGUAGE, article.language ?: "-")
			LabeledTextRow(Constants.LABEL_COUNTRY, article.country?.joinToString(", ") ?: "-")
			LabeledTextRow(Constants.LABEL_CATEGORY, article.category?.joinToString(", ") ?: "-")
		}
	}

	BackHandler {
		navController.popBackStack()
	}
}
