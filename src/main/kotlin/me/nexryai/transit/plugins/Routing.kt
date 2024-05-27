package me.nexryai.transit.plugins

import com.ucasoft.ktor.simpleCache.SimpleCache
import com.ucasoft.ktor.simpleCache.cacheOutput
import com.ucasoft.ktor.simpleMemoryCache.memoryCache
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nexryai.transit.entities.TimeMode
import me.nexryai.transit.entities.TransitParams
import me.nexryai.transit.services.TransitInfoService
import me.nexryai.transit.templates.ResultPageTemplate
import me.nexryai.transit.templates.RouteNotFoundPageTemplate
import me.nexryai.transit.templates.ServerErrorPageTemplate
import me.nexryai.transit.templates.WelcomePageTemplate
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    install(ContentNegotiation) {
        json()
    }
    install(SimpleCache) {
        memoryCache {
            invalidateAt = 10.seconds
        }
    }
    routing {
        get("/health") {
            call.respond(mapOf("status" to "ok"))
        }

        cacheOutput {
            get("/result") {
                val from = call.request.queryParameters["from"].toString()
                val to = call.request.queryParameters["to"].toString()

                var timeMode = if (call.request.queryParameters["timeMode"].toString() == "a") {
                    TimeMode.ARRIVAL
                } else if (call.request.queryParameters["timeMode"].toString() == "d") {
                    TimeMode.DEPARTURE
                } else {
                    TimeMode.IGNORE
                }

                val timeString = call.request.queryParameters["time"].toString()
                val parsedTime = if (timeString != "null") {
                    try {
                        LocalDateTime.parse(timeString)
                    } catch (e: Exception) {
                        LocalDateTime.now()
                    }
                } else {
                    timeMode = TimeMode.IGNORE
                    LocalDateTime.now()
                }


                if (from == "null" || to == "null" || from.isEmpty() || to.isEmpty()) {
                    call.respondText(text = "400: From or To is empty", status = HttpStatusCode.BadRequest)
                    return@get
                }

                val transitInfoService = TransitInfoService(TransitParams(from, to, parsedTime, timeMode))
                val res = try {
                    transitInfoService.getTransit()
                } catch (e: IllegalArgumentException) {
                    call.respondHtmlTemplate(RouteNotFoundPageTemplate()) {}
                    return@get
                } catch (e: Exception) {
                    call.respondHtmlTemplate(ServerErrorPageTemplate(e.toString().split(":")[0])) {}
                    return@get
                }

                call.respondHtmlTemplate(ResultPageTemplate(res)) {}
            }
        }

        // Static plugin.
        staticResources("/static", "static")
        get("/style.css") {
            val css = me.nexryai.transit.templates.styles
            call.response.header("Content-Type", "text/css")
            call.respondText(css.toString(), ContentType.Text.CSS)
        }
        get("/") {
            call.respondHtmlTemplate(WelcomePageTemplate()) {}
        }
    }
}
