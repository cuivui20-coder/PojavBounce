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
package net.ccbluex.liquidbounce.deeplearn

import ai.djl.engine.Engine
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import java.io.File

class MobileCompatibilityTest {

    @Test
    fun testDJLEngineCanInitialize() {
        // Test that DJL engine can be retrieved without crashing
        assertDoesNotThrow {
            val engine = Engine.getInstance()
            assertNotNull(engine)
            println("DJL Engine: ${engine.engineName} ${engine.version}")
        }
    }

    @Test
    fun testMobileFriendlyCacheDirectoriesExist() {
        // Initialize DeepLearningEngine to trigger property setup
        DeepLearningEngine
        
        // Test that our mobile-friendly cache directories are properly set up
        val djlCacheDir = System.getProperty("DJL_CACHE_DIR")
        val engineCacheDir = System.getProperty("ENGINE_CACHE_DIR")
        
        assertNotNull(djlCacheDir, "DJL_CACHE_DIR should be set")
        assertNotNull(engineCacheDir, "ENGINE_CACHE_DIR should be set")
        
        assertTrue(File(djlCacheDir).exists(), "DJL cache directory should exist")
        assertTrue(File(engineCacheDir).exists(), "Engine cache directory should exist")
        
        println("DJL Cache Dir: $djlCacheDir")
        println("Engine Cache Dir: $engineCacheDir")
    }

    @Test
    fun testJniLibsDirectoryStructure() {
        // Test that the jniLibs directory structure exists for Android compatibility
        val jniLibsDir = File("src/main/jniLibs")
        val armv7Dir = File(jniLibsDir, "armeabi-v7a")
        val arm64Dir = File(jniLibsDir, "arm64-v8a")
        
        assertTrue(jniLibsDir.exists(), "jniLibs directory should exist")
        assertTrue(armv7Dir.exists(), "armeabi-v7a directory should exist")
        assertTrue(arm64Dir.exists(), "arm64-v8a directory should exist")
        
        println("JNI Libs structure ready for mobile compatibility")
    }

    @Test
    fun testOptOutTrackingIsSet() {
        // Initialize DeepLearningEngine to trigger property setup
        DeepLearningEngine
        
        // Test that DJL tracking is disabled for privacy
        val optOut = System.getProperty("OPT_OUT_TRACKING")
        assertEquals("true", optOut, "DJL tracking should be disabled")
    }
}
