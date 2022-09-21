package fr.corpauration.cycelcat.resources

import kotlinx.serialization.Serializable

@Serializable
data class SideBarEventElement(
    val label: String?,
    val content: String?,
    val federationId: String?,
    val entityType: Int,
    val assignmentContext: String?,
    val containsHyperlinks: Boolean,
    val isNotes: Boolean,
    val isStudentSpecific: Boolean
)
