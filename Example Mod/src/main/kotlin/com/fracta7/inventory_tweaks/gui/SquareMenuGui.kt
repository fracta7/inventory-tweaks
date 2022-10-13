package com.fracta7.inventory_tweaks.gui

import com.fracta7.inventory_tweaks.ModMain
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItem
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

class SquareMenuGui(itemStacks: List<ItemStack>, key: Int) : LightweightGuiDescription() {
    init {
        val root = SquareMenu()
        setRootPanel(root)
        root.setSize(58, 58)
        root.insets = Insets(2)
        root.onKeyPressed(key, key, 0)
        root.add(WItem(itemStacks[0]), 0, 0)
        root.add(WItem(itemStacks[1]), 1, 0)
        root.add(WItem(itemStacks[2]), 2, 0)
        root.add(WItem(itemStacks[3]), 0, 1)
        root.add(WItem(itemStacks[4]), 2, 1)
        root.add(WItem(itemStacks[5]), 0, 2)
        root.add(WItem(itemStacks[6]), 1, 2)
        root.add(WItem(itemStacks[7]), 2, 2)
        root.add(SquareMenu(), 0, 0)
    }
}

class SquareMenu : WGridPanel() {
    private val TEXTURE = Identifier(ModMain.MOD_ID, "demo.png")
    override fun canResize(): Boolean {
        return false
    }

    override fun paint(matrices: MatrixStack?, x: Int, y: Int, mouseX: Int, mouseY: Int) {
        ScreenDrawing.texturedRect(matrices, x, y, 64, 64, TEXTURE, 0xF_FFFFFF)
    }

    override fun onKeyPressed(ch: Int, key: Int, modifiers: Int) {
        val client = MinecraftClient.getInstance()

        println("Is executed")
        client.currentScreen!!.close()
    }
}