//package com.asheriit.asheriitsfarmerslife.container;
//
//import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
//import com.asheriit.asheriitsfarmerslife.init.ModContainerTypes;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.PlayerInventory;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.IWorldPosCallable;
//import net.minecraftforge.api.distmarker.Dist;
//import net.minecraftforge.api.distmarker.OnlyIn;
//import net.minecraftforge.items.SlotItemHandler;
//
//import java.util.Objects;
//
//public class MortarAndPestleContainer extends AbstractInventoryContainer {
//    public MortarAndPestleTileEntity tileEntity;
//    private IWorldPosCallable iWorldPosCallable;
//
//    // Client Constructor
//    public MortarAndPestleContainer(final int windowId, PlayerInventory inventory, final PacketBuffer extraData) {
//        this(windowId, inventory, getTileEntity(inventory, extraData));
//    }
//
//    // Server Constructor
//    public MortarAndPestleContainer(final int windowId, final PlayerInventory playerInventory, final MortarAndPestleTileEntity tileEntity) {
//        super(ModContainerTypes.MORTAR_AND_PESTLE_CONTAINER.get(), windowId);
//        this.tileEntity = tileEntity;
//        this.iWorldPosCallable = IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos());
//
//        tileEntity.getInventoryCap().ifPresent((itemHandler) -> {
//            addSlot(new SlotItemHandler(itemHandler, MortarAndPestleTileEntity.ITEM_INPUT_SLOT_1, 44, 22));
//            addSlot(new SlotItemHandler(itemHandler, MortarAndPestleTileEntity.ITEM_INPUT_SLOT_2, 62, 22));
//            addSlot(new SlotItemHandler(itemHandler, MortarAndPestleTileEntity.ITEM_OUTPUT_SLOT_1, 113, 33));
//            addSlot(new SlotItemHandler(itemHandler, MortarAndPestleTileEntity.ITEM_OUTPUT_SLOT_2, 137, 33));
//        });
//        this.drawPlayerInventory(playerInventory, 8, 84);
//        this.drawPlayerHotbar(playerInventory, 8, 142);
//    }
//
//    @Override
//    public boolean canInteractWith(PlayerEntity playerIn) {
//        return isWithinUsableDistance(this.iWorldPosCallable, playerIn, ModBlocks.MORTAR_AND_PESTLE.get());
//    }
//
//    /**
//     * Get the MortarAndPestleTileEntity if it exists for the given position
//     *
//     * @param playerInventory: The player inventory
//     * @param data:            Extra data
//     * @return Returns the TileEntity or throws an error if the state is invalid
//     */
//    @OnlyIn(Dist.CLIENT)
//    private static MortarAndPestleTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
//        Objects.requireNonNull(playerInventory, "[MortarAndPestleContainer::getTileEntity] PlayerInventory can not be null!");
//        Objects.requireNonNull(data, "[MortarAndPestleContainer::getTileEntity] PacketBuffer can not be null!");
//        final TileEntity tileEntity = playerInventory.player.world.getTileEntity(data.readBlockPos());
//        if (tileEntity instanceof MortarAndPestleTileEntity) {
//            return (MortarAndPestleTileEntity) tileEntity;
//        }
//        throw new IllegalStateException("[MortarAndPestleContainer::getTileEntity] TileEntity is not valid: " + tileEntity);
//    }
//}
