package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModAlcoholicItems;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.objects.items.AlcoholicItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.GrassColors;
import net.minecraft.world.ILightReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandlerEventHandler {
    @SubscribeEvent
    public static void onBlockColorHandleEvent(final ColorHandlerEvent.Block event) {
        // --------------------------- GRASS COLOR ---------------------------
        event.getBlockColors().register((stateHolder, lightReader, pos, x) -> lightReader != null && pos != null ?
                        BiomeColors.getGrassColor(lightReader, pos) : GrassColors.get(0.5D, 1.0D),
                ModBlocks.PEAT_MOSS_GRASS.get(), ModBlocks.SHORT_GRASS.get(), ModBlocks.SUBMERGED_GRASS_FARMLAND.get(),
                ModBlocks.GRASS_SMALL_DAM.get(), ModBlocks.GRASS_SMALL_DAM_JAPONICA.get(), ModBlocks.GRASS_SMALL_DAM_BLACK_JAPONICA.get(),
                ModBlocks.GRASS_SMALL_DAM_INDICA.get(), ModBlocks.GRASS_LARGE_DAM.get(), ModBlocks.GRASS_LARGE_DAM_JAPONICA.get(),
                ModBlocks.GRASS_LARGE_DAM_BLACK_JAPONICA.get(), ModBlocks.GRASS_SMALL_DAM_YAMADANISHIKI.get(), ModBlocks.GRASS_LARGE_DAM_YAMADANISHIKI.get(),
                ModBlocks.GRASS_LARGE_DAM_INDICA.get(), ModBlocks.BARBERA_TRELLIS_CROP.get(), ModBlocks.CABERNET_TRELLIS_CROP.get(),
                ModBlocks.MERLOT_TRELLIS_CROP.get(), ModBlocks.RED_GLOBE_TRELLIS_CROP.get(), ModBlocks.KOSHU_TRELLIS_CROP.get(),
                ModBlocks.RIESLING_TRELLIS_CROP.get(), ModBlocks.SULTANA_TRELLIS_CROP.get());

        // --------------------------- WATER COLOR ---------------------------
        event.getBlockColors().register((stateHolder, lightReader, pos, x) -> lightReader != null && pos != null ?
                        BiomeColors.getWaterColor(lightReader, pos) : -1,
                ModBlocks.DIRT_DRYING_BED.get(), ModBlocks.DRYING_MACHINE.get());

        // --------------------------- LEAVES COLOR ---------------------------
        event.getBlockColors().register((stateHolder, lightReader, pos, x) -> {
            return lightReader != null && pos != null ? BiomeColors.getFoliageColor(lightReader, pos) : FoliageColors.getDefault();
        }, ModBlocks.CORK_OAK_LEAVES.get());
    }

    @SubscribeEvent
    public static void onItemColorHandleEvent(final ColorHandlerEvent.Item event) {
        // --------------------------- GRASS COLOR ---------------------------
        event.getItemColors().register((itemStack, tintIndex) -> {
                BlockState blockstate = ((BlockItem) itemStack.getItem()).getBlock().getDefaultState();
                return event.getBlockColors().getColor(blockstate, (ILightReader) null, (BlockPos) null, tintIndex);
            }, ModBlocks.SHORT_GRASS_ITEM.get(), ModBlocks.SUBMERGED_GRASS_FARMLAND_ITEM.get(), ModBlocks.GRASS_SMALL_DAM_ITEM.get(),
            ModBlocks.GRASS_LARGE_DAM_ITEM.get());

        // --------------------------- WINE POTION COLORS ---------------------------
        event.getItemColors().register((itemStack, tintIndex) -> {
                switch (tintIndex) {
                    case 0:
                        return Color.WHITE.getRGB();
                    case 1:
                        return itemStack.getItem() instanceof AlcoholicItem ? ((AlcoholicItem)itemStack.getItem()).getColor(itemStack, tintIndex) : Color.BLACK.getRGB();
                    default:
                        return Color.BLACK.getRGB();
                }
            }, // ------------------------- BARBERA WINE -------------------------
            ModAlcoholicItems.BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_BARBERA_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_BARBERA_WINE_BOTTLE.get(),
            // ------------------------- CABERNET SAUVIGNON WINE -------------------------
            ModAlcoholicItems.CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_CABERNET_SAUVIGNON_WINE_BOTTLE.get(),
            // ------------------------- MERLOT WINE -------------------------
            ModAlcoholicItems.MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_MERLOT_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_MERLOT_WINE_BOTTLE.get(),
            // ------------------------- RED GLOBE WINE -------------------------
            ModAlcoholicItems.RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_RED_GLOBE_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_RED_GLOBE_WINE_BOTTLE.get(),
            // ------------------------- KOSHU WINE -------------------------
            ModAlcoholicItems.KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_KOSHU_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_KOSHU_WINE_BOTTLE.get(),
            // ------------------------- RIESLING WINE -------------------------
            ModAlcoholicItems.RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.ENRICHED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_ENRICHED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_ENRICHED_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.POTENT_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.AGED_POTENT_RIESLING_WINE_BOTTLE.get(),
            ModAlcoholicItems.OLD_POTENT_RIESLING_WINE_BOTTLE.get());

        // --------------------------- LEAVES COLOR ---------------------------
        event.getItemColors().register((itemStack, tintIndex) -> {
            BlockState blockstate = ((BlockItem)itemStack.getItem()).getBlock().getDefaultState();
            return event.getBlockColors().getColor(blockstate, (ILightReader) null, (BlockPos) null, tintIndex);
        }, ModBlocks.CORK_OAK_LEAVES.get());
    }
}
