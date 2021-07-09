package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.DryingMachineTileEntity;
import com.asheriit.asheriitsfarmerslife.utils.ToolTipHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DryingMachineBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty FULLNESS = ModBlockStateProperties.FULLNESS_0_4;
    public static final BooleanProperty FLUID_CONNECTION = ModBlockStateProperties.HAS_CONNECTION;

    public DryingMachineBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(FACING, Direction.NORTH)
                .with(FULLNESS, 0)
                .with(FLUID_CONNECTION, false));
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getPos();
        TileEntity tileEntity = world.getTileEntity(blockPos.up());
        boolean isConnected = false;
        if (tileEntity != null && tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).isPresent()) {
            isConnected = true;
        }

        return this.getDefaultState()
                .with(FACING, context.getPlacementHorizontalFacing().getOpposite())
                .with(FLUID_CONNECTION, isConnected);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.UP) {
            TileEntity tileEntity = worldIn.getTileEntity(facingPos);
            boolean isConnected = false;
            if (tileEntity != null && tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite()).isPresent()) {
                isConnected = true;
            }
            return stateIn.with(FLUID_CONNECTION, isConnected);
        }

        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.DRYING_MACHINE_TILE_ENTITY.get().create();
    }

    /**
     * This method gets executed when the block is right clicked.
     * Opens the GUI if the player holds no {@link net.minecraft.item.BlockItem}
     * else it tries to empty or fill the bucket depending on the state of the TileEntity.
     *
     * @param state:   BlockState
     * @param worldIn: World
     * @param pos:     BlockPos
     * @param player:  PlayerEntity
     * @param handIn:  Hand
     * @param hit:     BlockRayTraceResult
     * @return ActionResultType
     */
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if (tileEntity != null && tileEntity instanceof DryingMachineTileEntity) {
                DryingMachineTileEntity dryingMachineTileEntity = (DryingMachineTileEntity) tileEntity;

                ItemStack playerItemStack = player.getHeldItem(Hand.MAIN_HAND);
                Item item = playerItemStack.getItem();
                // The item is not a BucketItem
                if (!(item instanceof BucketItem)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, dryingMachineTileEntity, pos);
                    return ActionResultType.SUCCESS;
                } else {
                    BucketItem bucketItem = (BucketItem) item;
                    Fluid fluidInBucket = bucketItem.getFluid();

                    if (fluidInBucket == Fluids.EMPTY) {
                        // The bucket is empty
                        ItemStack result = dryingMachineTileEntity.emptyIOTankIfPossible(playerItemStack, player, true, DryingMachineTileEntity.INPUT_TANK);

                        if (((BucketItem) result.getItem()).getFluid() != Fluids.EMPTY) {
                            playerItemStack.shrink(1);
                            player.addItemStackToInventory(result);
                            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        // The bucket is full
                        if (dryingMachineTileEntity.isFluidValid(DryingMachineTileEntity.INPUT_TANK, new FluidStack(fluidInBucket, 1))) {
                            ItemStack result = dryingMachineTileEntity.fillIOTankIfPossible(playerItemStack, player, true, DryingMachineTileEntity.INPUT_TANK);
                            player.setItemStackToSlot(EquipmentSlotType.MAINHAND, result);
                            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        } else {
                            NetworkHooks.openGui((ServerPlayerEntity) player, dryingMachineTileEntity, pos);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
            }
            return ActionResultType.FAIL;
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (!worldIn.isRemote && stack.getTag() != null && stack.hasTag()) {
            final DryingMachineTileEntity dryingMachineTileEntity = (DryingMachineTileEntity) worldIn.getTileEntity(pos);

            if (dryingMachineTileEntity != null) {
                CompoundNBT compoundNBT = stack.getTag();
                dryingMachineTileEntity.read(compoundNBT);
                dryingMachineTileEntity.setPos(pos);
            }
        }

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        TileEntity tileentity = builder.get(LootParameters.BLOCK_ENTITY);

        if (tileentity != null && tileentity instanceof DryingMachineTileEntity) {
            DryingMachineTileEntity dryingMachineTileEntity = (DryingMachineTileEntity) tileentity;

            if (!dryingMachineTileEntity.isEmpty()) {
                final CompoundNBT tileTag = dryingMachineTileEntity.write(new CompoundNBT());
                ItemStack stackToDrop = new ItemStack(Item.getItemFromBlock(state.getBlock()));
                stackToDrop.setTag(tileTag);

                List<ItemStack> list = new ArrayList<>();
                list.add(stackToDrop);
                return list;
            }
        }
        return super.getDrops(state, builder);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity != null && tileEntity instanceof DryingMachineTileEntity) {
                DryingMachineTileEntity dryingMachineTileEntity = (DryingMachineTileEntity) tileEntity;
                dryingMachineTileEntity.setPos(pos);

                dryingMachineTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, handler.getStackInSlot(index));
                }));
                worldIn.updateComparatorOutputLevel(pos, this);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public void addInformation(ItemStack stack, IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT compoundNBT = stack.getShareTag();
        if (compoundNBT != null && !compoundNBT.isEmpty()) {
            if (Screen.hasAltDown()) {
                ITextComponent tooltipToAdd = new StringTextComponent("");
                tooltipToAdd.appendText("" + TextFormatting.LIGHT_PURPLE)
                        .appendSibling(new StringTextComponent("NBT-Data: " + TextFormatting.DARK_GRAY))
                        .appendSibling(new StringTextComponent(stack.getTag().toString()));
                tooltip.add(tooltipToAdd);
            } else {
                tooltip.add(ToolTipHelper.HAS_ALT_DOWN_TOOLTIP_LOCATION);
            }
        }

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, FULLNESS, FLUID_CONNECTION);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }
}
