package eu.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.newsapp.ui.feed.FeedScreen
import eu.newsapp.ui.login.LoginScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
	NavHost(navController = navController, startDestination = Routes.LOGIN) {
		composable(Routes.LOGIN) {
			LoginScreen(navController)
		}
		composable(Routes.FEED) {
			FeedScreen(navController)
		}

	}
}
