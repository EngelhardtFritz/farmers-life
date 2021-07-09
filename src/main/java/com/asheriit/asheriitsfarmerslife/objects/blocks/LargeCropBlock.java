package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class LargeCropBlock extends BushBlock implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.makeCuboidShape(1, 1, 1, 15, 8, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 10, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 12, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 14, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 16, 15),
            Block.makeCuboidShape(1, 1, 1, 15, 16, 15)
    };
    private static final int MAX_AGE = 5;
    private static final int SPAWN_TOP_AGE = 3;

    public LargeCropBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(AGE, 0)
                .with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.isIn(ModTags.Blocks.FARMLANDS);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState stateUp = worldIn.getBlockState(pos.up());
        Block blockUp = stateUp.getBlock();
        boolean upperBlockValid = blockUp instanceof AirBlock || (blockUp == this && stateUp.get(HALF) == DoubleBlockHalf.UPPER);
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            return super.isValidPosition(state, worldIn, pos) && upperBlockValid;
        }
        return super.isValidPosition(state, worldIn, pos.down()) && upperBlockValid;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return PlantType.Crop;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof RavagerEntity && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entityIn)) {
            worldIn.destroyBlock(pos, true, entityIn);

            BlockPos upperHalf = pos.up();
            BlockState upperState = worldIn.getBlockState(upperHalf);
            if (upperState.getBlock() == this) {
                worldIn.destroyBlock(pos.up(), true, entityIn);
            }
        }

        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1))
            return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (worldIn.getLightSubtracted(pos, 0) >= 9) {
            int age = this.getAge(state);
            DoubleBlockHalf half = state.get(HALF);
            Block upperBlock = worldIn.getBlockState(pos.up()).getBlock();

            if (age < this.getMaxAge() && !(half == DoubleBlockHalf.LOWER && upperBlock instanceof LargeCropBlock)) {
                float growthChance = getGrowthChance(state, state.getBlock(), worldIn, pos);
                if (rand.nextInt((int) (25.0F / growthChance) + 1) == 0) {
                    if (half == DoubleBlockHalf.LOWER) {
                        if (age == SPAWN_TOP_AGE && worldIn.getBlockState(pos.up()).getBlock() instanceof AirBlock) {
                            worldIn.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER), 2);
                            return;
                        }
                    }

                    int newAge = age + 1;
                    if (half == DoubleBlockHalf.UPPER && newAge > SPAWN_TOP_AGE) {
                        BlockState lowerHalf = worldIn.getBlockState(pos.down());
                        if (lowerHalf.getBlock() instanceof LargeCropBlock) {
                            worldIn.setBlockState(pos.down(), lowerHalf.with(AGE, newAge));
                        }
                    }
                    worldIn.setBlockState(pos, state.with(AGE, newAge), 2);
                }
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int age = this.getAge(state);
        if (age == this.getMaxAge() && state.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos upperHalf = pos.up();
            BlockState upperState = worldIn.getBlockState(upperHalf);
            if (upperState.getBlock() != this) {
                // Upper block has not grown yet
                return ActionResultType.PASS;
            }

            if (this.getAge(upperState) == this.getMaxAge()) {
                // Large crop is fully grown
                if (worldIn.isRemote) {
                    return ActionResultType.SUCCESS;
                } else {
                    // Set states and drop loot
                    worldIn.destroyBlock(upperHalf, true);
                    worldIn.setBlockState(pos, state.with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER), 2);
                    return ActionResultType.SUCCESS;
                }
            }
        } else if (age == this.getMaxAge() && state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockPos lowerHalf = pos.down();
            BlockState lowerState = worldIn.getBlockState(lowerHalf);

            if (lowerState.getBlock() != this) {
                return ActionResultType.PASS;
            }

            if (worldIn.isRemote) {
                return ActionResultType.SUCCESS;
            } else {
                // Set states and drop loot
                worldIn.destroyBlock(pos, true);
                worldIn.setBlockState(lowerHalf, state.with(AGE, 0).with(HALF, DoubleBlockHalf.LOWER), 2);
                return ActionResultType.SUCCESS;
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleBlockHalf half = state.get(HALF);
        if (half == DoubleBlockHalf.LOWER && this.getAge(state) == this.getMaxAge()) {
            BlockPos upperHalf = pos.up();
            BlockState upperState = worldIn.getBlockState(upperHalf);
            if (upperState.getBlock() == this && !worldIn.isRemote) {
                worldIn.setBlockState(upperHalf, net.minecraft.block.Blocks.AIR.getDefaultState(), 2);
            }
        }
        if (half == DoubleBlockHalf.UPPER) {
            BlockPos posDown = pos.down();
            BlockState stateDown = worldIn.getBlockState(posDown);
            if (stateDown.getBlock() == this) {
                worldIn.destroyBlock(posDown, false);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE_BY_AGE[this.getAge(state)];
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        if (state.get(AGE) == this.getMaxAge() && state.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockPos posUp = pos.up();
            BlockState stateUp = worldIn.getBlockState(posUp);
            if (stateUp.getBlock() instanceof AirBlock) {
                return true;
            }

            if (stateUp.getBlock() == this) {
                return !this.isMaxAge(stateUp);
            }
        }
        return !this.isMaxAge(state);
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int currentAge = state.get(AGE);
        int bonemealAgeIncrease = this.getBonemealAgeIncrease(worldIn);
        DoubleBlockHalf half = state.get(HALF);

        if (half == DoubleBlockHalf.LOWER) {
            BlockPos posUp = pos.up();
            BlockState stateUp = worldIn.getBlockState(posUp);

            if (this.isMaxAge(state) && this.isMaxAge(stateUp)) return;

            int updatedAge = currentAge + bonemealAgeIncrease;
            if (updatedAge < SPAWN_TOP_AGE) {
                worldIn.setBlockState(pos, state.with(AGE, updatedAge), 2);
            } else if (updatedAge == SPAWN_TOP_AGE) {
                bonemealAgeIncrease--;
                worldIn.setBlockState(posUp, this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(AGE, bonemealAgeIncrease), 2);
            }
        }

        if (half == DoubleBlockHalf.UPPER) {
            BlockPos posDown = pos.down();
            BlockState stateDown = worldIn.getBlockState(posDown);

            if (this.isMaxAge(state) && this.isMaxAge(stateDown)) return;

            int updatedAge = currentAge + bonemealAgeIncrease;
            if (updatedAge <= SPAWN_TOP_AGE) {
                worldIn.setBlockState(pos, state.with(AGE, updatedAge), 2);
            } else {
                if (updatedAge > this.getMaxAge()) {
                    updatedAge = this.getMaxAge();
                }

                worldIn.setBlockState(pos, state.with(AGE, updatedAge), 2);
                worldIn.setBlockState(posDown, stateDown.with(AGE, updatedAge), 2);
            }
        }
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    public boolean isMaxAge(BlockState state) {
        return state.get(AGE) >= this.getMaxAge();
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 1, 4);
    }

    protected int getAge(BlockState state) {
        return state.get(AGE);
    }

    protected static float getGrowthChance(BlockState state, Block blockIn, IBlockReader worldIn, BlockPos pos) {
        BlockPos down;
        if (state.get(HALF) == DoubleBlockHalf.LOWER) {
            down = pos.down();
        } else {
            down = pos.add(0, -2, 0);
        }

        float f = 1.0F;
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                BlockState blockstate = worldIn.getBlockState(down.add(i, 0, j));
                boolean isFertilized = blockstate.getProperties().contains(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) && blockstate.get(ModBlockStateProperties.FERTILIZER_LEVEL_0_4) > 0;
                boolean isMoist = blockstate.getProperties().contains(BlockStateProperties.MOISTURE_0_7) && blockstate.get(BlockStateProperties.MOISTURE_0_7) > 0;
                float f1 = 0.0F;
                if (blockstate.canSustainPlant(worldIn, down.add(i, 0, j), net.minecraft.util.Direction.UP, (net.minecraftforge.common.IPlantable) blockIn)) {
                    f1 = 1.0F;
                    if (isMoist) {
                        f1 = 3.0F;
                    }
                    if (isFertilized) {
                        f1 += 4.0F;
                    }
                }

                if (i != 0 || j != 0) {
                    f1 = f1 / 4.0F;
                }

                f += f1;
            }
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean flag = blockIn == worldIn.getBlockState(west).getBlock() || blockIn == worldIn.getBlockState(east).getBlock();
        boolean flag1 = blockIn == worldIn.getBlockState(north).getBlock() || blockIn == worldIn.getBlockState(south).getBlock();
        if (flag && flag1) {
            f /= 2.0F;
        } else {
            boolean flag2 = blockIn == worldIn.getBlockState(west.north()).getBlock() || blockIn == worldIn.getBlockState(east.north()).getBlock() || blockIn == worldIn.getBlockState(east.south()).getBlock() || blockIn == worldIn.getBlockState(west.south()).getBlock();
            if (flag2) {
                f /= 2.0F;
            }
        }

        return f;
    }
}
