package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.objects.blocks.*;
import com.asheriit.asheriitsfarmerslife.objects.blocks.machines.*;
import com.asheriit.asheriitsfarmerslife.utils.BlockType;
import com.asheriit.asheriitsfarmerslife.world.feature.CorkOakTree;
import net.minecraft.block.Block;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockNamedItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FarmersLife.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FarmersLife.MOD_ID);


    // --------------------------------------------- TEST BLOCKS ---------------------------------------------
    public static final RegistryObject<Block> TEST_BLOCK_A = BLOCKS.register("test_block_a", () ->
            new Block(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F).notSolid()));
    public static final RegistryObject<BlockItem> TEST_BLOCK_A_ITEM = ITEMS.register("test_block_a", () ->
            new BlockItem(TEST_BLOCK_A.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Block> TEST_BLOCK_B = BLOCKS.register("test_block_b", () ->
            new Block(Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.CROP).notSolid()));
    public static final RegistryObject<BlockItem> TEST_BLOCK_B_ITEM = ITEMS.register("test_block_b", () ->
            new BlockItem(TEST_BLOCK_B.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));


    // --------------------------------------------- CROP BLOCKS ---------------------------------------------
    // --------------------------------------------- LARGE CROP BLOCKS ---------------------------------------------
    public static final RegistryObject<Block> CORN = BLOCKS.register("corn", () ->
            new LargeCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.CROP)));
    // --------------------------------------------- SUBMERGED CROP BLOCKS ---------------------------------------------
    public static final RegistryObject<Block> RICE_JAPONICA = BLOCKS.register("rice_japonica", () ->
            new WaterloggedCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> RICE_BLACK_JAPONICA = BLOCKS.register("rice_black_japonica", () ->
            new WaterloggedCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> RICE_INDICA = BLOCKS.register("rice_indica", () ->
            new WaterloggedCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> RICE_YAMADANISHIKI = BLOCKS.register("rice_yamadanishiki", () ->
            new WaterloggedCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.CROP)));
    // Threshing Blocks START
    public static final RegistryObject<Block> THRESHING_RICE_JAPONICA = BLOCKS.register("threshing_rice_japonica", () ->
            new ThreshingBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> THRESHING_RICE_BLACK_JAPONICA = BLOCKS.register("threshing_rice_black_japonica", () ->
            new ThreshingBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> THRESHING_RICE_INDICA = BLOCKS.register("threshing_rice_indica", () ->
            new ThreshingBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> THRESHING_RICE_YAMADANISHIKI = BLOCKS.register("threshing_rice_yamadanishiki", () ->
            new ThreshingBlock(Block.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F).sound(SoundType.PLANT)));
    // Threshing Blocks END

    // --------------------------------------------- BLOCKS ---------------------------------------------
    // Dam Blocks START
    public static final RegistryObject<Block> GRASS_SMALL_DAM = BLOCKS.register("grass_small_dam", () ->
            new GrassDamBlock(BlockType.SMALL, false, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_SMALL_DAM_JAPONICA = BLOCKS.register("grass_small_dam_japonica", () ->
            new GrassDamBlock(BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_SMALL_DAM_BLACK_JAPONICA = BLOCKS.register("grass_small_dam_black_japonica", () ->
            new GrassDamBlock(BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_SMALL_DAM_INDICA = BLOCKS.register("grass_small_dam_indica", () ->
            new GrassDamBlock(BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_SMALL_DAM_YAMADANISHIKI = BLOCKS.register("grass_small_dam_yamadanishiki", () ->
            new GrassDamBlock(BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));

    public static final RegistryObject<Block> DIRT_SMALL_DAM = BLOCKS.register("dirt_small_dam", () ->
            new DirtDamBlock(GRASS_SMALL_DAM.get(), BlockType.SMALL, false, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_SMALL_DAM_JAPONICA = BLOCKS.register("dirt_small_dam_japonica", () ->
            new DirtDamBlock(GRASS_SMALL_DAM_JAPONICA.get(), BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_SMALL_DAM_BLACK_JAPONICA = BLOCKS.register("dirt_small_dam_black_japonica", () ->
            new DirtDamBlock(GRASS_SMALL_DAM_BLACK_JAPONICA.get(), BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_SMALL_DAM_INDICA = BLOCKS.register("dirt_small_dam_indica", () ->
            new DirtDamBlock(GRASS_SMALL_DAM_INDICA.get(), BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_SMALL_DAM_YAMADANISHIKI = BLOCKS.register("dirt_small_dam_yamadanishiki", () ->
            new DirtDamBlock(GRASS_SMALL_DAM_YAMADANISHIKI.get(), BlockType.SMALL, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));

    public static final RegistryObject<Block> GRASS_LARGE_DAM = BLOCKS.register("grass_large_dam", () ->
            new GrassDamBlock(BlockType.LARGE, false, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_LARGE_DAM_JAPONICA = BLOCKS.register("grass_large_dam_japonica", () ->
            new GrassDamBlock(BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_LARGE_DAM_BLACK_JAPONICA = BLOCKS.register("grass_large_dam_black_japonica", () ->
            new GrassDamBlock(BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_LARGE_DAM_INDICA = BLOCKS.register("grass_large_dam_indica", () ->
            new GrassDamBlock(BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));
    public static final RegistryObject<Block> GRASS_LARGE_DAM_YAMADANISHIKI = BLOCKS.register("grass_large_dam_yamadanishiki", () ->
            new GrassDamBlock(BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().hardnessAndResistance(0.5F, 1.0F)));

    public static final RegistryObject<Block> DIRT_LARGE_DAM = BLOCKS.register("dirt_large_dam", () ->
            new DirtDamBlock(GRASS_LARGE_DAM.get(), BlockType.LARGE, false, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_LARGE_DAM_JAPONICA = BLOCKS.register("dirt_large_dam_japonica", () ->
            new DirtDamBlock(GRASS_LARGE_DAM_JAPONICA.get(), BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_LARGE_DAM_BLACK_JAPONICA = BLOCKS.register("dirt_large_dam_black_japonica", () ->
            new DirtDamBlock(GRASS_LARGE_DAM_BLACK_JAPONICA.get(), BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_LARGE_DAM_INDICA = BLOCKS.register("dirt_large_dam_indica", () ->
            new DirtDamBlock(GRASS_LARGE_DAM_INDICA.get(), BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    public static final RegistryObject<Block> DIRT_LARGE_DAM_YAMADANISHIKI = BLOCKS.register("dirt_large_dam_yamadanishiki", () ->
            new DirtDamBlock(GRASS_LARGE_DAM_YAMADANISHIKI.get(), BlockType.LARGE, true, Block.Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().notSolid().tickRandomly().hardnessAndResistance(0.4F, 1.0F)));
    // Dam Blocks END
    // Functional Blocks START
    public static final RegistryObject<Block> DIRT_DRYING_BED = BLOCKS.register("dirt_drying_bed", () ->
            new DryingBedBlock(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).notSolid().tickRandomly().hardnessAndResistance(1.0F, 2.5F)));
    public static final RegistryObject<Block> DRYING_MACHINE = BLOCKS.register("drying_machine", () ->
            new DryingMachineBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.5F, 2.5F)));
    public static final RegistryObject<Block> WOOD_WINE_PRESS = BLOCKS.register("wood_wine_press", () ->
            new WinePressBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.5F, 3.0F)));
    public static final RegistryObject<Block> WOOD_WINE_PRESS_HANDLE = BLOCKS.register("wood_wine_press_handle", () ->
            new WinePressHandleBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.0F, 1.5F)));
    public static final RegistryObject<Block> STOMPING_BARREL = BLOCKS.register("stomping_barrel", () ->
            new StompingBarrelBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.5F, 3.0F)));
    public static final RegistryObject<Block> FERTILIZER_COMPOSTER = BLOCKS.register("fertilizer_composter", () ->
            new FertilizerComposterBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.5F, 2.5F)));
    public static final RegistryObject<Block> FERMENTING_BARREL = BLOCKS.register("fermenting_barrel", () ->
            new FermentationBarrelBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(3.0F, 3.0F)));
    public static final RegistryObject<Block> WOOD_FLUID_STORAGE_BARREL = BLOCKS.register("wood_fluid_storage_barrel", () ->
            new FluidStorageBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.5F, 2.5F)));
    public static final RegistryObject<Block> FILTRATION_MACHINE = BLOCKS.register("filtration_machine", () ->
            new FiltrationMachineBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.PICKAXE).notSolid().hardnessAndResistance(2.5F, 3.0F)));
    public static final RegistryObject<Block> FINING_MACHINE = BLOCKS.register("fining_machine", () ->
            new FiningMachineBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.PICKAXE).notSolid().hardnessAndResistance(2.5F, 3.0F)));
    public static final RegistryObject<Block> BOTTLING_MACHINE = BLOCKS.register("bottling_machine", () ->
            new BottlingMachineBlock(Block.Properties.create(Material.IRON).harvestLevel(1).harvestTool(ToolType.PICKAXE).notSolid().hardnessAndResistance(3.5F, 3.5F)));
    public static final RegistryObject<Block> AGING_WINE_RACK = BLOCKS.register("aging_wine_rack", () ->
            new AgingWineRackBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.0F, 1.5F)));
    public static final RegistryObject<Block> FARMER_WORKBENCH = BLOCKS.register("farmer_workbench", () ->
            new FarmerCraftingTableBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(2.0F, 1.5F)));
    public static final RegistryObject<Block> BOILING_CAULDRON = BLOCKS.register("boiling_cauldron", () ->
            new BoilingCauldronBlock(Block.Properties.create(Material.IRON, MaterialColor.STONE).hardnessAndResistance(2.0F).notSolid()));
    public static final RegistryObject<Block> TEMPERATURE_CHAMBER = BLOCKS.register("temperature_chamber", () ->
            new TemperatureChamberBlock(Block.Properties.create(Material.IRON, MaterialColor.STONE).hardnessAndResistance(2.0F).notSolid()));
//    public static final RegistryObject<Block> MORTAR_AND_PESTLE = BLOCKS.register("mortar_and_pestle", () ->
//            new MortarAndPestleBlock(Block.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F).notSolid()));
    // Functional Blocks END
    // Wine Racks START
    public static final RegistryObject<Block> ACACIA_WINE_RACK = BLOCKS.register("acacia_wine_rack", () ->
            new WineRackBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(1.5F, 1.5F)));
    public static final RegistryObject<Block> BIRCH_WINE_RACK = BLOCKS.register("birch_wine_rack", () ->
            new WineRackBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(1.5F, 1.5F)));
    public static final RegistryObject<Block> DARK_OAK_WINE_RACK = BLOCKS.register("dark_oak_wine_rack", () ->
            new WineRackBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(1.5F, 1.5F)));
    public static final RegistryObject<Block> JUNGLE_WINE_RACK = BLOCKS.register("jungle_wine_rack", () ->
            new WineRackBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(1.5F, 1.5F)));
    public static final RegistryObject<Block> OAK_WINE_RACK = BLOCKS.register("oak_wine_rack", () ->
            new WineRackBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(1.5F, 1.5F)));
    public static final RegistryObject<Block> SPRUCE_WINE_RACK = BLOCKS.register("spruce_wine_rack", () ->
            new WineRackBlock(Block.Properties.create(Material.WOOD).harvestLevel(0).harvestTool(ToolType.AXE).notSolid().hardnessAndResistance(1.5F, 1.5F)));
    // Wine Racks END
    // Salt START
    public static final RegistryObject<Block> SALT_ORE = BLOCKS.register("salt_ore", () ->
            new ExperienceBlock(0, 2, Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(2.0F, 2.0F)));
    public static final RegistryObject<Block> COMPRESSED_ROCK_SALT = BLOCKS.register("compressed_rock_salt", () ->
            new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(1.5F, 1.5F)));
    public static final RegistryObject<Block> COMPRESSED_SEA_SALT = BLOCKS.register("compressed_sea_salt", () ->
            new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(1.5F, 1.5F)));
    // Salt END
    // GrassType START
    public static final RegistryObject<Block> SHORT_GRASS = BLOCKS.register("short_grass", () ->
            new ShortGrassBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().hardnessAndResistance(0F).sound(SoundType.PLANT)));
    // GrassType END
    // PeatMoss START
    public static final RegistryObject<Block> PEAT_MOSS = BLOCKS.register("peat_moss", () ->
            new PeatMossBlock(Block.Properties.create(Material.PLANTS).tickRandomly().hardnessAndResistance(0F).doesNotBlockMovement().sound(SoundType.PLANT)));
    public static final RegistryObject<Block> PEAT_MOSS_GRASS = BLOCKS.register("peat_moss_grass", () ->
            new GrassBlock(Block.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT)));
    public static final RegistryObject<Block> PEAT_STAGE_ONE = BLOCKS.register("peat_stage_one", () ->
            new Block(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> PEAT_STAGE_TWO = BLOCKS.register("peat_stage_two", () ->
            new Block(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> PEAT_STAGE_THREE = BLOCKS.register("peat_stage_three", () ->
            new Block(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.7F).sound(SoundType.GROUND)));
    // PeatMoss END
    // Trellis START
    public static final RegistryObject<Block> WOOD_TRELLIS = BLOCKS.register("wood_trellis", () ->
            new TrellisBlock(0.1F, Block.Properties.create(Material.GLASS).hardnessAndResistance(1.8F, 2.5F).sound(SoundType.WOOD).notSolid()));
    // Trellis END
    // TrellisCrop START
    public static final RegistryObject<Block> BARBERA_TRELLIS_CROP = BLOCKS.register("barbera", () ->
            new TrellisCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> CABERNET_TRELLIS_CROP = BLOCKS.register("cabernet_sauvignon", () ->
            new TrellisCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> MERLOT_TRELLIS_CROP = BLOCKS.register("merlot", () ->
            new TrellisCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> RED_GLOBE_TRELLIS_CROP = BLOCKS.register("red_globe", () ->
            new TrellisCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> KOSHU_TRELLIS_CROP = BLOCKS.register("koshu", () ->
            new TrellisCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> RIESLING_TRELLIS_CROP = BLOCKS.register("riesling", () ->
            new TrellisCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.CROP)));
    public static final RegistryObject<Block> SULTANA_TRELLIS_CROP = BLOCKS.register("sultana", () ->
            new TrellisCropBlock(Block.Properties.create(Material.PLANTS).notSolid().tickRandomly().hardnessAndResistance(0.1F).sound(SoundType.CROP)));
    // TrellisCrop END
    // GlassFloor START
    public static final RegistryObject<Block> MIDDLE_GLASS_DOOR = BLOCKS.register("glass_door", () ->
            new MiddleDoorBlock(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> GLASS_FLOOR = BLOCKS.register("glass_floor", () ->
            new GlassFloorBlock(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> WHITE_STAINED_GLASS_FLOOR = BLOCKS.register("white_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.WHITE, Block.Properties.create(Material.GLASS, DyeColor.WHITE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> ORANGE_STAINED_GLASS_FLOOR = BLOCKS.register("orange_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.ORANGE, Block.Properties.create(Material.GLASS, DyeColor.ORANGE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> MAGENTA_STAINED_GLASS_FLOOR = BLOCKS.register("magenta_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.MAGENTA, Block.Properties.create(Material.GLASS, DyeColor.MAGENTA).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_GLASS_FLOOR = BLOCKS.register("light_blue_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.LIGHT_BLUE, Block.Properties.create(Material.GLASS, DyeColor.LIGHT_BLUE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> YELLOW_STAINED_GLASS_FLOOR = BLOCKS.register("yellow_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.YELLOW, Block.Properties.create(Material.GLASS, DyeColor.YELLOW).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> LIME_STAINED_GLASS_FLOOR = BLOCKS.register("lime_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.LIME, Block.Properties.create(Material.GLASS, DyeColor.LIME).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> PINK_STAINED_GLASS_FLOOR = BLOCKS.register("pink_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.PINK, Block.Properties.create(Material.GLASS, DyeColor.PINK).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> GRAY_STAINED_GLASS_FLOOR = BLOCKS.register("gray_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.GRAY, Block.Properties.create(Material.GLASS, DyeColor.GRAY).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_GLASS_FLOOR = BLOCKS.register("light_gray_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.LIGHT_GRAY, Block.Properties.create(Material.GLASS, DyeColor.LIGHT_GRAY).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> CYAN_STAINED_GLASS_FLOOR = BLOCKS.register("cyan_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.CYAN, Block.Properties.create(Material.GLASS, DyeColor.CYAN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> PURPLE_STAINED_GLASS_FLOOR = BLOCKS.register("purple_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.PURPLE, Block.Properties.create(Material.GLASS, DyeColor.PURPLE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> BLUE_STAINED_GLASS_FLOOR = BLOCKS.register("blue_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.BLUE, Block.Properties.create(Material.GLASS, DyeColor.BLUE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> BROWN_STAINED_GLASS_FLOOR = BLOCKS.register("brown_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.BROWN, Block.Properties.create(Material.GLASS, DyeColor.BROWN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> GREEN_STAINED_GLASS_FLOOR = BLOCKS.register("green_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.GREEN, Block.Properties.create(Material.GLASS, DyeColor.GREEN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> RED_STAINED_GLASS_FLOOR = BLOCKS.register("red_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.RED, Block.Properties.create(Material.GLASS, DyeColor.RED).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> BLACK_STAINED_GLASS_FLOOR = BLOCKS.register("black_stained_glass_floor", () ->
            new StainedGlassFloorBlock(DyeColor.BLACK, Block.Properties.create(Material.GLASS, DyeColor.BLACK).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    // GlassFloor END
    // Farmland START
    public static final RegistryObject<Block> GARDEN_FARMLAND = BLOCKS.register("garden_farmland", () ->
            new GardenFarmlandBlock(Block.Properties.create(Material.EARTH).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> SUBMERGED_GRASS_FARMLAND = BLOCKS.register("submerged_grass_farmland", () ->
            new WaterloggedFarmlandBlock(Block.Properties.create(Material.EARTH).tickRandomly().hardnessAndResistance(0.7F).sound(SoundType.GROUND)));
    public static final RegistryObject<Block> SUBMERGED_DIRT_FARMLAND = BLOCKS.register("submerged_dirt_farmland", () ->
            new WaterloggedFarmlandBlock(Block.Properties.create(Material.EARTH).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.GROUND), ModBlocks.SUBMERGED_GRASS_FARMLAND.get()));
    // Farmland END
    // GardenDirt START
    public static final RegistryObject<Block> GARDEN_DIRT = BLOCKS.register("garden_dirt", () ->
            new Block(Block.Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.5F).sound(SoundType.GROUND)));
    // GardenDirt END
    // Raw Fertilizer Compressed START
    public static final RegistryObject<Block> COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER = BLOCKS.register("compressed_raw_animal_waste_fertilizer", () ->
            new Block(Block.Properties.create(Material.EARTH).sound(SoundType.WET_GRASS).hardnessAndResistance(0.8F)));
    public static final RegistryObject<Block> COMPRESSED_RAW_PEAT_FERTILIZER = BLOCKS.register("compressed_raw_peat_fertilizer", () ->
            new NoAmbientOcclusionBlock(Block.Properties.create(Material.EARTH).sound(SoundType.WET_GRASS).hardnessAndResistance(0.8F).notSolid()));
    public static final RegistryObject<Block> COMPRESSED_RAW_PLANT_TYPE_FERTILIZER = BLOCKS.register("compressed_raw_plant_type_fertilizer", () ->
            new Block(Block.Properties.create(Material.EARTH).sound(SoundType.WET_GRASS).hardnessAndResistance(0.8F)));
    // Raw Fertilizer Compressed END
    // Cork Oak START
    public static final RegistryObject<Block> CORK_OAK_LOG = BLOCKS.register("cork_oak_log", () ->
            new CorkOakLogBlock(MaterialColor.WOOD, Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<RenewableStrippedLogBlock> STRIPPED_CORK_OAK_LOG = BLOCKS.register("stripped_cork_oak_log", () ->
            new RenewableStrippedLogBlock(CORK_OAK_LOG.get(), MaterialColor.WOOD, Block.Properties.create(Material.WOOD, MaterialColor.OBSIDIAN).hardnessAndResistance(2.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CORK_OAK_LEAVES = BLOCKS.register("cork_oak_leaves", () ->
            new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()));
    public static final RegistryObject<Block> CORK_OAK_SAPLING = BLOCKS.register("cork_oak_sapling", () ->
            new ModSaplingBlock(CorkOakTree::new, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().hardnessAndResistance(0.0F).sound(SoundType.PLANT)));
    // Cork Oak END


    // --------------------------------------------- CROP ITEMS ---------------------------------------------
    // --------------------------------------------- LARGE CROP ITEMS ---------------------------------------------
    public static final RegistryObject<BlockItem> CORN_SEED_ITEM = ITEMS.register("corn_seed", () ->
            new BlockNamedItem(CORN.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // --------------------------------------------- SUBMERGED CROP BLOCKS ---------------------------------------------
    public static final RegistryObject<BlockItem> RICE_JAPONICA_SEED_ITEM = ITEMS.register("rice_japonica_seed", () ->
            new BlockNamedItem(RICE_JAPONICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> RICE_BLACK_JAPONICA_SEED_ITEM = ITEMS.register("rice_black_japonica_seed", () ->
            new BlockNamedItem(RICE_BLACK_JAPONICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> RICE_INDICA_SEED_ITEM = ITEMS.register("rice_indica_seed", () ->
            new BlockNamedItem(RICE_INDICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> RICE_YAMADANISHIKI_SEED_ITEM = ITEMS.register("rice_yamadanishiki_seed", () ->
            new BlockNamedItem(RICE_YAMADANISHIKI.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Threshing Blocks START
    public static final RegistryObject<BlockItem> THRESHING_RICE_JAPONICA_ITEM = ITEMS.register("threshing_rice_japonica", () ->
            new BlockItem(THRESHING_RICE_JAPONICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> THRESHING_RICE_BLACK_JAPONICA_ITEM = ITEMS.register("threshing_rice_black_japonica", () ->
            new BlockItem(THRESHING_RICE_BLACK_JAPONICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> THRESHING_RICE_INDICA_ITEM = ITEMS.register("threshing_rice_indica", () ->
            new BlockItem(THRESHING_RICE_INDICA.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> THRESHING_RICE_YAMADANISHIKI_ITEM = ITEMS.register("threshing_rice_yamadanishiki", () ->
            new BlockItem(THRESHING_RICE_YAMADANISHIKI.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Threshing Blocks END

    // --------------------------------------------- BLOCK ITEMS ---------------------------------------------
    // Dam Blocks START
    public static final RegistryObject<BlockItem> GRASS_SMALL_DAM_ITEM = ITEMS.register("grass_small_dam", () ->
            new BlockItem(GRASS_SMALL_DAM.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> DIRT_SMALL_DAM_ITEM = ITEMS.register("dirt_small_dam", () ->
            new BlockItem(DIRT_SMALL_DAM.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> GRASS_LARGE_DAM_ITEM = ITEMS.register("grass_large_dam", () ->
            new BlockItem(GRASS_LARGE_DAM.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> DIRT_LARGE_DAM_ITEM = ITEMS.register("dirt_large_dam", () ->
            new BlockItem(DIRT_LARGE_DAM.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Dam Blocks END
    // Functional Blocks START
    public static final RegistryObject<BlockItem> DIRT_DRYING_BED_ITEM = ITEMS.register("dirt_drying_bed", () ->
            new BlockItem(DIRT_DRYING_BED.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> DRYING_MACHINE_ITEM = ITEMS.register("drying_machine", () ->
            new BlockItem(DRYING_MACHINE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> WOOD_WINE_PRESS_ITEM = ITEMS.register("wood_wine_press", () ->
            new BlockItem(WOOD_WINE_PRESS.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> STOMPING_BARREL_ITEM = ITEMS.register("stomping_barrel", () ->
            new BlockItem(STOMPING_BARREL.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> FERTILIZER_COMPOSTER_ITEM = ITEMS.register("fertilizer_composter", () ->
            new BlockItem(FERTILIZER_COMPOSTER.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> FERMENTING_BARREL_ITEM = ITEMS.register("fermenting_barrel", () ->
            new BlockItem(FERMENTING_BARREL.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> WOOD_FLUID_STORAGE_BARREL_ITEM = ITEMS.register("wood_fluid_storage_barrel", () ->
            new BlockItem(WOOD_FLUID_STORAGE_BARREL.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> FILTRATION_MACHINE_ITEM = ITEMS.register("filtration_machine", () ->
            new BlockItem(FILTRATION_MACHINE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> FINING_MACHINE_ITEM = ITEMS.register("fining_machine", () ->
            new BlockItem(FINING_MACHINE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> BOTTLING_MACHINE_ITEM = ITEMS.register("bottling_machine", () ->
            new BlockItem(BOTTLING_MACHINE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> AGING_WINE_RACK_ITEM = ITEMS.register("aging_wine_rack", () ->
            new BlockItem(AGING_WINE_RACK.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> FARMER_WORKBENCH_ITEM = ITEMS.register("farmer_workbench", () ->
            new BlockItem(FARMER_WORKBENCH.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> BOILING_CAULDRON_ITEM = ITEMS.register("boiling_cauldron", () ->
            new BlockItem(BOILING_CAULDRON.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> TEMPERATURE_CHAMBER_ITEM = ITEMS.register("temperature_chamber", () ->
            new BlockItem(TEMPERATURE_CHAMBER.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
//    public static final RegistryObject<BlockItem> MORTAR_AND_PESTLE_ITEM = ITEMS.register("mortar_and_pestle", () ->
//            new BlockItem(MORTAR_AND_PESTLE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Functional Blocks END
    // Wine Racks START
    public static final RegistryObject<BlockItem> ACACIA_WINE_RACK_ITEM = ITEMS.register("acacia_wine_rack", () ->
            new BlockItem(ACACIA_WINE_RACK.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> BIRCH_WINE_RACK_ITEM = ITEMS.register("birch_wine_rack", () ->
            new BlockItem(BIRCH_WINE_RACK.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> DARK_OAK_WINE_RACK_ITEM = ITEMS.register("dark_oak_wine_rack", () ->
            new BlockItem(DARK_OAK_WINE_RACK.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> JUNGLE_WINE_RACK_ITEM = ITEMS.register("jungle_wine_rack", () ->
            new BlockItem(JUNGLE_WINE_RACK.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> OAK_WINE_RACK_ITEM = ITEMS.register("oak_wine_rack", () ->
            new BlockItem(OAK_WINE_RACK.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> SPRUCE_WINE_RACK_ITEM = ITEMS.register("spruce_wine_rack", () ->
            new BlockItem(SPRUCE_WINE_RACK.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Wine Racks END
    // Salt START
    public static final RegistryObject<BlockItem> SALT_ORE_ITEM = ITEMS.register("salt_ore", () ->
            new BlockItem(SALT_ORE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> COMPRESSED_ROCK_SALT_ITEM = ITEMS.register("compressed_rock_salt", () ->
            new BlockItem(COMPRESSED_ROCK_SALT.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> COMPRESSED_SEA_SALT_ITEM = ITEMS.register("compressed_sea_salt", () ->
            new BlockItem(COMPRESSED_SEA_SALT.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Salt END
    // GrassType START
    public static final RegistryObject<BlockItem> SHORT_GRASS_ITEM = ITEMS.register("short_grass", () ->
            new BlockItem(SHORT_GRASS.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // GrassType END
    // PeatMoss START
    public static final RegistryObject<BlockItem> PEAT_MOSS_ITEM = ITEMS.register("peat_moss", () ->
            new BlockItem(PEAT_MOSS.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> PEAT_STAGE_ONE_ITEM = ITEMS.register("peat_stage_one", () ->
            new BlockItem(PEAT_STAGE_ONE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> PEAT_STAGE_TWO_ITEM = ITEMS.register("peat_stage_two", () ->
            new BlockItem(PEAT_STAGE_TWO.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> PEAT_STAGE_THREE_ITEM = ITEMS.register("peat_stage_three", () ->
            new BlockItem(PEAT_STAGE_THREE.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // PeatMoss END
    // Trellis START
    public static final RegistryObject<BlockItem> WOOD_TRELLIS_ITEM = ITEMS.register("wood_trellis", () ->
            new BlockItem(WOOD_TRELLIS.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Trellis END
    // TrellisCrop START
    public static final RegistryObject<BlockItem> BARBERA_TRELLIS_SEED_ITEM = ITEMS.register("barbera_seed", () ->
            new BlockNamedItem(BARBERA_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> CABERNET_TRELLIS_SEED_ITEM = ITEMS.register("cabernet_sauvignon_seed", () ->
            new BlockNamedItem(CABERNET_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> MERLOT_TRELLIS_SEED_ITEM = ITEMS.register("merlot_seed", () ->
            new BlockNamedItem(MERLOT_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> RED_GLOBE_TRELLIS_SEED_ITEM = ITEMS.register("red_globe_seed", () ->
            new BlockNamedItem(RED_GLOBE_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> KOSHU_TRELLIS_SEED_ITEM = ITEMS.register("koshu_seed", () ->
            new BlockNamedItem(KOSHU_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> RIESLING_TRELLIS_SEED_ITEM = ITEMS.register("riesling_seed", () ->
            new BlockNamedItem(RIESLING_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> SULTANA_TRELLIS_SEED_ITEM = ITEMS.register("sultana_seed", () ->
            new BlockNamedItem(SULTANA_TRELLIS_CROP.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // TrellisCrop END
    // GlassFloor START
    public static final RegistryObject<BlockItem> MIDDLE_GLASS_DOOR_ITEM = ITEMS.register("glass_door", () ->
            new BlockItem(MIDDLE_GLASS_DOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> GLASS_FLOOR_ITEM = ITEMS.register("glass_floor", () ->
            new BlockItem(GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> WHITE_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("white_stained_glass_floor", () ->
            new BlockItem(WHITE_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> ORANGE_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("orange_stained_glass_floor", () ->
            new BlockItem(ORANGE_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> MAGENTA_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("magenta_stained_glass_floor", () ->
            new BlockItem(MAGENTA_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> LIGHT_BLUE_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("light_blue_stained_glass_floor", () ->
            new BlockItem(LIGHT_BLUE_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> YELLOW_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("yellow_stained_glass_floor", () ->
            new BlockItem(YELLOW_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> LIME_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("lime_stained_glass_floor", () ->
            new BlockItem(LIME_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> PINK_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("pink_stained_glass_floor", () ->
            new BlockItem(PINK_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> GRAY_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("gray_stained_glass_floor", () ->
            new BlockItem(GRAY_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> LIGHT_GRAY_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("light_gray_stained_glass_floor", () ->
            new BlockItem(LIGHT_GRAY_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> CYAN_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("cyan_stained_glass_floor", () ->
            new BlockItem(CYAN_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> PURPLE_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("purple_stained_glass_floor", () ->
            new BlockItem(PURPLE_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> BLUE_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("blue_stained_glass_floor", () ->
            new BlockItem(BLUE_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> BROWN_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("brown_stained_glass_floor", () ->
            new BlockItem(BROWN_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> GREEN_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("green_stained_glass_floor", () ->
            new BlockItem(GREEN_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> RED_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("red_stained_glass_floor", () ->
            new BlockItem(RED_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<Item> BLACK_STAINED_GLASS_FLOOR_ITEM = ITEMS.register("black_stained_glass_floor", () ->
            new BlockItem(BLACK_STAINED_GLASS_FLOOR.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // GlassFloor END
    // Farmland START
    public static final RegistryObject<BlockItem> GARDEN_FARMLAND_ITEM = ITEMS.register("garden_farmland", () ->
            new BlockItem(GARDEN_FARMLAND.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> SUBMERGED_GRASS_FARMLAND_ITEM = ITEMS.register("submerged_grass_farmland", () ->
            new BlockItem(SUBMERGED_GRASS_FARMLAND.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> SUBMERGED_DIRT_FARMLAND_ITEM = ITEMS.register("submerged_dirt_farmland", () ->
            new BlockItem(SUBMERGED_DIRT_FARMLAND.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Farmland END
    // GardenDirt START
    public static final RegistryObject<BlockItem> GARDEN_DIRT_ITEM = ITEMS.register("garden_dirt", () ->
            new BlockItem(GARDEN_DIRT.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // GardenDirt END
    // Raw Fertilizer Compressed START
    public static final RegistryObject<BlockItem> COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER_ITEM = ITEMS.register("compressed_raw_animal_waste_fertilizer", () ->
            new BlockItem(COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> COMPRESSED_RAW_PEAT_FERTILIZER_ITEM = ITEMS.register("compressed_raw_peat_fertilizer", () ->
            new BlockItem(COMPRESSED_RAW_PEAT_FERTILIZER.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> COMPRESSED_RAW_PLANT_TYPE_FERTILIZER_ITEM = ITEMS.register("compressed_raw_plant_type_fertilizer", () ->
            new BlockItem(COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Raw Fertilizer Compressed END
    // Cork Oak START
    public static final RegistryObject<BlockItem> CORK_OAK_LOG_ITEM = ITEMS.register("cork_oak_log", () ->
            new BlockItem(CORK_OAK_LOG.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> STRIPPED_CORK_OAK_LOG_ITEM = ITEMS.register("stripped_cork_oak_log", () ->
            new BlockItem(STRIPPED_CORK_OAK_LOG.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> CORK_OAK_LEAVES_ITEM = ITEMS.register("cork_oak_leaves", () ->
            new BlockItem(CORK_OAK_LEAVES.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    public static final RegistryObject<BlockItem> CORK_OAK_SAPLING_ITEM = ITEMS.register("cork_oak_sapling", () ->
            new BlockItem(CORK_OAK_SAPLING.get(), new Item.Properties().group(FarmersLife.FARMERS_LIFE_CATEGORY)));
    // Cork Oak END
}
