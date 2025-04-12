package eu.newsapp.ui.login.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import eu.newsapp.utils.Constants

@Composable
fun LoginRequiredDialog(
	onDismiss: () -> Unit,
	onTryLogin: () -> Unit
) {
	AlertDialog(
		onDismissRequest = onDismiss,
		confirmButton = {
			TextButton(onClick = onTryLogin) {
				Text(Constants.TRY_LOGIN_BUTTON)
			}
		},
		title = { Text(Constants.LOGIN_REQUIRED_DIALOG_TITLE) },
		text = { Text(Constants.LOGIN_REQUIRED_DIALOG_TEXT) }
	)
}
