package eu.newsapp.data.di

import eu.newsapp.data.network.NewsApiService
import eu.newsapp.data.preferences.UserPreferences
import eu.newsapp.utils.Constants
import eu.newsapp.viewmodel.login.LoginViewModel
import eu.newsapp.viewmodel.feed.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

/**
 * Defines the dependency injection module using Koin.
 *
 * This includes ViewModels, Repository, API service, and Preferences.
 */
val appModule = module {
	viewModel { LoginViewModel(get()) }
	viewModel { FeedViewModel(get()) }

	// Shared Preferences
	single { UserPreferences(context = get()) }

	// Retrofit
	single {
		Retrofit.Builder()
			.baseUrl(Constants.BASE_URL)
			.addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
			.client(
				okhttp3.OkHttpClient.Builder()
					.addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
						level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
					})
					.build()
			)
			.build()
			.create(NewsApiService::class.java)
	}

	// Repository
	single<eu.newsapp.repository.INewsRepository> { eu.newsapp.repository.NewsRepository(get()) }
}
