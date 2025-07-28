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
package net.ccbluex.liquidbounce.features.module.modules.render

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.OverlayRenderEvent
import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.ClientModule
import net.ccbluex.liquidbounce.features.module.modules.render.gui.hud.HudManager
import net.ccbluex.liquidbounce.utils.client.mc

/**
 * This module is responsible for rendering the in-game HUD.
 * Toggling it will show or hide all HUD elements.
 * The HUD editor can be opened with the .hud command or a keybind to the HudEditor module.
 */
object ModuleHUD : ClientModule("HUD", Category.RENDER) {

    init {
        enabled = true // The HUD is visible by default
    }

    @EventTarget
    fun onRender(event: OverlayRenderEvent) {
        // Don't render if the module is disabled, F3 menu is open, or any other screen is open
        if (!enabled || mc.options.debugEnabled || mc.currentScreen != null) {
            return
        }

        // Ensure elements are initialized
        HudManager.initialize()

        for (element in HudManager.elements) {
            element.renderPreview(event.context)
        }
    }
}
