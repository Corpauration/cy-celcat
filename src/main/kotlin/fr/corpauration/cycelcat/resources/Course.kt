package fr.corpauration.cycelcat.resources

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: String,
    val start: String,
    val end: String?,
    val allDay: Boolean,
    val description: String,
    val backgroundColor: String,
    val textColor: String,
    val department: String?,
    val faculty: String?,
    val eventCategory: String?,
    val sites: List<String>?,
    val modules: List<String>?,
    val registerStatus: Int,
    val studentMark: Int,
    val custom1: String?,
    val custom2: String?,
    val custom3: String?
)