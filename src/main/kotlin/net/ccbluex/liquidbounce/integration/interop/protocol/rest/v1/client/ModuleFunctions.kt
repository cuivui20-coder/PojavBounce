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

package net.ccbluex.liquidbounce.integration.interop.protocol.rest.v1.client

import net.ccbluex.liquidbounce.integration.interop.FullHttpResponse
import net.ccbluex.liquidbounce.integration.interop.RequestObject
import net.ccbluex.liquidbounce.integration.interop.httpOk

/**
 * Stubbed module functions for native GUI approach
 * 
 * All module REST API functions are stubbed since the native GUI
 * handles module operations directly without web interface.
 */

fun getAllModules(requestObject: RequestObject): String {
    return "Module listing requires web interface access"
}

fun getModules(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Module access requires web interface")
}

fun putModules(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Module updates require web interface")
}

fun getAllConfigurables(requestObject: RequestObject): String {
    return "Configurables listing requires web interface access"
}

fun getConfigurables(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Configurables access requires web interface")
}

fun putConfigurables(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Configurables updates require web interface")
}