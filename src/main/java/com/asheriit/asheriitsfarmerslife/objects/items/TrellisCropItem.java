package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.objects.blocks.TrellisCropBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class TrellisCropItem extends Item {
    private final Block tooltipBlock;

    public TrellisCropItem(Block tooltipBlock, Properties properties) {
        super(properties);
        this.tooltipBlock = tooltipBlock;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (this.tooltipBlock instanceof TrellisCropBlock) {
            String translationKey = this.tooltipBlock.getTranslationKey();
            TrellisCropBlock.addTrellisCropInformation(translationKey, tooltip, true);
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
