package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.api.utils.FacingHelper;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.utils.BlockType;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;
import java.util.Random;

public class GrassDamBlock extends Block {
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;
    protected static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = FacingHelper.FOUR_WAY_FACING;
    private static final int MAX_AGE = 5;
    private final VoxelShape[] smallShapes, largeShapes, collShapesSmall, collShapesLarge;
    private final Object2IntMap<BlockState> shapeToIntMap = new Object2IntOpenHashMap<>();
    private final BlockType blockType;
    private final boolean hasCrop;

    public GrassDamBlock(BlockType blockType, boolean hasCrop, Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(NORTH, false)
                .with(EAST, false)
                .with(SOUTH, false)
                .with(WEST, false)
                .with(AGE, 0));
        this.smallShapes = makeShapes(BlockType.SMALL, hasCrop);
        this.largeShapes = makeShapes(BlockType.LARGE, hasCrop);
        this.collShapesSmall = makeShapes(BlockType.SMALL, false);
        this.collShapesLarge = makeShapes(BlockType.LARGE, false);
        this.blockType = blockType;
        this.hasCrop = hasCrop;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.blockType == BlockType.SMALL ? smallShapes[this.getIndex(state)] : largeShapes[this.getIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.blockType == BlockType.SMALL ? collShapesSmall[this.getIndex(state)] : collShapesLarge[this.getIndex(state)];
    }

    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        BlockState blockstate = world.getBlockState(blockpos1);
        BlockState blockstate1 = world.getBlockState(blockpos2);
        BlockState blockstate2 = world.getBlockState(blockpos3);
        BlockState blockstate3 = world.getBlockState(blockpos4);
        return this.getDefaultState()
                .with(NORTH, this.canAttachTo(blockstate, blockstate.isSolidSide(world, blockpos1, Direction.SOUTH)))
                .with(SOUTH, this.canAttachTo(blockstate1, blockstate1.isSolidSide(world, blockpos2, Direction.NORTH)))
                .with(WEST, this.canAttachTo(blockstate2, blockstate2.isSolidSide(world, blockpos3, Direction.EAST)))
                .with(EAST, this.canAttachTo(blockstate3, blockstate3.isSolidSide(world, blockpos4, Direction.WEST)));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing.getAxis().isHorizontal()) {
            return stateIn.with(FACING_TO_PROPERTY_MAP.get(facing), this.canAttachTo(facingState, facingState.isSolidSide(worldIn, facingPos, facing.getOpposite())));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    /**
     * Check if the bottom block has changed.
     * If the bottom block is no longer an instance of WaterloggedFarmlandBlock remove crop from dam and spawn drops
     */
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        Vec3i posDif = new Vec3i(fromPos.getX() - pos.getX(), fromPos.getY() - pos.getY(), fromPos.getZ() - pos.getZ());
        if (posDif.equals(Direction.DOWN.getDirectionVec())) {
            Block blockAtChangedPos = worldIn.getBlockState(fromPos).getBlock();
            if (!(blockAtChangedPos instanceof WaterloggedFarmlandBlock)) {
                if (state.getBlock() instanceof GrassDamBlock) {
                    this.spawnDrops(state.getBlock(), state.get(AGE), worldIn, pos);
                }
                this.resetToCroplessState(state.getBlock(), worldIn, pos);
            }
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if (hasCrop) {
            int age = state.get(AGE);
            BlockState stateDown = worldIn.getBlockState(pos.down());
            if (age == getMaxAge() || (stateDown.getProperties().contains(BlockStateProperties.WATERLOGGED) && !stateDown.get(BlockStateProperties.WATERLOGGED))) {
                return;
            }

            int lightLevel = worldIn.getLightSubtracted(pos.up(), 0);
            if (lightLevel > 9 && rand.nextInt((int) (25 / getGrowthChance(worldIn, pos)) + 1) == 0) {
                worldIn.setBlockState(pos, state.with(AGE, age + 1), 2);
            }
        }
    }

    @Override
    public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        if (this.hasCrop) {
            player.addExhaustion(0.005F);
            Block block = state.getBlock();

            if (!player.isCreative()) {
                int ageOfCrop = state.get(AGE);
                this.spawnDrops(block, ageOfCrop, worldIn, pos);
            }

            this.resetToCroplessState(block, worldIn, pos);
        }
    }

    /**
     * NOTE: always sync with linked method if the block has a crop
     * {@link com.asheriit.asheriitsfarmerslife.objects.blocks.WaterloggedCropBlock#onBlockActivated(BlockState, World, BlockPos, PlayerEntity, Hand, BlockRayTraceResult)}
     */
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack handStack = player.getHeldItem(handIn);
        Item handItem = handStack.getItem();
        Block handBlock = Block.getBlockFromItem(handItem);

        final Block activatedBlock = state.getBlock();
        BlockState blockStateDown = worldIn.getBlockState(pos.down());
        Block blockDown = blockStateDown.getBlock();
        boolean blockDownValid = blockDown instanceof WaterloggedFarmlandBlock && blockStateDown.get(BlockStateProperties.WATERLOGGED);

        if (!(handBlock instanceof AirBlock) && blockDownValid && !this.hasCrop && !worldIn.isRemote) {
            Block blockToSet = null;
            if (handBlock == ModBlocks.RICE_JAPONICA.get()) {
                if (this.blockType == BlockType.SMALL) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_SMALL_DAM_JAPONICA.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_SMALL_DAM_JAPONICA.get();
                    }
                } else if (this.blockType == BlockType.LARGE) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_LARGE_DAM_JAPONICA.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_LARGE_DAM_JAPONICA.get();
                    }
                }
            } else if (handBlock == ModBlocks.RICE_BLACK_JAPONICA.get()) {
                if (this.blockType == BlockType.SMALL) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_SMALL_DAM_BLACK_JAPONICA.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_SMALL_DAM_BLACK_JAPONICA.get();
                    }
                } else if (this.blockType == BlockType.LARGE) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_LARGE_DAM_BLACK_JAPONICA.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_LARGE_DAM_BLACK_JAPONICA.get();
                    }
                }
            } else if (handBlock == ModBlocks.RICE_INDICA.get()) {
                if (this.blockType == BlockType.SMALL) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_SMALL_DAM_INDICA.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_SMALL_DAM_INDICA.get();
                    }
                } else if (this.blockType == BlockType.LARGE) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_LARGE_DAM_INDICA.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_LARGE_DAM_INDICA.get();
                    }
                }
            } else if (handBlock == ModBlocks.RICE_YAMADANISHIKI.get()) {
                if (this.blockType == BlockType.SMALL) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_SMALL_DAM_YAMADANISHIKI.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_SMALL_DAM_YAMADANISHIKI.get();
                    }
                } else if (this.blockType == BlockType.LARGE) {
                    if (activatedBlock instanceof GrassDamBlock) {
                        blockToSet = ModBlocks.GRASS_LARGE_DAM_YAMADANISHIKI.get();
                    }
                    if (activatedBlock instanceof DirtDamBlock) {
                        blockToSet = ModBlocks.DIRT_LARGE_DAM_YAMADANISHIKI.get();
                    }
                }
            }

            if (blockToSet != null) {
                worldIn.setBlockState(pos, this.setPlacementState(worldIn, pos, blockToSet.getDefaultState()), 2);
                return ActionResultType.SUCCESS;
            }
        }

        if (this.hasCrop) {
            int age = state.get(AGE);
            if (age == getMaxAge()) {
                if (!worldIn.isRemote) {
                    this.spawnDrops(activatedBlock, age, worldIn, pos);
                    worldIn.setBlockState(pos, state.with(AGE, 0), 2);
                }
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.PASS;
        }

        return ActionResultType.FAIL;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, AGE);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }

    public final boolean canAttachTo(BlockState state, boolean isSolidSide) {
        Block block = state.getBlock();
        return !cannotAttach(block) && isSolidSide;
    }

    protected int getIndex(BlockState state) {
        return this.shapeToIntMap.computeIntIfAbsent(state, (blockState) -> {
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

    protected static VoxelShape[] makeShapes(BlockType blockType, boolean hasCrop) {
        VoxelShape north;
        VoxelShape east;
        VoxelShape south;
        VoxelShape west;

        if (blockType == BlockType.LARGE) {
            north = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
            east = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
            south = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
            west = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 16.0D);
        } else {
            north = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 2.0D);
            east = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
            south = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 16.0D, 8.0D, 16.0D);
            west = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 8.0D, 16.0D);
        }

        VoxelShape age4and5 = Block.makeCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 10.0D, 14.0D);

        VoxelShape northSouth = VoxelShapes.or(north, south);
        VoxelShape westEast = VoxelShapes.or(west, east);
        VoxelShape northEastSouthWest = VoxelShapes.or(northSouth, westEast);

        VoxelShape northEast = VoxelShapes.or(north, east);
        VoxelShape southEast = VoxelShapes.or(south, east);
        VoxelShape southWest = VoxelShapes.or(south, west);
        VoxelShape northWest = VoxelShapes.or(north, west);

        VoxelShape northEastSouth = VoxelShapes.or(northEast, south);
        VoxelShape eastSouthWest = VoxelShapes.or(southEast, west);
        VoxelShape southWestNorth = VoxelShapes.or(southWest, north);
        VoxelShape westNorthEast = VoxelShapes.or(northWest, east);

        if (hasCrop) {
            northEastSouthWest = VoxelShapes.or(northEastSouthWest, age4and5);
            south = VoxelShapes.or(south, age4and5);
            west = VoxelShapes.or(west, age4and5);
            southWest = VoxelShapes.or(southWest, age4and5);
            north = VoxelShapes.or(north, age4and5);
            northSouth = VoxelShapes.or(northSouth, age4and5);
            northWest = VoxelShapes.or(northWest, age4and5);
            southWestNorth = VoxelShapes.or(southWestNorth, age4and5);
            east = VoxelShapes.or(east, age4and5);
            southEast = VoxelShapes.or(southEast, age4and5);
            westEast = VoxelShapes.or(westEast, age4and5);
            eastSouthWest = VoxelShapes.or(eastSouthWest, age4and5);
            northEast = VoxelShapes.or(northEast, age4and5);
            northEastSouth = VoxelShapes.or(northEastSouth, age4and5);
            westNorthEast = VoxelShapes.or(westNorthEast, age4and5);
            northEastSouthWest = VoxelShapes.or(northEastSouthWest, age4and5);
        }

        VoxelShape[] shapes = new VoxelShape[]{
                northEastSouthWest, south, west, southWest, north, northSouth, northWest, southWestNorth,
                east, southEast, westEast, eastSouthWest, northEast, northEastSouth, westNorthEast, northEastSouthWest
        };
        return shapes;
    }

    private static int getMask(Direction facing) {
        return 1 << facing.getHorizontalIndex();
    }

    private static int getMaxAge() {
        return MAX_AGE;
    }

    private int getGrowthChance(IBlockReader worldIn, BlockPos blockPos) {
        int chance = 4;
        BlockPos pos = blockPos;
        BlockPos posDown = pos.down();

        for (int x = -1; x <= 1; ++x) {
            for (int z = -1; z <= 1; ++z) {
                if (worldIn.getBlockState(posDown.add(x, 0, z)).isIn(ModTags.Blocks.SUBMERGED_FARMLAND) &&
                        worldIn.getBlockState(pos.add(x, 0, z)).getBlock() instanceof WaterloggedCropBlock &&
                        x != 0 && z != 0) {
                    chance += 1;
                }
            }
        }
        return chance;
    }

    private BlockState setPlacementState(World world, BlockPos pos, BlockState state) {
        BlockPos blockpos1 = pos.north();
        BlockPos blockpos2 = pos.south();
        BlockPos blockpos3 = pos.west();
        BlockPos blockpos4 = pos.east();
        BlockState blockstate = world.getBlockState(blockpos1);
        BlockState blockstate1 = world.getBlockState(blockpos2);
        BlockState blockstate2 = world.getBlockState(blockpos3);
        BlockState blockstate3 = world.getBlockState(blockpos4);
        return state.with(NORTH, this.canAttachTo(blockstate, blockstate.isSolidSide(world, blockpos1, Direction.SOUTH)))
                .with(SOUTH, this.canAttachTo(blockstate1, blockstate1.isSolidSide(world, blockpos2, Direction.NORTH)))
                .with(WEST, this.canAttachTo(blockstate2, blockstate2.isSolidSide(world, blockpos3, Direction.EAST)))
                .with(EAST, this.canAttachTo(blockstate3, blockstate3.isSolidSide(world, blockpos4, Direction.WEST)));
    }

    /**
     * Spawn drops for the currently held crop simulating the looting of the actual block
     *
     * @param blockIn:  Instance of GrassDamBlock which was activated
     * @param stateAge: Current age of the crop
     * @param worldIn:  World
     * @param pos:      Position of the block
     */
    private void spawnDrops(Block blockIn, int stateAge, World worldIn, BlockPos pos) {
        BlockState buildState = null;
        if (blockIn == ModBlocks.DIRT_SMALL_DAM_JAPONICA.get() || blockIn == ModBlocks.GRASS_SMALL_DAM_JAPONICA.get() ||
                blockIn == ModBlocks.DIRT_LARGE_DAM_JAPONICA.get() || blockIn == ModBlocks.GRASS_LARGE_DAM_JAPONICA.get()) {
            // Get equivalent state for Japonica rice
            buildState = ModBlocks.RICE_JAPONICA.get().getDefaultState().with(BlockStateProperties.AGE_0_5, stateAge);
        } else if (blockIn == ModBlocks.DIRT_SMALL_DAM_BLACK_JAPONICA.get() || blockIn == ModBlocks.GRASS_SMALL_DAM_BLACK_JAPONICA.get() ||
                blockIn == ModBlocks.DIRT_LARGE_DAM_BLACK_JAPONICA.get() || blockIn == ModBlocks.GRASS_LARGE_DAM_BLACK_JAPONICA.get()) {
            // Get equivalent state for black Japonica rice
            buildState = ModBlocks.RICE_BLACK_JAPONICA.get().getDefaultState().with(BlockStateProperties.AGE_0_5, stateAge);
        } else if (blockIn == ModBlocks.DIRT_SMALL_DAM_INDICA.get() || blockIn == ModBlocks.GRASS_SMALL_DAM_INDICA.get() ||
                blockIn == ModBlocks.DIRT_LARGE_DAM_INDICA.get() || blockIn == ModBlocks.GRASS_LARGE_DAM_INDICA.get()) {
            // Get equivalent state for Indica rice
            buildState = ModBlocks.RICE_INDICA.get().getDefaultState().with(BlockStateProperties.AGE_0_5, stateAge);
        } else if (blockIn == ModBlocks.DIRT_SMALL_DAM_YAMADANISHIKI.get() || blockIn == ModBlocks.GRASS_SMALL_DAM_YAMADANISHIKI.get() ||
                blockIn == ModBlocks.DIRT_LARGE_DAM_YAMADANISHIKI.get() || blockIn == ModBlocks.GRASS_LARGE_DAM_YAMADANISHIKI.get()) {
            // Get equivalent state for Yamada Nishiki rice
            buildState = ModBlocks.RICE_YAMADANISHIKI.get().getDefaultState().with(BlockStateProperties.AGE_0_5, stateAge);
        }

        if (buildState != null) {
            spawnDrops(buildState, worldIn, pos);
        }
    }

    /**
     * Reset the Current instance of the GrassDamBlock to state without a crop
     *
     * @param blockIn: Current Block instance
     * @param worldIn: World
     * @param pos:     Position of the block
     */
    private void resetToCroplessState(Block blockIn, World worldIn, BlockPos pos) {
        if (blockIn == ModBlocks.DIRT_SMALL_DAM_JAPONICA.get() ||
                blockIn == ModBlocks.DIRT_SMALL_DAM_BLACK_JAPONICA.get() ||
                blockIn == ModBlocks.DIRT_SMALL_DAM_INDICA.get() ||
                blockIn == ModBlocks.DIRT_SMALL_DAM_YAMADANISHIKI.get()) {
            blockIn = ModBlocks.DIRT_SMALL_DAM.get();
        } else if (blockIn == ModBlocks.DIRT_LARGE_DAM_JAPONICA.get() ||
                blockIn == ModBlocks.DIRT_LARGE_DAM_BLACK_JAPONICA.get() ||
                blockIn == ModBlocks.DIRT_LARGE_DAM_INDICA.get() ||
                blockIn == ModBlocks.DIRT_LARGE_DAM_YAMADANISHIKI.get()) {
            blockIn = ModBlocks.DIRT_LARGE_DAM.get();
        } else if (blockIn == ModBlocks.GRASS_SMALL_DAM_JAPONICA.get() ||
                blockIn == ModBlocks.GRASS_SMALL_DAM_BLACK_JAPONICA.get() ||
                blockIn == ModBlocks.GRASS_SMALL_DAM_INDICA.get() ||
                blockIn == ModBlocks.GRASS_SMALL_DAM_YAMADANISHIKI.get()) {
            blockIn = ModBlocks.GRASS_SMALL_DAM.get();
        } else if (blockIn == ModBlocks.GRASS_LARGE_DAM_JAPONICA.get() ||
                blockIn == ModBlocks.GRASS_LARGE_DAM_BLACK_JAPONICA.get() ||
                blockIn == ModBlocks.GRASS_LARGE_DAM_INDICA.get() ||
                blockIn == ModBlocks.GRASS_LARGE_DAM_YAMADANISHIKI.get()) {
            blockIn = ModBlocks.GRASS_LARGE_DAM.get();
        }

        if (!worldIn.isRemote) {
            worldIn.setBlockState(pos, this.setPlacementState(worldIn, pos, blockIn.getDefaultState()), 2);
        }
    }
}
