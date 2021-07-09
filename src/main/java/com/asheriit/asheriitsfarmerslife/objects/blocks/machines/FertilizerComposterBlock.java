package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;

import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.tileentity.FertilizerComposterTileEntity;
import com.asheriit.asheriitsfarmerslife.tileentity.WinePressTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class FertilizerComposterBlock extends Block {
    public static final VoxelShape NORTH = Block.makeCuboidShape(0, 0, 0, 16, 16, 2);
    public static final VoxelShape EAST = Block.makeCuboidShape(14, 0, 0, 16, 16, 16);
    public static final VoxelShape SOUTH = Block.makeCuboidShape(0, 0, 14, 16, 16, 16);
    public static final VoxelShape WEST = Block.makeCuboidShape(0, 0, 0, 2, 16, 16);
    public static final VoxelShape BOTTOM = Block.makeCuboidShape(0, 0, 0, 16, 3, 16);
    public static final VoxelShape SHAPE = VoxelShapes.or(NORTH, EAST, SOUTH, WEST, BOTTOM);


    public FertilizerComposterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof FertilizerComposterTileEntity) {
                FertilizerComposterTileEntity tileEntity = (FertilizerComposterTileEntity) te;

                NetworkHooks.openGui((ServerPlayerEntity) player, tileEntity, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if (tileEntity instanceof FertilizerComposterTileEntity) {
                FertilizerComposterTileEntity fertilizerComposterTileEntity = (FertilizerComposterTileEntity) tileEntity;
                fertilizerComposterTileEntity.setPos(pos);

                fertilizerComposterTileEntity.getInventoryCapability().ifPresent((handler) -> IntStream.range(0, handler.getSlots()).forEach((index) -> {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, handler.getStackInSlot(index));
                }));
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntityTypes.FERTILIZER_COMPOSTER_TILE_ENTITY.get().create();
    }
}
