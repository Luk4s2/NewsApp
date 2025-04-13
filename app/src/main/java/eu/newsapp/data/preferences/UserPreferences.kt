package eu.newsapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import eu.newsapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Extension property to create a singleton instance of DataStore<Preferences>.
 */
private val Context.dataStore by preferencesDataStore(name = Constants.DATASTORE_NAME)

/**
 * A class to manage user-related preferences using Jetpack DataStore.
 *
 * Currently used to store and observe login status across the app.
 *
 * @param context The application context used to access DataStore.
 */
class UserPreferences(private val context: Context) {

	companion object {
		/** Key for tracking the user's logged-in state. */
		val IS_LOGGED_IN = booleanPreferencesKey(Constants.PREF_IS_LOGGED_IN)
	}

	/**
	 * A [Flow] representing the user's current login state.
	 * Emits `true` if the user is logged in, otherwise `false`.
	 */
	val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
		.map { prefs -> prefs[IS_LOGGED_IN] ?: false }

	/**
	 * Updates the user's login state in DataStore.
	 *
	 * @param value `true` if logged in, `false` otherwise.
	 */
	suspend fun setLoggedIn(value: Boolean) {
		context.dataStore.edit { prefs ->
			prefs[IS_LOGGED_IN] = value
		}
	}
}
