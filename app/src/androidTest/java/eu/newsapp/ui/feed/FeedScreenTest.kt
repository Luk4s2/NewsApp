package eu.newsapp.ui.feed

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import eu.newsapp.model.Article
import eu.newsapp.repository.FakeRepository
import eu.newsapp.ui.navigation.NoOpNavController
import eu.newsapp.viewmodel.feed.FeedViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for [FeedScreen] using Compose Test API.
 */
@RunWith(AndroidJUnit4::class)
class FeedScreenTest {

	@get:Rule
	val composeTestRule = createComposeRule()

	private val fakeArticles = listOf(
		Article(
			title = "Test 1", link = "link", pubDate = "date", description = "desc",
			content = "content", imageUrl = "url", sourceId = "src", country = listOf("us"),
			category = listOf("tech"), language = "en"
		),
		Article(
			title = "Test 2", link = "link2", pubDate = "date", description = "desc",
			content = "content", imageUrl = "url", sourceId = "src", country = listOf("us"),
			category = listOf("tech"), language = "en"
		)
	)

	/**
	 * Verifies that the feed screen correctly displays a list of articles.
	 */
	@Test
	fun feedScreen_displaysArticles() {
		val viewModel = object : FeedViewModel(repository = FakeRepository(fakeArticles)) {
			override var articles: List<Article> = fakeArticles
			override var isLoading: Boolean = false
		}

		composeTestRule.setContent {
			FeedScreen(
				navController = NoOpNavController(),
				feedViewModel = viewModel
			)
		}

		composeTestRule.onAllNodesWithTag("newsItem").assertCountEquals(2)
	}

	/**
	 * Verifies that the feed screen shows an empty state when there are no articles.
	 */
	@Test
	fun feedScreen_showsEmptyState() {
		val viewModel = object : FeedViewModel(repository = FakeRepository(emptyList())) {
			override var articles: List<Article> = emptyList()
			override var isLoading: Boolean = false
		}

		composeTestRule.setContent {
			FeedScreen(
				navController = NoOpNavController(),
				feedViewModel = viewModel
			)
		}

		composeTestRule.onNodeWithTag("emptyState").assertIsDisplayed()
	}
}
