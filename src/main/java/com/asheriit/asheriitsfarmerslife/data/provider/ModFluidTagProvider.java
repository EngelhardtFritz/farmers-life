package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.init.ModFluids;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.tags.FluidTags;

public class ModFluidTagProvider extends FluidTagsProvider {
    public ModFluidTagProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        // ------------------------------- MUST -------------------------------
        this.getBuilder(ModTags.Fluids.BARBERA).add(
                ModFluids.BARBERA_MUST_FLUID.get(), ModFluids.BARBERA_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.CABERNET_SAUVIGNON).add(
                ModFluids.CABERNET_SAUVIGNON_MUST_FLUID.get(), ModFluids.CABERNET_SAUVIGNON_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.MERLOT).add(
                ModFluids.MERLOT_MUST_FLUID.get(), ModFluids.MERLOT_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.RED_GLOBE).add(
                ModFluids.RED_GLOBE_MUST_FLUID.get(), ModFluids.RED_GLOBE_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.KOSHU).add(
                ModFluids.KOSHU_MUST_FLUID.get(), ModFluids.KOSHU_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.RIESLING).add(
                ModFluids.RIESLING_MUST_FLUID.get(), ModFluids.RIESLING_MUST_FLOWING.get());

        // ------------------------------- FERMENTED MUST -------------------------------
        this.getBuilder(ModTags.Fluids.FERMENTED_BARBERA).add(
                ModFluids.FERMENTED_BARBERA_MUST_FLUID.get(), ModFluids.FERMENTED_BARBERA_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FERMENTED_CABERNET_SAUVIGNON).add(
                ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID.get(), ModFluids.FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FERMENTED_MERLOT).add(
                ModFluids.FERMENTED_MERLOT_MUST_FLUID.get(), ModFluids.FERMENTED_MERLOT_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FERMENTED_RED_GLOBE).add(
                ModFluids.FERMENTED_RED_GLOBE_MUST_FLUID.get(), ModFluids.FERMENTED_RED_GLOBE_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FERMENTED_KOSHU).add(
                ModFluids.FERMENTED_KOSHU_MUST_FLUID.get(), ModFluids.FERMENTED_KOSHU_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FERMENTED_RIESLING).add(
                ModFluids.FERMENTED_RIESLING_MUST_FLUID.get(), ModFluids.FERMENTED_RIESLING_MUST_FLOWING.get());

        // ------------------------------- FILTERED FERMENTED MUST -------------------------------
        this.getBuilder(ModTags.Fluids.FILTERED_BARBERA).add(
                ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLUID.get(), ModFluids.FILTERED_FERMENTED_BARBERA_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FILTERED_CABERNET_SAUVIGNON).add(
                ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLUID.get(), ModFluids.FILTERED_FERMENTED_CABERNET_SAUVIGNON_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FILTERED_MERLOT).add(
                ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLUID.get(), ModFluids.FILTERED_FERMENTED_MERLOT_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FILTERED_RED_GLOBE).add(
                ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLUID.get(), ModFluids.FILTERED_FERMENTED_RED_GLOBE_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FILTERED_KOSHU).add(
                ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLUID.get(), ModFluids.FILTERED_FERMENTED_KOSHU_MUST_FLOWING.get());

        this.getBuilder(ModTags.Fluids.FILTERED_RIESLING).add(
                ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLUID.get(), ModFluids.FILTERED_FERMENTED_RIESLING_MUST_FLOWING.get());

        // ------------------------------- WINE -------------------------------
        this.getBuilder(ModTags.Fluids.BARBERA_WINE).add(
                ModFluids.BARBERA_WINE_FLUID.get(), ModFluids.BARBERA_WINE_FLOWING.get());

        this.getBuilder(ModTags.Fluids.CABERNET_SAUVIGNON_WINE).add(
                ModFluids.CABERNET_SAUVIGNON_WINE_FLUID.get(), ModFluids.CABERNET_SAUVIGNON_WINE_FLOWING.get());

        this.getBuilder(ModTags.Fluids.MERLOT_WINE).add(
                ModFluids.MERLOT_WINE_FLUID.get(), ModFluids.MERLOT_WINE_FLOWING.get());

