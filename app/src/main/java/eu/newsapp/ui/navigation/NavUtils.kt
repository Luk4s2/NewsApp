package eu.newsapp.ui.navigation

import androidx.navigation.NavController
import eu.newsapp.model.Article
import eu.newsapp.utils.Constants

object NavUtils {

	fun navigateToFeed(navController: NavController) {
		navController.navigate(Routes.FEED) {
			popUpTo(Routes.LOGIN) { inclusive = true }
		}
	}

	fun navigateToDetail(navController: NavController, article: Article) {
		navController.navigate(Routes.DETAIL)
		navController.currentBackStackEntry
			?.savedStateHandle
			?.set(Constants.NAV_ARG_ARTICLE, article)
	}
}