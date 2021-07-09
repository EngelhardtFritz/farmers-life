package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.recipes.*;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeSerializer {
    public static final ResourceLocation FARMER_CRAFTING_ID = new ResourceLocation(FarmersLife.MOD_ID, "farmer_crafting");
    public static final ResourceLocation BOILING_SOAKING_ID = new ResourceLocation(FarmersLife.MOD_ID, "boiling_soaking");

    public static final IRecipeSerializer<DryingRecipe> DRYING_RECIPE_SERIALIZER_INSTANCE = new DryingRecipeSerializer();

    public static final IRecipeType<IDryingRecipe> DRYING_RECIPE_TYPE = registerType(IDryingRecipe.DRYING_ID);
    public static final IRecipeType<WinePressRecipe> WINE_PRESS_RECIPE_TYPE = registerType(WinePressRecipe.WINE_PRESS_ID);
    public static final IRecipeType<StompingBarrelRecipe> STOMPING_BARREL_RECIPE_TYPE = registerType(StompingBarrelRecipe.STOMPING_BARREL_ID);
    public static final IRecipeType<FertilizerComposterRecipe> FERTILIZER_COMPOSTER_RECIPE_TYPE = registerType(FertilizerComposterRecipe.FERTILIZER_COMPOSTER_ID);
    public static final IRecipeType<FermentingRecipe> FERMENTATION_RECIPE_TYPE = registerType(FermentingRecipe.FERMENTING_ID);
    public static final IRecipeType<FiltrationRecipe> FILTRATION_RECIPE_TYPE = registerType(FiltrationRecipe.FILTRATION_ID);
    public static final IRecipeType<FiningRecipe> FINING_RECIPE_TYPE = registerType(FiningRecipe.FINING_ID);
    public static final IRecipeType<BottlingRecipe> BOTTLING_RECIPE_TYPE = registerType(BottlingRecipe.BOTTLING_ID);
    public static final IRecipeType<AgingRecipe> AGING_RECIPE_TYPE = registerType(AgingRecipe.AGING_ID);
    public static final IRecipeType<IFarmerCraftingRecipe> FARMER_CRAFTING_TYPE = registerType(FARMER_CRAFTING_ID);
    public static final IRecipeType<IBoilingCauldronRecipe> BOILING_SOAKING_TYPE = registerType(BOILING_SOAKING_ID);
    public static final IRecipeType<BoilingRecipe> BOILING_RECIPE_TYPE = registerType(BoilingRecipe.BOILING_ID);
    public static final IRecipeType<SoakingRecipe> SOAKING_RECIPE_TYPE = registerType(SoakingRecipe.SOAKING_ID);
    public static final IRecipeType<TemperatureChamberRecipe> TEMPERATURE_CHAMBER_RECIPE_TYPE = registerType(TemperatureChamberRecipe.BOILING_ID);
    public static final IRecipeType<MortarAndPestleRecipe> MORTAR_AND_PESTLE_RECIPE_TYPE = registerType(MortarAndPestleRecipe.MORTAR_AND_PESTLE_RECIPE_SERIALIZER_ID);


    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FarmersLife.MOD_ID);

    public static final RegistryObject<IRecipeSerializer<?>> DRYING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("drying_machine",
            () -> DRYING_RECIPE_SERIALIZER_INSTANCE);

    public static final RegistryObject<IRecipeSerializer<?>> WINE_PRESS_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("wine_press",
            () -> WinePressRecipe.WINE_PRESS_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> STOMPING_BARREL_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("stomping_barrel",
            () -> StompingBarrelRecipe.STOMPING_BARREL_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> FERTILIZER_COMPOSTER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("fertilizer_composter",
            () -> FertilizerComposterRecipe.FERTILIZER_COMPOSTER_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> FERMENTING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("fermenting",
            () -> FermentingRecipe.FERMENTING_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> FILTRATION_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("filtration",
            () -> FiltrationRecipe.FILTRATION_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> FINING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("fining",
            () -> FiningRecipe.FINING_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> BOTTLING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("bottling",
            () -> BottlingRecipe.BOTTLING_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> AGING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("aging",
            () -> AgingRecipe.AGING_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> FARMER_SHAPED_SERIALIZER = RECIPE_SERIALIZERS.register("farmer_shaped",
            FarmerShapedRecipe.Serializer::new);

    public static final RegistryObject<IRecipeSerializer<?>> FARMER_SHAPELESS_SERIALIZER = RECIPE_SERIALIZERS.register("farmer_shapeless",
            FarmerShapelessRecipe.Serializer::new);

    public static final RegistryObject<IRecipeSerializer<?>> BOILING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("boiling",
            () -> BoilingRecipe.BOILING_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> SOAKING_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("soaking",
            () -> SoakingRecipe.SOAKING_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> TEMPERATURE_CHAMBER_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("temperature_chamber",
            () -> TemperatureChamberRecipe.TEMPERATURE_CHAMBER_RECIPE_SERIALIZER);

    public static final RegistryObject<IRecipeSerializer<?>> MORTAR_AND_PESTLE_RECIPE_SERIALIZER = RECIPE_SERIALIZERS.register("mortar_and_pestle",
            () -> MortarAndPestleRecipe.MORTAR_AND_PESTLE_RECIPE_SERIALIZER);


    private static class RecipeType<T extends IRecipe<?>> implements IRecipeType<T> {
        @Override
        public String toString() {
            return Registry.RECIPE_TYPE.getKey(this).toString();
        }
    }

    private static <T extends IRecipeType> T registerType(ResourceLocation dryingId) {
        return (T) Registry.register(Registry.RECIPE_TYPE, dryingId, new RecipeType<>());
    }
}
