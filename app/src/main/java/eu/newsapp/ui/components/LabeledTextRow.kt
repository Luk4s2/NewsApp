package eu.newsapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LabeledTextRow(label: String, value: String) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.spacedBy(6.dp)
	) {
		Text(
			text = "$label:",
			style = MaterialTheme.typography.labelMedium,
			color = MaterialTheme.colorScheme.primary
		)
		Text(
			text = value,
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurface
		)
	}
}
