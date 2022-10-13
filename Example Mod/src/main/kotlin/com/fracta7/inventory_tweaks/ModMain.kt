package com.fracta7.inventory_tweaks

import com.fracta7.inventory_tweaks.client.SquareMenuHudOverlay
import com.fracta7.inventory_tweaks.event.KeyInputHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

@Suppress("UNUSED")
object ModMain : ModInitializer {
    lateinit var SQUARE_MENU_SCREEN_HANDLER: ScreenHandlerType<*>
    const val MOD_ID = "inventory_tweaks"
    val BOX = Identifier(MOD_ID, "demo.png")
    override fun onInitialize() {
        val keyBind = KeyInputHandler.getKeybind()
        HudRenderCallback.EVENT.register(SquareMenuHudOverlay(keyBind))
    }
}