package eu.newsapp.viewmodel.feed

/**
 * Helper class to control pagination behavior in scroll-based lists.
 *
 * Prevents duplicate API calls when the user scrolls near the end of the list,
 * and tracks the last visible index to avoid redundant fetches.
 */
class PaginationController {

	/** The last index for which a pagination request was made. */
	private var lastRequestedIndex: Int = -1

	/** Flag to prevent overlapping pagination calls. */
	private var isRequestInProgress: Boolean = false

	/**
	 * Determines whether the next page should be loaded based on the current scroll position.
	 *
	 * @param currentIndex The index of the currently visible item.
	 * @param hasMorePages Boolean flag indicating whether more pages are available to load.
	 * @return `true` if a new request should be triggered, `false` otherwise.
	 */
	fun shouldLoadNextPage(
		currentIndex: Int,
		hasMorePages: Boolean
	): Boolean {
		if (isRequestInProgress) return false
		if (!hasMorePages) return false
		if (currentIndex == lastRequestedIndex) return false

		lastRequestedIndex = currentIndex
		isRequestInProgress = true
		return true
	}

	/**
	 * Marks that the current pagination request has finished,
	 * allowing new requests to be triggered.
	 */
	fun onRequestFinished() {
		isRequestInProgress = false
	}

	/**
	 * Resets the pagination state. Should be called on feed refresh or screen reload.
	 */
	fun reset() {
		lastRequestedIndex = -1
		isRequestInProgress = false
	}
}
