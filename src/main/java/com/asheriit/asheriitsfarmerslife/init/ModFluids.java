package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, FarmersLife.MOD_ID);

    // ----------------------------------- FLUIDS - MUST -----------------------------------
    public static final RegistryObject<FlowingFluid> BARBERA_MUST_FLUID = FLUIDS.register("barbera_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.BARBERA_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BARBERA_MUST_FLOWING = FLUIDS.register("barbera_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.BARBERA_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> CABERNET_SAUVIGNON_MUST_FLUID = FLUIDS.register("cabernet_sauvignon_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.CABERNET_SAUVIGNON_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> CABERNET_SAUVIGNON_MUST_FLOWING = FLUIDS.register("cabernet_sauvignon_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.CABERNET_SAUVIGNON_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MERLOT_MUST_FLUID = FLUIDS.register("merlot_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.MERLOT_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MERLOT_MUST_FLOWING = FLUIDS.register("merlot_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.MERLOT_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> RED_GLOBE_MUST_FLUID = FLUIDS.register("red_globe_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.RED_GLOBE_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> RED_GLOBE_MUST_FLOWING = FLUIDS.register("red_globe_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.RED_GLOBE_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> KOSHU_MUST_FLUID = FLUIDS.register("koshu_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.KOSHU_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> KOSHU_MUST_FLOWING = FLUIDS.register("koshu_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.KOSHU_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> RIESLING_MUST_FLUID = FLUIDS.register("riesling_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.RIESLING_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> RIESLING_MUST_FLOWING = FLUIDS.register("riesling_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.RIESLING_MUST_PROPERTIES));

    // ----------------------------------- FLUIDS - FERMENTED MUST -----------------------------------
    public static final RegistryObject<FlowingFluid> FERMENTED_BARBERA_MUST_FLUID = FLUIDS.register("fermented_barbera_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FERMENTED_BARBERA_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FERMENTED_BARBERA_MUST_FLOWING = FLUIDS.register("fermented_barbera_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FERMENTED_BARBERA_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID = FLUIDS.register("fermented_cabernet_sauvignon_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FERMENTED_CABERNET_SAUVIGNON_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING = FLUIDS.register("fermented_cabernet_sauvignon_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FERMENTED_CABERNET_SAUVIGNON_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FERMENTED_MERLOT_MUST_FLUID = FLUIDS.register("fermented_merlot_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FERMENTED_MERLOT_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FERMENTED_MERLOT_MUST_FLOWING = FLUIDS.register("fermented_merlot_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FERMENTED_MERLOT_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FERMENTED_RED_GLOBE_MUST_FLUID = FLUIDS.register("fermented_red_globe_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FERMENTED_RED_GLOBE_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FERMENTED_RED_GLOBE_MUST_FLOWING = FLUIDS.register("fermented_red_globe_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FERMENTED_RED_GLOBE_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FERMENTED_KOSHU_MUST_FLUID = FLUIDS.register("fermented_koshu_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FERMENTED_KOSHU_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FERMENTED_KOSHU_MUST_FLOWING = FLUIDS.register("fermented_koshu_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FERMENTED_KOSHU_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FERMENTED_RIESLING_MUST_FLUID = FLUIDS.register("fermented_riesling_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FERMENTED_RIESLING_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FERMENTED_RIESLING_MUST_FLOWING = FLUIDS.register("fermented_riesling_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FERMENTED_RIESLING_MUST_PROPERTIES));

    // ----------------------------------- FLUIDS - FILTERED FERMENTED MUST -----------------------------------
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_BARBERA_MUST_FLUID = FLUIDS.register("filtered_fermented_barbera_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FILTERED_FERMENTED_BARBERA_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_BARBERA_MUST_FLOWING = FLUIDS.register("filtered_fermented_barbera_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FILTERED_FERMENTED_BARBERA_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID = FLUIDS.register("filtered_fermented_cabernet_sauvignon_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING = FLUIDS.register("filtered_fermented_cabernet_sauvignon_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_MERLOT_MUST_FLUID = FLUIDS.register("filtered_fermented_merlot_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FILTERED_FERMENTED_MERLOT_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_MERLOT_MUST_FLOWING = FLUIDS.register("filtered_fermented_merlot_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FILTERED_FERMENTED_MERLOT_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_RED_GLOBE_MUST_FLUID = FLUIDS.register("filtered_fermented_red_globe_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FILTERED_FERMENTED_RED_GLOBE_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_RED_GLOBE_MUST_FLOWING = FLUIDS.register("filtered_fermented_red_globe_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FILTERED_FERMENTED_RED_GLOBE_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_KOSHU_MUST_FLUID = FLUIDS.register("filtered_fermented_koshu_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FILTERED_FERMENTED_KOSHU_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_KOSHU_MUST_FLOWING = FLUIDS.register("filtered_fermented_koshu_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FILTERED_FERMENTED_KOSHU_MUST_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_RIESLING_MUST_FLUID = FLUIDS.register("filtered_fermented_riesling_must_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FILTERED_FERMENTED_RIESLING_MUST_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_RIESLING_MUST_FLOWING = FLUIDS.register("filtered_fermented_riesling_must_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FILTERED_FERMENTED_RIESLING_MUST_PROPERTIES));

    // ----------------------------------- FLUIDS - WINE -----------------------------------
    public static final RegistryObject<FlowingFluid> BARBERA_WINE_FLUID = FLUIDS.register("barbera_wine_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.BARBERA_WINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> BARBERA_WINE_FLOWING = FLUIDS.register("barbera_wine_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.BARBERA_WINE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> CABERNET_SAUVIGNON_WINE_FLUID = FLUIDS.register("cabernet_sauvignon_wine_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.CABERNET_SAUVIGNON_WINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> CABERNET_SAUVIGNON_WINE_FLOWING = FLUIDS.register("cabernet_sauvignon_wine_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.CABERNET_SAUVIGNON_WINE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MERLOT_WINE_FLUID = FLUIDS.register("merlot_wine_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.MERLOT_WINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> MERLOT_WINE_FLOWING = FLUIDS.register("merlot_wine_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.MERLOT_WINE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> RED_GLOBE_WINE_FLUID = FLUIDS.register("red_globe_wine_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.RED_GLOBE_WINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> RED_GLOBE_WINE_FLOWING = FLUIDS.register("red_globe_wine_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.RED_GLOBE_WINE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> KOSHU_WINE_FLUID = FLUIDS.register("koshu_wine_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.KOSHU_WINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> KOSHU_WINE_FLOWING = FLUIDS.register("koshu_wine_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.KOSHU_WINE_PROPERTIES));

    public static final RegistryObject<FlowingFluid> RIESLING_WINE_FLUID = FLUIDS.register("riesling_wine_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.RIESLING_WINE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> RIESLING_WINE_FLOWING = FLUIDS.register("riesling_wine_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.RIESLING_WINE_PROPERTIES));

    // ----------------------------------- FLUIDS - SAKE STEPS -----------------------------------
    public static final RegistryObject<FlowingFluid> KOJI_WATER_MIX_FLUID = FLUIDS.register("koji_water_mix_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.KOJI_WATER_MIX_PROPERTIES));
    public static final RegistryObject<FlowingFluid> KOJI_WATER_MIX_FLOWING = FLUIDS.register("koji_water_mix_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.KOJI_WATER_MIX_PROPERTIES));

    public static final RegistryObject<FlowingFluid> RICE_WATER_MIX_FLUID = FLUIDS.register("rice_water_mix_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.RICE_WATER_MIX_PROPERTIES));
    public static final RegistryObject<FlowingFluid> RICE_WATER_MIX_FLOWING = FLUIDS.register("rice_water_mix_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.RICE_WATER_MIX_PROPERTIES));

    public static final RegistryObject<FlowingFluid> KOJI_RICE_WATER_MIX_FLUID = FLUIDS.register("koji_rice_water_mix_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.KOJI_RICE_WATER_MIX_PROPERTIES));
    public static final RegistryObject<FlowingFluid> KOJI_RICE_WATER_MIX_FLOWING = FLUIDS.register("koji_rice_water_mix_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.KOJI_RICE_WATER_MIX_PROPERTIES));

    public static final RegistryObject<FlowingFluid> KOJI_RICE_SHUBO_WATER_MIX_FLUID = FLUIDS.register("koji_rice_shubo_water_mix_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES));
    public static final RegistryObject<FlowingFluid> KOJI_RICE_SHUBO_WATER_MIX_FLOWING = FLUIDS.register("koji_rice_shubo_water_mix_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES));

    public static final RegistryObject<FlowingFluid> KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLUID = FLUIDS.register("koji_rice_shubo_water_mix_spoiled_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_PROPERTIES));
    public static final RegistryObject<FlowingFluid> KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLOWING = FLUIDS.register("koji_rice_shubo_water_mix_spoiled_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID = FLUIDS.register("fermented_koji_rice_shubo_water_mix_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING = FLUIDS.register("fermented_koji_rice_shubo_water_mix_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID = FLUIDS.register("filtered_fermented_koji_rice_shubo_water_mix_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING = FLUIDS.register("filtered_fermented_koji_rice_shubo_water_mix_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_PROPERTIES));

    public static final RegistryObject<FlowingFluid> SAKE_FLUID = FLUIDS.register("sake_fluid",
            () -> new ForgeFlowingFluid.Source(ModFluidProperties.SAKE_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SAKE_FLOWING = FLUIDS.register("sake_flowing",
            () -> new ForgeFlowingFluid.Flowing(ModFluidProperties.SAKE_PROPERTIES));


    // ----------------------------------- FLUID BLOCKS - MUST -----------------------------------
    public static final RegistryObject<FlowingFluidBlock> BARBERA_MUST_BLOCK = ModBlocks.BLOCKS.register("barbera_must",
            () -> new FlowingFluidBlock(() -> ModFluids.BARBERA_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(150.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> CABERNET_SAUVIGNON_MUST_BLOCK = ModBlocks.BLOCKS.register("cabernet_sauvignon_must",
            () -> new FlowingFluidBlock(() -> ModFluids.CABERNET_SAUVIGNON_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(150.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> MERLOT_MUST_BLOCK = ModBlocks.BLOCKS.register("merlot_must",
            () -> new FlowingFluidBlock(() -> ModFluids.MERLOT_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(150.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> RED_GLOBE_MUST_BLOCK = ModBlocks.BLOCKS.register("red_globe_must",
            () -> new FlowingFluidBlock(() -> ModFluids.RED_GLOBE_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(150.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> KOSHU_MUST_BLOCK = ModBlocks.BLOCKS.register("koshu_must",
            () -> new FlowingFluidBlock(() -> ModFluids.KOSHU_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(150.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> RIESLING_MUST_BLOCK = ModBlocks.BLOCKS.register("riesling_must",
            () -> new FlowingFluidBlock(() -> ModFluids.RIESLING_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(150.0F).noDrops()));

    // ----------------------------------- FLUID BLOCKS - FERMENTED MUST -----------------------------------
    public static final RegistryObject<FlowingFluidBlock> FERMENTED_BARBERA_MUST_BLOCK = ModBlocks.BLOCKS.register("fermented_barbera_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FERMENTED_BARBERA_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(120.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FERMENTED_CABERNET_SAUVIGNON_MUST_BLOCK = ModBlocks.BLOCKS.register("fermented_cabernet_sauvignon_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(120.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FERMENTED_MERLOT_MUST_BLOCK = ModBlocks.BLOCKS.register("fermented_merlot_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FERMENTED_MERLOT_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(120.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FERMENTED_RED_GLOBE_MUST_BLOCK = ModBlocks.BLOCKS.register("fermented_red_globe_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FERMENTED_RED_GLOBE_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(120.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FERMENTED_KOSHU_MUST_BLOCK = ModBlocks.BLOCKS.register("fermented_koshu_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FERMENTED_KOSHU_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(120.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FERMENTED_RIESLING_MUST_BLOCK = ModBlocks.BLOCKS.register("fermented_riesling_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FERMENTED_RIESLING_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(120.0F).noDrops()));

    // ----------------------------------- FLUID BLOCKS - FILTERED FERMENTED MUST -----------------------------------
    public static final RegistryObject<FlowingFluidBlock> FILTERED_FERMENTED_BARBERA_MUST_BLOCK = ModBlocks.BLOCKS.register("filtered_fermented_barbera_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_BLOCK = ModBlocks.BLOCKS.register("filtered_fermented_cabernet_sauvignon_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FILTERED_FERMENTED_MERLOT_MUST_BLOCK = ModBlocks.BLOCKS.register("filtered_fermented_merlot_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FILTERED_FERMENTED_RED_GLOBE_MUST_BLOCK = ModBlocks.BLOCKS.register("filtered_fermented_red_globe_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FILTERED_FERMENTED_KOSHU_MUST_BLOCK = ModBlocks.BLOCKS.register("filtered_fermented_koshu_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FILTERED_FERMENTED_RIESLING_MUST_BLOCK = ModBlocks.BLOCKS.register("filtered_fermented_riesling_must",
            () -> new FlowingFluidBlock(() -> ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    // ----------------------------------- FLUID BLOCKS - WINE -----------------------------------
    public static final RegistryObject<FlowingFluidBlock> BARBERA_WINE_BLOCK = ModBlocks.BLOCKS.register("barbera_wine",
            () -> new FlowingFluidBlock(() -> ModFluids.BARBERA_WINE_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> CABERNET_SAUVIGNON_WINE_BLOCK = ModBlocks.BLOCKS.register("cabernet_sauvignon_wine",
            () -> new FlowingFluidBlock(() -> ModFluids.CABERNET_SAUVIGNON_WINE_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> MERLOT_WINE_BLOCK = ModBlocks.BLOCKS.register("merlot_wine",
            () -> new FlowingFluidBlock(() -> ModFluids.MERLOT_WINE_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> RED_GLOBE_WINE_BLOCK = ModBlocks.BLOCKS.register("red_globe_wine",
            () -> new FlowingFluidBlock(() -> ModFluids.RED_GLOBE_WINE_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> KOSHU_WINE_BLOCK = ModBlocks.BLOCKS.register("koshu_wine",
            () -> new FlowingFluidBlock(() -> ModFluids.KOSHU_WINE_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> RIESLING_WINE_BLOCK = ModBlocks.BLOCKS.register("riesling_wine",
            () -> new FlowingFluidBlock(() -> ModFluids.RIESLING_WINE_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    // ----------------------------------- FLUID BLOCKS - SAKE -----------------------------------
    public static final RegistryObject<FlowingFluidBlock> KOJI_WATER_MIX_BLOCK = ModBlocks.BLOCKS.register("koji_water_mix",
            () -> new FlowingFluidBlock(() -> ModFluids.KOJI_WATER_MIX_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> RICE_WATER_MIX_BLOCK = ModBlocks.BLOCKS.register("rice_water_mix",
            () -> new FlowingFluidBlock(() -> ModFluids.RICE_WATER_MIX_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> KOJI_RICE_WATER_MIX_BLOCK = ModBlocks.BLOCKS.register("koji_rice_water_mix",
            () -> new FlowingFluidBlock(() -> ModFluids.KOJI_RICE_WATER_MIX_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> KOJI_RICE_SHUBO_WATER_MIX_BLOCK = ModBlocks.BLOCKS.register("koji_rice_shubo_water_mix",
            () -> new FlowingFluidBlock(() -> ModFluids.KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> KOJI_RICE_SHUBO_WATER_MIX_SPOILED_BLOCK = ModBlocks.BLOCKS.register("koji_rice_shubo_water_mix_spoiled",
            () -> new FlowingFluidBlock(() -> ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BLOCK = ModBlocks.BLOCKS.register("fermented_koji_rice_shubo_water_mix",
            () -> new FlowingFluidBlock(() -> ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_BLOCK = ModBlocks.BLOCKS.register("filtered_fermented_koji_rice_shubo_water_mix",
            () -> new FlowingFluidBlock(() -> ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

    public static final RegistryObject<FlowingFluidBlock> SAKE_BLOCK = ModBlocks.BLOCKS.register("sake",
            () -> new FlowingFluidBlock(() -> ModFluids.SAKE_FLUID.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));

}
