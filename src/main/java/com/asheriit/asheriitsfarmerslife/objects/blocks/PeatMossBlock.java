package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeatMossBlock extends Block implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;
    public static final VoxelShape SHAPE = Block.makeCuboidShape(1, 0, 1, 15, 5, 15);

    public PeatMossBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(AGE, 0));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        Block block = worldIn.getBlockState(pos).getBlock();
        return !cannotAttach(block) && !(block instanceof CropsBlock) && this.isValidGround(worldIn.getBlockState(blockpos), worldIn, blockpos);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.isValidPosition(worldIn, currentPos) ? net.minecraft.block.Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        int age = state.get(AGE);
        BlockPos posDown = pos.down();
        BlockPos posDownDown = posDown.down();
        BlockState stateDown = worldIn.getBlockState(posDown);
        BlockState stateDownDown = worldIn.getBlockState(posDownDown);
        Block blockDown = stateDown.getBlock();
        Block blockDownDown = stateDownDown.getBlock();

        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (blockDownDown == ModBlocks.PEAT_STAGE_THREE.get() && age == this.getMaxAge() && !(blockDown instanceof GrassBlock)) {
            return;
        }

        int lightLevel = worldIn.getLightSubtracted(pos.up(), 0);
        if (rand.nextInt(8) == 0 && lightLevel >= 10) {
            if (age == this.getMaxAge()) {
                if (blockDown instanceof GrassBlock) {
                    worldIn.setBlockState(pos.down(), ModBlocks.PEAT_MOSS_GRASS.get().getDefaultState().with(BlockStateProperties.SNOWY, stateDown.get(BlockStateProperties.SNOWY)), 2);
                }

                if (blockDownDown == net.minecraft.block.Blocks.DIRT) {
                    worldIn.setBlockState(posDownDown, ModBlocks.PEAT_STAGE_ONE.get().getDefaultState(), 2);
                    worldIn.setBlockState(pos, state.with(AGE, 2), 2);
                } else if (blockDownDown == ModBlocks.PEAT_STAGE_ONE.get()) {
                    worldIn.setBlockState(posDownDown, ModBlocks.PEAT_STAGE_TWO.get().getDefaultState(), 2);
                    worldIn.setBlockState(pos, state.with(AGE, 2), 2);
                } else if (blockDownDown == ModBlocks.PEAT_STAGE_TWO.get()) {
                    worldIn.setBlockState(posDownDown, ModBlocks.PEAT_STAGE_THREE.get().getDefaultState(), 2);
                    worldIn.setBlockState(pos, state.with(AGE, 2), 2);
                }
            } else {
                worldIn.setBlockState(pos, state.with(AGE, ++age), 2);
            }
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        net.minecraft.item.Item playerItem = player.getHeldItem(handIn).getItem();
        Random rand = worldIn.getRandom();
        if (playerItem.isIn(ModTags.Items.FERTILIZER)) {
            BlockPos randomPosToExpand = this.getExpandPosition(worldIn, pos, rand);
            if (randomPosToExpand == pos) {
                return ActionResultType.SUCCESS;
            }

            if (!worldIn.isRemote && rand.nextInt(2) == 0) {
                worldIn.setBlockState(randomPosToExpand, ModBlocks.PEAT_MOSS.get().getDefaultState(), 2);
            }
            player.getHeldItem(handIn).damageItem(1, player, (playerEntity) -> {
                playerEntity.sendBreakAnimation(player.getActiveHand());
            });
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);
        BlockPos posDown = pos.down();
        BlockState stateDown = worldIn.getBlockState(posDown);
        Block blockDown = stateDown.getBlock();
        if (blockDown == ModBlocks.PEAT_MOSS_GRASS.get() && !worldIn.isRemote) {
            worldIn.setBlockState(posDown, net.minecraft.block.Blocks.GRASS_BLOCK.getDefaultState().with(BlockStateProperties.SNOWY, stateDown.get(BlockStateProperties.SNOWY)));
        }
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int age = Math.min(this.getMaxAge(), state.get(AGE) + this.getBonemealAgeIncrease(worldIn));
        worldIn.setBlockState(pos, state.with(AGE, age), 2);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(AGE) < this.getMaxAge();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * This calculates the value the age is increased by when bonemeal is used
     *
     * @param worldIn: World to get the Random instance
     * @return
     */
    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 0, 2);
    }

    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return (block instanceof GrassBlock || block.isIn(Tags.Blocks.DIRT) || block.isIn(ModTags.Blocks.FARMLANDS) || block.isIn(Tags.Blocks.COBBLESTONE)) && block != this;
    }

    private BlockPos getExpandPosition(World world, BlockPos pos, Random rand) {
        BlockPos north = pos.north();
        BlockPos east = pos.east();
        BlockPos south = pos.south();
        BlockPos west = pos.west();

        List<BlockPos> validBlockPos = new ArrayList<>();
        if (this.isValidPosition(world.getBlockState(north), world, north)) {
            validBlockPos.add(north);
        }
        if (this.isValidPosition(world.getBlockState(east), world, east)) {
            validBlockPos.add(east);
        }
        if (this.isValidPosition(world.getBlockState(south), world, south)) {
            validBlockPos.add(south);
        }
        if (this.isValidPosition(world.getBlockState(west), world, west)) {
            validBlockPos.add(west);
        }

        if (validBlockPos.size() == 0) {
            return pos;
        }

        return validBlockPos.get(rand.nextInt(validBlockPos.size()));
    }

    private int getMaxAge() {
        return 5;
    }
}
