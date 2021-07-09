package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.objects.blocks.TrellisCropBlock;
import com.asheriit.asheriitsfarmerslife.objects.blocks.WaterloggedCropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class WaterloggedCropItem extends Item {
    private final Block tooltipBlock;

    public WaterloggedCropItem(Block tooltipBlock, Item.Properties properties) {
        super(properties);
        this.tooltipBlock = tooltipBlock;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (this.tooltipBlock instanceof WaterloggedCropBlock) {
            String translationKey = this.tooltipBlock.getTranslationKey();
            WaterloggedCropBlock.addWaterloggedCropInformation(translationKey, tooltip);
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
