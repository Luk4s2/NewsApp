package eu.newsapp.di

import eu.newsapp.viewmodel.login.LoginViewModel
import eu.newsapp.viewmodel.feed.FeedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
	viewModel { LoginViewModel() }
	viewModel { FeedViewModel(get()) }

	// Retrofit
	single {
		Retrofit.Builder()
			.baseUrl("https://newsdata.io/api/1/")
			.addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
			.client(
				okhttp3.OkHttpClient.Builder()
					.addInterceptor(okhttp3.logging.HttpLoggingInterceptor().apply {
						level = okhttp3.logging.HttpLoggingInterceptor.Level.BODY
					})
					.build()
			)
			.build()
			.create(eu.newsapp.network.NewsApiService::class.java)
	}

	// Repository
	single { eu.newsapp.repository.NewsRepository(get()) }
}
