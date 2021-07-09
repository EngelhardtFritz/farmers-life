package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

public class TrellisBlock extends SixWayBlock {
    protected final VoxelShape[] collisionShapes;
    protected final VoxelShape[] shapes;
    private final Object2IntMap<BlockState> blockStateMap = new Object2IntOpenHashMap<>();

    public TrellisBlock(float apothem, Properties properties) {
        super(apothem, properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(EAST, false));
        this.collisionShapes = makeShapes(0);
        this.shapes = makeShapes(1);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.shapes[this.getIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.collisionShapes[this.getIndex(state)];
    }

    // If the block is in a valid position it can be placed by the player
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState blockStateUp = worldIn.getBlockState(pos.up());
        BlockState blockStateDown = worldIn.getBlockState(pos.down());
        BlockState blockStateNorth = worldIn.getBlockState(pos.north());
        BlockState blockStateEast = worldIn.getBlockState(pos.east());
        BlockState blockStateSouth = worldIn.getBlockState(pos.south());
        BlockState blockStateWest = worldIn.getBlockState(pos.west());
        return isPlaceable(blockStateUp)
                || isPlaceable(blockStateDown)
                || isPlaceable(blockStateNorth)
                || isPlaceable(blockStateEast)
                || isPlaceable(blockStateSouth)
                || isPlaceable(blockStateWest);
    }

