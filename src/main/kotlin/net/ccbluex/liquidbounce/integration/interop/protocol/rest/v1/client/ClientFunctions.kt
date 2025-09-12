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
 * Stubbed client functions for native GUI approach
 * 
 * All client REST API functions are stubbed since the native GUI
 * handles client operations directly without web interface.
 */

fun getClientInfo(requestObject: RequestObject): String {
    return "Client info requires web interface access"
}

fun getUpdateInfo(requestObject: RequestObject): String {
    return "Update info requires web interface access"
}

fun postExit(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Exit command requires web interface access")
}

fun getWindowInfo(requestObject: RequestObject): String {
    return "Window info requires web interface access"
}

fun postBrowse(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Browse command requires web interface access")
}