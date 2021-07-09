package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.api.data.provider.LootTableGenerators;
import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.BiConsumer;

public class ModLootTableProvider implements IDataProvider {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator generator;

    public ModLootTableProvider(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        registerLootTables((id, lootTable) -> {
            final Path path = getPath(id);
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("[FarmersLife] Could not save Loot Table {}", path, e);
            }
        });
    }

    @Override
    public String getName() {
        return "[FarmersLife] Loot Table generator";
    }

    protected void registerLootTables(BiConsumer<ResourceLocation, LootTable> consumer) {
        // ------------------------------------- DEFAULT LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.GARDEN_DIRT.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.GARDEN_DIRT_ITEM.get()), consumer);
        registerBlock(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER_ITEM.get()), consumer);
        registerBlock(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER_ITEM.get()), consumer);
        registerBlock(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER_ITEM.get()), consumer);
        registerBlock(ModBlocks.WOOD_TRELLIS.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.WOOD_TRELLIS_ITEM.get()), consumer);
        registerBlock(ModBlocks.PEAT_MOSS.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.PEAT_MOSS_ITEM.get()), consumer);
        registerBlock(ModBlocks.COMPRESSED_ROCK_SALT.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.COMPRESSED_ROCK_SALT_ITEM.get()), consumer);
        registerBlock(ModBlocks.COMPRESSED_SEA_SALT.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.COMPRESSED_SEA_SALT_ITEM.get()), consumer);
        registerBlock(ModBlocks.DIRT_DRYING_BED.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.DIRT_DRYING_BED_ITEM.get()), consumer);
        registerBlock(ModBlocks.SUBMERGED_GRASS_FARMLAND.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.SUBMERGED_GRASS_FARMLAND_ITEM.get()), consumer);
        registerBlock(ModBlocks.SUBMERGED_DIRT_FARMLAND.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.SUBMERGED_DIRT_FARMLAND_ITEM.get()), consumer);
        registerBlock(ModBlocks.DIRT_SMALL_DAM.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.DIRT_SMALL_DAM_ITEM.get()), consumer);
        registerBlock(ModBlocks.DIRT_LARGE_DAM.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.DIRT_LARGE_DAM_ITEM.get()), consumer);
        registerBlock(ModBlocks.MIDDLE_GLASS_DOOR.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.MIDDLE_GLASS_DOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.DRYING_MACHINE.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.DRYING_MACHINE_ITEM.get()), consumer);
        registerBlock(ModBlocks.STOMPING_BARREL.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.STOMPING_BARREL_ITEM.get()), consumer);
        registerBlock(ModBlocks.WOOD_WINE_PRESS.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.WOOD_WINE_PRESS_ITEM.get()), consumer);
        registerBlock(ModBlocks.FERTILIZER_COMPOSTER.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.FERTILIZER_COMPOSTER_ITEM.get()), consumer);
        registerBlock(ModBlocks.FERMENTING_BARREL.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.FERMENTING_BARREL_ITEM.get()), consumer);
        registerBlock(ModBlocks.WOOD_FLUID_STORAGE_BARREL.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.WOOD_FLUID_STORAGE_BARREL_ITEM.get()), consumer);
        registerBlock(ModBlocks.FILTRATION_MACHINE.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.FILTRATION_MACHINE_ITEM.get()), consumer);
        registerBlock(ModBlocks.FINING_MACHINE.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.FINING_MACHINE_ITEM.get()), consumer);
        registerBlock(ModBlocks.AGING_WINE_RACK.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.AGING_WINE_RACK_ITEM.get()), consumer);
        registerBlock(ModBlocks.ACACIA_WINE_RACK.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.ACACIA_WINE_RACK_ITEM.get()), consumer);
        registerBlock(ModBlocks.OAK_WINE_RACK.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.OAK_WINE_RACK_ITEM.get()), consumer);
        registerBlock(ModBlocks.BIRCH_WINE_RACK.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.BIRCH_WINE_RACK_ITEM.get()), consumer);
        registerBlock(ModBlocks.DARK_OAK_WINE_RACK.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.DARK_OAK_WINE_RACK_ITEM.get()), consumer);
        registerBlock(ModBlocks.JUNGLE_WINE_RACK.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.JUNGLE_WINE_RACK_ITEM.get()), consumer);
        registerBlock(ModBlocks.SPRUCE_WINE_RACK.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.SPRUCE_WINE_RACK_ITEM.get()), consumer);
        registerBlock(ModBlocks.STRIPPED_CORK_OAK_LOG.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.STRIPPED_CORK_OAK_LOG_ITEM.get()), consumer);
        registerBlock(ModBlocks.CORK_OAK_LOG.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.CORK_OAK_LOG_ITEM.get()), consumer);
        registerBlock(ModBlocks.FARMER_WORKBENCH.get(), LootTableGenerators.addDefaultLootTable(ModBlocks.FARMER_WORKBENCH_ITEM.get()), consumer);

        // ------------------------------------- ADD DEFAULT WITH BIONOMIAL RANGE LOOT TABLES -------------------------------------
