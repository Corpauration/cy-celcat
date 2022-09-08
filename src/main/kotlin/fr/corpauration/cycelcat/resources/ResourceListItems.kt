package fr.corpauration.cycelcat.resources

import kotlinx.serialization.Serializable

@Serializable
data class ResourceListItems<E> (val total: Int, val results: List<E>)