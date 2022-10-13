package com.fracta7.inventory_tweaks

import com.fracta7.inventory_tweaks.client.SquareMenuHudOverlay
import com.fracta7.inventory_tweaks.event.KeyInputHandler
import com.fracta7.inventory_tweaks.gui.SquareMenuScreen
import com.fracta7.inventory_tweaks.gui.SquareMenuScreenHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.fabricmc.loader.impl.util.log.Log
import net.fabricmc.loader.impl.util.log.LogCategory
import net.fabricmc.loader.impl.util.log.LogLevel
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer.BOX
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerFactory
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier

@Suppress("UNUSED")
object ModMain : ModInitializer {
    lateinit var SQUARE_MENU_SCREEN_HANDLER: ScreenHandlerType<*>
    const val MOD_ID = "inventory_tweaks"
    val BOX = Identifier(MOD_ID, "demo.png")
    override fun onInitialize() {
        val keyBind = KeyInputHandler.getKeybind()
        val client = MinecraftClient.getInstance()
        HudRenderCallback.EVENT.register(SquareMenuHudOverlay(keyBind))
    }
}