//        registerBlock(ModBlocks.RICE_JAPONICA.get(), LootTableGenerators.addDefaultWithBinomialRangeLootTable(ModBlocks.RICE_JAPONICA_SEED_ITEM.get(), ModItems.RICE_JAPONICA_CROP.get(), 0.25F), consumer);

        // ------------------------------------- DEFAULT WITH SILK TOUCH AND FORTUNE LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.SALT_ORE.get(), LootTableGenerators.addDefaultSilkTouchFortuneLootTable(ModBlocks.SALT_ORE_ITEM.get(), ModItems.ROCK_SALT.get(), 2), consumer);

        // ------------------------------------- DEFAULT WITH RANGE AND SILK TOUCH LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.PEAT_STAGE_ONE.get(), LootTableGenerators.addDefaultWithRangeAndSilkTouchLootTable(ModItems.RAW_PEAT_FERTILIZER.get(), ModBlocks.PEAT_STAGE_ONE.get(), 0f, 1f), consumer);
        registerBlock(ModBlocks.PEAT_STAGE_TWO.get(), LootTableGenerators.addDefaultWithRangeAndSilkTouchLootTable(ModItems.RAW_PEAT_FERTILIZER.get(), ModBlocks.PEAT_STAGE_TWO.get(), 1f, 2f), consumer);
        registerBlock(ModBlocks.PEAT_STAGE_THREE.get(), LootTableGenerators.addDefaultWithRangeAndSilkTouchLootTable(ModItems.RAW_PEAT_FERTILIZER.get(), ModBlocks.PEAT_STAGE_THREE.get(), 2f, 3f), consumer);

        // ------------------------------------- MULTIPLE LOOT WITH CHANCE, SILK TOUCH AND SHEARS LOOT TABLES -------------------------------------
        // TODO: Reminder to add new blocks if new seeds exist which should be lootable by the player
        // TODO: Remove peat moss from loot table -> is generated in the world
        registerBlock(ModBlocks.SHORT_GRASS.get(), LootTableGenerators.addMultipleLootWithChanceSilkAndShearsLootTable(1, 1F, 0.125F, ModBlocks.SHORT_GRASS_ITEM.get(), ModBlocks.PEAT_MOSS_ITEM.get()), consumer);

        // ------------------------------------- DEFAULT CROP LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.RICE_JAPONICA.get(), LootTableGenerators.addDefaultMinecraftCropLootTable(ModBlocks.RICE_JAPONICA_SEED_ITEM.get(), ModItems.RICE_JAPONICA_CROP.get(), ModBlocks.RICE_JAPONICA.get(), BlockStateProperties.AGE_0_5, 5, 1, 0.4F, 1, 0.25F), consumer);
        registerBlock(ModBlocks.RICE_BLACK_JAPONICA.get(), LootTableGenerators.addDefaultMinecraftCropLootTable(ModBlocks.RICE_BLACK_JAPONICA_SEED_ITEM.get(), ModItems.RICE_BLACK_JAPONICA_CROP.get(), ModBlocks.RICE_BLACK_JAPONICA.get(), BlockStateProperties.AGE_0_5, 5, 1, 0.4F, 1, 0.25F), consumer);
        registerBlock(ModBlocks.RICE_INDICA.get(), LootTableGenerators.addDefaultMinecraftCropLootTable(ModBlocks.RICE_INDICA_SEED_ITEM.get(), ModItems.RICE_INDICA_CROP.get(), ModBlocks.RICE_INDICA.get(), BlockStateProperties.AGE_0_5, 5, 1, 0.4F, 1, 0.25F), consumer);
        registerBlock(ModBlocks.RICE_YAMADANISHIKI.get(), LootTableGenerators.addDefaultMinecraftCropLootTable(ModBlocks.RICE_YAMADANISHIKI_SEED_ITEM.get(), ModItems.RICE_YAMADANISHIKI_CROP.get(), ModBlocks.RICE_YAMADANISHIKI.get(), BlockStateProperties.AGE_0_5, 5, 1, 0.4F, 1, 0.25F), consumer);

        // ------------------------------------- DEFAULT CROP LOOT TABLES WITH HUSK -------------------------------------
        registerBlock(ModBlocks.CORN.get(), LootTableGenerators.addDefaultMinecraftCropLootTable(ModBlocks.CORN_SEED_ITEM.get(), ModItems.CORN_CROP.get(), ModItems.CORN_HUSK.get(), ModBlocks.CORN.get(), BlockStateProperties.AGE_0_5, 5, 1, 0.5F, 1, 0.33F, 3, 0.5F), consumer);

        // ------------------------------------- TRELLIS CROP LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.BARBERA_TRELLIS_CROP.get(), LootTableGenerators.addTrellisCropLootTable(ModBlocks.BARBERA_TRELLIS_SEED_ITEM.get(), ModItems.BARBERA_CROP.get(), ModBlocks.BARBERA_TRELLIS_CROP.get(), BlockStateProperties.AGE_0_7, 7, 1, 0.33F), consumer);
        registerBlock(ModBlocks.CABERNET_TRELLIS_CROP.get(), LootTableGenerators.addTrellisCropLootTable(ModBlocks.CABERNET_TRELLIS_SEED_ITEM.get(), ModItems.CABERNET_CROP.get(), ModBlocks.CABERNET_TRELLIS_CROP.get(), BlockStateProperties.AGE_0_7, 7, 1, 0.33F), consumer);
        registerBlock(ModBlocks.MERLOT_TRELLIS_CROP.get(), LootTableGenerators.addTrellisCropLootTable(ModBlocks.MERLOT_TRELLIS_SEED_ITEM.get(), ModItems.MERLOT_CROP.get(), ModBlocks.MERLOT_TRELLIS_CROP.get(), BlockStateProperties.AGE_0_7, 7, 1, 0.33F), consumer);
        registerBlock(ModBlocks.RED_GLOBE_TRELLIS_CROP.get(), LootTableGenerators.addTrellisCropLootTable(ModBlocks.RED_GLOBE_TRELLIS_SEED_ITEM.get(), ModItems.RED_GLOBE_CROP.get(), ModBlocks.RED_GLOBE_TRELLIS_CROP.get(), BlockStateProperties.AGE_0_7, 7, 1, 0.33F), consumer);
        registerBlock(ModBlocks.KOSHU_TRELLIS_CROP.get(), LootTableGenerators.addTrellisCropLootTable(ModBlocks.KOSHU_TRELLIS_SEED_ITEM.get(), ModItems.KOSHU_CROP.get(), ModBlocks.KOSHU_TRELLIS_CROP.get(), BlockStateProperties.AGE_0_7, 7, 1, 0.33F), consumer);
        registerBlock(ModBlocks.RIESLING_TRELLIS_CROP.get(), LootTableGenerators.addTrellisCropLootTable(ModBlocks.RIESLING_TRELLIS_SEED_ITEM.get(), ModItems.RIESLING_CROP.get(), ModBlocks.RIESLING_TRELLIS_CROP.get(), BlockStateProperties.AGE_0_7, 7, 1, 0.33F), consumer);
        registerBlock(ModBlocks.SULTANA_TRELLIS_CROP.get(), LootTableGenerators.addTrellisCropLootTable(ModBlocks.SULTANA_TRELLIS_SEED_ITEM.get(), ModItems.SULTANA_CROP.get(), ModBlocks.SULTANA_TRELLIS_CROP.get(), BlockStateProperties.AGE_0_7, 7, 1, 0.33F), consumer);

        // ------------------------------------- SILK TOUCH WITH ALTERNATIVE LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.GARDEN_FARMLAND.get(), LootTableGenerators.addSilkTouchBlockLootTable(ModBlocks.GARDEN_DIRT_ITEM.get(), ModBlocks.GARDEN_FARMLAND_ITEM.get()), consumer);
        registerBlock(ModBlocks.PEAT_MOSS_GRASS.get(), LootTableGenerators.addSilkTouchBlockLootTable(Blocks.DIRT, Blocks.GRASS_BLOCK), consumer);
        registerBlock(ModBlocks.GRASS_SMALL_DAM.get(), LootTableGenerators.addSilkTouchBlockLootTable(ModBlocks.DIRT_SMALL_DAM_ITEM.get(), ModBlocks.GRASS_SMALL_DAM_ITEM.get()), consumer);
        registerBlock(ModBlocks.GRASS_LARGE_DAM.get(), LootTableGenerators.addSilkTouchBlockLootTable(ModBlocks.DIRT_LARGE_DAM_ITEM.get(), ModBlocks.GRASS_LARGE_DAM_ITEM.get()), consumer);

        // ------------------------------------- LEAVE LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.CORK_OAK_LEAVES.get(), LootTableGenerators.addLeaveLootTable(ModBlocks.CORK_OAK_LEAVES_ITEM.get(), ModBlocks.CORK_OAK_SAPLING_ITEM.get(), Items.STICK, 0.025F, 0.08F), consumer);

        // ------------------------------------- SILK TOUCH ONLY LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.BLACK_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.BLACK_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.BLUE_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.BLUE_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.BROWN_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.BROWN_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.CYAN_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.CYAN_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.GRAY_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.GRAY_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.GREEN_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.GREEN_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.LIME_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.LIME_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.ORANGE_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.ORANGE_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.PINK_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.PINK_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.PURPLE_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.PURPLE_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.RED_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.RED_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.WHITE_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.WHITE_STAINED_GLASS_FLOOR_ITEM.get()), consumer);
        registerBlock(ModBlocks.YELLOW_STAINED_GLASS_FLOOR.get(), LootTableGenerators.addSilkTouchOnlyBlockLootTable(ModBlocks.YELLOW_STAINED_GLASS_FLOOR_ITEM.get()), consumer);

        // ------------------------------------- THRESHING BLOCK LOOT TABLES -------------------------------------
        registerBlock(ModBlocks.THRESHING_RICE_JAPONICA.get(), LootTableGenerators.addThreshingBlockLootTable(ModItems.RICE_GRAINS_JAPONICA.get(), ModItems.CHAFF.get(), ModItems.RICE_HUSK.get()), consumer);
        registerBlock(ModBlocks.THRESHING_RICE_BLACK_JAPONICA.get(), LootTableGenerators.addThreshingBlockLootTable(ModItems.RICE_GRAINS_BLACK_JAPONICA.get(), ModItems.CHAFF.get(), ModItems.RICE_HUSK.get()), consumer);
        registerBlock(ModBlocks.THRESHING_RICE_INDICA.get(), LootTableGenerators.addThreshingBlockLootTable(ModItems.RICE_GRAINS_INDICA.get(), ModItems.CHAFF.get(), ModItems.RICE_HUSK.get()), consumer);
        registerBlock(ModBlocks.THRESHING_RICE_YAMADANISHIKI.get(), LootTableGenerators.addThreshingBlockLootTable(ModItems.RICE_GRAINS_YAMADANISHIKI.get(), ModItems.CHAFF.get(), ModItems.RICE_HUSK.get()), consumer);
        registerSpecial(ModBlocks.THRESHING_RICE_JAPONICA.get(), LootTableGenerators.addThreshingSpecialLootTable(ModItems.RICE_JAPONICA_CROP.get(), ModBlocks.THRESHING_RICE_JAPONICA.get(), ModBlockStateProperties.USES_0_2), consumer);
        registerSpecial(ModBlocks.THRESHING_RICE_BLACK_JAPONICA.get(), LootTableGenerators.addThreshingSpecialLootTable(ModItems.RICE_BLACK_JAPONICA_CROP.get(), ModBlocks.THRESHING_RICE_BLACK_JAPONICA.get(), ModBlockStateProperties.USES_0_2), consumer);
        registerSpecial(ModBlocks.THRESHING_RICE_INDICA.get(), LootTableGenerators.addThreshingSpecialLootTable(ModItems.RICE_INDICA_CROP.get(), ModBlocks.THRESHING_RICE_INDICA.get(), ModBlockStateProperties.USES_0_2), consumer);
        registerSpecial(ModBlocks.THRESHING_RICE_YAMADANISHIKI.get(), LootTableGenerators.addThreshingSpecialLootTable(ModItems.RICE_YAMADANISHIKI_CROP.get(), ModBlocks.THRESHING_RICE_YAMADANISHIKI.get(), ModBlockStateProperties.USES_0_2), consumer);
    }

    private Path getPath(ResourceLocation id) {
        return generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/loot_tables/" + id.getPath() + ".json");
    }

    private static void registerBlock(Block block, LootTable lootTable, BiConsumer<ResourceLocation, LootTable> consumer) {
        final ResourceLocation registryName = block.getRegistryName();
        consumer.accept(new ResourceLocation(registryName.getNamespace(), "blocks/" + registryName.getPath()), lootTable);
    }

    private static void registerSpecial(Block block, LootTable lootTable, BiConsumer<ResourceLocation, LootTable> consumer) {
        final ResourceLocation registryName = block.getRegistryName();
        consumer.accept(new ResourceLocation(registryName.getNamespace(), "special/" + registryName.getPath()), lootTable);
    }
}
