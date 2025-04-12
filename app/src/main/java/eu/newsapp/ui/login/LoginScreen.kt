package eu.newsapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.newsapp.ui.navigation.NavUtils
import eu.newsapp.utils.Constants
import eu.newsapp.viewmodel.login.LoginViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
	navController: NavController,
	loginViewModel: LoginViewModel = getViewModel()
) {
	val focusManager = LocalFocusManager.current
	val keyboardController = LocalSoftwareKeyboardController.current

	val isLoggedIn = loginViewModel.isLoggedIn
	val loginFailed = loginViewModel.loginFailed
	val showDialog = loginViewModel.showLoginRequiredDialog

	LaunchedEffect(isLoggedIn) {
		if (isLoggedIn == true) {
			NavUtils.navigateToFeed(navController)
		}
	}

	if (showDialog) {
		AlertDialog(
			onDismissRequest = { },
			confirmButton = {
				TextButton(
					onClick = {
						loginViewModel.closeLoginRequiredDialog()
						loginViewModel.resetLoginState()
					}
				) {
					Text(Constants.TRY_LOGIN_BUTTON)
				}
			},
			title = { Text(Constants.LOGIN_REQUIRED_DIALOG_TITLE) },
			text = { Text(Constants.LOGIN_REQUIRED_DIALOG_TEXT) }
		)
	} else {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(24.dp),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			TextField(
				value = loginViewModel.email,
				onValueChange = { loginViewModel.onEmailChange(it) },
				label = { Text(Constants.EMAIL_LABEL) },
				isError = loginFailed,
				modifier = Modifier.fillMaxWidth(),
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
			)

			Spacer(modifier = Modifier.height(16.dp))

			TextField(
				value = loginViewModel.password,
				onValueChange = { loginViewModel.onPasswordChange(it) },
				label = { Text(Constants.PASSWORD_LABEL) },
				isError = loginFailed,
				modifier = Modifier.fillMaxWidth(),
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
			)

			if (loginFailed) {
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = Constants.LOGIN_ERROR_MESSAGE,
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.bodySmall
				)
			}

			Spacer(modifier = Modifier.height(24.dp))

			Button(
				onClick = {
					focusManager.clearFocus()
					keyboardController?.hide()
					loginViewModel.login()
				},
				modifier = Modifier.fillMaxWidth()
			) {
				Text(Constants.LOGIN_BUTTON)
			}

			Spacer(modifier = Modifier.height(12.dp))

			TextButton(onClick = { loginViewModel.skipLogin() }) {
				Text(Constants.SKIP_BUTTON)
			}
		}
	}
}
