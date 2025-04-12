package eu.newsapp.ui.navigation

import androidx.navigation.NavController

object NavUtils {

	fun navigateToFeed(navController: NavController) {
		navController.navigate(Routes.FEED) {
			popUpTo(Routes.LOGIN) { inclusive = true }
		}
	}

	fun navigateToLogin(navController: NavController) {
		navController.navigate(Routes.LOGIN) {
			popUpTo(0)
		}
	}
}