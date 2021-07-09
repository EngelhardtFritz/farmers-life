package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.FarmerWorkbenchContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class FarmerCraftingTableBlock extends Block {
    private static final ITextComponent CONTAINER_NAME = new TranslationTextComponent("container." + FarmersLife.MOD_ID + ".farmer_workbench");

    public FarmerCraftingTableBlock(Properties properties) {
        super(properties);
    }

    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            NetworkHooks.openGui((ServerPlayerEntity) player, this.getContainer(state, worldIn, pos));
            player.addStat(Stats.INTERACT_WITH_CRAFTING_TABLE);
            return ActionResultType.SUCCESS;
        }
    }

    @Override
    public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
        return new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> {
            return new FarmerWorkbenchContainer(windowId, playerInventory, IWorldPosCallable.of(worldIn, pos));
        }, CONTAINER_NAME);
    }
}
