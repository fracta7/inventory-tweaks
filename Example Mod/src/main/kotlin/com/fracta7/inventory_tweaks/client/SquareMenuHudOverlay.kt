package com.fracta7.inventory_tweaks.client

import com.fracta7.inventory_tweaks.ModMain
import com.mojang.blaze3d.systems.RenderSystem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.render.item.ItemRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Identifier

@OptIn(DelicateCoroutinesApi::class)
class SquareMenuHudOverlay(val openSquareKey: KeyBinding) : HudRenderCallback {
    private val SQUARE_WHEEL = Identifier(ModMain.MOD_ID, "square_wheel.png")
    private val SELECTOR = Identifier(ModMain.MOD_ID, "selector.png")
    private val client = MinecraftClient.getInstance()
    val item = Items.ACACIA_BOAT.defaultStack
    var count = 0

    override fun onHudRender(matrixStack: MatrixStack, tickDelta: Float) {
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
            if (openSquareKey.wasPressed()) {
                count++
                delay(1000L)
                if (count >= 7) count = -1
            }
        }
        if (openSquareKey.isPressed) {
            RenderSystem.setShader(GameRenderer::getPositionShader)
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
            RenderSystem.setShaderTexture(0, SQUARE_WHEEL)
            DrawableHelper.drawTexture(matrixStack, x - 31, y - 31, 0f, 0f, 62, 62, 62, 62)
            RenderSystem.setShaderTexture(0, SELECTOR)
            when (count) {
                0 -> {
                    draw(x - 32, y - 32, matrixStack)
                }
                1 -> {
                    draw(x - 12, y - 32, matrixStack)
                }
                2 -> {
                    draw(x + 8, y - 32, matrixStack)
                }
                3 -> {
                    draw(x + 8, y - 12, matrixStack)
                }
                4 -> {
                    draw(x + 8, y + 8, matrixStack)
                }
                5 -> {
                    draw(x - 12, y + 8, matrixStack)
                }
                6 -> {
                    draw(x - 32, y + 8, matrixStack)
                }
                7 -> {
                    draw(x - 32, y - 12, matrixStack)
                }
            }
            matrixStack.translate(0.0, 0.0, 32.0)
            RenderSystem.applyModelViewMatrix()
            client.itemRenderer.zOffset = 200f
            client.itemRenderer.renderInGuiWithOverrides(item,x,y)
            client.itemRenderer.zOffset = 0f
        }
    }

}

private fun draw(x: Int, y: Int, matrixStack: MatrixStack) {
    DrawableHelper.drawTexture(matrixStack, x, y, 0f, 0f, 24, 24, 24, 24)
}