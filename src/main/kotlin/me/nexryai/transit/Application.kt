package me.nexryai.transit

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import me.nexryai.transit.plugins.*
import me.nexryai.transit.utils.Logger

fun main() {
    val log = Logger()
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    val host = System.getenv("HOST") ?: "127.0.0.1"

    log.info("Starting server...")
    try {
        log.info("Server started at $host:$port")
        embeddedServer(Netty, port = port, host = host, module = Application::module)
            .start(wait = true)
    } catch (e: Exception) {
        log.error("Failed to start server: ${e.message}")
    }
}

fun Application.module() {
    configureRouting()
}
