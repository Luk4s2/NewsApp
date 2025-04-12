package eu.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.newsapp.model.Article
import eu.newsapp.ui.detail.FeedDetailScreen
import eu.newsapp.ui.feed.FeedScreen
import eu.newsapp.ui.login.LoginScreen
import eu.newsapp.utils.Constants

@Composable
fun AppNavGraph(navController: NavHostController) {
	NavHost(
		navController = navController,
		startDestination = Routes.LOGIN
	) {
		composable(Routes.LOGIN) {
			LoginScreen(navController)
		}

		composable(Routes.FEED) {
			FeedScreen(navController)
		}

		composable(Routes.DETAIL) { backStackEntry ->
			val article = backStackEntry
				.savedStateHandle
				.get<Article>(Constants.NAV_ARG_ARTICLE)

			article?.let {
				FeedDetailScreen(article = it, navController = navController)
			}
		}
	}
}
