package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TrellisCropBlock extends Block implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;
    public static final IntegerProperty DISTANCE = ModBlockStateProperties.DISTANCE_0_5;
    public static final BooleanProperty ROOT_BLOCK = ModBlockStateProperties.IS_ROOT_BLOCK;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0, 0, 0, 16, 16, 16);
    protected final VoxelShape[] collisionShapes;
    private static final Direction[] DIRECTIONS = {
            Direction.UP,
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST,
            Direction.DOWN
    };
    private final Object2IntMap<WorldPosObject> blockStateMap = new Object2IntOpenHashMap<>();
    private static final int MAX_DISTANCE = 5;
    private static final int MAX_AGE = 7;

    public TrellisCropBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(AGE, 0)
                .with(ROOT_BLOCK, false)
                .with(DISTANCE, 0)
                .with(FACING, Direction.NORTH));
        this.collisionShapes = TrellisBlock.makeShapes(0);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return this.collisionShapes[this.getCollisionIndex(new WorldPosObject(worldIn, pos))];
    }

    /**
     * Condition for placing the block is always false
     * Placing the block is handled in TrellisBlock
     */
    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) && stateIn.getProperties().contains(ROOT_BLOCK) && !stateIn.get(ROOT_BLOCK)) {
            int distance = this.getShortestDistance(worldIn.getWorld(), currentPos) + 1;
            if (distance <= this.getMaxDistance() && this.hasRoot(worldIn.getWorld(), currentPos)) {
                return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos)
                        .with(DISTANCE, distance);
            } else {
                World world = worldIn.getWorld();

                if (!world.isRemote && world.getBlockState(facingPos).getBlock() instanceof TrellisCropBlock) {
                    worldIn.destroyBlock(facingPos, true);
                }
                return this.setToTrellis(world, currentPos);
            }
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    /**
     * Function which is executed on each random tick
     * With a calculated chance the crop will increase its age or try to expand to a new block if the age is at the maximum
     */
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        // Early return -> if block age is max and it can not connect
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        if (state.get(AGE) == this.getMaxAge() && !this.canExpand(worldIn, pos)) {
            return;
        }

        int cropAge = state.get(AGE);
        boolean isRoot = state.get(ROOT_BLOCK);
        int lightLevel = worldIn.getLightSubtracted(pos.up(), 0);
        int growthChance = getGrowthChance(worldIn, pos);
        int growthDivider = 20;

        if (isRoot && cropAge >= 5) {
            growthDivider = 60;
        } else if (!isRoot && cropAge >= 2) {
            growthDivider = 30;
        }

        if (cropAge < this.getMaxAge()) {
            if (lightLevel >= 8 && lightLevel < 11) {
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(growthDivider / growthChance) == 0)) {
                    worldIn.setBlockState(pos, state.with(AGE, ++cropAge));
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            } else if (lightLevel >= 11) {
                growthChance += 2;
                if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(growthDivider / growthChance) == 0)) {
                    worldIn.setBlockState(pos, state.with(AGE, ++cropAge));
                    net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
                }
            }
        }

        // The crop will only expand if light level is above 9 and the age is the maximum age
        if (cropAge >= this.getMaxAge() && lightLevel >= 10 && worldIn.isDaytime()) {
            BlockPos randomPosToExpand = this.getRandomBlockPosToExpand(worldIn, pos, rand);
            // if the block can not expand return
            if (randomPosToExpand == null) {
                return;
            }

            if (rand.nextInt(10) == 0) {
                int distance = this.getShortestDistance(worldIn, randomPosToExpand) + 1;

                if (distance <= this.getMaxDistance()) {
                    BlockState blockStateToPlace = this.stateContainer.getBaseState()
                            .with(AGE, 0)
                            .with(ROOT_BLOCK, false)
                            .with(DISTANCE, distance)
                            .with(FACING, state.get(FACING));
                    worldIn.setBlockState(randomPosToExpand, blockStateToPlace, 2);
                }
            }
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    /**
     * Slowdown the player when inside the block
     */
    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        entityIn.setMotionMultiplier(state, new Vec3d(0.96D, 1.0D, 0.96D));
    }

    /**
     * Do the same as when harvesting the block
     */
    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (!world.isRemote) {
            Block block = world.getBlockState(pos).getBlock();
            if (!(block instanceof AirBlock)) {
                world.destroyBlock(pos, false, player);
            }
            if (!(block instanceof TrellisBlock)) {
                this.setToTrellis(world, pos);
            }
        }
        return true;
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isRemote) {
            worldIn.destroyBlock(pos, false, player);
            this.setToTrellis(worldIn, pos);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    /**
     * Drop loot when the block is right clicked and the age is at its maximum
     * The age is reset to 2 because the initial growth stages are represented by age [0-2]
     */
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            int cropAge = state.get(AGE);

            if (cropAge >= this.getMaxAge()) {
                boolean isRoot = state.get(ROOT_BLOCK);
                spawnDrops(state, worldIn, pos);

                if (isRoot) {
                    worldIn.setBlockState(pos, state.with(AGE, 5), 2);
                } else {
                    worldIn.setBlockState(pos, state.with(AGE, 2), 2);
                }
                return ActionResultType.SUCCESS;
            }
            return ActionResultType.FAIL;
        }
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    /**
     * Adds information dynamically depending on which translations can be found for the locations
     *
     * @param stack:   ItemStack to add the information for
     * @param worldIn: IBlockReader
     * @param tooltip: List<ITextComponent> the list containing all tooltip information
     * @param flagIn:  ITooltipFlag
     */
    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        TrellisCropBlock.addTrellisCropInformation(this.getTranslationKey(), tooltip, false);
        super.addInformation(stack, worldIn, tooltip, flagIn);
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
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int age = Math.min(this.getMaxAge(), state.get(AGE) + this.getBonemealAgeIncrease(worldIn));
        worldIn.setBlockState(pos, state.with(AGE, age), 2);
    }

    /**
     * Spawns an itemStack with modified directions
     */
    public static void spawnAsEntityModified(World worldIn, BlockPos blockPos, ItemStack itemStack) {
        if (!worldIn.isRemote && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) { // do not drop items while restoring blockstates, prevents item dupe
            double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            if (!worldIn.getBlockState(blockPos).isSolid()) {
                ItemEntity entity = new ItemEntity(worldIn, (double) blockPos.getX() + d0, (double) blockPos.getY() + d1, (double) blockPos.getZ() + d2, itemStack);
                entity.setDefaultPickupDelay();
                worldIn.addEntity(entity);
                return;
            } else {
                for (Direction direction : DIRECTIONS) {
                    if (!worldIn.getBlockState(blockPos.offset(direction)).isSolid() || direction == Direction.DOWN) {
                        ItemEntity itemEntity = new ItemEntity(worldIn,
                                (double) blockPos.getX() + d0 + direction.getXOffset(),
                                (double) blockPos.getY() + (direction.getYOffset() != 0 ? 0.05D : d1) + direction.getYOffset(),
                                (double) blockPos.getZ() + d2 + direction.getZOffset(), itemStack);
                        double signX = Math.signum(direction.getXOffset());
                        double signZ = Math.signum(direction.getZOffset());
                        itemEntity.setMotion((worldIn.rand.nextDouble() * 0.1D) * signX, 0.1D, (worldIn.rand.nextDouble() * 0.1D) * signZ);
                        itemEntity.setDefaultPickupDelay();
                        worldIn.addEntity(itemEntity);
                        return;
                    }
                }
            }
        }
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    public int getMaxDistance() {
        return MAX_DISTANCE;
    }

    /**
     * Calculate collision index depending on the neighbour states
     *
     * @param worldPosObject: Object containing the World and BlockPos
     * @return Index
     */
    public int getCollisionIndex(WorldPosObject worldPosObject) {
        return this.blockStateMap.computeIntIfAbsent((worldPosObject), (blockState) -> {
            IBlockReader worldIn = worldPosObject.getWorld();
            BlockPos pos = worldPosObject.getBlockPos();
            BlockState north = worldIn.getBlockState(pos.north());
            BlockState east = worldIn.getBlockState(pos.east());
            BlockState south = worldIn.getBlockState(pos.south());
            BlockState west = worldIn.getBlockState(pos.west());
            BlockState up = worldIn.getBlockState(pos.up());
            BlockState down = worldIn.getBlockState(pos.down());

            int i = 0;
            if (this.canConnect(up)) {
                i |= getMask(Direction.UP);
            }

            if (this.canConnect(down)) {
                i |= getMask(Direction.DOWN);
            }

            if (this.canConnect(north)) {
                i |= getMask(Direction.NORTH);
            }

            if (this.canConnect(east)) {
                i |= getMask(Direction.EAST);
            }

            if (this.canConnect(south)) {
                i |= getMask(Direction.SOUTH);
            }

            if (this.canConnect(west)) {
                i |= getMask(Direction.WEST);
            }

            return i;
        });
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ROOT_BLOCK, AGE, DISTANCE, FACING);
    }

    /**
     * Add all information which can be found to the tooltip
     *
     * @param translationKey: String of translation key
     * @param tooltip:        List of all tooltip ITextComponents
     */
    public static void addTrellisCropInformation(String translationKey, List<ITextComponent> tooltip, boolean isCrop) {
        String speciesLocation = "tooltip.asheriitsfarmerslife.trellis_crop_block.species";
        String countryOfOriginLocation = "tooltip.asheriitsfarmerslife.trellis_crop_block.country_of_origin";
        String possibleProductsLocation = "tooltip.asheriitsfarmerslife.trellis_crop_block.possible_products";
        String growsOnLocation = "tooltip.asheriitsfarmerslife.trellis_crop_block.grows_on";
        String otherInformationLocation = "tooltip.asheriitsfarmerslife.trellis_crop_block.other";

        String tooltipSpeciesLoc = "tooltip." + translationKey + ".species";
        String tooltipCountryOfOriginsLoc = "tooltip." + translationKey + ".country_of_origin";
        String tooltipProductsLoc = "tooltip." + translationKey + ".possible_products";
        String tooltipGrowsOnLoc = "tooltip." + translationKey + ".grows_on";
        String tooltipOtherLoc = "tooltip." + translationKey + ".other";

        TranslationTextComponent translationSpecies = new TranslationTextComponent(tooltipSpeciesLoc);
        TranslationTextComponent translationCountryOfOrigin = new TranslationTextComponent(tooltipCountryOfOriginsLoc);
        TranslationTextComponent translationProducts = new TranslationTextComponent(tooltipProductsLoc);
        TranslationTextComponent translationGrowsOn = new TranslationTextComponent(tooltipGrowsOnLoc);
        TranslationTextComponent translationOther = new TranslationTextComponent(tooltipOtherLoc);

        if (!isCrop) {
            tooltip.add(new StringTextComponent("")
                    .appendSibling(ToolTipHelper.WINE_GRAPE_PLACEMENT_TOOLTIP_LOCATION
                            .applyTextStyle(TextFormatting.DARK_GRAY))
                    .applyTextStyle(TextFormatting.WHITE));
        }

        if (!translationSpecies.getFormattedText().equals(tooltipSpeciesLoc) ||
                !translationCountryOfOrigin.getFormattedText().equals(tooltipCountryOfOriginsLoc) ||
                !translationProducts.getFormattedText().equals(tooltipProductsLoc) ||
                !translationGrowsOn.getFormattedText().equals(tooltipGrowsOnLoc) ||
                !translationOther.getFormattedText().equals(tooltipOtherLoc)) {
            if (!isCrop) {
                tooltip.add(ToolTipHelper.getEmptyLine());
            }
            tooltip.add(ToolTipHelper.HAS_SHIFT_DOWN_TOOLTIP_LOCATION);

            if (Screen.hasShiftDown()) {
                List<ITextComponent> tooltipsToAdd = new ArrayList<>();
                tooltip.add(ToolTipHelper.getEmptyLine());

                if (!translationSpecies.getFormattedText().equals(tooltipSpeciesLoc)) {
                    tooltipsToAdd.add(new StringTextComponent("")
                            .applyTextStyle(TextFormatting.GRAY)
                            .appendSibling(new TranslationTextComponent(speciesLocation)
                                    .appendText(ToolTipHelper.STYLE_WHITE_COLON_WITH_PURPLE_TEXT))
                            .appendSibling(translationSpecies));
                }

                if (!translationCountryOfOrigin.getFormattedText().equals(tooltipCountryOfOriginsLoc)) {
                    tooltipsToAdd.add(new StringTextComponent("")
                            .applyTextStyle(TextFormatting.GRAY)
                            .appendSibling(new TranslationTextComponent(countryOfOriginLocation)
                                    .appendText(ToolTipHelper.STYLE_WHITE_COLON_WITH_PURPLE_TEXT))
                            .appendSibling(translationCountryOfOrigin));
                }

                if (!translationProducts.getFormattedText().equals(tooltipProductsLoc)) {
                    tooltipsToAdd.add(new StringTextComponent("")
                            .applyTextStyle(TextFormatting.GRAY)
                            .appendSibling(new TranslationTextComponent(possibleProductsLocation)
                                    .appendText(ToolTipHelper.STYLE_WHITE_COLON_WITH_PURPLE_TEXT))
                            .appendSibling(translationProducts));
                }

                if (!translationGrowsOn.getFormattedText().equals(tooltipGrowsOnLoc)) {
                    tooltipsToAdd.add(new StringTextComponent("")
                            .applyTextStyle(TextFormatting.GRAY)
                            .appendSibling(new TranslationTextComponent(growsOnLocation)
                                    .appendText(ToolTipHelper.STYLE_WHITE_COLON_WITH_PURPLE_TEXT))
                            .appendSibling(translationGrowsOn));
                }

                if (!translationOther.getFormattedText().equals(tooltipOtherLoc)) {
                    tooltipsToAdd.add(new StringTextComponent(""));
                    tooltipsToAdd.add(new StringTextComponent("")
                            .applyTextStyle(TextFormatting.GRAY)
                            .appendSibling(new TranslationTextComponent(otherInformationLocation)
                                    .appendText(TextFormatting.WHITE + ":" + ToolTipHelper.getEmptyLine()))
                            .appendSibling(translationOther));
                }

                tooltip.addAll(tooltipsToAdd);
            }
        }
    }

    /**
     * This calculates the value the age is increased by when bonemeal is used
     *
     * @param worldIn: World to get the Random instance
     * @return
     */
    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 1, 3);
    }

    /**
     * This method builds a list with all valid block positions the crop can expand to
     * If the block can expand a random entry gets chosen and returned
     *
     * @param worldIn:  The world to get all possible locations
     * @param blockPos: The source block position where a place to expand to is looked for
     * @param rand:     Random instance to calculate probability of one path to be chosen
     * @return
     */
    private BlockPos getRandomBlockPosToExpand(ServerWorld worldIn, BlockPos blockPos, Random rand) {
        BlockPos[] neighbours = getNeighbours(blockPos);
        ArrayList<BlockPos> validNeighbours = new ArrayList<>();
        for (int i = 0; i < neighbours.length; i++) {
            if (worldIn.getBlockState(neighbours[i]).isIn(ModTags.Blocks.TRELLISES)) {
                validNeighbours.add(neighbours[i]);
            }
        }

        int validNeighboursSize = validNeighbours.size();
        if (validNeighboursSize <= 0) {
            return null;
        }

        int randomIndex = rand.nextInt(validNeighboursSize);
        return validNeighbours.get(randomIndex);
    }

    /**
     * Checks if the block can expand for the given block position
     *
     * @param worldIn:  World(Server) Object to get blockStates
     * @param blockPos: The block position to check if it can expand
     * @return
     */
    private boolean canExpand(ServerWorld worldIn, BlockPos blockPos) {
        BlockPos[] neighbours = getNeighbours(blockPos);
        for (int i = 0; i < neighbours.length; i++) {
            boolean isInTrellisBlockTag = worldIn.getBlockState(neighbours[i]).isIn(ModTags.Blocks.TRELLISES);
            if (isInTrellisBlockTag) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the growth chance depending on the neighbours
     *
     * @param worldIn:  World to get the required block states
     * @param blockPos: Is required to get the block neighbours
     * @return
     */
    private int getGrowthChance(IBlockReader worldIn, BlockPos blockPos) {
        int chance = 4;
        BlockPos[] neighbours = getNeighbours(blockPos);
        for (int i = 0; i < neighbours.length; i++) {
            BlockState blockState = worldIn.getBlockState(neighbours[i]);
            boolean isTrellisCrop = blockState.isIn(ModTags.Blocks.CROPS_WINE_GRAPES);
            if (isTrellisCrop) {
                chance += 1;
            }
        }
        return chance;
    }

    /**
     * Gets all neighbours(as defined) of the given position and returns them as an array
     *
     * @param blockPos: Source position
     * @return
     */
    private BlockPos[] getNeighbours(BlockPos blockPos) {
        return new BlockPos[]{
                blockPos.add(Direction.UP.getDirectionVec()),
                blockPos.add(Direction.NORTH.getDirectionVec()),
                blockPos.add(Direction.SOUTH.getDirectionVec()),
                blockPos.add(Direction.WEST.getDirectionVec()),
                blockPos.add(Direction.EAST.getDirectionVec()),
        };
    }

    /**
     * Gets the shortest distance as int from a source block by getting the minimum of all values
     *
     * @param world: World object to check for the block states
     * @param pos:   Source position
     * @return
     */
    private int getShortestDistance(World world, BlockPos pos) {
        List<Integer> distances = new ArrayList<>();

        BlockState stateUp = world.getBlockState(pos.up());
        if (stateUp.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
            distances.add(stateUp.get(DISTANCE));
        }

        BlockState stateDown = world.getBlockState(pos.down());
        if (stateDown.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
            distances.add(stateDown.get(DISTANCE));
        }

        BlockState stateNorth = world.getBlockState(pos.north());
        if (stateNorth.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
            distances.add(stateNorth.get(DISTANCE));
        }

        BlockState stateEast = world.getBlockState(pos.east());
        if (stateEast.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
            distances.add(stateEast.get(DISTANCE));
        }

        BlockState stateSouth = world.getBlockState(pos.south());
        if (stateSouth.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
            distances.add(stateSouth.get(DISTANCE));
        }

        BlockState stateWest = world.getBlockState(pos.west());
        if (stateWest.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
            distances.add(stateWest.get(DISTANCE));
        }

        if (distances.size() == 0) {
            return 0;
        }

        Collections.sort(distances);
        return distances.get(0);
    }

    /**
     * Checks if the block at the given position has a connection to a root block
     * Worst case is calculating the for loop for all possibilities and finding no source block
     *
     * @param world:    World object to get all required information
     * @param blockPos: Block position the root is searched for
     * @return
     */
    private boolean hasRoot(World world, BlockPos blockPos) {
        BlockState state = world.getBlockState(blockPos);
        BlockPos currentPosition = blockPos;

        for (int i = 0; i <= this.getMaxDistance(); i++) {
            if (state.isIn(ModTags.Blocks.CROPS_WINE_GRAPES)) {
                int distance = this.getShortestDistance(world, currentPosition) + 1;

                if (!world.isRemote && distance <= this.getMaxDistance()) {
                    world.setBlockState(currentPosition, state.with(DISTANCE, distance), 2);
                }

                BlockPos nextPosition = posWithSmallestDistance(world, currentPosition, state.get(DISTANCE));
                if (currentPosition != nextPosition) {
                    if (world.getBlockState(nextPosition).get(ROOT_BLOCK)) {
                        return true;
                    }
                    currentPosition = nextPosition;
                    state = world.getBlockState(currentPosition);
                }
            }
        }
        return false;
    }

    /**
     * Searches for the block position with the smallest distance and returns it
     * Used when searching for the root block
     *
     * @param world:           World object to get all required information
     * @param blockPos:        Block position to check
     * @param currentDistance: Maximum the block distance is allowed for the search
     * @return
     */
    private BlockPos posWithSmallestDistance(World world, BlockPos blockPos, int currentDistance) {
        BlockState stateUp = world.getBlockState(blockPos.up());
        BlockState stateDown = world.getBlockState(blockPos.down());
        BlockState stateNorth = world.getBlockState(blockPos.north());
        BlockState stateEast = world.getBlockState(blockPos.east());
        BlockState stateSouth = world.getBlockState(blockPos.south());
        BlockState stateWest = world.getBlockState(blockPos.west());

        if (stateUp.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) && stateUp.get(DISTANCE) < currentDistance) {
            return blockPos.up();
        }

        if (stateDown.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) && stateDown.get(DISTANCE) < currentDistance) {
            return blockPos.down();
        }

        if (stateNorth.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) && stateNorth.get(DISTANCE) < currentDistance) {
            return blockPos.north();
        }

        if (stateEast.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) && stateEast.get(DISTANCE) < currentDistance) {
            return blockPos.east();
        }

        if (stateSouth.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) && stateSouth.get(DISTANCE) < currentDistance) {
            return blockPos.south();
        }

        if (stateWest.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) && stateWest.get(DISTANCE) < currentDistance) {
            return blockPos.west();
        }

        return blockPos;
    }

    private boolean canConnect(BlockState blockState) {
        return blockState.isIn(ModTags.Blocks.TRELLISES) ||
                blockState.isIn(ModTags.Blocks.CROPS_WINE_GRAPES) ||
                blockState.isIn(Tags.Blocks.DIRT) ||
                blockState.isIn(ModTags.Blocks.WINE_GRAPE_SOILS);
    }

    private BlockState setToTrellis(World world, BlockPos pos) {
        BlockState up = world.getBlockState(pos.up());
        BlockState down = world.getBlockState(pos.down());
        BlockState north = world.getBlockState(pos.north());
        BlockState east = world.getBlockState(pos.east());
        BlockState south = world.getBlockState(pos.south());
        BlockState west = world.getBlockState(pos.west());

        BlockState trellisState = ModBlocks.WOOD_TRELLIS.get().getDefaultState()
                .with(BlockStateProperties.UP, this.canConnect(up))
                .with(BlockStateProperties.DOWN, this.canConnect(down))
                .with(BlockStateProperties.NORTH, this.canConnect(north))
                .with(BlockStateProperties.EAST, this.canConnect(east))
                .with(BlockStateProperties.SOUTH, this.canConnect(south))
                .with(BlockStateProperties.WEST, this.canConnect(west));
        if (!world.isRemote) {
            world.setBlockState(pos, trellisState, 2);
        }
        return trellisState;
    }

    private static int getMask(Direction facing) {
        return 1 << facing.getIndex();
    }

    /**
     * Private class to create for simple creation of the ObjectToInt HashMap
     */
    private static class WorldPosObject {
        private final IBlockReader world;
        private final BlockPos blockPos;

        public WorldPosObject(IBlockReader world, BlockPos blockPos) {
            this.world = world;
            this.blockPos = blockPos;
        }

        public IBlockReader getWorld() {
            return world;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }
    }
}
