package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;

public class ClientSetup {

    public static void setRenderLayers() {
        FarmersLife.LOGGER.debug("[" + FarmersLife.MOD_ID + "] Setting Render Layers started!");
        RenderType renderCutoutMipmapped = RenderType.getCutoutMipped();
        RenderTypeLookup.setRenderLayer(ModBlocks.PEAT_MOSS_GRASS.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.SHORT_GRASS.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.SUBMERGED_GRASS_FARMLAND.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_SMALL_DAM.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_SMALL_DAM_JAPONICA.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_SMALL_DAM_BLACK_JAPONICA.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_SMALL_DAM_INDICA.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_SMALL_DAM_YAMADANISHIKI.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_LARGE_DAM.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_LARGE_DAM_JAPONICA.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_LARGE_DAM_BLACK_JAPONICA.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_LARGE_DAM_INDICA.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRASS_LARGE_DAM_YAMADANISHIKI.get(), renderCutoutMipmapped);
        RenderTypeLookup.setRenderLayer(ModBlocks.CORK_OAK_LEAVES.get(), renderCutoutMipmapped);

        RenderType renderCutout = RenderType.getCutout();
        RenderTypeLookup.setRenderLayer(ModBlocks.GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.MIDDLE_GLASS_DOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.WHITE_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.ORANGE_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.YELLOW_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.LIME_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.PINK_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRAY_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.CYAN_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.PURPLE_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.BROWN_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.GREEN_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.RED_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.BLACK_STAINED_GLASS_FLOOR.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.FERMENTING_BARREL.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.WOOD_FLUID_STORAGE_BARREL.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.FILTRATION_MACHINE.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.FINING_MACHINE.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.BOTTLING_MACHINE.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.AGING_WINE_RACK.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.CORK_OAK_SAPLING.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.BOILING_CAULDRON.get(), renderCutout);
        // Crops
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_SMALL_DAM_JAPONICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_SMALL_DAM_BLACK_JAPONICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_SMALL_DAM_INDICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_SMALL_DAM_YAMADANISHIKI.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_LARGE_DAM_JAPONICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_LARGE_DAM_BLACK_JAPONICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_LARGE_DAM_INDICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_LARGE_DAM_YAMADANISHIKI.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.PEAT_MOSS.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.RICE_JAPONICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.RICE_BLACK_JAPONICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.RICE_INDICA.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.RICE_YAMADANISHIKI.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.BARBERA_TRELLIS_CROP.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.CABERNET_TRELLIS_CROP.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.MERLOT_TRELLIS_CROP.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.RED_GLOBE_TRELLIS_CROP.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.KOSHU_TRELLIS_CROP.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.RIESLING_TRELLIS_CROP.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.SULTANA_TRELLIS_CROP.get(), renderCutout);
        RenderTypeLookup.setRenderLayer(ModBlocks.CORN.get(), renderCutout);

        RenderType renderTranslucent = RenderType.getTranslucent();
        RenderTypeLookup.setRenderLayer(ModBlocks.MIDDLE_GLASS_DOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.WHITE_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.ORANGE_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.YELLOW_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.LIME_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.PINK_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.GRAY_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.CYAN_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.PURPLE_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.BLUE_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.BROWN_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.GREEN_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.RED_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModBlocks.BLACK_STAINED_GLASS_FLOOR.get(), renderTranslucent);
        // FLUIDS
        RenderTypeLookup.setRenderLayer(ModFluids.BARBERA_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.BARBERA_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.CABERNET_SAUVIGNON_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.CABERNET_SAUVIGNON_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.MERLOT_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.MERLOT_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RED_GLOBE_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RED_GLOBE_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOSHU_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOSHU_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RIESLING_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RIESLING_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_BARBERA_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_BARBERA_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_MERLOT_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_MERLOT_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_RED_GLOBE_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_RED_GLOBE_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_KOSHU_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_KOSHU_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_RIESLING_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_RIESLING_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.BARBERA_WINE_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.BARBERA_WINE_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.CABERNET_SAUVIGNON_WINE_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.CABERNET_SAUVIGNON_WINE_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.MERLOT_WINE_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.MERLOT_WINE_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RED_GLOBE_WINE_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RED_GLOBE_WINE_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOSHU_WINE_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOSHU_WINE_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RIESLING_WINE_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RIESLING_WINE_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_WATER_MIX_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_WATER_MIX_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RICE_WATER_MIX_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.RICE_WATER_MIX_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_RICE_WATER_MIX_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_RICE_WATER_MIX_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_RICE_SHUBO_WATER_MIX_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.SAKE_FLUID.get(), renderTranslucent);
        RenderTypeLookup.setRenderLayer(ModFluids.SAKE_FLOWING.get(), renderTranslucent);


        // -------------------------  SET MULTIPLE RENDER LAYERS -------------------------
        RenderTypeLookup.setRenderLayer(ModBlocks.DIRT_DRYING_BED.get(), ClientSetup::isSolidOrTranslucentLayer);
        RenderTypeLookup.setRenderLayer(ModBlocks.DRYING_MACHINE.get(), ClientSetup::isSolidOrTranslucentLayer);
        FarmersLife.LOGGER.debug("[" + FarmersLife.MOD_ID + "] Setting Render Layers finished!");
    }

    private static boolean isSolidOrTranslucentLayer(RenderType layerToCheck) {
        return layerToCheck == RenderType.getSolid() || layerToCheck == RenderType.getTranslucent();
    }
}
