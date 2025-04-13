package eu.newsapp.viewmodel.feed

class PaginationController {

	private var lastRequestedIndex: Int = -1
	private var isRequestInProgress: Boolean = false

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

	fun onRequestFinished() {
		isRequestInProgress = false
	}

	fun reset() {
		lastRequestedIndex = -1
		isRequestInProgress = false
	}
}
