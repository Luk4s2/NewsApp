package eu.newsapp.viewmodel.login

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

	var loginFailed by mutableStateOf(false)
		private set

	var showLoginRequiredDialog by mutableStateOf(false)
		private set

	fun onEmailChange(newEmail: String) {
		email = newEmail
		loginFailed = false
	}

	fun onPasswordChange(newPassword: String) {
		password = newPassword
		loginFailed = false
	}

	fun login() {
//		if (email == Constants.VALID_EMAIL && password == Constants.VALID_PASSWORD) {
			isLoggedIn = true
//			loginFailed = false
//		} else {
//			loginFailed = true
//			emptyTextFields()
//		}
	}

	fun skipLogin() {
		showLoginRequiredDialog = true
	}

	private fun emptyTextFields(){
		email = ""
		password = ""
	}

	fun closeLoginRequiredDialog() {
		showLoginRequiredDialog = false
	}

	fun resetLoginState() {
		isLoggedIn = null
		emptyTextFields()
		loginFailed = false
	}
}
