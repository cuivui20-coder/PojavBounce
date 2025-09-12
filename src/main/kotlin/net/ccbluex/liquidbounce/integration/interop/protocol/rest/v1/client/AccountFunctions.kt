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
 * Stubbed account functions for native GUI approach
 * 
 * All account management REST API functions are stubbed since
 * the native GUI handles account operations directly.
 */

fun getAccountInfo(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account info requires web interface access")
}

fun postAccountLogin(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account login requires web interface access")  
}

fun postAccountLogout(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account logout requires web interface access")
}

fun postAccountRefresh(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account refresh requires web interface access")
}

fun deleteAccount(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account deletion requires web interface access")
}

fun postAccountCreate(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account creation requires web interface access")
}

fun getAllAccounts(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account listing requires web interface access")
}

fun getAccount(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account details require web interface access")  
}

fun putAccount(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account updates require web interface access")
}

fun deleteAccount2(requestObject: RequestObject): FullHttpResponse {
    return httpOk("Account deletion requires web interface access")
}