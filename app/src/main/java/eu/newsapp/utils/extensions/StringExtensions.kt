package eu.newsapp.utils.extensions

fun String.capitalizeFirstWord(): String {
	return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}