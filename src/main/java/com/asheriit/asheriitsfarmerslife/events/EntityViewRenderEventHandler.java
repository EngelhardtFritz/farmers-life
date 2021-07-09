package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModEffects;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.utils.ColorHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityViewRenderEventHandler {
    @SubscribeEvent
    public static void onEntityViewRenderFogColorsEvent(EntityViewRenderEvent.FogColors event) {
        ActiveRenderInfo renderInfo = event.getInfo();
        IFluidState fluidState = getFluidStateForEntity(renderInfo);

        if (fluidState.isTagged(ModTags.Fluids.BARBERA)) {
            event.setRed(((ColorHelper.BARBERA_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.BARBERA_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.BARBERA_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.CABERNET_SAUVIGNON)) {
            event.setRed(((ColorHelper.CABERNET_SAUVIGNON_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.CABERNET_SAUVIGNON_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.CABERNET_SAUVIGNON_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.MERLOT)) {
            event.setRed(((ColorHelper.MERLOT_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.MERLOT_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.MERLOT_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.RED_GLOBE)) {
            event.setRed(((ColorHelper.RED_GLOBE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.RED_GLOBE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.RED_GLOBE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.KOSHU)) {
            event.setRed(((ColorHelper.KOSHU_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.KOSHU_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.KOSHU_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.RIESLING)) {
            event.setRed(((ColorHelper.RIESLING_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.RIESLING_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.RIESLING_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FERMENTED_BARBERA)) {
            event.setRed(((ColorHelper.FERMENTED_BARBERA_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FERMENTED_BARBERA_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FERMENTED_BARBERA_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FERMENTED_CABERNET_SAUVIGNON)) {
            event.setRed(((ColorHelper.FERMENTED_CABERNET_SAUVIGNON_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FERMENTED_CABERNET_SAUVIGNON_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FERMENTED_CABERNET_SAUVIGNON_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FERMENTED_MERLOT)) {
            event.setRed(((ColorHelper.FERMENTED_MERLOT_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FERMENTED_MERLOT_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FERMENTED_MERLOT_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FERMENTED_RED_GLOBE)) {
            event.setRed(((ColorHelper.FERMENTED_RED_GLOBE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FERMENTED_RED_GLOBE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FERMENTED_RED_GLOBE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FERMENTED_KOSHU)) {
            event.setRed(((ColorHelper.FERMENTED_KOSHU_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FERMENTED_KOSHU_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FERMENTED_KOSHU_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FERMENTED_RIESLING)) {
            event.setRed(((ColorHelper.FERMENTED_RIESLING_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FERMENTED_RIESLING_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FERMENTED_RIESLING_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FILTERED_BARBERA)) {
            event.setRed(((ColorHelper.FILTERED_BARBERA_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FILTERED_BARBERA_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FILTERED_BARBERA_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FILTERED_CABERNET_SAUVIGNON)) {
            event.setRed(((ColorHelper.FILTERED_CABERNET_SAUVIGNON_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FILTERED_CABERNET_SAUVIGNON_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FILTERED_CABERNET_SAUVIGNON_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FILTERED_MERLOT)) {
            event.setRed(((ColorHelper.FILTERED_MERLOT_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FILTERED_MERLOT_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FILTERED_MERLOT_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FILTERED_RED_GLOBE)) {
            event.setRed(((ColorHelper.FILTERED_RED_GLOBE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FILTERED_RED_GLOBE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FILTERED_RED_GLOBE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FILTERED_KOSHU)) {
            event.setRed(((ColorHelper.FILTERED_KOSHU_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FILTERED_KOSHU_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FILTERED_KOSHU_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.FILTERED_RIESLING)) {
            event.setRed(((ColorHelper.FILTERED_RIESLING_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.FILTERED_RIESLING_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.FILTERED_RIESLING_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.BARBERA_WINE)) {
            event.setRed(((ColorHelper.WineColors.BARBERA_WINE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.WineColors.BARBERA_WINE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.WineColors.BARBERA_WINE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.CABERNET_SAUVIGNON_WINE)) {
            event.setRed(((ColorHelper.WineColors.CABERNET_SAUVIGNON_WINE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.WineColors.CABERNET_SAUVIGNON_WINE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.WineColors.CABERNET_SAUVIGNON_WINE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.MERLOT_WINE)) {
            event.setRed(((ColorHelper.WineColors.MERLOT_WINE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.WineColors.MERLOT_WINE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.WineColors.MERLOT_WINE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.RED_GLOBE_WINE)) {
            event.setRed(((ColorHelper.WineColors.RED_GLOBE_WINE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.WineColors.RED_GLOBE_WINE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.WineColors.RED_GLOBE_WINE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.KOSHU_WINE)) {
            event.setRed(((ColorHelper.WineColors.KOSHU_WINE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.WineColors.KOSHU_WINE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.WineColors.KOSHU_WINE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.RIESLING_WINE)) {
            event.setRed(((ColorHelper.WineColors.RIESLING_WINE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.WineColors.RIESLING_WINE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.WineColors.RIESLING_WINE_COLOR_HEX & 255) / 255.0F);
        } else if (fluidState.isTagged(ModTags.Fluids.SAKE) || fluidState.isTagged(ModTags.Fluids.SAKE_STEPS)) {
            event.setRed(((ColorHelper.SakeColors.SAKE_COLOR_HEX >> 16) & 255) / 255.0F);
            event.setGreen(((ColorHelper.SakeColors.SAKE_COLOR_HEX >> 8) & 255) / 255.0F);
            event.setBlue((ColorHelper.SakeColors.SAKE_COLOR_HEX & 255) / 255.0F);
        }
    }

    @SubscribeEvent
    public static void onEntityViewRenderFogDensityEvent(EntityViewRenderEvent.FogDensity event) {
        ActiveRenderInfo renderInfo = event.getInfo();
        IFluidState fluidState = getFluidStateForEntity(renderInfo);
        Entity entity = Minecraft.getInstance().getRenderViewEntity();
        PlayerEntity player = null;
        if (entity instanceof PlayerEntity) {
            player = (PlayerEntity) entity;
        }

        if (fluidState.isTagged(ModTags.Fluids.MUST) || fluidState.isTagged(ModTags.Fluids.SAKE_STEPS)) {
            event.setDensity(0.50F);
            event.setCanceled(true);
        } else if (fluidState.isTagged(ModTags.Fluids.FERMENTED_MUST)) {
            event.setDensity(0.85F);
            event.setCanceled(true);
        } else if (fluidState.isTagged(ModTags.Fluids.FILTERED_FERMENTED_MUST)) {
            event.setDensity(0.35F);
            event.setCanceled(true);
        } else if (fluidState.isTagged(ModTags.Fluids.WINE) || fluidState.isTagged(ModTags.Fluids.SAKE)) {
            event.setDensity(0.20F);
            event.setCanceled(true);
        } else if (fluidState.isTagged(FluidTags.WATER) && player != null && (player.isPotionActive(ModEffects.AQUATIC_STAGE_1.get()) ||
                player.isPotionActive(ModEffects.AQUATIC_STAGE_2.get()) || player.isPotionActive(ModEffects.AQUATIC_STAGE_3.get()))) {
            float fogDensity;
            if (player.isPotionActive(ModEffects.AQUATIC_STAGE_3.get())) {
                fogDensity = ModEffects.AQUATIC_STAGE_3.get().getFogDensity();
            } else if (player.isPotionActive(ModEffects.AQUATIC_STAGE_2.get())) {
                fogDensity = ModEffects.AQUATIC_STAGE_2.get().getFogDensity();
            } else {
                fogDensity = ModEffects.AQUATIC_STAGE_1.get().getFogDensity();
            }
            event.setDensity(fogDensity);
            event.setCanceled(true);
        }
    }

    public static IFluidState getFluidStateForEntity(ActiveRenderInfo renderInfo) {
        Entity entity = renderInfo.getRenderViewEntity();
        World world = renderInfo.getRenderViewEntity().getEntityWorld();
        // Get coordinates for the block the head from the entity is in
        int x = MathHelper.floor(entity.getPosX());
        int y = MathHelper.floor(entity.getPosYEye());
        int z = MathHelper.floor(entity.getPosZ());
        return world.getFluidState(new BlockPos(x, y, z));
    }
}
