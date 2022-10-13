package com.fracta7.inventory_tweaks.gui

import com.fracta7.inventory_tweaks.ModMain
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.GameRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import org.lwjgl.glfw.GLFW

class SquareMenuScreen : Screen(Text.literal("Square Wheel")) {
    private val SQUARE_WHEEL = Identifier(ModMain.MOD_ID, "demo.png")

    init {
        render(MatrixStack(), client!!.window.x, client!!.window.y, 0f)
        keyPressed(GLFW.GLFW_KEY_G, GLFW.GLFW_KEY_G, 0)
    }

    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        RenderSystem.setShader(GameRenderer::getPositionShader)
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
        RenderSystem.setShaderTexture(0, SQUARE_WHEEL)
        DrawableHelper.drawTexture(matrices, mouseX - 28, mouseY - 28, 0f, 0f, 56, 56, 56, 56)
    }

    override fun shouldPause(): Boolean {
        return false
    }

    override fun keyPressed(keyCode: Int, scanCode: Int, modifiers: Int): Boolean {
        close()
        return true
    }
}