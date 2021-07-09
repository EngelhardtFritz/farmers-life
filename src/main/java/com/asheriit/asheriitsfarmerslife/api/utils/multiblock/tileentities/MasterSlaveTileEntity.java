package com.asheriit.asheriitsfarmerslife.api.utils.multiblock.tileentities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class MasterSlaveTileEntity extends TileEntity implements ITickableTileEntity {
    private List<BlockPos> slaves;
    private BlockPos master;
    private boolean isMaster;

    public MasterSlaveTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }


    @Override
    public void tick() {
        // TODO: set master etc
    }

    @Nullable
    public BlockPos hasValidMaster() {
        if (this.isMaster) {
            return this.getPos();
        } else {
            final List<BlockPos> connectedPositions = this.findAllConnectedTileEntities();
            if (connectedPositions == null || connectedPositions.isEmpty()) return null;
            World world = this.getWorld();
            for (BlockPos pos: connectedPositions) {
                MasterSlaveTileEntity tileEntity = (MasterSlaveTileEntity) world.getTileEntity(pos);
                if (tileEntity.isMaster()) {
                    return pos;
                } else {
                }
            }
            return null;
        }
    }

    @Nullable
    public List<BlockPos> findAllConnectedTileEntities() {
        List<BlockPos> connectedTileEntities = new ArrayList<>();
        int rangeX = this.getConnectionRangeXFromMaster();
        int rangeY = this.getConnectionRangeXFromMaster();
        int rangeZ = this.getConnectionRangeXFromMaster();
        World world = this.getWorld();

        if (rangeX <= 0 || rangeY <= 0 || rangeZ <= 0 || world == null) {
            // All ranges require to be at least 1
            return connectedTileEntities;
        }
        BlockPos currentPos = this.getPos();
        for (int x = -rangeX; x < rangeX; x++) {
            for (int y = -rangeY; y < rangeY; y++) {
                for (int z = -rangeZ; z < rangeZ; z++) {
                    BlockPos pos = currentPos.add(x, y, z);
                    TileEntity te = world.getTileEntity(pos);
                    if (te != null && te instanceof MasterSlaveTileEntity) {
                        connectedTileEntities.add(pos);
                    }
                }
            }
        }
        return connectedTileEntities;
    }

    public void joinMultipleMasters() {

    }

    public void seperateMasters() {

    }

    // ----------- ABSTRACT METHODS TO IMPLEMENT -----------
    public abstract int getConnectionRangeXFromMaster();
    public abstract int getConnectionRangeYFromMaster();
    public abstract int getConnectionRangeZFromMaster();


    // ----------- GETTER AND SETTER -----------
    public List<BlockPos> getSlaves() {
        return slaves;
    }

    public void addSlave(BlockPos pos) {
        boolean exists = false;
        for (BlockPos blockPos: this.slaves) {
            if (blockPos == pos) {
                exists = true;
            }
        }

        if (!exists) {
            this.slaves.add(pos);
        }
    }

    public BlockPos removeSlave(BlockPos pos) {
        if (pos == null) return null;
        this.slaves.remove(pos);
        return pos;
    }

    public BlockPos getMaster() {
        return master;
    }

    public void setMaster(BlockPos master) {
        this.master = master;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
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
