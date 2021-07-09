package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.particles.ColoredParticle;
import com.asheriit.asheriitsfarmerslife.recipes.FiltrationRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.AbstractClarificationMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.FiltrationMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class FiltrationMachineBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static final VoxelShape NORTH = Block.makeCuboidShape(0, 0, 0, 16, 16, 2);
    private static final VoxelShape EAST = Block.makeCuboidShape(14, 0, 0, 16, 16, 16);
    private static final VoxelShape SOUTH = Block.makeCuboidShape(0, 0, 14, 16, 16, 16);
    private static final VoxelShape WEST = Block.makeCuboidShape(0, 0, 0, 2, 16, 16);
    private static final VoxelShape BOTTOM_BOT = Block.makeCuboidShape(0, 0, 0, 16, 2, 16);
    private static final VoxelShape BOTTOM_TOP = Block.makeCuboidShape(0, 0, 0, 16, 13, 16);
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            VoxelShapes.or(NORTH, EAST, SOUTH, WEST, BOTTOM_BOT),
            VoxelShapes.or(NORTH, EAST, SOUTH, WEST, BOTTOM_TOP)
    };

    public FiltrationMachineBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[1];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[this.getIndex(worldIn.getTileEntity(pos))];
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FiltrationMachineTileEntity) {
                FiltrationMachineTileEntity filtrationMachineTileEntity = (FiltrationMachineTileEntity) tileEntity;

                ItemStack playerItemStack = player.getHeldItem(Hand.MAIN_HAND);
                Item item = playerItemStack.getItem();
                // The item is not a BucketItem
                if (!(item instanceof BucketItem)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, filtrationMachineTileEntity, pos);
                    return ActionResultType.SUCCESS;
                } else {
                    BucketItem bucketItem = (BucketItem) item;
                    Fluid fluidInBucket = bucketItem.getFluid();

                    if (fluidInBucket == Fluids.EMPTY) {
                        // The bucket is empty
                        ItemStack result = filtrationMachineTileEntity.emptyIOTankIfPossible(playerItemStack, player, false, AbstractClarificationMachineTileEntity.OUTPUT_TANK);

                        if (((BucketItem) result.getItem()).getFluid() != Fluids.EMPTY) {
                            playerItemStack.shrink(1);
                            player.addItemStackToInventory(result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        // Check if slot for filters has content
                        ItemStack stack = filtrationMachineTileEntity.getInventory().getStackInSlot(AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT);
                        if (stack.isEmpty()) {
                            return ActionResultType.PASS;
                        }
                        // The bucket is full
                        if (filtrationMachineTileEntity.isFluidValid(AbstractClarificationMachineTileEntity.INPUT_TANK, new FluidStack(fluidInBucket, 1)) &&
                                FiltrationRecipe.FiltrationRecipeSerializer.fluidInputList.contains(fluidInBucket)) {
                            ItemStack result = filtrationMachineTileEntity.fillIOTankIfPossible(playerItemStack, player, true, AbstractClarificationMachineTileEntity.INPUT_TANK);
                            player.setItemStackToSlot(EquipmentSlotType.MAINHAND, result);
                            worldIn.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        } else {
                            NetworkHooks.openGui((ServerPlayerEntity) player, filtrationMachineTileEntity, pos);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
            }
            return ActionResultType.PASS;
        }
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FiltrationMachineTileEntity) {
                FiltrationMachineTileEntity filtrationMachineTileEntity = (FiltrationMachineTileEntity) tileEntity;
                filtrationMachineTileEntity.setPos(pos);

                filtrationMachineTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, handler.getStackInSlot(index));
                }));
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof FiltrationMachineTileEntity) {
                FiltrationMachineTileEntity filtrationMachineTileEntity = (FiltrationMachineTileEntity) tileEntity;
                FluidStack outputFluid = filtrationMachineTileEntity.getFluidInTank(AbstractClarificationMachineTileEntity.OUTPUT_TANK);
                if (outputFluid.getAmount() >= 1000) {
                    IFluidState fluidState = outputFluid.getFluid().getDefaultState();
                    return super.removedByPlayer(state, world, pos, player, willHarvest, fluidState);
                }
            }
        }

        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.FILTRATION_MACHINE_TILE_ENTITY.get().create();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity != null && tileEntity instanceof FiltrationMachineTileEntity) {
            FiltrationMachineTileEntity filtrationMachineTileEntity = (FiltrationMachineTileEntity) tileEntity;
            boolean isActive = filtrationMachineTileEntity.isActive();

            FluidStack inputTankFluid = filtrationMachineTileEntity.getFluidInTank(AbstractClarificationMachineTileEntity.INPUT_TANK);
            if (inputTankFluid.isEmpty() || inputTankFluid.getAmount() <= 0) {
                return;
            }

            if (isActive) {
                FluidStack fluidStack = filtrationMachineTileEntity.getFluidInTank(AbstractClarificationMachineTileEntity.OUTPUT_TANK);
                if (fluidStack.isEmpty() || fluidStack.getAmount() <= 0) {
                    fluidStack = inputTankFluid;
                }

                double d0 = (double) ((float) pos.getX() + (2.0F / 16.0F) + (rand.nextFloat() * (12.0F / 16.0F)));
                double d1 = (double) pos.getY() + (11.0F / 16.0F);
                double d2 = (double) ((float) pos.getZ() + (2.0F / 16.0F) + (rand.nextFloat() * (12.0F / 16.0F)));

                int color = fluidStack.getFluid().getAttributes().getColor();
                float red = ((color >> 16) & 0xFF) / 255f;
                float green = ((color >> 8) & 0xFF) / 255f;
                float blue = (color & 0xFF) / 255f;

                worldIn.addParticle(new ColoredParticle.DrippingColoredParticleData(red, green, blue, 1.0F, pos.getY() + (1.0F / 16.0F)), d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(ToolTipHelper.MACHINE_AUTOMATION_TOOLTIP_LOCATION);

        if (Screen.hasShiftDown()) {
            tooltip.add(ToolTipHelper.getEmptyLine());

            tooltip.add(ToolTipHelper.CLARIFICATION_NORTH_LOCATION);
            tooltip.add(ToolTipHelper.CLARIFICATION_WEST_EAST_LOCATION);
            tooltip.add(ToolTipHelper.CLARIFICATION_SOUTH_LOCATION);
            tooltip.add(ToolTipHelper.CLARIFICATION_UP_LOCATION);
            tooltip.add(ToolTipHelper.CLARIFICATION_DOWN_LOCATION);
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    protected int getIndex(TileEntity tileEntity) {
        if (tileEntity instanceof FiltrationMachineTileEntity) {
            FiltrationMachineTileEntity filtrationMachineTileEntity = (FiltrationMachineTileEntity) tileEntity;
            if (filtrationMachineTileEntity.getInventory().getStackInSlot(AbstractClarificationMachineTileEntity.INPUT_ITEM_SLOT).getCount() > 0) {
                return 1;
            }
        }
        return 0;
    }
}
