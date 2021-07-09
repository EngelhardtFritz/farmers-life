package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        // ---------------------------------------- REGISTER TO FORGE TAGS ----------------------------------------
        this.registerForgeBlockTags();

        // ---------------------------------------- REGISTER TO MINECRAFT TAGS ----------------------------------------
        this.registerMinecraftBlockTags();

        // ---------------------------------------- SOILS BLOCKS ----------------------------------------
        getBuilder(ModTags.Blocks.SOILS)
                .add(ModTags.Blocks.WINE_GRAPE_SOILS);

        // ---------------------------------------- WINE GRAPE SOIL BLOCKS ----------------------------------------
        getBuilder(ModTags.Blocks.WINE_GRAPE_SOILS)
                .add(Blocks.CLAY)
                .add(Tags.Blocks.DIRT)
                .add(BlockTags.SAND)
                .add(Tags.Blocks.GRAVEL);

        // ---------------------------------------- GRASS BLOCKS ----------------------------------------
        getBuilder(ModTags.Blocks.GRASS_BLOCKS)
                .add(Blocks.GRASS_BLOCK)
                .add(ModBlocks.SUBMERGED_GRASS_FARMLAND.get())
                .add(ModBlocks.GRASS_SMALL_DAM.get())
                .add(ModBlocks.GRASS_LARGE_DAM.get());

        // ---------------------------------------- FARMLAND ----------------------------------------
        getBuilder(ModTags.Blocks.FARMLANDS)
                .add(Blocks.FARMLAND)
                .add(ModBlocks.GARDEN_FARMLAND.get());

        // ---------------------------------------- SUBMERGED FARMLAND ----------------------------------------
        getBuilder(ModTags.Blocks.SUBMERGED_FARMLAND)
                .add(ModBlocks.SUBMERGED_DIRT_FARMLAND.get())
                .add(ModBlocks.SUBMERGED_GRASS_FARMLAND.get());

        getBuilder(ModTags.Blocks.GRASS_SUBMERGED_FARMLAND)
                .add(ModBlocks.SUBMERGED_GRASS_FARMLAND.get());

        // ---------------------------------------- CROPS - RICE ----------------------------------------
        getBuilder(ModTags.Blocks.CROPS_RICE)
                .add(ModBlocks.RICE_JAPONICA.get())
                .add(ModBlocks.RICE_BLACK_JAPONICA.get())
                .add(ModBlocks.RICE_INDICA.get())
                .add(ModBlocks.RICE_YAMADANISHIKI.get());

        // ---------------------------------------- CROPS - WINE GRAPES ----------------------------------------
        getBuilder(ModTags.Blocks.CROPS_WINE_GRAPES)
                .add(ModBlocks.BARBERA_TRELLIS_CROP.get())
                .add(ModBlocks.CABERNET_TRELLIS_CROP.get())
                .add(ModBlocks.MERLOT_TRELLIS_CROP.get())
                .add(ModBlocks.RED_GLOBE_TRELLIS_CROP.get())
                .add(ModBlocks.KOSHU_TRELLIS_CROP.get())
                .add(ModBlocks.RIESLING_TRELLIS_CROP.get())
                .add(ModBlocks.SULTANA_TRELLIS_CROP.get());

        // ---------------------------------------- CROPS - CORN ----------------------------------------
        getBuilder(ModTags.Blocks.CROPS_CORN)
                .add(ModBlocks.CORN.get());

        // ---------------------------------------- TRELLISES ----------------------------------------
        getBuilder(ModTags.Blocks.TRELLISES)
                .add(ModBlocks.WOOD_TRELLIS.get());

        // ---------------------------------------- COMPRESSED BLOCKS ----------------------------------------
        getBuilder(ModTags.Blocks.COMPRESSED)
                .add(ModTags.Blocks.COMPRESSED_FERTILIZER)
                .add(ModBlocks.COMPRESSED_ROCK_SALT.get(), ModBlocks.COMPRESSED_SEA_SALT.get());

        // ---------------------------------------- COMPRESSED FERTILIZER ----------------------------------------
        getBuilder(ModTags.Blocks.COMPRESSED_FERTILIZER)
                .add(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get())
                .add(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get())
                .add(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get());

        // ---------------------------------------- CORK OAK TAG ----------------------------------------
        getBuilder(ModTags.Blocks.CORK_OAK_LOGS)
                .add(ModBlocks.CORK_OAK_LOG.get())
                .add(ModBlocks.STRIPPED_CORK_OAK_LOG.get());

    }

    private void registerForgeBlockTags() {
        // ---------------------------------------- GLASS ----------------------------------------
        getBuilder(Tags.Blocks.GLASS_BLACK).add(ModBlocks.BLACK_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_BLUE).add(ModBlocks.BLUE_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_BROWN).add(ModBlocks.BROWN_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_COLORLESS).add(ModBlocks.GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_CYAN).add(ModBlocks.CYAN_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_GRAY).add(ModBlocks.GRAY_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_GREEN).add(ModBlocks.GREEN_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_LIGHT_BLUE).add(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_LIGHT_GRAY).add(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_LIME).add(ModBlocks.LIME_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_MAGENTA).add(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_ORANGE).add(ModBlocks.ORANGE_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_PINK).add(ModBlocks.PINK_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_PURPLE).add(ModBlocks.PURPLE_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_RED).add(ModBlocks.RED_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_WHITE).add(ModBlocks.WHITE_STAINED_GLASS_FLOOR.get());
        getBuilder(Tags.Blocks.GLASS_YELLOW).add(ModBlocks.YELLOW_STAINED_GLASS_FLOOR.get());

        // ---------------------------------------- STAINED GLASS ----------------------------------------
        getBuilder(Tags.Blocks.STAINED_GLASS)
                .add(ModBlocks.BLACK_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.BLUE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.BROWN_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.CYAN_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.GRAY_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.GREEN_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.LIME_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.ORANGE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.PINK_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.PURPLE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.RED_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.WHITE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.YELLOW_STAINED_GLASS_FLOOR.get());

        // ---------------------------------------- DIRT ----------------------------------------
        getBuilder(Tags.Blocks.DIRT).add(ModBlocks.GARDEN_DIRT.get());
    }

    private void registerMinecraftBlockTags() {
        // ---------------------------------------- IMPERMEABLE ----------------------------------------
        // Blocks where water can not drip through
        getBuilder(BlockTags.IMPERMEABLE)
                .add(ModBlocks.GLASS_FLOOR.get())
                .add(ModBlocks.BLACK_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.BLUE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.BROWN_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.CYAN_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.GRAY_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.GREEN_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.LIME_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.ORANGE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.PINK_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.PURPLE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.RED_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.WHITE_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.YELLOW_STAINED_GLASS_FLOOR.get())
                .add(ModBlocks.GRASS_SMALL_DAM.get())
                .add(ModBlocks.GRASS_LARGE_DAM.get());

        // ---------------------------------------- DOORS ----------------------------------------
        getBuilder(BlockTags.DOORS)
                .add(ModBlocks.MIDDLE_GLASS_DOOR.get());

        // ---------------------------------------- CROPS ----------------------------------------
        getBuilder(BlockTags.CROPS)
                .add(ModBlocks.PEAT_MOSS.get())
                .add(ModTags.Blocks.CROPS_RICE)
                .add(ModTags.Blocks.CROPS_WINE_GRAPES)
                .add(ModTags.Blocks.CROPS_CORN);

        // ---------------------------------------- LEAVES ----------------------------------------
        getBuilder(BlockTags.LEAVES)
                .add(ModBlocks.CORK_OAK_LEAVES.get());

        // ---------------------------------------- SAPLING ----------------------------------------
        getBuilder(BlockTags.SAPLINGS)
                .add(ModBlocks.CORK_OAK_SAPLING.get());

        // ---------------------------------------- LOGS ----------------------------------------
        getBuilder(BlockTags.LOGS)
                .add(ModTags.Blocks.CORK_OAK_LOGS);
    }
}
