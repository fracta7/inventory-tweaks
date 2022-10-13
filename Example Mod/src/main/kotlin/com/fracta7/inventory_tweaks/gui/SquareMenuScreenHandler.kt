package com.fracta7.inventory_tweaks.gui

import kotlin.jvm.JvmOverloads
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SimpleInventory
import net.minecraft.screen.ScreenHandler
import com.fracta7.inventory_tweaks.ModMain
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.screen.slot.Slot

class SquareMenuScreenHandler @JvmOverloads constructor(
    syncId: Int,
    playerInventory: PlayerInventory,
    inventory: Inventory = SimpleInventory(8)
) : ScreenHandler(ModMain.SQUARE_MENU_SCREEN_HANDLER, syncId) {
    private val inventory: Inventory
    override fun canUse(player: PlayerEntity): Boolean {
        return inventory.canPlayerUse(player)
    }

    // Shift + Player Inv Slot
    override fun transferSlot(player: PlayerEntity, invSlot: Int): ItemStack {
        var newStack = ItemStack.EMPTY
        val slot = slots[invSlot]
        if (slot != null && slot.hasStack()) {
            val originalStack = slot.stack
            newStack = originalStack.copy()
            if (invSlot < inventory.size()) {
                if (!insertItem(originalStack, inventory.size(), slots.size, true)) {
                    return ItemStack.EMPTY
                }
            } else if (!insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY
            }
            if (originalStack.isEmpty) {
                slot.stack = ItemStack.EMPTY
            } else {
                slot.markDirty()
            }
        }
        return newStack
    }

    //This constructor gets called from the BlockEntity on the server without calling the other constructor first, the server knows the inventory of the container
    //and can therefore directly provide it as an argument. This inventory will then be synced to the client.
    init {
        checkSize(inventory, 9)
        this.inventory = inventory
        //some inventories do custom logic when a player opens it.
        inventory.onOpen(playerInventory.player)

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        var m: Int
        var l: Int
        //Our inventory
        m = 0
        while (m < 3) {
            l = 0
            while (l < 3) {
                addSlot(Slot(inventory, l + m * 3, 62 + l * 18, 17 + m * 18))
                ++l
            }
            ++m
        }
        //The player inventory
        m = 0
        while (m < 3) {
            l = 0
            while (l < 9) {
                addSlot(Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18))
                ++l
            }
            ++m
        }
        //The player Hotbar
        m = 0
        while (m < 9) {
            addSlot(Slot(playerInventory, m, 8 + m * 18, 142))
            ++m
        }
    }
}