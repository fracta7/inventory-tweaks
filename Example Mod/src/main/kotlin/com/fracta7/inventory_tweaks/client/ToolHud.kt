package com.fracta7.inventory_tweaks.client

import com.fracta7.inventory_tweaks.ModMain
import com.fracta7.inventory_tweaks.data.helper_tools
import com.fracta7.inventory_tweaks.data.misc_tools
import com.fracta7.inventory_tweaks.data.pickaxes
import com.mojang.blaze3d.systems.RenderSystem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

@OptIn(DelicateCoroutinesApi::class)
class ToolHud(val keyBinding: KeyBinding) : HudRenderCallback {
    private val TOOL_LIST = Identifier(ModMain.MOD_ID, "tool_wheel.png")
    private val SELECTOR = Identifier(ModMain.MOD_ID, "selector.png")
    private var isInventoryEmpty = true
    private val client = MinecraftClient.getInstance()
    private var item: ItemStack =
        if (client.player?.inventory?.mainHandStack != null) client.player!!.inventory.mainHandStack else Items.AIR.defaultStack
    var count = 0
    override fun onHudRender(matrixStack: MatrixStack?, tickDelta: Float) {
        var isAvailable: MutableList<Boolean> = mutableListOf()
        val items: MutableList<ItemStack> = mutableListOf()
        val slotId: MutableList<Int> = mutableListOf()
        val pickaxesInventory: MutableList<ItemStack> = mutableListOf()
        item =
            if (client.player?.inventory?.mainHandStack != null) client.player!!.inventory.mainHandStack else Items.AIR.defaultStack
        if (client.player?.inventory != null) {
            var counter = 0
            var current: Int
            val inventory = client.player!!.inventory.main
            pickaxes.forEach { it1 ->
                inventory.forEach { it2 ->
                    if (it2.item == it1) {
                        current = inventory.indexOf(it2)
                        pickaxesInventory.add(inventory[current])
                        slotId.add(current)
                        counter++
                    }
                }
            }

        } else {
            isInventoryEmpty = true
        }
        var x = 0
        var y = 0
        val client = MinecraftClient.getInstance()
        if (client != null) {
            val width = client.window.scaledWidth
            val height = client.window.scaledHeight
            x = width / 2
            y = height / 2
        }
        GlobalScope.launch {
            if (keyBinding.wasPressed() && isToggleable(item.item)) {
                count++
                delay(500L)
                if (count >= pickaxesInventory.size) count = 0
            }
        }
        if (keyBinding.isPressed && isToggleable(item.item)) {
            if (helper_tools.contains(item.item) || misc_tools.contains(item.item) || pickaxes.contains(item.item)) {
                var nText = -63
                var nGui = -70
                var n = -67
                pickaxesInventory.forEach {
                    RenderSystem.setShader(GameRenderer::getPositionShader)
                    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
                    RenderSystem.setShaderTexture(0, TOOL_LIST)
                    DrawableHelper.drawTexture(matrixStack, x - 91, (y * 2) + nGui, 0f, 0f, 182, 22, 182, 22)
                    client.itemRenderer.zOffset = 200f
                    client.itemRenderer.renderInGuiWithOverrides(it, x - 88, (y * 2) + n)
                    client.textRenderer.draw(
                        matrixStack,
                        Text.empty().append(it.name).formatted(it.rarity.formatting, if(it.hasCustomName()) Formatting.ITALIC else it.rarity.formatting),
                        (x - 64).toFloat(),
                        ((y * 2) + nText).toFloat(),
                        0xffffff
                    )
                    client.itemRenderer.zOffset = 0f
                    n -= 22
                    nGui -= 22
                    nText -= 22
                }
                RenderSystem.setShader(GameRenderer::getPositionShader)
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
                RenderSystem.setShaderTexture(0, SELECTOR)
                DrawableHelper.drawTexture(matrixStack, x - 92, (y * 2) - 71 - (count * 22), 0f, 0f, 24, 24, 24, 24)
            }
        }
    }
}

private fun isToggleable(item: Item): Boolean {
    return helper_tools.contains(item) ||
            misc_tools.contains(item) ||
            pickaxes.contains(item)
}

private fun draw(matrixStack: MatrixStack?, x: Int, y: Int) {
    DrawableHelper.drawTexture(matrixStack, x, y, 0f, 0f, 24, 24, 24, 24)
}