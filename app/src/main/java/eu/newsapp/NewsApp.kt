package eu.newsapp

import android.app.Application
import eu.newsapp.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Custom [Application] class for the News App.
 *
 * Initializes the Koin dependency injection framework when the application starts.
 */
class NewsApp : Application() {

	/**
	 * Called when the application is created.
	 *
	 * Sets up the Koin DI framework with the application context and modules.
	 */
	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@NewsApp)
			modules(appModule)
		}
	}
}
