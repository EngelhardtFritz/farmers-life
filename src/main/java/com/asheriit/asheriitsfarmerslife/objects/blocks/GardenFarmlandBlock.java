package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class GardenFarmlandBlock extends FarmlandBlock {
    public static final IntegerProperty FERTILIZER_LEVEL = ModBlockStateProperties.FERTILIZER_LEVEL_0_4;

    public GardenFarmlandBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(MOISTURE, 0)
                .with(FERTILIZER_LEVEL, 0));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.UP && !stateIn.isValidPosition(worldIn, currentPos)) {
            turnToGardenDirt(stateIn, worldIn.getWorld(), currentPos);
        }

        return stateIn;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!state.isValidPosition(worldIn, pos)) {
            turnToGardenDirt(state, worldIn, pos);
        } else {
            int fertilizerLevel = state.get(FERTILIZER_LEVEL);
            int moisture = state.get(MOISTURE);
            int maxMoisture = 7;
            int defaultProbability = 30;

            if (rand.nextInt(defaultProbability * 2) == 0 && fertilizerLevel > 0) {
                worldIn.setBlockState(pos, state.with(FERTILIZER_LEVEL, fertilizerLevel - 1));
            }

            if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
                if (rand.nextInt(defaultProbability) == 0 && moisture > 0) {
                    worldIn.setBlockState(pos, state.with(MOISTURE, moisture - 1), 2);
                }
            } else if (moisture < maxMoisture) {
                worldIn.setBlockState(pos, state.with(MOISTURE, maxMoisture), 2);
            }
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 1.0F);
    }

    /**
     * Block instances which have the farmland tag will be able to sustain any crop
     */
    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        BlockPos plantPos = pos.offset(facing);
        PlantType plantType = plantable.getPlantType(world, plantPos);
        Block block = this.getBlock();

        if (plantType == PlantType.Crop) {
            return block.isIn(ModTags.Blocks.FARMLANDS);
        }

        return super.canSustainPlant(state, world, pos, facing, plantable);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE, FERTILIZER_LEVEL);
    }

    private static boolean hasWater(IWorldReader worldIn, BlockPos pos) {
        for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
            if (worldIn.getFluidState(blockpos).isTagged(FluidTags.WATER)) {
                return true;
            }
        }

        return net.minecraftforge.common.FarmlandWaterManager.hasBlockWaterTicket(worldIn, pos);
    }

    private static void turnToGardenDirt(BlockState state, World worldIn, BlockPos pos) {
        if (ModBlocks.GARDEN_DIRT.isPresent()) {
            worldIn.setBlockState(pos, nudgeEntitiesWithNewState(state, ModBlocks.GARDEN_DIRT.get().getDefaultState(), worldIn, pos));
        } else {
            LOGGER.error("[GardenFarmlandBlock] Could not get default state of GARDEN_DIRT_BLOCK!");
        }
    }
}
