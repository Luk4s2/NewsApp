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
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 6.dp)
			.clip(MaterialTheme.shapes.medium)
			.clickable { onClick() },
		colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
		elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
	) {
		Column(modifier = Modifier.padding(16.dp)) {
			article.imageUrl?.let {
				AsyncImage(
					model = it,
					contentDescription = article.title,
					modifier = Modifier
						.fillMaxWidth()
						.height(180.dp)
						.clip(MaterialTheme.shapes.medium)
				)

				Spacer(modifier = Modifier.height(12.dp))
			}

			Text(
				text = article.title ?: Constants.FEED_PLACEHOLDER_TITLE,
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurface
			)
		}
	}
}