        this.getBuilder(ModTags.Fluids.RED_GLOBE_WINE).add(
                ModFluids.RED_GLOBE_WINE_FLUID.get(), ModFluids.RED_GLOBE_WINE_FLOWING.get());

        this.getBuilder(ModTags.Fluids.KOSHU_WINE).add(
                ModFluids.KOSHU_WINE_FLUID.get(), ModFluids.KOSHU_WINE_FLOWING.get());

        this.getBuilder(ModTags.Fluids.RIESLING_WINE).add(
                ModFluids.RIESLING_WINE_FLUID.get(), ModFluids.RIESLING_WINE_FLOWING.get());

        this.getBuilder(ModTags.Fluids.MUST).add(
                ModTags.Fluids.BARBERA,
                ModTags.Fluids.CABERNET_SAUVIGNON,
                ModTags.Fluids.MERLOT,
                ModTags.Fluids.RED_GLOBE,
                ModTags.Fluids.KOSHU,
                ModTags.Fluids.RIESLING);

        this.getBuilder(ModTags.Fluids.FERMENTED_MUST).add(
                ModTags.Fluids.FERMENTED_BARBERA,
                ModTags.Fluids.FERMENTED_CABERNET_SAUVIGNON,
                ModTags.Fluids.FERMENTED_MERLOT,
                ModTags.Fluids.FERMENTED_RED_GLOBE,
                ModTags.Fluids.FERMENTED_KOSHU,
                ModTags.Fluids.FERMENTED_RIESLING);

        this.getBuilder(ModTags.Fluids.FILTERED_FERMENTED_MUST).add(
                ModTags.Fluids.FILTERED_BARBERA,
                ModTags.Fluids.FILTERED_CABERNET_SAUVIGNON,
                ModTags.Fluids.FILTERED_MERLOT,
                ModTags.Fluids.FILTERED_RED_GLOBE,
                ModTags.Fluids.FILTERED_KOSHU,
                ModTags.Fluids.FILTERED_RIESLING);

        this.getBuilder(ModTags.Fluids.WINE).add(
                ModTags.Fluids.BARBERA_WINE,
                ModTags.Fluids.CABERNET_SAUVIGNON_WINE,
                ModTags.Fluids.MERLOT_WINE,
                ModTags.Fluids.RED_GLOBE_WINE,
                ModTags.Fluids.KOSHU_WINE,
                ModTags.Fluids.RIESLING_WINE);

        // ------------------------------- SAKE -------------------------------
        this.getBuilder(ModTags.Fluids.SAKE).add(
                ModFluids.SAKE_FLUID.get(), ModFluids.SAKE_FLOWING.get());

        this.getBuilder(ModTags.Fluids.SAKE_STEPS).add(
                ModFluids.RICE_WATER_MIX_FLUID.get(), ModFluids.RICE_WATER_MIX_FLOWING.get(),
                ModFluids.KOJI_WATER_MIX_FLUID.get(), ModFluids.KOJI_WATER_MIX_FLOWING.get(),
                ModFluids.KOJI_RICE_WATER_MIX_FLUID.get(), ModFluids.KOJI_RICE_WATER_MIX_FLOWING.get(),
                ModFluids.KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), ModFluids.KOJI_RICE_SHUBO_WATER_MIX_FLOWING.get(),
                ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLUID.get(), ModFluids.KOJI_RICE_SHUBO_WATER_MIX_SPOILED_FLOWING.get(),
                ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), ModFluids.FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING.get(),
                ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLUID.get(), ModFluids.FILTERED_FERMENTED_KOJI_RICE_SHUBO_WATER_MIX_FLOWING.get());

        this.getBuilder(FluidTags.WATER).add(
                ModTags.Fluids.MUST, ModTags.Fluids.FERMENTED_MUST, ModTags.Fluids.FILTERED_FERMENTED_MUST, ModTags.Fluids.WINE,
                ModTags.Fluids.SAKE, ModTags.Fluids.SAKE_STEPS);
    }
}
