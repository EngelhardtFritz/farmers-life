package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WaterloggedCropBlock extends BushBlock implements IGrowable {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_5;
    private static final VoxelShape[] SHAPES_BY_AGE = new VoxelShape[]{
            Block.makeCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 2.0D, 14.0D),
            Block.makeCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 4.0D, 14.0D),
            Block.makeCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 6.0D, 14.0D),
            Block.makeCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 8.0D, 14.0D),
            Block.makeCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 10.0D, 14.0D),
            Block.makeCuboidShape(2.0D, -8.0D, 2.0D, 14.0D, 13.0D, 14.0D)
    };
    private static final int MAX_AGE = 5;

    public WaterloggedCropBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(AGE, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES_BY_AGE[getAge(state)];
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        int age = getAge(state);
        BlockState stateDown = worldIn.getBlockState(pos.down());
        if (age == getMaxAge() || (stateDown.getProperties().contains(BlockStateProperties.WATERLOGGED) && !stateDown.get(BlockStateProperties.WATERLOGGED))) {
            return;
        }

        int lightLevel = worldIn.getLightSubtracted(pos.up(), 0);
        if (lightLevel > 9 && rand.nextInt((int) (25 / getGrowthChance(worldIn, pos)) + 1) == 0) {
            worldIn.setBlockState(pos, this.withAge(age + 1), 2);
        }
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int age = getAge(state);
        if (age < getMaxAge()) {
            return ActionResultType.PASS;
        }

        if (!worldIn.isRemote) {
            spawnDrops(state, worldIn, pos);
            worldIn.setBlockState(pos, state.with(AGE, 0), 2);
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos blockpos = pos.down();
        BlockState blockState = worldIn.getBlockState(blockpos);
        return blockState.isIn(ModTags.Blocks.SUBMERGED_FARMLAND);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return getAge(state) < getMaxAge();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        int age = Math.min(getMaxAge(), getAge(state) + this.getBonemealAgeIncrease(worldIn));
        worldIn.setBlockState(pos, state.with(AGE, age), 2);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        WaterloggedCropBlock.addWaterloggedCropInformation(this.getTranslationKey(), tooltip);
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    protected int getBonemealAgeIncrease(World worldIn) {
        return MathHelper.nextInt(worldIn.rand, 1, 3);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    /**
     * Add all information which can be found to the tooltip
     *
     * @param translationKey: String of translation key
     * @param tooltip:        List of all tooltip ITextComponents
     */
    public static void addWaterloggedCropInformation(String translationKey, List<ITextComponent> tooltip) {
        String speciesLocation = "tooltip.asheriitsfarmerslife.waterlogged_crop.species";
        String wayOfGrowthLocation = "tooltip.asheriitsfarmerslife.waterlogged_crop.way_of_growth";
        String grainTypeLocation = "tooltip.asheriitsfarmerslife.waterlogged_crop.grain_type";

        String tooltipSpeciesLoc = "tooltip." + translationKey + ".species";
        String tooltipWayOfGrowthLoc = "tooltip." + translationKey + ".way_of_growth";
        String tooltipGrainTypeLoc = "tooltip." + translationKey + ".grain_type";

        TranslationTextComponent translationSpecies = new TranslationTextComponent(tooltipSpeciesLoc);
        TranslationTextComponent translationWayOfGrowth = new TranslationTextComponent(tooltipWayOfGrowthLoc);
        TranslationTextComponent translationGrainType = new TranslationTextComponent(tooltipGrainTypeLoc);

        if (!translationSpecies.getFormattedText().equals(tooltipSpeciesLoc) ||
                !translationWayOfGrowth.getFormattedText().equals(tooltipWayOfGrowthLoc) ||
                !translationGrainType.getFormattedText().equals(tooltipGrainTypeLoc)) {
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

                if (!translationWayOfGrowth.getFormattedText().equals(tooltipWayOfGrowthLoc)) {
                    tooltipsToAdd.add(new StringTextComponent("")
                            .applyTextStyle(TextFormatting.GRAY)
                            .appendSibling(new TranslationTextComponent(wayOfGrowthLocation)
                                    .appendText(ToolTipHelper.STYLE_WHITE_COLON_WITH_PURPLE_TEXT))
                            .appendSibling(translationWayOfGrowth));
                }

                if (!translationGrainType.getFormattedText().equals(tooltipGrainTypeLoc)) {
                    tooltipsToAdd.add(new StringTextComponent("")
                            .applyTextStyle(TextFormatting.GRAY)
                            .appendSibling(new TranslationTextComponent(grainTypeLocation)
                                    .appendText(ToolTipHelper.STYLE_WHITE_COLON_WITH_PURPLE_TEXT))
                            .appendSibling(translationGrainType));
                }

                tooltip.addAll(tooltipsToAdd);
            }
        }
    }

    public static int getMaxAge() {
        return MAX_AGE;
    }

    public static int getAge(BlockState blockState) {
        return blockState.get(AGE);
    }

    public BlockState withAge(int age) {
        return this.getDefaultState().with(AGE, age);
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
}
