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
package net.ccbluex.liquidbounce.integration.theme

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Test class to verify default theme initialization
 */
class ThemeDefaultsTest {

    @Test
    fun `defaults theme should have initialized components`() {
        // This test verifies that Theme.defaults() creates a theme with properly initialized components
        val defaultTheme = Theme.defaults()
        
        // The components should be accessible without throwing IllegalArgumentException
        try {
            val components = defaultTheme.components
            assertNotNull(components, "Components should not be null")
        } catch (e: IllegalArgumentException) {
            fail("Expected components to be initialized, but got: ${e.message}")
        }
    }

    @Test
    fun `defaults theme should have initialized settings`() {
        // This test verifies that Theme.defaults() creates a theme with properly initialized settings
        val defaultTheme = Theme.defaults()
        
        // The settings should be accessible without throwing IllegalArgumentException
        try {
            val settings = defaultTheme.settings
            assertNotNull(settings, "Settings should not be null")
        } catch (e: IllegalArgumentException) {
            fail("Expected settings to be initialized, but got: ${e.message}")
        }
    }
}