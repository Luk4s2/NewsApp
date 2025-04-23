package eu.newsapp.repository

import eu.newsapp.model.Article

/**
 * Fake implementation of INewsRepository for testing.
 */
class FakeNewsRepository(
	private val articlesToReturn: List<Article> = emptyList(),
	private val failInitial: Boolean = false,
	private val failNext: Boolean = false
) : INewsRepository {
	override suspend fun getInitialNews(apiKey: String) = if (failInitial)
		Result.Error("No more pages")
	else
		Result.Success(PagedResult(articlesToReturn, null))

	override suspend fun getNextNews(apiKey: String) = if (failNext)
		Result.Error("No more pages")
	else
		Result.Success(PagedResult(articlesToReturn, null))

	override fun hasMorePages(): Boolean = !failNext
}
