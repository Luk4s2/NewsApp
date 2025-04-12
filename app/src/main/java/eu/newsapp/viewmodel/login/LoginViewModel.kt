package eu.newsapp.viewmodel.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import eu.newsapp.utils.Constants

class LoginViewModel : ViewModel() {

	var email by mutableStateOf("")
		private set

	var password by mutableStateOf("")
		private set

	var isLoggedIn by mutableStateOf<Boolean?>(null)
		private set

	fun onEmailChange(newEmail: String) {
		email = newEmail
	}

	fun onPasswordChange(newPassword: String) {
		password = newPassword
	}

	fun login() {
		Log.d("Login Pressed", "Login successful:  email = $email password = $password")
		isLoggedIn = email == Constants.VALID_EMAIL && password == Constants.VALID_PASSWORD
	}

	fun skipLogin() {
		isLoggedIn = false
	}
}
