package fr.corpauration.cycelcat

import fr.corpauration.cycelcat.resources.Course
import fr.corpauration.cycelcat.resources.ResourceListItems
import fr.corpauration.cycelcat.resources.Resources
import fr.corpauration.cycelcat.resources.SideBarEvent
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import java.time.LocalDate
import kotlin.reflect.KClass

class CyCelcat {
    companion object {
        var BASE_URL = "https://services-web.cyu.fr/calendar"
    }

    val client = HttpClient(CIO) {
        install(HttpCookies)
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun login(login: String, password: String) {
        var response: HttpResponse = client.get("$BASE_URL/LdapLogin")
        if (response.status.value == 200) {
            val regex = Regex("<input name=\"__RequestVerificationToken\".*?value=\"([^\"]+)\"")
            val token = regex.find(response.bodyAsText())?.groups?.get(1)?.value
            response = client.submitForm (
                url = "$BASE_URL/LdapLogin/Logon",
                encodeInQuery = false,
                formParameters = Parameters.build {
                    append("Name", login)
                    append("Password", password)
                    if (token != null) {
                        append("__RequestVerificationToken", token)
                    }
                }
            )
            if (response.status.value != 302) {
                throw Error("Failed to connect")
            }
        } else {
            throw Error("Failed to get token")
        }
    }

    suspend inline fun <reified E : Any> readResourceListItems(resourceType: KClass<E>, myResources: Boolean, searchTerm: String, pageSize: Int, pageNumber: Int): ResourceListItems<E> {
        val response = client.get("$BASE_URL/Home/ReadResourceListItems") {
            url {
                parameters.append("myResources", myResources.toString())
                parameters.append("searchTerm", searchTerm)
                parameters.append("pageSize", pageSize.toString())
                parameters.append("pageNumber", pageNumber.toString())
                parameters.append("resType", Resources.valueOf(resourceType).toString())
            }
        }
        return response.body()
    }

    suspend inline fun <reified E : Any> readAllResourceListItems(resourceType: KClass<E>, myResources: Boolean, searchTerm: String): ResourceListItems<E> {
        val total = readResourceListItems(resourceType, myResources, searchTerm, 1, 1).total
        return readResourceListItems(resourceType, myResources, searchTerm, total, 1)
    }

    suspend fun <E : Any> getCalendarData(start: LocalDate, end: LocalDate, resourceType: KClass<E>, calView: String, federationIds: Int): List<Course> {
        val response = client.submitForm (
            url = "$BASE_URL/Home/GetCalendarData",
            encodeInQuery = false,
            formParameters = Parameters.build {
                append("start", start.toString())
                append("end", end.toString())
                append("resType", Resources.valueOf(resourceType).toString())
                append("calView", calView)
                append("federationIds", federationIds.toString())
            }
        )
        return response.body()
    }

    suspend fun getSideBarEvent(eventId: String): SideBarEvent {
        val response = client.submitForm (
            url = "$BASE_URL/Home/GetSideBarEvent",
            encodeInQuery = false,
            formParameters = Parameters.build {
                append("eventId", eventId)
            }
        )
        return response.body()
    }
}