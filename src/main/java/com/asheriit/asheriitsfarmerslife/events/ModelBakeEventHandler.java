package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.client.baked_model.TrellisCropBakedModel;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelBakeEventHandler {

    @SubscribeEvent
    public static void onModelBakeEvent(ModelBakeEvent event) {
        for (BlockState blockState : ModBlocks.BARBERA_TRELLIS_CROP.get().getStateContainer().getValidStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                FarmersLife.LOGGER.warn("Did not find the expected vanilla baked model(s) for BARBERA_TRELLIS_CROP in registry");
            } else if (existingModel instanceof TrellisCropBakedModel) {
                FarmersLife.LOGGER.warn("Tried to replace TrellisCropBakedModel twice");
            } else {
                TrellisCropBakedModel customModel = new TrellisCropBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

        for (BlockState blockState : ModBlocks.CABERNET_TRELLIS_CROP.get().getStateContainer().getValidStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                FarmersLife.LOGGER.warn("Did not find the expected vanilla baked model(s) for CABERNET_TRELLIS_CROP in registry");
            } else if (existingModel instanceof TrellisCropBakedModel) {
                FarmersLife.LOGGER.warn("Tried to replace TrellisCropBakedModel twice");
            } else {
                TrellisCropBakedModel customModel = new TrellisCropBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

        for (BlockState blockState : ModBlocks.MERLOT_TRELLIS_CROP.get().getStateContainer().getValidStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                FarmersLife.LOGGER.warn("Did not find the expected vanilla baked model(s) for MERLOT_TRELLIS_CROP in registry");
            } else if (existingModel instanceof TrellisCropBakedModel) {
                FarmersLife.LOGGER.warn("Tried to replace TrellisCropBakedModel twice");
            } else {
                TrellisCropBakedModel customModel = new TrellisCropBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

        for (BlockState blockState : ModBlocks.RED_GLOBE_TRELLIS_CROP.get().getStateContainer().getValidStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                FarmersLife.LOGGER.warn("Did not find the expected vanilla baked model(s) for RED_GLOBE_TRELLIS_CROP in registry");
            } else if (existingModel instanceof TrellisCropBakedModel) {
                FarmersLife.LOGGER.warn("Tried to replace TrellisCropBakedModel twice");
            } else {
                TrellisCropBakedModel customModel = new TrellisCropBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

        for (BlockState blockState : ModBlocks.KOSHU_TRELLIS_CROP.get().getStateContainer().getValidStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                FarmersLife.LOGGER.warn("Did not find the expected vanilla baked model(s) for KOSHU_TRELLIS_CROP in registry");
            } else if (existingModel instanceof TrellisCropBakedModel) {
                FarmersLife.LOGGER.warn("Tried to replace TrellisCropBakedModel twice");
            } else {
                TrellisCropBakedModel customModel = new TrellisCropBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

        for (BlockState blockState : ModBlocks.RIESLING_TRELLIS_CROP.get().getStateContainer().getValidStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                FarmersLife.LOGGER.warn("Did not find the expected vanilla baked model(s) for RIESLING_TRELLIS_CROP in registry");
            } else if (existingModel instanceof TrellisCropBakedModel) {
                FarmersLife.LOGGER.warn("Tried to replace TrellisCropBakedModel twice");
            } else {
                TrellisCropBakedModel customModel = new TrellisCropBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }

        for (BlockState blockState : ModBlocks.SULTANA_TRELLIS_CROP.get().getStateContainer().getValidStates()) {
            ModelResourceLocation variantMRL = BlockModelShapes.getModelLocation(blockState);
            IBakedModel existingModel = event.getModelRegistry().get(variantMRL);
            if (existingModel == null) {
                FarmersLife.LOGGER.warn("Did not find the expected vanilla baked model(s) for SULTANA_TRELLIS_CROP in registry");
            } else if (existingModel instanceof TrellisCropBakedModel) {
                FarmersLife.LOGGER.warn("Tried to replace TrellisCropBakedModel twice");
            } else {
                TrellisCropBakedModel customModel = new TrellisCropBakedModel(existingModel);
                event.getModelRegistry().put(variantMRL, customModel);
            }
        }
    }
}
