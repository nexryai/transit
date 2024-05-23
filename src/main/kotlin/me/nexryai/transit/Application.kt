package me.nexryai.transit

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.nexryai.transit.plugins.*
import me.nexryai.transit.utils.Logger

fun main() {
    val log = Logger()
    log.info("Starting server...")
    try {
        embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
            .start(wait = true)
    } catch (e: Exception) {
        log.error("Failed to start server: ${e.message}")
    }
}

fun Application.module() {
    configureRouting()
}
