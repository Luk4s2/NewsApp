package eu.newsapp.ui.feed.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import eu.newsapp.model.Article
import eu.newsapp.utils.Constants

@Composable
fun NewsItem(
	article: Article,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier
			.fillMaxWidth()
			.padding(vertical = Constants.CARD_SPACING_VERTICAL.dp)
			.clip(MaterialTheme.shapes.medium)
			.clickable { onClick() },
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
		elevation = CardDefaults.cardElevation(defaultElevation = Constants.CARD_ELEVATION.dp)
	) {
		Column(modifier = modifier.padding(Constants.CARD_PADDING.dp)) {
			article.imageUrl?.let {
				AsyncImage(
					model = it,
					contentDescription = article.title,
					modifier = modifier
						.fillMaxWidth()
						.height(Constants.IMAGE_HEIGHT.dp)
						.clip(MaterialTheme.shapes.medium)
				)

				Spacer(modifier = modifier.height(Constants.IMAGE_SPACER_HEIGHT.dp))
			}

			Text(
				text = article.title ?: Constants.FEED_PLACEHOLDER_TITLE,
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}
