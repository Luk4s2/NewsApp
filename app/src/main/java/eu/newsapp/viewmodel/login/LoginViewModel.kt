package eu.newsapp.viewmodel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.newsapp.data.preferences.UserPreferences
import eu.newsapp.utils.Constants
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.compose.runtime.*

/**
 * ViewModel for handling login screen logic including static credential checking,
 * login state management, and dialog visibility.
 *
 * Uses [UserPreferences] for persisting login status.
 */
class LoginViewModel(
	private val userPreferences: UserPreferences
) : ViewModel() {

	/** Email input field state. */
	var email by mutableStateOf("")
		private set

	/** Password input field state. */
	var password by mutableStateOf("")
		private set

	/**
	 * State flow representing whether the user is currently logged in.
	 * Backed by [UserPreferences].
	 */
	val isLoggedIn: StateFlow<Boolean> = userPreferences.isLoggedInFlow
		.stateIn(
			scope = viewModelScope,
			started = SharingStarted.WhileSubscribed(5000),
			initialValue = false
		)

	/** Flag to indicate a failed login attempt. */
	var loginFailed by mutableStateOf(false)
		private set

	/** Controls visibility of the 'login required' dialog after skipping login. */
	var showLoginRequiredDialog by mutableStateOf(false)
		private set

	/**
	 * Updates the email input and resets the login failure flag.
	 * @param newEmail the new email entered by the user
	 */
	fun onEmailChange(newEmail: String) {
		email = newEmail
		loginFailed = false
	}

	/**
	 * Updates the password input and resets the login failure flag.
	 * @param newPassword the new password entered by the user
	 */
	fun onPasswordChange(newPassword: String) {
		password = newPassword
		loginFailed = false
	}

	/**
	 * Performs static login check using constants. If valid, sets logged in state.
	 * Otherwise resets the input fields and sets the login failure flag.
	 */
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

	/**
	 * Called when user chooses to skip login.
	 * Triggers display of a dialog explaining login is required to access content.
	 */
	fun skipLogin() {
		showLoginRequiredDialog = true
	}

	/** Clears email and password fields. */
	private fun emptyTextFields() {
		email = ""
		password = ""
	}

	/** Closes the login required dialog. */
	fun closeLoginRequiredDialog() {
		showLoginRequiredDialog = false
	}

	/**
	 * Resets login state in preferences and clears any credentials or error flags.
	 */
	fun resetLoginState() {
		viewModelScope.launch {
			userPreferences.setLoggedIn(false)
		}
		emptyTextFields()
		loginFailed = false
	}
}
