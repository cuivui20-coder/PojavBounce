/*
 * This file is part of LiquidBounce (https://github.com/CCBlueX/LiquidBounce)
 *
 * Copyright (c) 2015 - 2025 CCBlueX
 *
 * LiquidBounce is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LiquidBounce is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LiquidBounce. If not, see <https://www.gnu.org/licenses/>.
 */
package net.ccbluex.liquidbounce.integration.interop

/**
 * Stub types to replace HTTP interop functionality since we're using native GUI instead of web-based UI.
 * These stubs prevent compilation errors while the HTTP server functionality is being phased out.
 */

// HTTP Request/Response stubs
data class RequestObject(val data: Any? = null) {
    inline fun <reified T> asJson(): T = data as T
}

interface FullHttpResponse

// HTTP Response functions stubs
fun httpOk(data: Any) = data
fun httpBadRequest(message: String) = message
fun httpForbidden(message: String) = message
fun httpNotFound(message: String) = message
fun httpInternalServerError(message: String) = message
fun httpNoContent() = Unit
fun httpCreated(data: Any) = data
fun httpAccepted(data: Any) = data

// HTTP Server stubs
interface HttpServer {
    fun start()
    fun stop()
}

class HttpServerBuilder {
    fun port(port: Int) = this
    fun build(): HttpServer = object : HttpServer {
        override fun start() {}
        override fun stop() {}
    }
}