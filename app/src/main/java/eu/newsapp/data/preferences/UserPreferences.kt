package eu.newsapp.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import eu.newsapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = Constants.DATASTORE_NAME)

class UserPreferences(private val context: Context) {

	companion object {
		val IS_LOGGED_IN = booleanPreferencesKey(Constants.PREF_IS_LOGGED_IN)
	}

	val isLoggedInFlow: Flow<Boolean> = context.dataStore.data
		.map { prefs -> prefs[IS_LOGGED_IN] ?: false }

	suspend fun setLoggedIn(value: Boolean) {
		context.dataStore.edit { prefs ->
			prefs[IS_LOGGED_IN] = value
		}
	}
}
