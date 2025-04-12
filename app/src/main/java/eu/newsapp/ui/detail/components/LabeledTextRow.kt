package eu.newsapp.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.newsapp.utils.Constants
import eu.newsapp.utils.extensions.capitalizeFirstWord

@Composable
fun LabeledTextRow(
	label: String,
	value: String
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(bottom = Constants.LABEL_BOTTOM_PADDING.dp),
		horizontalArrangement = Arrangement.spacedBy(Constants.LABEL_SPACING.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Text(
			text = "$label:",
			style = MaterialTheme.typography.labelMedium,
			color = MaterialTheme.colorScheme.primary,
			modifier = Modifier
				.width(Constants.LABEL_WIDTH.dp)
				.padding(top = Constants.LABEL_TOP_PADDING.dp),
			textAlign = TextAlign.Start
		)
		Text(
			text = value.capitalizeFirstWord(),
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurface
		)
	}
}
