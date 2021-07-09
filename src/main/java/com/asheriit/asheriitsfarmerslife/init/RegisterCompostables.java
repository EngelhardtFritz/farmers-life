package com.asheriit.asheriitsfarmerslife.init;

import net.minecraft.block.ComposterBlock;

public class RegisterCompostables {
    public static final void registerAll() {
        ComposterBlock.registerCompostable(0.3F, ModBlocks.RICE_JAPONICA_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.3F, ModBlocks.RICE_INDICA_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.3F, ModBlocks.RICE_BLACK_JAPONICA_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.4F, ModBlocks.BARBERA_TRELLIS_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.4F, ModBlocks.CABERNET_TRELLIS_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.4F, ModBlocks.MERLOT_TRELLIS_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.4F, ModBlocks.RED_GLOBE_TRELLIS_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.4F, ModBlocks.KOSHU_TRELLIS_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.4F, ModBlocks.RIESLING_TRELLIS_SEED_ITEM.get());
        ComposterBlock.registerCompostable(0.4F, ModBlocks.SULTANA_TRELLIS_SEED_ITEM.get());
    }
}