    // Set the state the block will be placed with, checks direct block neighbours if it can connect
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        IBlockReader iblockreader = context.getWorld();
        BlockPos blockpos = context.getPos();
        BlockState blockStateUp = iblockreader.getBlockState(blockpos.up());
        BlockState blockStateDown = iblockreader.getBlockState(blockpos.down());
        BlockState blockStateNorth = iblockreader.getBlockState(blockpos.north());
        BlockState blockStateEast = iblockreader.getBlockState(blockpos.east());
        BlockState blockStateSouth = iblockreader.getBlockState(blockpos.south());
        BlockState blockStateWest = iblockreader.getBlockState(blockpos.west());
        return super.getStateForPlacement(context)
                .with(UP, this.canConnect(blockStateUp))
                .with(DOWN, this.canConnect(blockStateDown))
                .with(NORTH, this.canConnect(blockStateNorth))
                .with(EAST, this.canConnect(blockStateEast))
                .with(SOUTH, this.canConnect(blockStateSouth))
                .with(WEST, this.canConnect(blockStateWest));
    }

    // Update the provided state given the provided neighbor facing and neighbor state, returning a new state.
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing.getAxis().getPlane() == Direction.Plane.HORIZONTAL || facing.getAxis().getPlane() == Direction.Plane.VERTICAL) {
            // Get boolean property from facing map
            BooleanProperty blockFacingProperty = FACING_TO_PROPERTY_MAP.get(facing);
            // Can the facing block be connected
            Boolean connectInDirection = this.canConnect(facingState);
            return stateIn.with(blockFacingProperty, connectInDirection);
        }
        return stateIn;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        BlockState blockStateDown = worldIn.getBlockState(pos.down());
        ItemStack heldItem = player.getHeldItem(handIn);
        Block blockFromHand = Block.getBlockFromItem(heldItem.getItem());
        // If below block is not dirt -> don't place any crop type

        if (!blockFromHand.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
            return ActionResultType.FAIL;
        }


        // Different placement conditions for specific crops
        Block blockDown = blockStateDown.getBlock();
        if ((blockFromHand == ModBlocks.BARBERA_TRELLIS_CROP.get() && (blockDown == net.minecraft.block.Blocks.CLAY || blockStateDown.isIn(Tags.Blocks.DIRT))) ||
                (blockFromHand == ModBlocks.CABERNET_TRELLIS_CROP.get() && blockStateDown.isIn(Tags.Blocks.GRAVEL)) ||
                (blockFromHand == ModBlocks.MERLOT_TRELLIS_CROP.get() && (blockDown == net.minecraft.block.Blocks.CLAY || blockStateDown.isIn(Tags.Blocks.DIRT))) ||
                (blockFromHand == ModBlocks.RED_GLOBE_TRELLIS_CROP.get() && blockStateDown.isIn(Tags.Blocks.DIRT)) ||
                (blockFromHand == ModBlocks.KOSHU_TRELLIS_CROP.get() && blockDown == net.minecraft.block.Blocks.CLAY) ||
                (blockFromHand == ModBlocks.RIESLING_TRELLIS_CROP.get() && (blockDown == net.minecraft.block.Blocks.CLAY || blockStateDown.isIn(Tags.Blocks.SAND))) ||
                (blockFromHand == ModBlocks.SULTANA_TRELLIS_CROP.get() && blockStateDown.isIn(Tags.Blocks.DIRT))) {
            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos, blockFromHand.getDefaultState()
                        .with(ModBlockStateProperties.IS_ROOT_BLOCK, true)
                        .with(BlockStateProperties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite()));
                this.shrinkItem(player, heldItem);
                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    public void shrinkItem(PlayerEntity player, ItemStack stackToShrink) {
        if (!player.isCreative()) {
            stackToShrink.shrink(1);
        }
    }

    public boolean canConnect(BlockState blockState) {
        Block block = blockState.getBlock();
        boolean isInTrellisTagAndSameMaterial = block.isIn(ModTags.Blocks.TRELLISES) && blockState.getMaterial() == this.material;
        boolean isInTrellisCropTag = block.isIn(ModTags.Blocks.CROPS_WINE_GRAPES);
        boolean isValidSoil = block.isIn(ModTags.Blocks.WINE_GRAPE_SOILS);
        return isInTrellisTagAndSameMaterial || isInTrellisCropTag || isValidSoil;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
    }

    protected int getIndex(BlockState state) {
        return this.blockStateMap.computeIntIfAbsent(state, (blockState) -> {
            int i = 0;
            if (blockState.get(UP)) {
                i |= getMask(Direction.UP);
            }

            if (blockState.get(DOWN)) {
                i |= getMask(Direction.DOWN);
            }

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

    private boolean isPlaceable(BlockState blockState) {
        Block block = blockState.getBlock();
        boolean isInFenceTag = block.isIn(BlockTags.FENCES);
        boolean isInGlassOrGlassPaneTags = block.isIn(Tags.Blocks.GLASS) || block.isIn(Tags.Blocks.GLASS_PANES);
        boolean isAirBlock = block instanceof AirBlock;
        return !isInFenceTag && !isInGlassOrGlassPaneTags && !isAirBlock;
    }

    public static VoxelShape[] makeShapes(int extraShape) {
        VoxelShape up = Block.makeCuboidShape(7 - extraShape, 9 - extraShape, 7 - extraShape, 9 + extraShape, 16, 9 + extraShape);
        VoxelShape down = Block.makeCuboidShape(7 - extraShape, 0, 7 - extraShape, 9 + extraShape, 11 + extraShape, 9 + extraShape);
        VoxelShape north = Block.makeCuboidShape(7 - extraShape, 9 - extraShape, 0, 9 + extraShape, 11 + extraShape, 9 + extraShape);
        VoxelShape east = Block.makeCuboidShape(7 - extraShape, 9 - extraShape, 7 - extraShape, 16, 11 + extraShape, 9 + extraShape);
        VoxelShape west = Block.makeCuboidShape(0, 9 - extraShape, 7 - extraShape, 9 + extraShape, 11 + extraShape, 9 + extraShape);
        VoxelShape south = Block.makeCuboidShape(7 - extraShape, 9 - extraShape, 7 - extraShape, 9 + extraShape, 11 + extraShape, 16);
        VoxelShape downUp = VoxelShapes.or(down, up);
        VoxelShape eastWest = VoxelShapes.or(east, west);
        VoxelShape downNorth = VoxelShapes.or(down, north);
        VoxelShape upNorth = VoxelShapes.or(up, north);
        VoxelShape upDownNorth = VoxelShapes.or(downUp, north);
        VoxelShape downSouth = VoxelShapes.or(down, south);
        VoxelShape upSouth = VoxelShapes.or(up, south);
        VoxelShape upDownSouth = VoxelShapes.or(downUp, south);
        VoxelShape northSouth = VoxelShapes.or(north, south);
        VoxelShape downNorthSouth = VoxelShapes.or(northSouth, down);
        VoxelShape upNorthSouth = VoxelShapes.or(northSouth, up);
        VoxelShape upDownNorthSouth = VoxelShapes.or(downUp, northSouth);
        VoxelShape westDown = VoxelShapes.or(down, west);
        VoxelShape westUp = VoxelShapes.or(up, west);
        VoxelShape westUpDown = VoxelShapes.or(down, westUp);
        VoxelShape westNorth = VoxelShapes.or(north, west);
        VoxelShape westNorthDown = VoxelShapes.or(down, westNorth);
        VoxelShape westNorthUp = VoxelShapes.or(up, westNorth);
        VoxelShape westNorthUpDown = VoxelShapes.or(westNorth, downUp);
        VoxelShape westSouth = VoxelShapes.or(south, west);
        VoxelShape westSouthDown = VoxelShapes.or(down, westSouth);
        VoxelShape westSouthUp = VoxelShapes.or(up, westSouth);
        VoxelShape westSouthUpDown = VoxelShapes.or(westSouth, downUp);
        VoxelShape westSouthNorth = VoxelShapes.or(westSouth, north);
        VoxelShape westSouthNorthDown = VoxelShapes.or(westSouthNorth, down);
        VoxelShape westSouthNorthUp = VoxelShapes.or(westSouthNorth, up);
        VoxelShape westSouthNorthUpDown = VoxelShapes.or(westSouthNorth, downUp);
        VoxelShape eastDown = VoxelShapes.or(east, down);
        VoxelShape eastUp = VoxelShapes.or(east, up);
        VoxelShape eastUpDown = VoxelShapes.or(eastUp, down);
        VoxelShape eastNorth = VoxelShapes.or(east, north);
        VoxelShape eastNorthDown = VoxelShapes.or(eastNorth, down);
        VoxelShape eastNorthUp = VoxelShapes.or(eastNorth, up);
        VoxelShape eastNorthUpDown = VoxelShapes.or(eastNorth, downUp);
        VoxelShape eastSouth = VoxelShapes.or(east, south);
        VoxelShape eastSouthDown = VoxelShapes.or(eastSouth, down);
        VoxelShape eastSouthUp = VoxelShapes.or(eastSouth, up);
        VoxelShape eastSouthUpDown = VoxelShapes.or(eastSouth, downUp);
        VoxelShape eastSouthNorth = VoxelShapes.or(eastSouth, north);
        VoxelShape eastSouthNorthDown = VoxelShapes.or(eastSouthNorth, down);
        VoxelShape eastSouthNorthUp = VoxelShapes.or(eastSouthNorth, up);
        VoxelShape eastSouthNorthUpDown = VoxelShapes.or(eastSouthNorth, downUp);
        VoxelShape eastWestDown = VoxelShapes.or(eastWest, down);
        VoxelShape eastWestUp = VoxelShapes.or(eastWest, up);
        VoxelShape eastWestUpDown = VoxelShapes.or(eastWest, downUp);
        VoxelShape eastWestNorth = VoxelShapes.or(eastWest, north);
        VoxelShape eastWestNorthDown = VoxelShapes.or(eastWestNorth, down);
        VoxelShape eastWestNorthUp = VoxelShapes.or(eastWestNorth, up);
        VoxelShape eastWestNorthUpDown = VoxelShapes.or(eastWestNorth, downUp);
        VoxelShape eastWestSouth = VoxelShapes.or(eastWest, south);
        VoxelShape eastWestSouthDown = VoxelShapes.or(eastWestSouth, down);
        VoxelShape eastWestSouthUp = VoxelShapes.or(eastWestSouth, up);
        VoxelShape eastWestSouthUpDown = VoxelShapes.or(eastWestSouth, downUp);
        VoxelShape eastWestSouthNorth = VoxelShapes.or(eastWestSouth, north);
        VoxelShape eastWestSouthNorthDown = VoxelShapes.or(eastWestSouthNorth, down);
        VoxelShape eastWestSouthNorthUp = VoxelShapes.or(eastWestSouthNorth, up);
        VoxelShape eastWestSouthNorthUpDown = VoxelShapes.or(eastWestSouthNorth, downUp);

        VoxelShape[] shapes = new VoxelShape[]{
                eastWestSouthNorthUpDown, down, up, downUp, north, downNorth, upNorth, upDownNorth,
                south, downSouth, upSouth, upDownSouth, northSouth, downNorthSouth, upNorthSouth, upDownNorthSouth,
                west, westDown, westUp, westUpDown, westNorth, westNorthDown, westNorthUp, westNorthUpDown,
                westSouth, westSouthDown, westSouthUp, westSouthUpDown, westSouthNorth, westSouthNorthDown, westSouthNorthUp, westSouthNorthUpDown,
                east, eastDown, eastUp, eastUpDown, eastNorth, eastNorthDown, eastNorthUp, eastNorthUpDown,
                eastSouth, eastSouthDown, eastSouthUp, eastSouthUpDown, eastSouthNorth, eastSouthNorthDown, eastSouthNorthUp, eastSouthNorthUpDown,
                eastWest, eastWestDown, eastWestUp, eastWestUpDown, eastWestNorth, eastWestNorthDown, eastWestNorthUp, eastWestNorthUpDown,
                eastWestSouth, eastWestSouthDown, eastWestSouthUp, eastWestSouthUpDown, eastWestSouthNorth, eastWestSouthNorthDown, eastWestSouthNorthUp, eastWestSouthNorthUpDown
        };

        return shapes;
    }

    private static int getMask(Direction facing) {
        return 1 << facing.getIndex();
    }
}
