package eu.newsapp.utils

object Constants {

	// API endpoint
	const val BASE_URL = "https://newsdata.io/api/1/"
	const val LANGUAGE = "en"
	const val QUERY = "world"

	// To Reviewer who look at this , (KEYs passwords etc.) SHOULD NEVER be stored in APK or hardcoded for security purposes
	// but this time to demonstrate the working task, so i put it here
	const val API_KEY = "pub_800090b8212f2332b13a2dcefefa42783e091"

	// Correct Credentials
	const val VALID_EMAIL = "elonga@elonga.com"
	const val VALID_PASSWORD = "ElongaTheBest"

	// DataStore Preferences
	const val DATASTORE_NAME = "user_prefs"
	const val PREF_IS_LOGGED_IN = "is_logged_in"

	// Login labels
	const val EMAIL_LABEL = "Email"
	const val PASSWORD_LABEL = "Password"
	const val LOGIN_BUTTON = "Login"
	const val SKIP_BUTTON = "Skip Login"

	// Navigation keys
	const val NAV_ARG_ARTICLE = "article"

	// Login errors
	const val LOGIN_ERROR_MESSAGE = "Wrong email or password."

	// Login dialog
	const val LOGIN_REQUIRED_DIALOG_TITLE = "Login Required"
	const val LOGIN_REQUIRED_DIALOG_TEXT = "You must be logged in to view the news feed."
	const val TRY_LOGIN_BUTTON = "Try Login"

	// Feed errors
	const val RESPONSE_ERROR = "An error occurred"
	const val REQUEST_IN_PROGRESS = "Request already in progress."
	const val NO_MORE_PAGES = "No more pages available."

	// Feed screen
	const val FEED_PLACEHOLDER_TITLE = "No Title"
	const val FEED_PLACEHOLDER_CONTENT = "No content available."
	const val FEED_ERROR_PREFIX = "Error loading feed: "

	// Detail screen
	const val DETAIL_TITLE = "Article Detail"
	const val SHARE_ARTICLE_TITLE = "Share article"
	const val BACK_BUTTON_DESCRIPTION = "Back"
	const val SHARE_BUTTON_DESCRIPTION = "Share"

	// Labels
	const val LABEL_SOURCE = "Source"
	const val LABEL_LANGUAGE = "Language"
	const val LABEL_COUNTRY = "Country"
	const val LABEL_CATEGORY = "Category"

	// UI Dimensions
	const val CARD_ELEVATION = 6
	const val CARD_PADDING = 16
	const val CARD_SPACING_VERTICAL = 6
	const val IMAGE_HEIGHT = 180
	const val IMAGE_SPACER_HEIGHT = 12
	const val FEED_HORIZONTAL_PADDING = 16
	const val FEED_VERTICAL_PADDING = 12
	const val FEED_ITEM_SPACING = 8
	const val LABEL_WIDTH = 88
	const val LABEL_TOP_PADDING = 2
	const val LABEL_SPACING = 8
	const val LABEL_BOTTOM_PADDING = 4

	// Login screen spacing
	const val LOGIN_SCREEN_PADDING = 24
	const val LOGIN_FIELD_SPACING = 16
	const val LOGIN_ERROR_SPACING = 8
	const val LOGIN_BUTTON_SPACING = 24
	const val LOGIN_SKIP_SPACING = 12
}