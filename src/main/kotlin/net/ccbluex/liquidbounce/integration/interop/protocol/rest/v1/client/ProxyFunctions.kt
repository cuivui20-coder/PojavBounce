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
 * Stubbed proxy functions for native GUI approach
 * 
 * All proxy REST API functions are stubbed since the native GUI
 * handles proxy operations directly without web interface.
 */

fun getAllProxies(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Proxy listing requires web interface access")
}

fun putProxies(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Proxy updates require web interface access")
}

fun deleteProxies(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Proxy deletion requires web interface access")
}

fun getProxies(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Proxy access requires web interface")
}

fun putProxies2(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Proxy configuration requires web interface access")
}

fun deleteProxies2(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Proxy removal requires web interface access")
}