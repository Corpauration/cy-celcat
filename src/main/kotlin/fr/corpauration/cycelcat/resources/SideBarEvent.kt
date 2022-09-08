package fr.corpauration.cycelcat.resources

import kotlinx.serialization.Serializable

@Serializable
data class SideBarEvent(val federationId: String?, val entityType: Int, val elements: List<SideBarEventElement>)
