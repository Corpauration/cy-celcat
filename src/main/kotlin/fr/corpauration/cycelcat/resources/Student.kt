package fr.corpauration.cycelcat.resources

import kotlinx.serialization.Serializable

@Serializable
data class Student(val id: Int, val text: String, val dept: String) {
    fun getFirstname() = text.split(" ").first()
    fun getLastname() = text.split(" ").takeLast(text.split(" ").size - 2).joinToString(" ")
}
