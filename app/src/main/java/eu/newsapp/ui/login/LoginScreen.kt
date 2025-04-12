package eu.newsapp.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import eu.newsapp.utils.Constants
import eu.newsapp.ui.navigation.Routes
import eu.newsapp.viewmodel.login.LoginViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun LoginScreen(
	navController: NavController,
	loginViewModel: LoginViewModel = getViewModel()
) {
	val isLoggedIn = loginViewModel.isLoggedIn

	// Auto-redirect if login succeeded
	LaunchedEffect(isLoggedIn) {
		if (isLoggedIn != null) {
			navController.navigate(Routes.FEED) {
				popUpTo(Routes.LOGIN) { inclusive = true }
			}
		}
	}

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
			modifier = Modifier.fillMaxWidth(),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
		)

		Spacer(modifier = Modifier.height(16.dp))

		TextField(
			value = loginViewModel.password,
			onValueChange = { loginViewModel.onPasswordChange(it) },
			label = { Text(Constants.PASSWORD_LABEL) },
			modifier = Modifier.fillMaxWidth(),
			visualTransformation = PasswordVisualTransformation(),
			keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
		)

		Spacer(modifier = Modifier.height(24.dp))

		Button(
			onClick = { loginViewModel.login() },
			modifier = Modifier.fillMaxWidth()
		) {
			Text(Constants.LOGIN_BUTTON)
		}

		Spacer(modifier = Modifier.height(12.dp))

		TextButton(onClick = {
			loginViewModel.skipLogin()
		}) {
			Text(Constants.SKIP_BUTTON)
		}
	}
}
