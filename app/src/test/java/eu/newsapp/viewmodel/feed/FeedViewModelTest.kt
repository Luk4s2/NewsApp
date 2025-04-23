package eu.newsapp.viewmodel.feed

import eu.newsapp.model.Article
import eu.newsapp.repository.FakeNewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [FeedViewModel].
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FeedViewModelTest {

	private val testDispatcher = StandardTestDispatcher()

	@Before
	fun setup() {
		Dispatchers.setMain(testDispatcher)
	}

	@After
	fun tearDown() {
		Dispatchers.resetMain()
	}

	private val testArticles = listOf(
		Article(
			title = "A",
			link = "",
			pubDate = "",
			description = "",
			content = "",
			imageUrl = "",
			sourceId = "",
			country = listOf(),
			category = listOf(),
			language = ""
		)
	)

	@Test
	fun viewModel_loadInitialNews_success_populatesArticles() = runTest {
		val viewModel = FeedViewModel(FakeNewsRepository(testArticles))

		viewModel.loadInitialNews()
		testScheduler.advanceUntilIdle()

		assertEquals(1, viewModel.articles.size)
		assertEquals("A", viewModel.articles[0].title)
	}

	@Test
	fun viewModel_loadInitialNews_error_setsErrorMessage() = runTest {
		val viewModel = FeedViewModel(FakeNewsRepository(emptyList(), failInitial = true))

		viewModel.loadInitialNews()
		testScheduler.advanceUntilIdle()

		assertTrue(viewModel.articles.isEmpty())
		assertEquals("No more pages", viewModel.errorMessage)
	}


	@Test
	fun viewModel_hasMorePages_returnsFalse_whenNoPagesLeft() {
		val viewModel = FeedViewModel(FakeNewsRepository(emptyList(), failNext = true))
		assertFalse(viewModel.hasMorePages())
	}
}
