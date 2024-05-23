package me.nexryai.transit.plugins

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import me.nexryai.transit.entities.TransitParams
import me.nexryai.transit.services.TransitInfoService
import me.nexryai.transit.templates.LayoutTemplate

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    install(ContentNegotiation) {
        json()
    }
    routing {
        get("/api/transit") {
            val from = call.request.queryParameters["from"].toString()
            val to = call.request.queryParameters["to"].toString()

            if (from == "null" || to == "null" || from.isEmpty() || to.isEmpty()) {
                call.respondText(text = "400: From or To is empty", status = HttpStatusCode.BadRequest)
                return@get
            }

            val transitInfoService = TransitInfoService(TransitParams(from, to, "2021/10/10", "12:00"))
            val res = try {
                transitInfoService.getTransit()
            } catch (e: IllegalArgumentException) {
                call.respondText(text = "Invalid params or route not found", status = HttpStatusCode.BadRequest)
            } catch (e: Exception) {
                call.respondText(text = "500: Internal server error", status = HttpStatusCode.InternalServerError)
            }

            //jsonを返す
            call.respond(res)
        }

        get("/api/health") {
            call.respond(mapOf("status" to "ok"))
        }

        // Static plugin.
        staticResources("/static", "static")
        get("/style.css") {
            val css = me.nexryai.transit.templates.styles
            call.response.header("Content-Type", "text/css")
            call.respondText(css.toString(), ContentType.Text.CSS)
        }
        get("/") {
            call.respondHtmlTemplate(LayoutTemplate()) {
                header {
                    +"TransitKt"
                }
                content {
                    articleTitle {
                        +"Hello from Ktor!"
                    }
                    articleText {
                        +"Kotlin Framework for creating connected systems."
                    }
                }
            }
        }
    }
}
