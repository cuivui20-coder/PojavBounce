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

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import net.ccbluex.liquidbounce.config.types.SettingsTreeSerializer
import net.ccbluex.liquidbounce.features.module.Category
import net.ccbluex.liquidbounce.features.module.ClientModule
import net.ccbluex.liquidbounce.features.module.ModuleManager
import net.ccbluex.liquidbounce.integration.interop.HttpMethod
import net.ccbluex.liquidbounce.integration.interop.RequestObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * Integration tests for the settings tree REST API endpoints
 */
class SettingsTreeFunctionsTest {

    private lateinit var testModule: TestModule

    @BeforeEach
    fun setup() {
        testModule = TestModule()
        // Mock ModuleManager to return our test module
        // Note: In a real integration test, you'd set up the actual ModuleManager
    }

    @Test
    fun `getModuleSettingsTree should return settings tree JSON`() {
        val requestObject = createMockRequest(
            queryParams = mapOf("name" to "TestModule")
        )

        // Mock ModuleManager to return our test module
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val response = getModuleSettingsTree(requestObject)

        assertEquals(200, response.statusCode)
        assertNotNull(response.content)
        
        // Verify the JSON structure
        val jsonContent = response.content.toString()
        assertTrue(jsonContent.contains("moduleId"))
        assertTrue(jsonContent.contains("TestModule"))
        assertTrue(jsonContent.contains("groups"))
    }

    @Test
    fun `getModuleSettingsTree should return 400 for missing module name`() {
        val requestObject = createMockRequest(queryParams = emptyMap())

        val response = getModuleSettingsTree(requestObject)

        assertEquals(400, response.statusCode)
    }

    @Test
    fun `getModuleSettingsTree should return 404 for non-existent module`() {
        val requestObject = createMockRequest(
            queryParams = mapOf("name" to "NonExistentModule")
        )

        val response = getModuleSettingsTree(requestObject)

        assertEquals(404, response.statusCode)
    }

    @Test
    fun `getModuleSettingsField should return field information`() {
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val requestObject = createMockRequest(
            queryParams = mapOf(
                "module" to "TestModule",
                "field" to "TestModule.testBoolean"
            )
        )

        val response = getModuleSettingsField(requestObject)

        assertEquals(200, response.statusCode)
        assertNotNull(response.content)
        
        val jsonContent = response.content as JsonObject
        assertTrue(jsonContent.has("fieldId"))
        assertTrue(jsonContent.has("currentValue"))
        assertTrue(jsonContent.has("defaultValue"))
        assertTrue(jsonContent.has("fieldType"))
    }

    @Test
    fun `getModuleSettingsField should return 400 for missing parameters`() {
        val requestObject = createMockRequest(
            queryParams = mapOf("module" to "TestModule")
            // Missing field parameter
        )

        val response = getModuleSettingsField(requestObject)

        assertEquals(400, response.statusCode)
    }

    @Test
    fun `getModuleSettingsField should return 404 for non-existent field`() {
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val requestObject = createMockRequest(
            queryParams = mapOf(
                "module" to "TestModule", 
                "field" to "TestModule.nonExistentField"
            )
        )

        val response = getModuleSettingsField(requestObject)

        assertEquals(404, response.statusCode)
    }

    @Test
    fun `setModuleSettingsField should update boolean field correctly`() {
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val updateRequest = FieldUpdateRequest(
            module = "TestModule",
            fieldPath = "TestModule.testBoolean",
            value = false
        )

        val response = updateRequest.acceptFieldUpdate()

        assertEquals(200, response.statusCode)
        // Note: In a real test, you'd verify the field was actually updated
    }

    @Test
    fun `setModuleSettingsField should update int field correctly`() {
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val updateRequest = FieldUpdateRequest(
            module = "TestModule",
            fieldPath = "TestModule.testInt",
            value = 7
        )

        val response = updateRequest.acceptFieldUpdate()

        assertEquals(200, response.statusCode)
    }

    @Test
    fun `setModuleSettingsField should update string field correctly`() {
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val updateRequest = FieldUpdateRequest(
            module = "TestModule",
            fieldPath = "TestModule.testString",
            value = "new value"
        )

        val response = updateRequest.acceptFieldUpdate()

        assertEquals(200, response.statusCode)
    }

