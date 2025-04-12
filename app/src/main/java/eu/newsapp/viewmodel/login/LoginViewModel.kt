package eu.newsapp.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.newsapp.data.preferences.UserPreferences
import eu.newsapp.utils.Constants
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

class LoginViewModel(
	private val userPreferences: UserPreferences
) : ViewModel() {

	var email by mutableStateOf("")
		private set

	var password by mutableStateOf("")
		private set

	val isLoggedIn: StateFlow<Boolean> = userPreferences.isLoggedInFlow
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = false
		)

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
		if (email == Constants.VALID_EMAIL && password == Constants.VALID_PASSWORD) {
			viewModelScope.launch {
				userPreferences.setLoggedIn(true)
			}
			loginFailed = false
		} else {
			loginFailed = true
			emptyTextFields()
		}
	}

	fun skipLogin() {
		showLoginRequiredDialog = true
	}

	private fun emptyTextFields() {
		email = ""
		password = ""
	}

	fun closeLoginRequiredDialog() {
		showLoginRequiredDialog = false
	}

	fun resetLoginState() {
		viewModelScope.launch {
			userPreferences.setLoggedIn(false)
		}
		emptyTextFields()
		loginFailed = false
	}
}
