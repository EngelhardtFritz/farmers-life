package com.asheriit.asheriitsfarmerslife.tileentity;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

public abstract class AbstractNamedContainerTileEntity extends TileEntity implements INamedContainerProvider {
    private ITextComponent customName;

    public AbstractNamedContainerTileEntity(TileEntityType<?> tileEntityTypeIn, ITextComponent customName) {
        super(tileEntityTypeIn);
        this.customName = customName;
    }

    /**
     * Gets the default display name for the tile entity
     *
     * @return ITextComponent with default display name
     */
    public abstract ITextComponent getDefaultDisplayName();

    // ----------- GETTER AND SETTER -----------
    @Override
    public ITextComponent getDisplayName() {
        return this.getName();
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultDisplayName();
    }

    public ITextComponent getCustomName() {
        return this.customName;
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }
}
