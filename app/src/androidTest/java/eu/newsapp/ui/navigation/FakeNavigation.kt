package eu.newsapp.ui.navigation

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.test.core.app.ApplicationProvider

/**
 * A no-operation [NavController] for use in UI tests where actual navigation is not needed.
 */
open class NoOpNavController : NavController(ApplicationProvider.getApplicationContext()) {

	/**
	 * No-op implementation of navigate.
	 */
	override fun navigate(
		resId: Int,
		args: Bundle?,
		navOptions: NavOptions?,
		navigatorExtras: Navigator.Extras?
	) {}

	/**
	 * Always returns true to simulate popping back stack.
	 */
	override fun popBackStack(): Boolean = true

	/**
	 * No-op implementation for setting navigation graph.
	 */
	override fun setGraph(graph: NavGraph, startDestinationArgs: Bundle?) {}
}
