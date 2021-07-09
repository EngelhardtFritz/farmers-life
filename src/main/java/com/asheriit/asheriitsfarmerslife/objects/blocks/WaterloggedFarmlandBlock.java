package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.api.utils.FacingHelper;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Random;

public class WaterloggedFarmlandBlock extends Block implements IWaterLoggable {
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = FacingHelper.FOUR_WAY_FACING;
    private final VoxelShape[] shapes;
    private final Object2IntMap<BlockState> blockStateMap = new Object2IntOpenHashMap<>();
    private final Block grassBlock;

    public WaterloggedFarmlandBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(WATERLOGGED, false));
        this.shapes = this.makeShapes();
        this.grassBlock = this;
    }

    public WaterloggedFarmlandBlock(Properties properties, Block grassBlock) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(WATERLOGGED, false));
        this.shapes = this.makeShapes();
        this.grassBlock = grassBlock;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[this.getIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[this.getIndex(state)];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState blockStateNorth = world.getBlockState(pos.north());
        BlockState blockStateEast = world.getBlockState(pos.east());
        BlockState blockStateSouth = world.getBlockState(pos.south());
        BlockState blockStateWest = world.getBlockState(pos.west());
        IFluidState ifluidstate = world.getWorld().getFluidState(pos);
        return this.getDefaultState()
                .with(NORTH, this.canConnect(blockStateNorth))
                .with(EAST, this.canConnect(blockStateEast))
                .with(SOUTH, this.canConnect(blockStateSouth))
                .with(WEST, this.canConnect(blockStateWest))
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (state.isIn(ModTags.Blocks.GRASS_SUBMERGED_FARMLAND)) {
            return;
        }

        BlockState north = worldIn.getBlockState(pos.north());
        BlockState east = worldIn.getBlockState(pos.east());
        BlockState south = worldIn.getBlockState(pos.south());
        BlockState west = worldIn.getBlockState(pos.west());

        if (north.isIn(ModTags.Blocks.GRASS_BLOCKS) || east.isIn(ModTags.Blocks.GRASS_BLOCKS) || south.isIn(ModTags.Blocks.GRASS_BLOCKS) || west.isIn(ModTags.Blocks.GRASS_BLOCKS)) {
            if (rand.nextInt(3) == 0) {
                BlockState stateToSet = grassBlock.getDefaultState()
                        .with(WATERLOGGED, state.get(WATERLOGGED))
                        .with(NORTH, state.get(NORTH))
                        .with(EAST, state.get(EAST))
                        .with(SOUTH, state.get(SOUTH))
                        .with(WEST, state.get(WEST));
                worldIn.setBlockState(pos, stateToSet, 2);
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL) {
            // Get boolean property from facing map
            BooleanProperty blockFacingProperty = FACING_TO_PROPERTY_MAP.get(facing);
            // Can the facing block be connected
            Boolean connectInDirection = this.canConnect(facingState);
            return stateIn
                    .with(blockFacingProperty, connectInDirection);
        }
        return stateIn;
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        Block block = this.getBlock();
        if (block instanceof WaterloggedCropBlock) {
            return block.isIn(ModTags.Blocks.SUBMERGED_FARMLAND);
        }

        return super.canSustainPlant(state, world, pos, facing, plantable);
    }

    @Override
    public IFluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH, EAST, SOUTH, WEST);
    }

    public VoxelShape[] makeShapes() {
        VoxelShape bottom = Block.makeCuboidShape(0, 0, 0, 16, 8, 16);
        VoxelShape north = Block.makeCuboidShape(0, 0, 0, 16, 16, 2);
        VoxelShape east = Block.makeCuboidShape(14, 0, 0, 16, 16, 16);
        VoxelShape south = Block.makeCuboidShape(0, 0, 14, 16, 16, 16);
        VoxelShape west = Block.makeCuboidShape(0, 0, 0, 2, 16, 16);
        VoxelShape none = VoxelShapes.or(VoxelShapes.or(VoxelShapes.or(north, east), VoxelShapes.or(south, west)), bottom);
        VoxelShape eastNorthSouth = VoxelShapes.or(bottom, west);
        VoxelShape eastNorthWest = VoxelShapes.or(bottom, south);
        VoxelShape northWestSouth = VoxelShapes.or(bottom, east);
        VoxelShape eastWestSouth = VoxelShapes.or(bottom, north);
        VoxelShape westSouth = VoxelShapes.or(eastWestSouth, east);
        VoxelShape northSouth = VoxelShapes.or(northWestSouth, west);
        VoxelShape northWest = VoxelShapes.or(northWestSouth, south);
        VoxelShape eastSouth = VoxelShapes.or(eastWestSouth, west);
        VoxelShape eastWest = VoxelShapes.or(eastNorthWest, north);
        VoxelShape eastNorth = VoxelShapes.or(eastNorthSouth, south);
        VoxelShape allButNorth = VoxelShapes.or(northSouth, south);
        VoxelShape allButEast = VoxelShapes.or(eastSouth, south);
        VoxelShape allButSouth = VoxelShapes.or(northSouth, north);
        VoxelShape allButWest = VoxelShapes.or(eastWest, east);

        VoxelShape[] resultShape = new VoxelShape[]{
                none, allButSouth, allButWest, westSouth, allButNorth, northSouth, northWest, northWestSouth,
                allButEast, eastSouth, eastWest, eastWestSouth, eastNorth, eastNorthSouth, eastNorthWest, bottom
        };
        return resultShape;
    }

    protected int getIndex(BlockState state) {
        return this.blockStateMap.computeIntIfAbsent(state, (blockState) -> {
            int i = 0;

            if (blockState.get(NORTH)) {
                i |= getMask(Direction.NORTH);
            }

            if (blockState.get(EAST)) {
                i |= getMask(Direction.EAST);
            }

            if (blockState.get(SOUTH)) {
                i |= getMask(Direction.SOUTH);
            }

            if (blockState.get(WEST)) {
                i |= getMask(Direction.WEST);
            }

            return i;
        });
    }

    private static int getMask(Direction facing) {
        return 1 << facing.getHorizontalIndex();
    }

    private boolean canConnect(BlockState blockState) {
        return blockState.getBlock().getClass() == this.getClass();
    }
}
