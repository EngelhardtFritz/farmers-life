package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.objects.blocks.ThreshingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.TieredItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class FlailItem extends TieredItem {
    public FlailItem(IItemTier tierIn, Properties builder) {
        super(tierIn, builder);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        BlockPos pos = context.getPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        PlayerEntity player = context.getPlayer();

        if (player != null && block instanceof ThreshingBlock && state.getProperties().contains(ModBlockStateProperties.USES_0_2)) {
            int currentUses = state.get(ModBlockStateProperties.USES_0_2);
            spawnDropsWithMovement(state, world, pos);
            if (currentUses == 0) {
                if (!world.isRemote) {
                    world.destroyBlock(pos, false);
                }
            } else {
                if (!world.isRemote) {
                    world.setBlockState(pos, state.with(ModBlockStateProperties.USES_0_2, --currentUses));
                }
            }

            if (!world.isRemote) {
                context.getItem().damageItem(1, player, (playerEntity) -> {
                    playerEntity.sendBreakAnimation(context.getHand());
                });
            }

            return ActionResultType.SUCCESS;
        }

        return super.onItemUse(context);
    }

    private static void spawnDropsWithMovement(BlockState state, World worldIn, BlockPos pos) {
        if (worldIn instanceof ServerWorld) {
            Block.getDrops(state, (ServerWorld) worldIn, pos, (TileEntity) null).forEach((itemStack) -> {
                spawnAsMovingEntity(worldIn, pos, itemStack);
            });
        }
    }

    private static void spawnAsMovingEntity(World worldIn, BlockPos pos, ItemStack stack) {
        if (!worldIn.isRemote && !stack.isEmpty() && worldIn.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && !worldIn.restoringBlockSnapshots) {
            float f = 0.5F;
            double d0 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d1 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            double d2 = (double) (worldIn.rand.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itementity = new ItemEntity(worldIn, (double) pos.getX() + d0, (double) pos.getY() + d1, (double) pos.getZ() + d2, stack);
            double varX = worldIn.rand.nextFloat() * 0.15F;
            double varY = worldIn.rand.nextFloat() * 0.65F;
            double varZ = worldIn.rand.nextFloat() * 0.15F;
            double motionX = worldIn.rand.nextFloat() < 0.5D ? varX : -varX;
            double motionZ = worldIn.rand.nextFloat() < 0.5D ? varZ : -varZ;
            itementity.setMotion(motionX, varY, motionZ);
            itementity.setDefaultPickupDelay();
            worldIn.addEntity(itementity);
        }
    }
}
