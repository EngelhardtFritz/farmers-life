package com.asheriit.asheriitsfarmerslife.tileentity;

import com.asheriit.asheriitsfarmerslife.api.utils.AnimationTimingHelper;
import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
import com.asheriit.asheriitsfarmerslife.objects.blocks.machines.WinePressBlock;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WinePressHandleTileEntity extends TileEntity {

    private BlockPos masterPos;
    private float animationEnd;
    private boolean animationActivated;
    private float animationStartStep;

    public WinePressHandleTileEntity(final TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.animationActivated = false;
        this.animationStartStep = 0;
        this.animationEnd = AnimationTimingHelper.getElapsedTime();
    }

    public WinePressHandleTileEntity() {
        this(ModTileEntityTypes.WINE_PRESS_HANDLE_TILE_ENTITY.get());
    }

    public void triggerHandle() {
        if (masterPos == null) {
            BlockPos masterPos = this.findMaster();
            if (masterPos == null) return;
            this.masterPos = masterPos;
        }

        if (world != null) {
            TileEntity te = world.getTileEntity(masterPos);
            if (te instanceof WinePressTileEntity) {
                WinePressTileEntity tileEntity = (WinePressTileEntity) world.getTileEntity(masterPos);
                if (tileEntity != null) {
                    // Only execute if interaction is allowed
                    if (AnimationTimingHelper.getElapsedTime() >= tileEntity.getNextAllowedInteraction()) {
                        tileEntity.setNextAllowedInteraction(AnimationTimingHelper.getElapsedTime() + WinePressTileEntity.ANIMATION_LENGTH);
                        tileEntity.updateRecipeProgress();
                    }
                }
            }
        }
    }

    @Nonnull
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("MasterX", this.masterPos.getX());
        compound.putInt("MasterY", this.masterPos.getY());
        compound.putInt("MasterZ", this.masterPos.getZ());
        compound.putFloat("AnimationStartStep", this.animationStartStep);
        return super.write(compound);
    }

    @Override
    public void read(@Nonnull CompoundNBT compound) {
        super.read(compound);
        int x = compound.getInt("MasterX");
        int y = compound.getInt("MasterY");
        int z = compound.getInt("MasterZ");
        this.animationStartStep = compound.getFloat("AnimationStartStep");
        this.masterPos = new BlockPos(x, y, z);
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        final int MAX_DISTANCE_IN_BLOCKS = 48;
        return MAX_DISTANCE_IN_BLOCKS * MAX_DISTANCE_IN_BLOCKS;
    }

    public BlockPos getMasterPos() {
        return masterPos;
    }

    public int getAnimationLength() {
        return WinePressTileEntity.ANIMATION_LENGTH;
    }

    public float getAnimationEnd() {
        return animationEnd;
    }

    public void setAnimationEnd(float animationEnd) {
        this.animationEnd = animationEnd;
    }

    public boolean isAnimationActivated() {
        return this.animationActivated;
    }

    public void setAnimationActivated(boolean animationActivated) {
        this.animationActivated = animationActivated;
    }

    public void setMasterPos(BlockPos masterPos) {
        this.masterPos = masterPos;
    }

    public int getProgressStepsCurrent() {
        if (masterPos == null) {
            this.masterPos = findMaster();
            if (this.masterPos == null) return 0;
        }

        if (world != null) {
            TileEntity te = world.getTileEntity(masterPos);
            if (te instanceof WinePressTileEntity) {
                WinePressTileEntity tileEntity = (WinePressTileEntity) world.getTileEntity(masterPos);
                if (tileEntity != null) {
                    return tileEntity.getProcessStepCurrent();
                }
            }
        }
        return 0;
    }

    public int getProgressStepsTotal() {
        if (masterPos == null) {
            this.masterPos = findMaster();
            if (this.masterPos == null) return 0;
        }

        if (world != null) {
            TileEntity te = world.getTileEntity(masterPos);
            if (te instanceof WinePressTileEntity) {
                WinePressTileEntity tileEntity = (WinePressTileEntity) world.getTileEntity(masterPos);
                if (tileEntity != null) {
                    return tileEntity.getProcessStepTotal();
                }
            }
        }
        return 0;
    }

    public float getAnimationStartStep() {
        return animationStartStep;
    }

    public void setAnimationStartStep(float animationStartStep) {
        this.animationStartStep = animationStartStep;
    }

    private BlockPos findMaster() {
        BlockPos blockPos = this.getPos().add(0, -1, 0);
        if (world != null) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() instanceof WinePressBlock) {
                WinePressTileEntity winePressTileEntity = (WinePressTileEntity) world.getTileEntity(blockPos);
                if (winePressTileEntity != null) {
                    return blockPos;
                }
            }
        }
        return null;
    }

    // ----------- SYNC PACKETS START -----------
    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(getPos(), 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundNBT nbt) {
        this.read(nbt);
    }
    // ----------- SYNC PACKETS END -----------
}