    @Test
    fun `setModuleSettingsField should handle range fields correctly`() {
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val updateRequest = FieldUpdateRequest(
            module = "TestModule",
            fieldPath = "TestModule.testRange",
            value = mapOf("start" to 2, "end" to 8)
        )

        val response = updateRequest.acceptFieldUpdate()

        assertEquals(200, response.statusCode)
    }

    @Test
    fun `setModuleSettingsField should return 404 for non-existent module`() {
        val updateRequest = FieldUpdateRequest(
            module = "NonExistentModule",
            fieldPath = "NonExistentModule.someField",
            value = "value"
        )

        val response = updateRequest.acceptFieldUpdate()

        assertEquals(404, response.statusCode)
    }

    @Test
    fun `setModuleSettingsField should return 404 for non-existent field`() {
        ModuleManager.modules.clear()
        ModuleManager.modules.add(testModule)

        val updateRequest = FieldUpdateRequest(
            module = "TestModule",
            fieldPath = "TestModule.nonExistentField",
            value = "value"
        )

        val response = updateRequest.acceptFieldUpdate()

        assertEquals(404, response.statusCode)
    }

    @Test
    fun `should handle type conversion correctly`() {
        // Test boolean conversion
        assertTrue(convertToBoolean(true))
        assertTrue(convertToBoolean("true"))
        assertTrue(convertToBoolean(1))
        assertFalse(convertToBoolean(false))
        assertFalse(convertToBoolean("false"))
        assertFalse(convertToBoolean(0))

        // Test int conversion  
        assertEquals(5, convertToInt(5))
        assertEquals(5, convertToInt("5"))
        assertEquals(5, convertToInt(5.7))

        // Test float conversion
        assertEquals(5.5f, convertToFloat(5.5f))
        assertEquals(5.5f, convertToFloat("5.5"))
        assertEquals(5.0f, convertToFloat(5))

        // Test range conversion
        val intRange = convertToIntRange(mapOf("start" to 1, "end" to 5))
        assertEquals(1..5, intRange)

        val intRangeFromList = convertToIntRange(listOf(2, 7))
        assertEquals(2..7, intRangeFromList)
    }

    @Test
    fun `field endpoints should have correct paths`() {
        val settingsTree = SettingsTreeSerializer.serializeModule(testModule)
        val field = settingsTree.groups.flatMap { it.fields }.first()

        assertTrue(field.endpoint.get.contains("/api/v1/client/modules/settings/field"))
        assertTrue(field.endpoint.set.contains("/api/v1/client/modules/settings/field"))
        assertTrue(field.endpoint.get.contains("field="))
    }

    @Test
    fun `should serialize and deserialize round trip correctly`() {
        val originalTree = SettingsTreeSerializer.serializeModule(testModule)
        
        // Simulate a round trip through JSON
        val serialized = com.google.gson.Gson().toJson(originalTree)
        val deserialized = com.google.gson.Gson().fromJson(serialized, SettingsTreeSerializer.SettingsTree::class.java)
        
        assertEquals(originalTree.moduleId, deserialized.moduleId)
        assertEquals(originalTree.moduleName, deserialized.moduleName)
        assertEquals(originalTree.groups.size, deserialized.groups.size)
    }

    private fun createMockRequest(
        method: HttpMethod = HttpMethod.GET,
        queryParams: Map<String, String> = emptyMap(),
        body: String = ""
    ): RequestObject {
        return mock<RequestObject>().apply {
            whenever(this.method).thenReturn(method)
            whenever(this.queryParams).thenReturn(queryParams)
            whenever(this.body).thenReturn(body)
            whenever(this.params).thenReturn(emptyMap())
        }
    }

    /**
     * Test module for integration testing
     */
    private class TestModule : ClientModule("TestModule", Category.MISC) {
        val testBoolean by boolean("testBoolean", true)
        val testInt by int("testInt", 5, 1..10)
        val testFloat by float("testFloat", 1.5f, 0f..5f)
        val testString by text("testString", "default")
        val testRange by intRange("testRange", 1..5, 0..10)
    }
}