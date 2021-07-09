package com.asheriit.asheriitsfarmerslife.api.utils.multiblock;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public abstract class MasterSlaveBlock<TE extends TileEntity> extends Block {
    public MasterSlaveBlock(Properties properties) {
        super(properties);
    }
}
