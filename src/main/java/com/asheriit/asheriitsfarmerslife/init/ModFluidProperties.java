package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.utils.ColorHelper;
import net.minecraft.item.Rarity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class ModFluidProperties {
    public static final ResourceLocation MUST_FLOWING_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/must_flow");
    public static final ResourceLocation MUST_STILL_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/must_still");
    public static final ResourceLocation MUST_OVERLAY = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/must_overlay");
    public static final ResourceLocation FERMENTED_MUST_OVERLAY = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/fermented_must_overlay");
    public static final ResourceLocation FERMENTED_MUST_STILL_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/fermented_must_still");
    public static final ResourceLocation FERMENTED_MUST_FLOWING_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/fermented_must_flow");
    public static final ResourceLocation FILTERED_FERMENTED_MUST_OVERLAY = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/filtered_fermented_must_overlay");
    public static final ResourceLocation FILTERED_FERMENTED_MUST_STILL_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/filtered_fermented_must_still");
    public static final ResourceLocation FILTERED_FERMENTED_MUST_FLOWING_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/filtered_fermented_must_flow");
    public static final ResourceLocation WINE_OVERLAY = new ResourceLocation("minecraft", "block/water_overlay");
    public static final ResourceLocation WINE_STILL_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/wine_still");
    public static final ResourceLocation WINE_FLOWING_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/wine_flow");
    public static final ResourceLocation WATER_STILL = new ResourceLocation("minecraft", "block/water_still");
    public static final ResourceLocation WATER_FLOWING = new ResourceLocation("minecraft", "block/water_flow");
    public static final ResourceLocation SAKE_FLOWING_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/sake_flow");
    public static final ResourceLocation SAKE_STILL_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/sake_still");
    public static final ResourceLocation SAKE_OVERLAY = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/sake_overlay");
    public static final ResourceLocation FILTERED_SAKE_FLOWING_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/filtered_sake_flow");
    public static final ResourceLocation FILTERED_SAKE_STILL_RL = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/filtered_sake_still");
    public static final ResourceLocation FILTERED_SAKE_OVERLAY = new ResourceLocation(FarmersLife.MOD_ID, "blocks/fluids/filtered_sake_overlay");

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------- WINE - MUST ----------------------------------------
    // ---------------------------------------------------------------------------------------------
    public static final ForgeFlowingFluid.Properties BARBERA_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.BARBERA_MUST_FLUID,
            ModFluids.BARBERA_MUST_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.BARBERA_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(2000)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(20)
            .block(ModFluids.BARBERA_MUST_BLOCK)
            .bucket(ModItems.BARBERA_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties CABERNET_SAUVIGNON_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.CABERNET_SAUVIGNON_MUST_FLUID,
            ModFluids.CABERNET_SAUVIGNON_MUST_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.CABERNET_SAUVIGNON_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(2000)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(20)
            .block(ModFluids.CABERNET_SAUVIGNON_MUST_BLOCK)
            .bucket(ModItems.CABERNET_SAUVIGNON_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties MERLOT_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.MERLOT_MUST_FLUID,
            ModFluids.MERLOT_MUST_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.MERLOT_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(2000)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(20)
            .block(ModFluids.MERLOT_MUST_BLOCK)
            .bucket(ModItems.MERLOT_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties RED_GLOBE_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.RED_GLOBE_MUST_FLUID,
            ModFluids.RED_GLOBE_MUST_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.RED_GLOBE_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(2000)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(20)
            .block(ModFluids.RED_GLOBE_MUST_BLOCK)
            .bucket(ModItems.RED_GLOBE_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties KOSHU_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.KOSHU_MUST_FLUID,
            ModFluids.KOSHU_MUST_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.KOSHU_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(2000)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(20)
            .block(ModFluids.KOSHU_MUST_BLOCK)
            .bucket(ModItems.KOSHU_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties RIESLING_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.RIESLING_MUST_FLUID,
            ModFluids.RIESLING_MUST_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.RIESLING_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(2000)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(20)
            .block(ModFluids.RIESLING_MUST_BLOCK)
            .bucket(ModItems.RIESLING_MUST_BUCKET);

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------- WINE - FERMENTED MUST -----------------------------------
    // ---------------------------------------------------------------------------------------------
    public static final ForgeFlowingFluid.Properties FERMENTED_BARBERA_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERMENTED_BARBERA_MUST_FLUID,
            ModFluids.FERMENTED_BARBERA_MUST_FLOWING,
            FluidAttributes.builder(FERMENTED_MUST_STILL_RL, FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FERMENTED_BARBERA_COLOR_HEX)
                    .overlay(FERMENTED_MUST_OVERLAY)
                    .density(1600)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(15)
            .block(ModFluids.FERMENTED_BARBERA_MUST_BLOCK)
            .bucket(ModItems.FERMENTED_BARBERA_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FERMENTED_CABERNET_SAUVIGNON_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID,
            ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING,
            FluidAttributes.builder(FERMENTED_MUST_STILL_RL, FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FERMENTED_CABERNET_SAUVIGNON_COLOR_HEX)
                    .overlay(FERMENTED_MUST_OVERLAY)
                    .density(1600)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(15)
            .block(ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_BLOCK)
            .bucket(ModItems.FERMENTED_CABERNET_SAUVIGNON_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FERMENTED_MERLOT_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERMENTED_MERLOT_MUST_FLUID,
            ModFluids.FERMENTED_MERLOT_MUST_FLOWING,
            FluidAttributes.builder(FERMENTED_MUST_STILL_RL, FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FERMENTED_MERLOT_COLOR_HEX)
                    .overlay(FERMENTED_MUST_OVERLAY)
                    .density(1600)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(15)
            .block(ModFluids.FERMENTED_MERLOT_MUST_BLOCK)
            .bucket(ModItems.FERMENTED_MERLOT_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FERMENTED_RED_GLOBE_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERMENTED_RED_GLOBE_MUST_FLUID,
            ModFluids.FERMENTED_RED_GLOBE_MUST_FLOWING,
            FluidAttributes.builder(FERMENTED_MUST_STILL_RL, FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FERMENTED_RED_GLOBE_COLOR_HEX)
                    .overlay(FERMENTED_MUST_OVERLAY)
                    .density(1600)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(15)
            .block(ModFluids.FERMENTED_RED_GLOBE_MUST_BLOCK)
            .bucket(ModItems.FERMENTED_RED_GLOBE_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FERMENTED_KOSHU_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERMENTED_KOSHU_MUST_FLUID,
            ModFluids.FERMENTED_KOSHU_MUST_FLOWING,
            FluidAttributes.builder(FERMENTED_MUST_STILL_RL, FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FERMENTED_KOSHU_COLOR_HEX)
                    .overlay(FERMENTED_MUST_OVERLAY)
                    .density(1600)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(15)
            .block(ModFluids.FERMENTED_KOSHU_MUST_BLOCK)
            .bucket(ModItems.FERMENTED_KOSHU_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FERMENTED_RIESLING_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERMENTED_RIESLING_MUST_FLUID,
            ModFluids.FERMENTED_RIESLING_MUST_FLOWING,
            FluidAttributes.builder(FERMENTED_MUST_STILL_RL, FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FERMENTED_RIESLING_COLOR_HEX)
                    .overlay(FERMENTED_MUST_OVERLAY)
                    .density(1600)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(15)
            .block(ModFluids.FERMENTED_RIESLING_MUST_BLOCK)
            .bucket(ModItems.FERMENTED_RIESLING_MUST_BUCKET);

    // ----------------------------------------------------------------------------------------------
    // ------------------------------- WINE - FILTERED FERMENTED MUST -------------------------------
    // ----------------------------------------------------------------------------------------------
    public static final ForgeFlowingFluid.Properties FILTERED_FERMENTED_BARBERA_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLUID,
            ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FILTERED_BARBERA_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1400)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(12)
            .block(ModFluids.FILTERED_FERMENTED_BARBERA_MUST_BLOCK)
            .bucket(ModItems.FILTERED_FERMENTED_BARBERA_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID,
            ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FILTERED_CABERNET_SAUVIGNON_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1400)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(12)
            .block(ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_BLOCK)
            .bucket(ModItems.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FILTERED_FERMENTED_MERLOT_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLUID,
            ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FILTERED_MERLOT_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1400)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(12)
            .block(ModFluids.FILTERED_FERMENTED_MERLOT_MUST_BLOCK)
            .bucket(ModItems.FILTERED_FERMENTED_MERLOT_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FILTERED_FERMENTED_RED_GLOBE_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLUID,
            ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FILTERED_RED_GLOBE_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1400)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(12)
            .block(ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_BLOCK)
            .bucket(ModItems.FILTERED_FERMENTED_RED_GLOBE_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FILTERED_FERMENTED_KOSHU_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLUID,
            ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FILTERED_KOSHU_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1400)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(12)
            .block(ModFluids.FILTERED_FERMENTED_KOSHU_MUST_BLOCK)
            .bucket(ModItems.FILTERED_FERMENTED_KOSHU_MUST_BUCKET);

    public static final ForgeFlowingFluid.Properties FILTERED_FERMENTED_RIESLING_MUST_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLUID,
            ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.FILTERED_RIESLING_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1400)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(3)
            .tickRate(12)
            .block(ModFluids.FILTERED_FERMENTED_RIESLING_MUST_BLOCK)
            .bucket(ModItems.FILTERED_FERMENTED_RIESLING_MUST_BUCKET);

    // ---------------------------------------------------------------------------------------------
    // ------------------------------------------- WINE --------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public static final ForgeFlowingFluid.Properties BARBERA_WINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.BARBERA_WINE_FLUID,
            ModFluids.BARBERA_WINE_FLOWING,
            FluidAttributes.builder(WINE_STILL_RL, WINE_FLOWING_RL)
                    .color(ColorHelper.WineColors.BARBERA_WINE_COLOR_HEX)
                    .overlay(WINE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(10)
            .block(ModFluids.BARBERA_WINE_BLOCK)
            .bucket(ModItems.BARBERA_WINE_BUCKET);

    public static final ForgeFlowingFluid.Properties CABERNET_SAUVIGNON_WINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.CABERNET_SAUVIGNON_WINE_FLUID,
            ModFluids.CABERNET_SAUVIGNON_WINE_FLOWING,
            FluidAttributes.builder(WINE_STILL_RL, WINE_FLOWING_RL)
                    .color(ColorHelper.WineColors.CABERNET_SAUVIGNON_WINE_COLOR_HEX)
                    .overlay(WINE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(10)
            .block(ModFluids.CABERNET_SAUVIGNON_WINE_BLOCK)
            .bucket(ModItems.CABERNET_SAUVIGNON_WINE_BUCKET);

    public static final ForgeFlowingFluid.Properties MERLOT_WINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.MERLOT_WINE_FLUID,
            ModFluids.MERLOT_WINE_FLOWING,
            FluidAttributes.builder(WINE_STILL_RL, WINE_FLOWING_RL)
                    .color(ColorHelper.WineColors.MERLOT_WINE_COLOR_HEX)
                    .overlay(WINE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(10)
            .block(ModFluids.MERLOT_WINE_BLOCK)
            .bucket(ModItems.MERLOT_WINE_BUCKET);

    public static final ForgeFlowingFluid.Properties RED_GLOBE_WINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.RED_GLOBE_WINE_FLUID,
            ModFluids.RED_GLOBE_WINE_FLOWING,
            FluidAttributes.builder(WINE_STILL_RL, WINE_FLOWING_RL)
                    .color(ColorHelper.WineColors.RED_GLOBE_WINE_COLOR_HEX)
                    .overlay(WINE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(10)
            .block(ModFluids.RED_GLOBE_WINE_BLOCK)
            .bucket(ModItems.RED_GLOBE_WINE_BUCKET);

    public static final ForgeFlowingFluid.Properties KOSHU_WINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.KOSHU_WINE_FLUID,
            ModFluids.KOSHU_WINE_FLOWING,
            FluidAttributes.builder(WINE_STILL_RL, WINE_FLOWING_RL)
                    .color(ColorHelper.WineColors.KOSHU_WINE_COLOR_HEX)
                    .overlay(WINE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(10)
            .block(ModFluids.KOSHU_WINE_BLOCK)
            .bucket(ModItems.KOSHU_WINE_BUCKET);

    public static final ForgeFlowingFluid.Properties RIESLING_WINE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.RIESLING_WINE_FLUID,
            ModFluids.RIESLING_WINE_FLOWING,
            FluidAttributes.builder(WINE_STILL_RL, WINE_FLOWING_RL)
                    .color(ColorHelper.WineColors.RIESLING_WINE_COLOR_HEX)
                    .overlay(WINE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .tickRate(10)
            .block(ModFluids.RIESLING_WINE_BLOCK)
            .bucket(ModItems.RIESLING_WINE_BUCKET);

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------------- SAKE ----------------------------------------------
    // ---------------------------------------------------------------------------------------------
    public static final ForgeFlowingFluid.Properties KOJI_WATER_MIX_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.KOJI_WATER_MIX_FLUID,
            ModFluids.KOJI_WATER_MIX_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.SakeColors.KOJI_WATER_MIX_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.KOJI_WATER_MIX_BLOCK);

    public static final ForgeFlowingFluid.Properties RICE_WATER_MIX_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.RICE_WATER_MIX_FLUID,
            ModFluids.RICE_WATER_MIX_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.SakeColors.RICE_WATER_MIX_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.RICE_WATER_MIX_BLOCK);

    public static final ForgeFlowingFluid.Properties KOJI_RICE_WATER_MIX_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.KOJI_RICE_WATER_MIX_FLUID,
            ModFluids.KOJI_RICE_WATER_MIX_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.SakeColors.KOJI_RICE_WATER_MIX_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.KOJI_RICE_WATER_MIX_BLOCK);

    public static final ForgeFlowingFluid.Properties KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.KOJI_RICE_SHUBO_WATER_MIX_FLUID,
            ModFluids.KOJI_RICE_SHUBO_WATER_MIX_FLOWING,
            FluidAttributes.builder(MUST_STILL_RL, MUST_FLOWING_RL)
                    .color(ColorHelper.SakeColors.KOJI_RICE_SHUBO_WATER_MIX_COLOR_HEX)
                    .overlay(MUST_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.KOJI_RICE_SHUBO_WATER_MIX_BLOCK);

    public static final ForgeFlowingFluid.Properties KOJI_RICE_SHUBO_WATER_MIX_SPOILED_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLUID,
            ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.SakeColors.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_BLOCK)
            .bucket(ModItems.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_BUCKET);

    public static final ForgeFlowingFluid.Properties FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID,
            ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING,
            FluidAttributes.builder(FILTERED_FERMENTED_MUST_STILL_RL, FILTERED_FERMENTED_MUST_FLOWING_RL)
                    .color(ColorHelper.SakeColors.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_COLOR_HEX)
                    .overlay(FILTERED_FERMENTED_MUST_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BLOCK)
            .bucket(ModItems.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BUCKET);

    public static final ForgeFlowingFluid.Properties FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID,
            ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING,
            FluidAttributes.builder(FILTERED_SAKE_STILL_RL, FILTERED_SAKE_FLOWING_RL)
                    .color(ColorHelper.SakeColors.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_COLOR_HEX)
                    .overlay(FILTERED_SAKE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BLOCK)
            .bucket(ModItems.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BUCKET);

    public static final ForgeFlowingFluid.Properties SAKE_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluids.SAKE_FLUID,
            ModFluids.SAKE_FLOWING,
            FluidAttributes.builder(SAKE_STILL_RL, SAKE_FLOWING_RL)
                    .color(ColorHelper.SakeColors.SAKE_COLOR_HEX)
                    .overlay(SAKE_OVERLAY)
                    .density(1200)
                    .rarity(Rarity.COMMON)
                    .sound(SoundEvents.BLOCK_WATER_AMBIENT))
            .tickRate(10)
            .block(ModFluids.SAKE_BLOCK)
            .bucket(ModItems.SAKE_BUCKET);
}
