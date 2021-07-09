package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.data.provider.builders.FarmerShapedRecipeBuilder;
import com.asheriit.asheriitsfarmerslife.data.provider.builders.FarmerShapelessRecipeBuilder;
import com.asheriit.asheriitsfarmerslife.data.provider.builders.MortarAndPestleRecipeBuilder;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModItems;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ForgeItemTagsProvider;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);

    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        this.registerItemRecipes(consumer);
        this.registerBlockItemRecipes(consumer);
        this.registerVanillaExtraRecipes(consumer);
    }

    private void registerVanillaExtraRecipes(Consumer<IFinishedRecipe> consumer) {
    }

    private void registerItemRecipes(Consumer<IFinishedRecipe> consumer) {
        this.generateWateringCanPartRecipes(consumer);
        this.generateWateringCanRecipes(consumer);
        this.generateFertilizerRecipes(consumer);
        this.generateSpadeRecipes(consumer);
        this.generateSeedRecipes(consumer);
        this.generatePouchRecipes(consumer);
        this.generateMiscRecipes(consumer);
        this.generateFoodRecipes(consumer);
        this.generateWineBottleRecipes(consumer);
    }

    private void registerBlockItemRecipes(Consumer<IFinishedRecipe> consumer) {
        this.generateWorkbenchRecipe(consumer);
        this.generateGlassRecipes(consumer);
        this.generateGardenFarmlandRecipes(consumer);
        this.generateTrellisRecipes(consumer);
        this.generateCompressedBlockRecipes(consumer);
        this.generateMachineRecipes(consumer);
        this.generateMiddleDoorRecipes(consumer);
        this.generateWineRackRecipes(consumer);
    }

    private void generateWorkbenchRecipe(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.FARMER_WORKBENCH.get(), 1)
                .patternLine("## ")
                .patternLine("## ")
                .key('#', ModBlocks.CORK_OAK_LOG_ITEM.get())
                .addCriterion("has_cork_oak_log", hasItem(ModBlocks.CORK_OAK_LOG_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_workbench"));
    }

    private void generateTrellisRecipes(Consumer<IFinishedRecipe> consumer) {
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.WOOD_TRELLIS.get(), 2)
                .patternLine(" # ")
                .patternLine("#X#")
                .patternLine(" # ")
                .setGroup("trellis")
                .key('#', Items.STICK)
                .key('X', ItemTags.PLANKS)
                .addCriterion("has_stick", hasItem(Items.STICK))
                .addCriterion("has_plank", hasItem(ItemTags.PLANKS))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wood_trellis"));
    }

    private void generateFertilizerRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- COMPRESSED RAW FERTILIZER BLOCKS -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get(), 9)
                .setGroup("compressed_raw_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_raw_animal_waste_fertilizer"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get(), 9)
                .setGroup("compressed_raw_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_raw_animal_waste_fertilizer"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PEAT_FERTILIZER.get(), 9)
                .setGroup("compressed_raw_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_PEAT_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_raw_peat_fertilizer"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PEAT_FERTILIZER.get(), 9)
                .setGroup("compressed_raw_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_PEAT_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_raw_peat_fertilizer"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PLANT_TYPE_FERTILIZER.get(), 9)
                .setGroup("compressed_raw_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_PLANT_TYPE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_raw_plant_type_fertilizer"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PLANT_TYPE_FERTILIZER.get(), 9)
                .setGroup("compressed_raw_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_PLANT_TYPE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_raw_plant_type_fertilizer"));

        // -------------------------------- COMPRESSED RAW FERTILIZER BLOCKS TO ITEMS -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .setGroup("compressed_raw_fertilizer_to_item")
                .addCriterion("has_raw_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_raw_animal_waste_fertilizer_to_item"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .setGroup("compressed_raw_fertilizer_to_item")
                .addCriterion("has_raw_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_raw_animal_waste_fertilizer_to_item"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.RAW_PEAT_FERTILIZER.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get(), 1)
                .setGroup("compressed_raw_fertilizer_to_item")
                .addCriterion("has_raw_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_raw_peat_fertilizer_to_item"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.RAW_PEAT_FERTILIZER.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get(), 1)
                .setGroup("compressed_raw_fertilizer_to_item")
                .addCriterion("has_raw_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_raw_peat_fertilizer_to_item"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.RAW_PLANT_TYPE_FERTILIZER.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get(), 1)
                .setGroup("compressed_raw_fertilizer_to_item")
                .addCriterion("has_raw_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_raw_plant_type_fertilizer_to_item"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.RAW_PLANT_TYPE_FERTILIZER.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get(), 1)
                .setGroup("compressed_raw_fertilizer_to_item")
                .addCriterion("has_raw_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_raw_plant_type_fertilizer_to_item"));

        // -------------------------------- EMPTY FERTILIZER BAG -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.EMPTY_LARGE_FERTILIZER_BAG.get(), 1)
                .patternLine("#L#")
                .patternLine("#X#")
                .patternLine("#L#")
                .setGroup("empty_fertilizer_bag")
                .key('#', Tags.Items.STRING)
                .key('X', Items.PAPER)
                .key('L', Tags.Items.DYES_BROWN)
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .addCriterion("has_paper", hasItem(Items.PAPER))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "empty_fertilizer_bag"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.EMPTY_SMALL_FERTILIZER_BAG.get(), 1)
                .patternLine("#X#")
                .patternLine("#L#")
                .setGroup("empty_fertilizer_bag")
                .key('#', Tags.Items.STRING)
                .key('X', Items.PAPER)
                .key('L', Tags.Items.DYES_BROWN)
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .addCriterion("has_paper", hasItem(Items.PAPER))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "empty_small_fertilizer_bag"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.EMPTY_MEDIUM_FERTILIZER_BAG.get(), 1)
                .patternLine(" # ")
                .patternLine("#X#")
                .patternLine("#L#")
                .setGroup("empty_fertilizer_bag")
                .key('#', Tags.Items.STRING)
                .key('X', Items.PAPER)
                .key('L', Tags.Items.DYES_LIGHT_GRAY)
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .addCriterion("has_paper", hasItem(Items.PAPER))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "empty_medium_fertilizer_bag"));

        // -------------------------------- FERTILIZER PACKS -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get(), 3)
                .addIngredient(ModItems.EMPTY_SMALL_FERTILIZER_BAG.get())
                .setGroup("small_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_small_animal_waste_fertilizer"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get(), 3)
                .addIngredient(ModItems.EMPTY_SMALL_FERTILIZER_BAG.get())
                .setGroup("small_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "small_animal_waste_fertilizer"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_PEAT_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PEAT_FERTILIZER.get(), 5)
                .addIngredient(ModItems.EMPTY_SMALL_FERTILIZER_BAG.get())
                .setGroup("small_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_small_peat_fertilizer"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_PEAT_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PEAT_FERTILIZER.get(), 5)
                .addIngredient(ModItems.EMPTY_SMALL_FERTILIZER_BAG.get())
                .setGroup("small_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "small_peat_fertilizer"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_PLANT_TYPE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PLANT_TYPE_FERTILIZER.get(), 8)
                .addIngredient(ModItems.EMPTY_SMALL_FERTILIZER_BAG.get())
                .setGroup("small_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_small_plant_type_fertilizer"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_PLANT_TYPE_FERTILIZER.get(), 1)
                .addIngredient(ModItems.RAW_PLANT_TYPE_FERTILIZER.get(), 8)
                .addIngredient(ModItems.EMPTY_SMALL_FERTILIZER_BAG.get())
                .setGroup("small_fertilizer")
                .addCriterion("has_raw_fertilizer", hasItem(ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "small_plant_type_fertilizer"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.MEDIUM_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .patternLine("X#")
                .patternLine("#L")
                .key('#', ModItems.RAW_ANIMAL_WASTE_FERTILIZER.get())
                .key('X', ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get())
                .key('L', ModItems.EMPTY_MEDIUM_FERTILIZER_BAG.get())
                .setGroup("medium_fertilizer")
                .addCriterion("has_compressed_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "medium_animal_waste_fertilizer"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.MEDIUM_PEAT_FERTILIZER.get(), 1)
                .patternLine("#X")
                .patternLine("XL")
                .key('#', ModItems.RAW_PEAT_FERTILIZER.get())
                .key('X', ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get())
                .key('L', ModItems.EMPTY_MEDIUM_FERTILIZER_BAG.get())
                .setGroup("medium_fertilizer")
                .addCriterion("has_compressed_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "medium_peat_fertilizer"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.MEDIUM_PLANT_TYPE_FERTILIZER.get(), 1)
                .patternLine("##")
                .patternLine("#X")
                .key('#', ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get())
                .key('X', ModItems.EMPTY_MEDIUM_FERTILIZER_BAG.get())
                .setGroup("medium_fertilizer")
                .addCriterion("has_compressed_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "medium_plant_type_fertilizer"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.LARGE_ANIMAL_WASTE_FERTILIZER.get(), 1)
                .patternLine("##")
                .patternLine("#X")
                .key('#', ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get())
                .key('X', ModItems.EMPTY_LARGE_FERTILIZER_BAG.get())
                .setGroup("large_fertilizer")
                .addCriterion("has_compressed_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_ANIMAL_WASTE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "large_animal_waste_fertilizer"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.LARGE_PEAT_FERTILIZER.get(), 1)
                .patternLine(" # ")
                .patternLine("#X#")
                .key('#', ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get())
                .key('X', ModItems.EMPTY_LARGE_FERTILIZER_BAG.get())
                .setGroup("large_fertilizer")
                .addCriterion("has_compressed_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PEAT_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "large_peat_fertilizer"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.LARGE_PLANT_TYPE_FERTILIZER.get(), 1)
                .patternLine("###")
                .patternLine("#X#")
                .key('#', ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get())
                .key('X', ModItems.EMPTY_LARGE_FERTILIZER_BAG.get())
                .setGroup("large_fertilizer")
                .addCriterion("has_compressed_fertilizer", hasItem(ModBlocks.COMPRESSED_RAW_PLANT_TYPE_FERTILIZER.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "large_plant_type_fertilizer"));
    }

    private void generateSpadeRecipes(Consumer<IFinishedRecipe> consumer) {
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.WOOD_SPADE.get(), 1)
                .patternLine("XZX")
                .patternLine(" # ")
                .setGroup("spade")
                .key('#', Items.STICK)
                .key('X', ItemTags.PLANKS)
                .key('Z', Items.WOODEN_SHOVEL)
                .addCriterion("has_stick", hasItem(Items.STICK))
                .addCriterion("has_plank", hasItem(ItemTags.PLANKS))
                .addCriterion("has_shovel", hasItem(Items.WOODEN_SHOVEL))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wood_spade"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.STONE_SPADE.get(), 1)
                .patternLine("XZX")
                .patternLine(" # ")
                .setGroup("spade")
                .key('#', Items.STICK)
                .key('X', Items.COBBLESTONE)
                .key('Z', Items.STONE_SHOVEL)
                .addCriterion("has_stick", hasItem(Items.STICK))
                .addCriterion("has_cobblestone", hasItem(Items.COBBLESTONE))
                .addCriterion("has_shovel", hasItem(Items.STONE_SHOVEL))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "stone_spade"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.IRON_SPADE.get(), 1)
                .patternLine("XZX")
                .patternLine(" # ")
                .setGroup("spade")
                .key('#', Items.STICK)
                .key('X', Tags.Items.INGOTS_IRON)
                .key('Z', Items.IRON_SHOVEL)
                .addCriterion("has_stick", hasItem(Items.STICK))
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_shovel", hasItem(Items.WOODEN_SHOVEL))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "iron_spade"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.GOLD_SPADE.get(), 1)
                .patternLine("XZX")
                .patternLine(" # ")
                .setGroup("spade")
                .key('#', Items.STICK)
                .key('X', Items.GOLD_INGOT)
                .key('Z', Items.GOLDEN_SHOVEL)
                .addCriterion("has_stick", hasItem(Items.STICK))
                .addCriterion("has_gold_ingot", hasItem(Items.GOLD_INGOT))
                .addCriterion("has_shovel", hasItem(Items.GOLDEN_SHOVEL))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "gold_spade"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.DIAMOND_SPADE.get(), 1)
                .patternLine("XZX")
                .patternLine(" # ")
                .setGroup("spade")
                .key('#', Items.STICK)
                .key('X', Items.DIAMOND)
                .key('Z', Items.DIAMOND_SHOVEL)
                .addCriterion("has_stick", hasItem(Items.STICK))
                .addCriterion("has_diamond", hasItem(Items.DIAMOND))
                .addCriterion("has_shovel", hasItem(Items.DIAMOND_SHOVEL))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "diamond_spade"));
    }

    private void generateSeedRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- LARGE CROP SEEDS -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.CORN_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.CORN_CROP.get(), 1)
                .setGroup("corn_seeds")
                .addCriterion("has_corn_crop", hasItem(ModItems.CORN_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_corn_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.CORN_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.CORN_CROP.get(), 1)
                .setGroup("corn_seeds")
                .addCriterion("has_corn_crop", hasItem(ModItems.CORN_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "corn_seed"));

        // -------------------------------- WATERLOGGED CROP SEEDS -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RICE_JAPONICA_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.RICE_JAPONICA_CROP.get(), 1)
                .setGroup("rice_seeds")
                .addCriterion("has_rice_crop", hasItem(ModItems.RICE_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_rice_japonica_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RICE_JAPONICA_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.RICE_JAPONICA_CROP.get(), 1)
                .setGroup("rice_seeds")
                .addCriterion("has_rice_crop", hasItem(ModItems.RICE_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "rice_japonica_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RICE_BLACK_JAPONICA_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.RICE_BLACK_JAPONICA_CROP.get(), 1)
                .setGroup("rice_seeds")
                .addCriterion("has_rice_crop", hasItem(ModItems.RICE_BLACK_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_rice_black_japonica_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RICE_BLACK_JAPONICA_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.RICE_BLACK_JAPONICA_CROP.get(), 1)
                .setGroup("rice_seeds")
                .addCriterion("has_rice_crop", hasItem(ModItems.RICE_BLACK_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "rice_black_japonica_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RICE_INDICA_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.RICE_INDICA_CROP.get(), 1)
                .setGroup("rice_seeds")
                .addCriterion("has_rice_crop", hasItem(ModItems.RICE_INDICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_rice_indica_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RICE_INDICA_SEED_ITEM.get(), 4)
                .addIngredient(ModItems.RICE_INDICA_CROP.get(), 1)
                .setGroup("rice_seeds")
                .addCriterion("has_rice_crop", hasItem(ModItems.RICE_INDICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "rice_indica_seed"));

        // -------------------------------- TRELLIS WINE CROP SEEDS -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.BARBERA_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.BARBERA_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_barbera_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.BARBERA_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.BARBERA_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "barbera_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.CABERNET_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.CABERNET_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_cabernet_sauvignon_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.CABERNET_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.CABERNET_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "cabernet_sauvignon_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.MERLOT_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.MERLOT_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_merlot_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.MERLOT_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.MERLOT_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "merlot_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RED_GLOBE_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.RED_GLOBE_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_red_globe_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RED_GLOBE_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.RED_GLOBE_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "red_globe_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.KOSHU_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.KOSHU_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_koshu_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.KOSHU_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.KOSHU_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "koshu_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RIESLING_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.RIESLING_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_riesling_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.RIESLING_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.RIESLING_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "riesling_seed"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.SULTANA_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.SULTANA_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_sultana_seed"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.SULTANA_TRELLIS_SEED_ITEM.get(), 2)
                .addIngredient(ModItems.SEED_POUCH.get(), 1)
                .addIngredient(ModItems.SULTANA_CROP.get(), 1)
                .setGroup("wine_grapes")
                .addCriterion("has_seed_pouch", hasItem(ModItems.SEED_POUCH.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "sultana_seed"));
    }

    private void generateWateringCanPartRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- TUBES -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.SMALL_WATER_TUBE.get(), 1)
                .patternLine("#  ")
                .patternLine("#  ")
                .key('#', Tags.Items.INGOTS_IRON)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "small_water_tube"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.WATER_TUBE.get(), 1)
                .patternLine("#  ")
                .patternLine("#  ")
                .patternLine("#  ")
                .key('#', Tags.Items.INGOTS_IRON)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "water_tube"));

        // -------------------------------- BODY -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.SMALL_WATERING_CAN_BODY.get(), 1)
                .patternLine("# #")
                .patternLine("#X#")
                .key('#', Tags.Items.INGOTS_IRON)
                .key('X', Blocks.IRON_BLOCK)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_iron_block", hasItem(Blocks.IRON_BLOCK))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "small_watering_can_body"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.MEDIUM_WATERING_CAN_BODY.get(), 1)
                .patternLine("# #")
                .patternLine("X#X")
                .key('#', Tags.Items.INGOTS_IRON)
                .key('X', Blocks.IRON_BLOCK)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_iron_block", hasItem(Blocks.IRON_BLOCK))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "medium_watering_can_body"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.LARGE_WATERING_CAN_BODY.get(), 1)
                .patternLine("# #")
                .patternLine("X X")
                .patternLine("XXX")
                .key('#', Tags.Items.INGOTS_IRON)
                .key('X', Blocks.IRON_BLOCK)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_iron_block", hasItem(Blocks.IRON_BLOCK))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "large_watering_can_body"));

        // -------------------------------- HANDLE -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.WATERING_CAN_HANDLE.get(), 1)
                .patternLine(" # ")
                .patternLine("# #")
                .patternLine("# #")
                .key('#', Tags.Items.INGOTS_IRON)
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_iron_block", hasItem(Blocks.IRON_BLOCK))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "watering_can_handle"));
    }

    private void generateWateringCanRecipes(Consumer<IFinishedRecipe> consumer) {
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_WATERING_CAN.get(), 1)
                .addIngredient(ModItems.SMALL_WATER_TUBE.get())
                .addIngredient(ModItems.SMALL_WATERING_CAN_BODY.get())
                .addIngredient(ModItems.WATERING_CAN_HANDLE.get())
                .addCriterion("has_tube", hasItem(ModItems.SMALL_WATER_TUBE.get()))
                .addCriterion("has_body", hasItem(ModItems.SMALL_WATERING_CAN_BODY.get()))
                .addCriterion("has_handle", hasItem(ModItems.WATERING_CAN_HANDLE.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_small_watering_can"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.SMALL_WATERING_CAN.get(), 1)
                .addIngredient(ModItems.SMALL_WATER_TUBE.get())
                .addIngredient(ModItems.SMALL_WATERING_CAN_BODY.get())
                .addIngredient(ModItems.WATERING_CAN_HANDLE.get())
                .addCriterion("has_tube", hasItem(ModItems.SMALL_WATER_TUBE.get()))
                .addCriterion("has_body", hasItem(ModItems.SMALL_WATERING_CAN_BODY.get()))
                .addCriterion("has_handle", hasItem(ModItems.WATERING_CAN_HANDLE.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "small_watering_can"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.MEDIUM_WATERING_CAN.get(), 1)
                .addIngredient(ModItems.WATER_TUBE.get())
                .addIngredient(ModItems.MEDIUM_WATERING_CAN_BODY.get())
                .addIngredient(ModItems.WATERING_CAN_HANDLE.get())
                .addCriterion("has_tube", hasItem(ModItems.WATER_TUBE.get()))
                .addCriterion("has_body", hasItem(ModItems.MEDIUM_WATERING_CAN_BODY.get()))
                .addCriterion("has_handle", hasItem(ModItems.WATERING_CAN_HANDLE.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_medium_watering_can"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.MEDIUM_WATERING_CAN.get(), 1)
                .addIngredient(ModItems.WATER_TUBE.get())
                .addIngredient(ModItems.MEDIUM_WATERING_CAN_BODY.get())
                .addIngredient(ModItems.WATERING_CAN_HANDLE.get())
                .addCriterion("has_tube", hasItem(ModItems.WATER_TUBE.get()))
                .addCriterion("has_body", hasItem(ModItems.MEDIUM_WATERING_CAN_BODY.get()))
                .addCriterion("has_handle", hasItem(ModItems.WATERING_CAN_HANDLE.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "medium_watering_can"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.LARGE_WATERING_CAN.get(), 1)
                .addIngredient(ModItems.WATER_TUBE.get())
                .addIngredient(ModItems.LARGE_WATERING_CAN_BODY.get())
                .addIngredient(ModItems.WATERING_CAN_HANDLE.get())
                .addCriterion("has_tube", hasItem(ModItems.WATER_TUBE.get()))
                .addCriterion("has_body", hasItem(ModItems.LARGE_WATERING_CAN_BODY.get()))
                .addCriterion("has_handle", hasItem(ModItems.WATERING_CAN_HANDLE.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_large_watering_can"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.LARGE_WATERING_CAN.get(), 1)
                .addIngredient(ModItems.WATER_TUBE.get())
                .addIngredient(ModItems.LARGE_WATERING_CAN_BODY.get())
                .addIngredient(ModItems.WATERING_CAN_HANDLE.get())
                .addCriterion("has_tube", hasItem(ModItems.WATER_TUBE.get()))
                .addCriterion("has_body", hasItem(ModItems.LARGE_WATERING_CAN_BODY.get()))
                .addCriterion("has_handle", hasItem(ModItems.WATERING_CAN_HANDLE.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "large_watering_can"));
    }

    private void generateGlassRecipes(Consumer<IFinishedRecipe> consumer) {
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine(" # ")
                .patternLine("###")
                .setGroup("glass_floor")
                .key('#', Blocks.GLASS)
                .addCriterion("has_glass_floor", hasItem(Blocks.GLASS))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.BLACK_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.BLACK_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_BLACK)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "black_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.BLUE_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.BLUE_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_BLUE)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "blue_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.BROWN_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.BROWN_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_BROWN)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "brown_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.CYAN_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.CYAN_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_CYAN)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "cyan_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.GRAY_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.GRAY_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.GLASS_GRAY)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "gray_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.GREEN_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.GREEN_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_GREEN)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "green_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.LIGHT_BLUE_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_LIGHT_BLUE)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "light_blue_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.LIGHT_GRAY_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_LIGHT_GRAY)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "light_gray_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.LIME_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.LIME_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_LIME)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "lime_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.MAGENTA_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.MAGENTA_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_MAGENTA)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "magenta_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.ORANGE_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.ORANGE_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_ORANGE)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "orange_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.PINK_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.PINK_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_PINK)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "pink_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.PURPLE_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.PURPLE_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_PURPLE)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "purple_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.RED_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.RED_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_RED)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "red_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.WHITE_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.WHITE_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_WHITE)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "white_stained_glass_floor"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.YELLOW_STAINED_GLASS_FLOOR_ITEM.get(), 8)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("###")
                .setGroup("stained_glass_floor")
                .key('#', ModBlocks.YELLOW_STAINED_GLASS_FLOOR_ITEM.get())
                .key('X', Tags.Items.DYES_YELLOW)
                .addCriterion("has_glass_floor", hasItem(ModBlocks.GLASS_FLOOR_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "yellow_stained_glass_floor"));
    }

    private void generateGardenFarmlandRecipes(Consumer<IFinishedRecipe> consumer) {
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.GARDEN_FARMLAND.get(), 1)
                .patternLine("##")
                .patternLine("#X")
                .setGroup("garden_farmland")
                .key('#', ModTags.Items.RAW_FERTILIZER)
                .key('X', Blocks.DIRT)
                .addCriterion("has_dirt", hasItem(Blocks.DIRT))
                .addCriterion("has_raw_fertilizer", hasItem(ModTags.Items.RAW_FERTILIZER))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "garden_farmland"));
    }

    private void generateCompressedBlockRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- COMPRESSED ROCK SALT BLOCK -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_ROCK_SALT.get(), 1)
                .addIngredient(ModItems.ROCK_SALT.get(), 9)
                .setGroup("compressed_salt")
                .addCriterion("has_rock_salt", hasItem(ModItems.ROCK_SALT.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_rock_salt"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_ROCK_SALT.get(), 1)
                .addIngredient(ModItems.ROCK_SALT.get(), 9)
                .setGroup("compressed_salt")
                .addCriterion("has_rock_salt", hasItem(ModItems.ROCK_SALT.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_rock_salt"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_SEA_SALT.get(), 1)
                .addIngredient(ModItems.SEA_SALT.get(), 9)
                .setGroup("compressed_salt")
                .addCriterion("has_rock_salt", hasItem(ModItems.SEA_SALT.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_compressed_sea_salt"));
        ShapelessRecipeBuilder.shapelessRecipe(ModBlocks.COMPRESSED_SEA_SALT.get(), 1)
                .addIngredient(ModItems.SEA_SALT.get(), 9)
                .setGroup("compressed_salt")
                .addCriterion("has_rock_salt", hasItem(ModItems.SEA_SALT.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "compressed_sea_salt"));

        // -------------------------------- DECOMPRESSED ROCK SALT BLOCK -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.ROCK_SALT.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_ROCK_SALT_ITEM.get(), 1)
                .setGroup("salt")
                .addCriterion("has_compressed_rock_salt", hasItem(ModBlocks.COMPRESSED_ROCK_SALT_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_rock_salt"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.ROCK_SALT.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_ROCK_SALT_ITEM.get(), 1)
                .setGroup("salt")
                .addCriterion("has_compressed_rock_salt", hasItem(ModBlocks.COMPRESSED_ROCK_SALT_ITEM.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "rock_salt"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.SEA_SALT.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_SEA_SALT.get(), 1)
                .setGroup("salt")
                .addCriterion("has_compressed_water_salt", hasItem(ModBlocks.COMPRESSED_SEA_SALT.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_sea_salt"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.SEA_SALT.get(), 9)
                .addIngredient(ModBlocks.COMPRESSED_SEA_SALT.get(), 1)
                .setGroup("salt")
                .addCriterion("has_compressed_water_salt", hasItem(ModBlocks.COMPRESSED_SEA_SALT.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "sea_salt"));
    }

    private void generateMachineRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- DRYING BED BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.DIRT_DRYING_BED.get(), 4)
                .patternLine(" # ")
                .patternLine("# #")
                .patternLine(" # ")
                .key('#', Blocks.DIRT)
                .addCriterion("has_dirt", hasItem(Blocks.DIRT))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "dirt_drying_bed"));

        // -------------------------------- DRYING MACHINE BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.DRYING_MACHINE_ITEM.get(), 1)
                .patternLine("###")
                .patternLine("#X#")
                .patternLine("MMM")
                .key('#', Blocks.GLASS_PANE)
                .key('M', ItemTags.PLANKS)
                .key('X', Items.REDSTONE)
                .addCriterion("has_glass_pane", hasItem(Blocks.GLASS_PANE))
                .addCriterion("has_plank", hasItem(ItemTags.PLANKS))
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "drying_machine"));

        // -------------------------------- STOMPING BARREL BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.STOMPING_BARREL_ITEM.get(), 1)
                .patternLine("# #")
                .patternLine("X X")
                .patternLine("###")
                .key('#', ItemTags.PLANKS)
                .key('X', Tags.Items.INGOTS_IRON)
                .addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "stomping_barrel"));

        // -------------------------------- WINE PRESS BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.WOOD_WINE_PRESS_ITEM.get(), 1)
                .patternLine(" # ")
                .patternLine(" X ")
                .patternLine("MLM")
                .key('#', ModItems.WINE_PRESS_PISTON.get())
                .key('X', ModItems.WINE_PRESS_MOUNT.get())
                .key('L', ModBlocks.STOMPING_BARREL_ITEM.get())
                .key('M', Items.REDSTONE)
                .addCriterion("has_stomping_barrel", hasItem(ModBlocks.STOMPING_BARREL_ITEM.get()))
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wine_press"));

        // -------------------------------- FERTILIZER COMPOSTER BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.FERTILIZER_COMPOSTER_ITEM.get(), 1)
                .patternLine("# #")
                .patternLine("# #")
                .patternLine("XLX")
                .key('#', ItemTags.WOODEN_SLABS)
                .key('X', ItemTags.PLANKS)
                .key('L', Items.REDSTONE)
                .addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .addCriterion("has_wood_slab", hasItem(ItemTags.WOODEN_SLABS))
                .addCriterion("has_redstone", hasItem(Items.REDSTONE))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "fertilizer_composter"));

        // -------------------------------- FERMENTING BARREL BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.FERMENTING_BARREL_ITEM.get(), 1)
                .patternLine("LXL")
                .patternLine("# #")
                .patternLine("LXL")
                .key('#', ItemTags.PLANKS)
                .key('L', ItemTags.LOGS)
                .key('X', ModItems.WOOD_BARREL_LID.get())
                .addCriterion("has_fluid_storage", hasItem(ItemTags.PLANKS))
                .addCriterion("has_planks", hasItem(ItemTags.LOGS))
                .addCriterion("has_wood_barrel_lid", hasItem(ModItems.WOOD_BARREL_LID.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "fermenting_barrel"));

        // -------------------------------- WOOD FLUID STORAGE BARREL BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.WOOD_FLUID_STORAGE_BARREL_ITEM.get(), 1)
                .patternLine("#X#")
                .patternLine("# #")
                .patternLine("#X#")
                .key('#', ItemTags.PLANKS)
                .key('X', ModItems.WOOD_BARREL_LID.get())
                .addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .addCriterion("has_wood_barrel_lid", hasItem(ModItems.WOOD_BARREL_LID.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wood_fluid_storage_barrel"));

        // -------------------------------- FILTRATION BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.FILTRATION_MACHINE_ITEM.get(), 1)
                .patternLine("# #")
                .patternLine("S S")
                .patternLine("L#L")
                .key('#', ItemTags.PLANKS)
                .key('S', Tags.Items.RODS_WOODEN)
                .key('L', ItemTags.LOGS)
                .addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .addCriterion("has_wooden_rods", hasItem(Tags.Items.RODS_WOODEN))
                .addCriterion("has_logs", hasItem(ItemTags.LOGS))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "filtration_machine"));

        // -------------------------------- FINING BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.FINING_MACHINE_ITEM.get(), 1)
                .patternLine("SXS")
                .patternLine("# #")
                .patternLine("###")
                .key('#', ItemTags.PLANKS)
                .key('S', ItemTags.WOODEN_SLABS)
                .key('X', Tags.Items.INGOTS_IRON)
                .addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "fining_machine"));

        // -------------------------------- BOTTLING MACHINE BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.BOTTLING_MACHINE_ITEM.get(), 1)
                .patternLine("IBI")
                .patternLine(" B ")
                .patternLine("COC")
                .key('C', Tags.Items.COBBLESTONE)
                .key('O', Items.OBSIDIAN)
                .key('B', Tags.Items.RODS_BLAZE)
                .key('I', Tags.Items.INGOTS_IRON)
                .addCriterion("has_cobblestone", hasItem(Tags.Items.COBBLESTONE))
                .addCriterion("has_iron_ingot", hasItem(Tags.Items.INGOTS_IRON))
                .addCriterion("has_blaze_rod", hasItem(Tags.Items.RODS_BLAZE))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "bottling_machine"));

        // -------------------------------- AGING WINE RACK BLOCK -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.AGING_WINE_RACK_ITEM.get(), 1)
                .patternLine("LPL")
                .patternLine("P P")
                .patternLine("LPL")
                .key('L', ItemTags.LOGS)
                .key('P', ItemTags.PLANKS)
                .addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .addCriterion("has_logs", hasItem(ItemTags.LOGS))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "aging_wine_rack"));
    }

    private void generateMiddleDoorRecipes(Consumer<IFinishedRecipe> consumer) {
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.MIDDLE_GLASS_DOOR_ITEM.get(), 1)
                .patternLine("## ")
                .patternLine("## ")
                .patternLine("## ")
                .setGroup("middle_door")
                .key('#', Blocks.GLASS_PANE.asItem())
                .addCriterion("has_glass_pane", hasItem(Blocks.GLASS_PANE.asItem()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "middle_glass_door_item"));
    }

    private void generatePouchRecipes(Consumer<IFinishedRecipe> consumer) {
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.SEED_POUCH.get(), 2)
                .patternLine("# #")
                .patternLine(" # ")
                .setGroup("pouch")
                .key('#', Tags.Items.STRING)
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "seed_pouch"));
    }

    private void generateMiscRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- MISC ITEMS - FOOD INGREDIENTS -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.RICE_IN_HUSK.get(), 1)
                .patternLine("X")
                .patternLine("X")
                .patternLine("#")
                .setGroup("husked_rice")
                .key('#', ModItems.CORN_HUSK.get())
                .key('X', ModTags.Items.GRAINS_RICE)
                .addCriterion("has_corn_husk", hasItem(ModItems.CORN_HUSK.get()))
                .addCriterion("has_rice_grains", hasItem(ModTags.Items.GRAINS_RICE))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_rice_in_husk"));
        ShapedRecipeBuilder.shapedRecipe(ModItems.RICE_IN_HUSK.get(), 1)
                .patternLine("X")
                .patternLine("X")
                .patternLine("#")
                .setGroup("husked_rice")
                .key('#', ModItems.CORN_HUSK.get())
                .key('X', ModTags.Items.GRAINS_RICE)
                .addCriterion("has_corn_husk", hasItem(ModItems.CORN_HUSK.get()))
                .addCriterion("has_rice_grains", hasItem(ModTags.Items.GRAINS_RICE))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "rice_in_husk"));

        // -------------------------------- MISC ITEMS - FLUID FILTER -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.PAPER_FLUID_FILTER.get(), 2)
                .patternLine("#S#")
                .patternLine("SXS")
                .patternLine("#S#")
                .setGroup("fluid_filter")
                .key('#', Tags.Items.RODS_WOODEN)
                .key('S', Tags.Items.STRING)
                .key('X', Items.PAPER)
                .addCriterion("has_stick", hasItem(Tags.Items.RODS_WOODEN))
                .addCriterion("has_paper", hasItem(Items.PAPER))
                .addCriterion("has_string", hasItem(Tags.Items.STRING))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "paper_fluid_filter"));


        // -------------------------------- MISC ITEMS -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.WINE_PRESS_MOUNT.get(), 1)
                .patternLine("#X#")
                .patternLine("# #")
                .key('#', ItemTags.PLANKS)
                .key('X', Tags.Items.INGOTS_IRON)
                .addCriterion("has_plank", hasItem(ItemTags.PLANKS))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wine_press_mount"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.WINE_PRESS_PISTON.get(), 1)
                .patternLine(" X ")
                .patternLine(" X ")
                .patternLine("###")
                .key('#', ItemTags.PLANKS)
                .key('X', Tags.Items.INGOTS_IRON)
                .addCriterion("has_plank", hasItem(ItemTags.PLANKS))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wine_press_piston"));

        // -------------------------------- MISC ITEMS - BARREL LID -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.WOOD_BARREL_LID.get(), 1)
                .patternLine("X#X")
                .key('#', ItemTags.WOODEN_SLABS)
                .key('X', Tags.Items.INGOTS_IRON)
                .addCriterion("has_planks", hasItem(ItemTags.PLANKS))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wood_barrel_lid"));

        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.WINE_CORK.get(), 2)
                .addIngredient(ModItems.CORK_BARK.get(), 1)
                .setGroup("wine_cork")
                .addCriterion("has_cork_bark", hasItem(ModItems.CORK_BARK.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_wine_cork"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.WINE_CORK.get(), 2)
                .addIngredient(ModItems.CORK_BARK.get(), 1)
                .setGroup("wine_cork")
                .addCriterion("has_cork_bark", hasItem(ModItems.CORK_BARK.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wine_cork"));

        // -------------------------------- MISC ITEMS - CHAFF ROD (FUEL ITEM) -----------------------------------
        FarmerShapelessRecipeBuilder.shapelessRecipe(ModItems.CHAFF_ROD.get(), 1)
                .addIngredient(ModItems.CHAFF.get(), 2)
                .addCriterion("has_chaff", hasItem(ModItems.CHAFF.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_chaff_rod"));
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.CHAFF_ROD.get(), 1)
                .addIngredient(ModItems.CHAFF.get(), 2)
                .addCriterion("has_chaff", hasItem(ModItems.CHAFF.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "chaff_rod"));
    }

    private void generateFoodRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- FLOUR RECIPES -----------------------------------
        MortarAndPestleRecipeBuilder.mortarAndPestleRecipe(ModItems.WHEAT_FLOUR.get(), 1)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .addIngredient(Tags.Items.CROPS_WHEAT)
                .setGroup("flour")
                .addCriterion("has_wheat_crop", hasItem(Tags.Items.CROPS_WHEAT))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wheat_flour"));

        // -------------------------------- GRAIN RECIPES -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_JAPONICA.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_JAPONICA_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_threshing_rice_japonica"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_JAPONICA.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_JAPONICA_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "threshing_rice_japonica"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_BLACK_JAPONICA.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_BLACK_JAPONICA_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_BLACK_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_threshing_rice_black_japonica"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_BLACK_JAPONICA.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_BLACK_JAPONICA_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_BLACK_JAPONICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "threshing_rice_black_japonica"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_INDICA.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_INDICA_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_INDICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_threshing_rice_indica"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_INDICA.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_INDICA_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_INDICA_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "threshing_rice_indica"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_YAMADANISHIKI.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_YAMADANISHIKI_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_YAMADANISHIKI_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "farmer_threshing_rice_yamadanishiki"));
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.THRESHING_RICE_YAMADANISHIKI.get(), 1)
                .patternLine("###")
                .patternLine("###")
                .key('#', ModItems.RICE_YAMADANISHIKI_CROP.get())
                .addCriterion("has_crop", hasItem(ModItems.RICE_YAMADANISHIKI_CROP.get()))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "threshing_rice_yamadanishiki"));


        // -------------------------------- EGG ITEMS -----------------------------------
        // TODO: recipes for egg yolk / protein -> do this when blocks for food preparation are made
    }

    private void generateWineBottleRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- WINE BOTTLES -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModItems.CORKED_GLASS_BOTTLE.get(), 2)
                .patternLine(" X ")
                .patternLine("# #")
                .patternLine("###")
                .key('#', ModItems.MOLTEN_GLASS.get())
                .key('X', ModItems.WINE_CORK.get())
                .addCriterion("has_glass_pane", hasItem(Tags.Items.GLASS_PANES))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "wine_bottle"));
    }

    private void generateWineRackRecipes(Consumer<IFinishedRecipe> consumer) {
        // -------------------------------- WINE RACKS -----------------------------------
        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.ACACIA_WINE_RACK_ITEM.get(), 1)
                .patternLine("#S#")
                .patternLine("#S#")
                .setGroup("wine_rack")
                .key('#', Blocks.ACACIA_PLANKS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_planks", hasItem(Blocks.ACACIA_PLANKS))
                .addCriterion("has_sticks", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "acacia_wine_rack"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.BIRCH_WINE_RACK_ITEM.get(), 1)
                .patternLine("#S#")
                .patternLine("#S#")
                .setGroup("wine_rack")
                .key('#', Blocks.BIRCH_PLANKS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_planks", hasItem(Blocks.BIRCH_PLANKS))
                .addCriterion("has_sticks", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "birch_wine_rack"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.DARK_OAK_WINE_RACK_ITEM.get(), 1)
                .patternLine("#S#")
                .patternLine("#S#")
                .setGroup("wine_rack")
                .key('#', Blocks.DARK_OAK_PLANKS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_planks", hasItem(Blocks.DARK_OAK_PLANKS))
                .addCriterion("has_sticks", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "dark_oak_wine_rack"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.JUNGLE_WINE_RACK_ITEM.get(), 1)
                .patternLine("#S#")
                .patternLine("#S#")
                .setGroup("wine_rack")
                .key('#', Blocks.JUNGLE_PLANKS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_planks", hasItem(Blocks.JUNGLE_PLANKS))
                .addCriterion("has_sticks", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "jungle_wine_rack"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.OAK_WINE_RACK_ITEM.get(), 1)
                .patternLine("#S#")
                .patternLine("#S#")
                .setGroup("wine_rack")
                .key('#', Blocks.OAK_PLANKS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_planks", hasItem(Blocks.OAK_PLANKS))
                .addCriterion("has_sticks", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "oak_wine_rack"));

        FarmerShapedRecipeBuilder.shapedRecipe(ModBlocks.SPRUCE_WINE_RACK_ITEM.get(), 1)
                .patternLine("#S#")
                .patternLine("#S#")
                .setGroup("wine_rack")
                .key('#', Blocks.SPRUCE_PLANKS)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_planks", hasItem(Blocks.SPRUCE_PLANKS))
                .addCriterion("has_sticks", hasItem(Tags.Items.RODS_WOODEN))
                .build(consumer, new ResourceLocation(FarmersLife.MOD_ID, "spruce_wine_rack"));
    }
}
