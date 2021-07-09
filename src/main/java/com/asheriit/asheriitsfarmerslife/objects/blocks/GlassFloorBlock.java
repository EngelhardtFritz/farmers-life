package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.state.properties.DiagonalBlockType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class GlassFloorBlock extends PaneBlock {
    public static final EnumProperty<DiagonalBlockType> MISSING_DIAGONAL_BLOCK = ModBlockStateProperties.DIAGONAL_BLOCK_TYPE;
    protected final VoxelShape[] shapes;
    private final Object2IntMap<BlockState> shapesHashMap = new Object2IntOpenHashMap<>();

    public GlassFloorBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(MISSING_DIAGONAL_BLOCK, DiagonalBlockType.EMPTY)
                .with(WATERLOGGED, false));
        this.shapes = this.makeShapes();
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[this.getShapeIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[this.getShapeIndex(state)];
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        IFluidState ifluidstate = context.getWorld().getFluidState(blockPos);
        BlockPos north = blockPos.add(0, 0, -1);
        BlockPos east = blockPos.add(1, 0, 0);
        BlockPos south = blockPos.add(0, 0, 1);
        BlockPos west = blockPos.add(-1, 0, 0);
        BlockState stateNorth = world.getBlockState(blockPos.add(0, 0, -1));
        BlockState stateEast = world.getBlockState(blockPos.add(1, 0, 0));
        BlockState stateSouth = world.getBlockState(blockPos.add(0, 0, 1));
        BlockState stateWest = world.getBlockState(blockPos.add(-1, 0, 0));

        return this.getDefaultState().with(NORTH, this.canConnect(stateNorth, stateNorth.isSolidSide(world, north, Direction.SOUTH)))
                .with(EAST, this.canConnect(stateEast, stateEast.isSolidSide(world, east, Direction.WEST)))
                .with(SOUTH, this.canConnect(stateSouth, stateSouth.isSolidSide(world, south, Direction.NORTH)))
                .with(WEST, this.canConnect(stateWest, stateWest.isSolidSide(world, west, Direction.EAST)))
                .with(MISSING_DIAGONAL_BLOCK, this.setDiagonalBlockType(world, blockPos))
                .with(WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos)
                .with(MISSING_DIAGONAL_BLOCK, this.setDiagonalBlockType(worldIn, currentPos));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, WATERLOGGED, MISSING_DIAGONAL_BLOCK);
    }

    private boolean canConnect(BlockState blockState, boolean isSolidSide) {
        Block block = blockState.getBlock();
        Tag<Block> glassTag = Tags.Blocks.GLASS;
        return !cannotAttach(block) && isSolidSide || block.isIn(glassTag);
    }

    private DiagonalBlockType setDiagonalBlockType(IWorld world, BlockPos blockPos) {
        BlockState stateNorthEast = world.getBlockState(blockPos.add(1, 0, -1));
        BlockState stateSouthEast = world.getBlockState(blockPos.add(1, 0, 1));
        BlockState stateSouthWest = world.getBlockState(blockPos.add(-1, 0, 1));
        BlockState stateNorthWest = world.getBlockState(blockPos.add(-1, 0, -1));
        boolean northEastIsSolid = this.isSolidBlock(stateNorthEast);
        boolean southEastIsSolid = this.isSolidBlock(stateSouthEast);
        boolean southWestIsSolid = this.isSolidBlock(stateSouthWest);
        boolean northWestIsSolid = this.isSolidBlock(stateNorthWest);

        if (!northEastIsSolid && southEastIsSolid && southWestIsSolid && northWestIsSolid) {
            return DiagonalBlockType.NORTHEAST;
        } else if (northEastIsSolid && !southEastIsSolid && southWestIsSolid && northWestIsSolid) {
            return DiagonalBlockType.SOUTHEAST;
        } else if (northEastIsSolid && southEastIsSolid && !southWestIsSolid && northWestIsSolid) {
            return DiagonalBlockType.SOUTHWEST;
        } else if (northEastIsSolid && southEastIsSolid && southWestIsSolid && !northWestIsSolid) {
            return DiagonalBlockType.NORTHWEST;
        } else if (northEastIsSolid && southEastIsSolid && southWestIsSolid && northWestIsSolid) {
            return DiagonalBlockType.ALL;
        }
        return DiagonalBlockType.EMPTY;
    }

    private boolean isSolidBlock(BlockState blockState) {
        Block block = blockState.getBlock();
        return !(block instanceof AirBlock) && !cannotAttach(block) && !(block instanceof FlowingFluidBlock) && !block.getClass().isAssignableFrom(Fluid.class) && !block.isIn(BlockTags.CROPS);
    }

    private VoxelShape[] makeShapes() {
        VoxelShape edgeNorthEast = Block.makeCuboidShape(7, 0, 0, 16, 16, 9);
        VoxelShape edgeSouthEast = Block.makeCuboidShape(7, 0, 7, 16, 16, 16);
        VoxelShape edgeSouthWest = Block.makeCuboidShape(0, 0, 7, 9, 16, 16);
        VoxelShape edgeNorthWest = Block.makeCuboidShape(0, 0, 0, 9, 16, 9);

        VoxelShape halfEast = Block.makeCuboidShape(7, 0, 0, 16, 16, 16);
        VoxelShape halfWest = Block.makeCuboidShape(0, 0, 0, 9, 16, 16);
        VoxelShape halfNorth = Block.makeCuboidShape(0, 0, 0, 16, 16, 9);
        VoxelShape halfSouth = Block.makeCuboidShape(0, 0, 7, 16, 16, 16);

        VoxelShape invEdgeNorthEast = VoxelShapes.or(halfWest, edgeSouthEast);
        VoxelShape invEdgeSouthEast = VoxelShapes.or(halfWest, edgeNorthEast);
        VoxelShape invEdgeSouthWest = VoxelShapes.or(halfEast, edgeNorthWest);
        VoxelShape invEdgeNorthWest = VoxelShapes.or(halfEast, edgeSouthWest);

        VoxelShape full = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
        VoxelShape[] shapes = new VoxelShape[]{
                edgeNorthEast, edgeSouthEast, edgeSouthWest, edgeNorthWest,
                halfEast, halfSouth, halfWest, halfNorth,
                invEdgeNorthEast, invEdgeSouthEast, invEdgeSouthWest, invEdgeNorthWest,
                full
        };
        return shapes;
    }

    private int getShapeIndex(BlockState blockState) {
        return this.shapesHashMap.computeIntIfAbsent(blockState, (state) -> {
            boolean north = state.get(NORTH);
            boolean east = state.get(EAST);
            boolean south = state.get(SOUTH);
            boolean west = state.get(WEST);
            DiagonalBlockType diagonalBlockType = state.get(MISSING_DIAGONAL_BLOCK);

            if (north && east && !south && !west) {
                return 0;
            }
            if (!north && east && south && !west) {
                return 1;
            }
            if (!north && !east && south && west) {
                return 2;
            }
            if (north && !east && !south && west) {
                return 3;
            }
            if (north && east && south && !west) {
                return 4;
            }
            if (!north && east && south) {
                return 5;
            }
            if (north && !east && south && west) {
                return 6;
            }
            if (north && east && !south) {
                return 7;
            }
            if (north && east && diagonalBlockType == DiagonalBlockType.NORTHEAST) {
                return 8;
            }
            if (north && east && diagonalBlockType == DiagonalBlockType.SOUTHEAST) {
                return 9;
            }
            if (north && east && diagonalBlockType == DiagonalBlockType.SOUTHWEST) {
                return 10;
            }
            if (north && east && diagonalBlockType == DiagonalBlockType.NORTHWEST) {
                return 11;
            }
            if ((north && east && (diagonalBlockType == DiagonalBlockType.EMPTY || diagonalBlockType == DiagonalBlockType.ALL)) ||
                    (north && !east && !south) || (!north && east && !west) ||
                    (!north && !east && south) || (!north && !east && west)) {
                return shapes.length - 1;
            }

            return shapes.length - 1;
        });
    }
}
