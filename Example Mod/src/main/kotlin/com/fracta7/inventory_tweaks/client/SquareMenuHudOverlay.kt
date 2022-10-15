package com.fracta7.inventory_tweaks.client

import com.fracta7.inventory_tweaks.ModMain
import com.fracta7.inventory_tweaks.data.*
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
import net.minecraft.util.Identifier

@OptIn(DelicateCoroutinesApi::class)
class SquareMenuHudOverlay(val openSquareKey: KeyBinding) : HudRenderCallback {
    private val SQUARE_WHEEL = Identifier(ModMain.MOD_ID, "square_wheel.png")
    private val TOOL_LIST = Identifier(ModMain.MOD_ID, "tool_wheel.png")
    private val SELECTOR = Identifier(ModMain.MOD_ID, "selector.png")
    private var isInventoryEmpty = true
    private val client = MinecraftClient.getInstance()
    private var item: ItemStack =
        if (client.player?.inventory?.mainHandStack != null) client.player!!.inventory.mainHandStack else Items.AIR.defaultStack

    var count = 0

    override fun onHudRender(matrixStack: MatrixStack, tickDelta: Float) {

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
            if (openSquareKey.wasPressed() && isToggleable(item.item)) {
                count++
                delay(1000L)
                if (count >= 7) count = -1
            }
        }
        if (openSquareKey.isPressed && isToggleable(item.item)) {
            if (helper_tools.contains(item.item) || misc_tools.contains(item.item) || pickaxes.contains(item.item)) {
                RenderSystem.setShader(GameRenderer::getPositionShader)
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f)
                RenderSystem.setShaderTexture(0, TOOL_LIST)
                var nText = -63
                var nGui = -70
                var n = -67
                pickaxesInventory.forEach {
                    DrawableHelper.drawTexture(matrixStack, x - 91, (y * 2) + nGui, 0f, 0f, 182, 22, 182, 22)
                    client.itemRenderer.zOffset = 200f
                    client.itemRenderer.renderInGuiWithOverrides(it, x - 88, (y * 2) + n)
                    client.textRenderer.draw(
                        matrixStack,
                        it.name,
                        (x - 64).toFloat(),
                        ((y * 2) + nText).toFloat(),
                        0xffffff
                    )
                    client.itemRenderer.zOffset = 0f
                    n -= 18
                    nGui-=22
                    nText -= 22
                }

            } else {
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
                client.itemRenderer.renderInGuiWithOverrides(item, x - 28, y - 28)
                val counter = if (item.count <= 1) "" else item.count.toString()
                client.itemRenderer.renderGuiItemOverlay(client.textRenderer, item, x - 28, y - 28, counter)
                client.itemRenderer.renderInGuiWithOverrides(item, x - 8, y - 28)
                client.itemRenderer.zOffset = 0f
            }
        }
    }
}

private fun draw(x: Int, y: Int, matrixStack: MatrixStack) {
    DrawableHelper.drawTexture(matrixStack, x, y, 0f, 0f, 24, 24, 24, 24)
}

private fun isToggleable(item: Item): Boolean {
    return stone_brick.contains(item) ||
            stone.contains(item) ||
            oak.contains(item) ||
            spruce.contains(item) ||
            birch.contains(item) ||
            acacia.contains(item) ||
            jungle.contains(item) ||
            dark_oak.contains(item) ||
            mangrove.contains(item) ||
            warped.contains(item) ||
            crimson.contains(item) ||
            granite.contains(item) ||
            diorite.contains(item) ||
            andesite.contains(item) ||
            cobbled_deepslate.contains(item) ||
            polished_deepslate.contains(item) ||
            deepslate_bricks.contains(item) ||
            deepslate_tiles.contains(item) ||
            cobblestone.contains(item) ||
            mossy_cobblestone.contains(item) ||
            mossy_stone_brick.contains(item) ||
            sandstone.contains(item) ||
            smooth_sandstone.contains(item) ||
            red_sandstone.contains(item) ||
            red_smooth_sandstone.contains(item) ||
            copper.contains(item) ||
            exposed_copper.contains(item) ||
            weathered_copper.contains(item) ||
            oxidized_copper.contains(item) ||
            prismarine.contains(item) ||
            prismarine_bricks.contains(item) ||
            dark_prismarine.contains(item) ||
            smooth_stone.contains(item) ||
            bricks.contains(item) ||
            mud.contains(item) ||
            nether_bricks.contains(item) ||
            red_nether_bricks.contains(item) ||
            quartz.contains(item) ||
            purpur.contains(item) ||
            end_stone.contains(item) ||
            blackstone.contains(item) ||
            polished_blackstone.contains(item) ||
            blackstone_bricks.contains(item) ||
            basalt.contains(item) ||
            ice.contains(item) ||
            obsidian.contains(item) ||
            dirt.contains(item) ||
            netherrack.contains(item) ||
            leaves.contains(item) ||
            saplings.contains(item) ||
            froglight.contains(item) ||
            helper_tools.contains(item) ||
            misc_tools.contains(item) ||
            pickaxes.contains(item)
}