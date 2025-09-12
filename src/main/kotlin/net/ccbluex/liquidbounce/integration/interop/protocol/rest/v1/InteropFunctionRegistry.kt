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

package net.ccbluex.liquidbounce.integration.interop.protocol.rest.v1

/**
 * Stubbed interop function registry for native GUI approach
 * 
 * This file registers REST API endpoints for web-based integration.
 * Since we're using a native GUI approach, all web integration is stubbed out.
 */

// Stub Node class for REST API registration
class Node {
    fun withPath(path: String, block: Node.() -> Unit): Node = this
    fun get(path: String, handler: Any) {}
    fun post(path: String, handler: Any) {}
    fun put(path: String, handler: Any) {}
    fun delete(path: String, handler: Any) {}
}

internal fun registerInteropFunctions(node: Node) {
    // All REST API registration stubbed for native GUI approach
    // Web integration endpoints are not needed in native implementation
}