package com.fracta7.inventory_tweaks.event

import com.fracta7.inventory_tweaks.client.SquareMenuHudOverlay
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

object KeyInputHandler {
    private const val KEY_CATEGORY = "key.category.inventory_tweaks"
    private const val KEY_OPEN_SQUARE_MENU = "key.inventory_tweaks.open_square_menu"
    private val openSquareKey: KeyBinding = KeyBindingHelper.registerKeyBinding(
        KeyBinding(
            KEY_OPEN_SQUARE_MENU,
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            KEY_CATEGORY,
        )
    )
    fun getKeybind(): KeyBinding{
        return openSquareKey
    }

    fun register() {

    }
}