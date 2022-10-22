package com.fracta7.inventory_tweaks

import com.fracta7.inventory_tweaks.client.SquareMenuHudOverlay
import com.fracta7.inventory_tweaks.client.ToolHud
import com.fracta7.inventory_tweaks.event.KeyInputHandler
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback

@Suppress("UNUSED")
object ModMain : ModInitializer {
    const val MOD_ID = "inventory_tweaks"
    override fun onInitialize() {
        val keyBind = KeyInputHandler.getKeybind()
        HudRenderCallback.EVENT.register(SquareMenuHudOverlay(keyBind))
        HudRenderCallback.EVENT.register(ToolHud(keyBind))
    }
}