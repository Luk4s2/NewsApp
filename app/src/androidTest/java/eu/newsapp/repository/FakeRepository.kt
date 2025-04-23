package eu.newsapp.repository

import eu.newsapp.model.Article

/**
 * Fake implementation of [INewsRepository] for testing ViewModel behavior.
 *
 * @param articlesToReturn List of articles to return on success.
 * @param failInitial If true, [getInitialNews] will return an error.
 * @param failNext If true, [getNextNews] will return an error.
 */
class FakeRepository(
	private val articlesToReturn: List<Article> = emptyList(),
	private val failInitial: Boolean = false,
	private val failNext: Boolean = false
) : INewsRepository {

	override suspend fun getInitialNews(apiKey: String): Result<PagedResult> {
		return if (failInitial) {
			Result.Error("No more pages")
		} else {
			Result.Success(PagedResult(articlesToReturn, null))
		}
	}

	override suspend fun getNextNews(apiKey: String): Result<PagedResult> {
		return if (failNext) {
			Result.Error("No more pages")
		} else {
			Result.Success(PagedResult(articlesToReturn, null))
		}
	}

	override fun hasMorePages(): Boolean = !failNext
}
