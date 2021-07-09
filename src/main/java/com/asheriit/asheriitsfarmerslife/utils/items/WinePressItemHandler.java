package com.asheriit.asheriitsfarmerslife.utils.items;

import com.asheriit.asheriitsfarmerslife.recipes.WinePressRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class WinePressItemHandler<TE extends TileEntity> extends ItemStackHandler {
    private TE tileEntity;

    public WinePressItemHandler(int size, TE tileEntity) {
        super(size);
        this.tileEntity = tileEntity;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        switch (slot) {
            case WinePressTileEntity.INPUT_ITEM_SLOT:
                return WinePressRecipe.WinePressRecipeSerializer.ingredientList.contains(stack.getItem());
            default:
                return false;
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        this.tileEntity.markDirty();
        this.tileEntity.getWorld().notifyBlockUpdate(this.tileEntity.getPos(), this.tileEntity.getBlockState(), this.tileEntity.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
        super.onContentsChanged(slot);
    }

    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
