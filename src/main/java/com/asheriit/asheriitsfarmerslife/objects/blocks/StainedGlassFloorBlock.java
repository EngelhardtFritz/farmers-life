package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.state.properties.DiagonalBlockType;
import net.minecraft.block.Block;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.item.DyeColor;

public class StainedGlassFloorBlock extends GlassFloorBlock implements IBeaconBeamColorProvider {
    private final DyeColor color;

    public StainedGlassFloorBlock(DyeColor dyeColor, Block.Properties builder) {
        super(builder);
        this.color = dyeColor;
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(EAST, false)
                .with(WATERLOGGED, false)
                .with(MISSING_DIAGONAL_BLOCK, DiagonalBlockType.EMPTY));
    }

    @Override
    public DyeColor getColor() {
        return color;
    }
}
