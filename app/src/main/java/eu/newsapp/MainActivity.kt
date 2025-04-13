package eu.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import eu.newsapp.ui.navigation.AppNavGraph

/**
 * The main entry point for the News App.
 * Sets the content view and initializes navigation and theme.
 */
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val navController = rememberNavController()
			AppNavGraph(navController)
		}
	}
}