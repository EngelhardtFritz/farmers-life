package com.asheriit.asheriitsfarmerslife.client.baked_model;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.objects.blocks.TrellisBlock;
import com.asheriit.asheriitsfarmerslife.objects.blocks.TrellisCropBlock;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILightReader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.client.model.pipeline.TRSRTransformer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class TrellisCropBakedModel implements IBakedModel {
    public static ModelProperty<Optional<Map<Direction, BlockState>>> DIRECTION_TO_STATE_MAP = new ModelProperty<>();
    public static final ResourceLocation TRELLIS_NORTH = new ResourceLocation(FarmersLife.MOD_ID, "block/trellis/wood_trellis");
    public static final ResourceLocation TRELLIS_EAST = new ResourceLocation(FarmersLife.MOD_ID, "block/trellis/wood_trellis_east");
    public static final ResourceLocation TRELLIS_SOUTH = new ResourceLocation(FarmersLife.MOD_ID, "block/trellis/wood_trellis_alt");
    public static final ResourceLocation TRELLIS_WEST = new ResourceLocation(FarmersLife.MOD_ID, "block/trellis/wood_trellis_west");
    public static final ResourceLocation TRELLIS_UP = new ResourceLocation(FarmersLife.MOD_ID, "block/trellis/wood_trellis_bot");
    public static final ResourceLocation TRELLIS_DOWN = new ResourceLocation(FarmersLife.MOD_ID, "block/trellis/wood_trellis_top");
    public static final ResourceLocation TRELLIS_MIDDLE = new ResourceLocation(FarmersLife.MOD_ID, "block/trellis/wood_trellis_middle");
    private IBakedModel bakedModel;

    public TrellisCropBakedModel(IBakedModel defaultModel) {
        bakedModel = defaultModel;
    }

    @Override
    @Nonnull
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        return getBakedModelFromIModelData(state, side, rand, extraData);
    }

    @Override
    public TextureAtlasSprite getParticleTexture(@Nonnull IModelData data) {
        return bakedModel.getParticleTexture();
    }

    /**
     * Get Neighbour BlockStates for further use
     *
     * @param world:    World
     * @param pos:      BlockPos
     * @param state:    BlockState
     * @param tileData: TileData
     * @return IModelData including the added data
     */
    @Override
    @Nonnull
    public IModelData getModelData(@Nonnull ILightReader world, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull IModelData tileData) {
        BlockState north = world.getBlockState(pos.north());
        BlockState east = world.getBlockState(pos.east());
        BlockState south = world.getBlockState(pos.south());
        BlockState west = world.getBlockState(pos.west());
        BlockState up = world.getBlockState(pos.up());
        BlockState down = world.getBlockState(pos.down());

        Map<Direction, BlockState> directionBlockStateMap = new HashMap<>();
        directionBlockStateMap.put(Direction.NORTH, north);
        directionBlockStateMap.put(Direction.EAST, east);
        directionBlockStateMap.put(Direction.SOUTH, south);
        directionBlockStateMap.put(Direction.WEST, west);
        directionBlockStateMap.put(Direction.UP, up);
        directionBlockStateMap.put(Direction.DOWN, down);

        Optional<Map<Direction, BlockState>> optionalDirectionBlockStateMap = Optional.of(directionBlockStateMap);
        ModelDataMap modelDataMap = getEmptyIModelData();
        modelDataMap.setData(DIRECTION_TO_STATE_MAP, optionalDirectionBlockStateMap);
        return modelDataMap;
    }

    public static ModelDataMap getEmptyIModelData() {
        ModelDataMap.Builder builder = new ModelDataMap.Builder();
        builder.withInitial(DIRECTION_TO_STATE_MAP, Optional.empty());
        ModelDataMap modelDataMap = builder.build();
        return modelDataMap;
    }

    /**
     * Uses the data gathered in {@link #getModelData(ILightReader world, BlockPos pos, BlockState state, IModelData tileData) getModelData()}
     * to add quads to the model
     *
     * @param state: BlockState
     * @param side:  Direction
     * @param rand:  Random
     * @param data:  IModelData
     * @return List with all BakedQuads to render
     */
    private List<BakedQuad> getBakedModelFromIModelData(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData data) {
        IBakedModel model = bakedModel;
        if (!data.hasProperty(DIRECTION_TO_STATE_MAP)) {
            return model.getQuads(state, side, rand);
        }

        Optional<Map<Direction, BlockState>> optionalDirectionBlockStateMap = data.getData(DIRECTION_TO_STATE_MAP);
        if (!optionalDirectionBlockStateMap.isPresent()) {
            return model.getQuads(state, side, rand);
        }

        BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();

        Map<Direction, BlockState> directionBlockStateMap = optionalDirectionBlockStateMap.get();
        Map<Direction, Boolean> directionsToConnect = new HashMap<>();
        for (Direction direction : Direction.values()) {
            BlockState blockState = directionBlockStateMap.get(direction);
            Block block = blockState.getBlock();

            if (block instanceof TrellisBlock || block instanceof TrellisCropBlock || block.isIn(ModTags.Blocks.WINE_GRAPE_SOILS)) {
                directionsToConnect.put(direction, true);
            } else {
                directionsToConnect.put(direction, false);
            }
        }

        List<BakedQuad> bakedQuads = new LinkedList<>();
        boolean canConnectNorth = directionsToConnect.get(Direction.NORTH);
        boolean canConnectEast = directionsToConnect.get(Direction.EAST);
        boolean canConnectSouth = directionsToConnect.get(Direction.SOUTH);
        boolean canConnectWest = directionsToConnect.get(Direction.WEST);
        boolean canConnectUp = directionsToConnect.get(Direction.UP);
        boolean canConnectDown = directionsToConnect.get(Direction.DOWN);
        boolean canConnectNowhere = !canConnectNorth && !canConnectEast && !canConnectSouth && !canConnectWest && !canConnectUp && !canConnectDown;


        if (!canConnectNowhere) {
            IBakedModel trellisBase = blockRendererDispatcher.getBlockModelShapes().getModelManager().getModel(TRELLIS_MIDDLE);
            bakedQuads.addAll(trellisBase.getQuads(state, side, rand));
        }
        if (canConnectNorth || canConnectNowhere) {
            IBakedModel trellisBase = blockRendererDispatcher.getBlockModelShapes().getModelManager().getModel(TRELLIS_NORTH);
            bakedQuads.addAll(trellisBase.getQuads(state, side, rand));
        }
        if (canConnectEast || canConnectNowhere) {
            IBakedModel trellisBase = blockRendererDispatcher.getBlockModelShapes().getModelManager().getModel(TRELLIS_EAST);
            bakedQuads.addAll(trellisBase.getQuads(state, side, rand));
        }
        if (canConnectSouth || canConnectNowhere) {
            IBakedModel trellisBase = blockRendererDispatcher.getBlockModelShapes().getModelManager().getModel(TRELLIS_SOUTH);
            bakedQuads.addAll(trellisBase.getQuads(state, side, rand));
        }
        if (canConnectWest || canConnectNowhere) {
            IBakedModel trellisBase = blockRendererDispatcher.getBlockModelShapes().getModelManager().getModel(TRELLIS_WEST);
            bakedQuads.addAll(trellisBase.getQuads(state, side, rand));
        }
        if (canConnectDown || (!canConnectNorth && !canConnectEast && !canConnectSouth && !canConnectWest && !canConnectUp)) {
            IBakedModel trellisBase = blockRendererDispatcher.getBlockModelShapes().getModelManager().getModel(TRELLIS_DOWN);
            bakedQuads.addAll(trellisBase.getQuads(state, side, rand));
        }
        if (canConnectUp || (!canConnectNorth && !canConnectEast && !canConnectSouth && !canConnectWest && !canConnectDown)) {
            IBakedModel trellisBase = blockRendererDispatcher.getBlockModelShapes().getModelManager().getModel(TRELLIS_UP);
            bakedQuads.addAll(trellisBase.getQuads(state, side, rand));
        }

        bakedQuads.addAll(model.getQuads(state, side, rand, data));
        return bakedQuads;
    }

    // -------------------------- IBakedModel Method Implementations --------------------------
    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction side, Random rand) {
        throw new AssertionError("[TrellisCropBakedModel::getQuads] Should never be called!, only IForgeBakedModel::getQuads");
    }

    @Override
    public boolean isAmbientOcclusion() {
        return bakedModel.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return bakedModel.isGui3d();
    }

    @Override
    public boolean func_230044_c_() {
        return bakedModel.func_230044_c_();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return bakedModel.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return bakedModel.getParticleTexture();
    }

    @Override
    public ItemOverrideList getOverrides() {
        return bakedModel.getOverrides();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return bakedModel.getItemCameraTransforms();
    }
}
