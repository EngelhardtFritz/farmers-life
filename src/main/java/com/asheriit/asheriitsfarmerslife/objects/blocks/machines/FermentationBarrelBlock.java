package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.recipes.FermentingRecipe;
import com.asheriit.asheriitsfarmerslife.tileentity.FermentingBarrelTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.StompingBarrelTileEntity;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class FermentationBarrelBlock extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HAS_CONNECTION = ModBlockStateProperties.HAS_CONNECTION;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public FermentationBarrelBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(FACING, Direction.NORTH)
                .with(HAS_CONNECTION, false)
                .with(UP, false)
                .with(DOWN, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Direction direction = context.getPlacementHorizontalFacing();
        return this.getDefaultState()
                .with(FACING, direction.getOpposite())
                .with(HAS_CONNECTION, isConnectionEnabled(world, pos, direction))
                .with(UP, this.canConnect(world.getBlockState(pos.offset(Direction.UP))))
                .with(DOWN, this.canConnect(world.getBlockState(pos.offset(Direction.DOWN))));
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.UP) {
            return stateIn.with(UP, this.canConnect(facingState));
        } else if (facing == Direction.DOWN) {
            return stateIn.with(DOWN, this.canConnect(facingState));
        } else if (facing == stateIn.get(FACING).getOpposite() || facing == stateIn.get(FACING)) {
            return stateIn.with(HAS_CONNECTION, isConnectionEnabled(worldIn.getWorld(), currentPos, facing));
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand handIn, @Nonnull BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FermentingBarrelTileEntity) {
                FermentingBarrelTileEntity fermentingBarrelTileEntity = (FermentingBarrelTileEntity) tileEntity;

                ItemStack playerItemStack = player.getHeldItem(Hand.MAIN_HAND);
                Item item = playerItemStack.getItem();
                // The item is not a BucketItem
                if (!(item instanceof BucketItem)) {
                    NetworkHooks.openGui((ServerPlayerEntity) player, fermentingBarrelTileEntity, pos);
                    return ActionResultType.SUCCESS;
                } else {
                    BucketItem bucketItem = (BucketItem) item;
                    Fluid fluidInBucket = bucketItem.getFluid();

                    if (fluidInBucket == Fluids.EMPTY) {
                        // The bucket is empty
                        ItemStack result = fermentingBarrelTileEntity.emptyIOTankIfPossible(playerItemStack, player, true, FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK);

                        if (((BucketItem) result.getItem()).getFluid() != Fluids.EMPTY) {
                            playerItemStack.shrink(1);
                            player.addItemStackToInventory(result);
                            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        }
                    } else {
                        // The bucket is full
                        boolean isFluidValid = fermentingBarrelTileEntity.isFluidValid(FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK, new FluidStack(fluidInBucket, 1));
                        boolean isIngredientFluid = FermentingRecipe.FermentingRecipeSerializer.fluidInputList.contains(fluidInBucket);
                        if (isFluidValid && isIngredientFluid) {
                            ItemStack result = fermentingBarrelTileEntity.fillIOTankIfPossible(playerItemStack, player, true, FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK);
                            player.setItemStackToSlot(EquipmentSlotType.MAINHAND, result);
                            worldIn.playSound((PlayerEntity)null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            return ActionResultType.SUCCESS;
                        } else {
                            NetworkHooks.openGui((ServerPlayerEntity) player, fermentingBarrelTileEntity, pos);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
            }
            return ActionResultType.PASS;
        }
    }

    @Override
    public boolean removedByPlayer(BlockState state, World world, BlockPos pos, PlayerEntity player, boolean willHarvest, IFluidState fluid) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof FermentingBarrelTileEntity) {
                FermentingBarrelTileEntity fermentingBarrelTileEntity = (FermentingBarrelTileEntity) tileEntity;
                FluidStack outputFluid = fermentingBarrelTileEntity.getFluidInTank(FermentingBarrelTileEntity.INPUT_OUTPUT_FLUID_TANK);
                if (outputFluid.getAmount() >= 1000) {
                    IFluidState fluidState = outputFluid.getFluid().getDefaultState();
                    return super.removedByPlayer(state, world, pos, player, willHarvest, fluidState);
                }
            }
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.FERMENTING_BARREL_TILE_ENTITY.get().create();
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, HAS_CONNECTION, UP, DOWN);
    }

    public boolean isConnectionEnabled(World world, BlockPos pos, Direction offsetDirection) {
        BlockPos posToConnect = pos.add(offsetDirection.getDirectionVec());
        TileEntity tileEntity = world.getTileEntity(posToConnect);
        boolean connect = false;
        if (tileEntity != null && tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, offsetDirection.getOpposite()).isPresent()) {
            connect = true;
        }
        return connect;
    }

    public boolean canConnect(BlockState facingState) {
        Block block = facingState.getBlock();
        return !cannotAttach(block) && !(block instanceof AirBlock);
    }
}
