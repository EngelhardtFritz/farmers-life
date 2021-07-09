package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.tileentity.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, FarmersLife.MOD_ID);


    public static final RegistryObject<TileEntityType<DryingMachineTileEntity>> DRYING_MACHINE_TILE_ENTITY = TILE_ENTITY_TYPES.register("drying_machine_tile_entity",
            () -> TileEntityType.Builder.create(DryingMachineTileEntity::new, ModBlocks.DRYING_MACHINE.get()).build(null));

    public static final RegistryObject<TileEntityType<WinePressTileEntity>> WINE_PRESS_TILE_ENTITY = TILE_ENTITY_TYPES.register("wine_press_tile_entity",
            () -> TileEntityType.Builder.create(WinePressTileEntity::new, ModBlocks.WOOD_WINE_PRESS.get()).build(null));

    public static final RegistryObject<TileEntityType<WinePressHandleTileEntity>> WINE_PRESS_HANDLE_TILE_ENTITY = TILE_ENTITY_TYPES.register("wine_press_handle_tile_entity",
            () -> TileEntityType.Builder.create(WinePressHandleTileEntity::new, ModBlocks.WOOD_WINE_PRESS_HANDLE.get()).build(null));

    public static final RegistryObject<TileEntityType<StompingBarrelTileEntity>> STOMPING_BARREL_TILE_ENTITY = TILE_ENTITY_TYPES.register("stomping_barrel_tile_entity",
            () -> TileEntityType.Builder.create(StompingBarrelTileEntity::new, ModBlocks.STOMPING_BARREL.get()).build(null));

    public static final RegistryObject<TileEntityType<FertilizerComposterTileEntity>> FERTILIZER_COMPOSTER_TILE_ENTITY = TILE_ENTITY_TYPES.register("fertilizer_composter_tile_entity",
            () -> TileEntityType.Builder.create(FertilizerComposterTileEntity::new, ModBlocks.FERTILIZER_COMPOSTER.get()).build(null));

    public static final RegistryObject<TileEntityType<FermentingBarrelTileEntity>> FERMENTING_BARREL_TILE_ENTITY = TILE_ENTITY_TYPES.register("fermenting_barrel_tile_entity",
            () -> TileEntityType.Builder.create(FermentingBarrelTileEntity::new, ModBlocks.FERMENTING_BARREL.get()).build(null));

    public static final RegistryObject<TileEntityType<FluidStorageTileEntity>> WOOD_FLUID_STORAGE_BARREL_TILE_ENTITY = TILE_ENTITY_TYPES.register("wood_fluid_storage_barrel_tile_entity",
            () -> TileEntityType.Builder.create(() -> new FluidStorageTileEntity(16000), ModBlocks.WOOD_FLUID_STORAGE_BARREL.get()).build(null));

    public static final RegistryObject<TileEntityType<FiltrationMachineTileEntity>> FILTRATION_MACHINE_TILE_ENTITY = TILE_ENTITY_TYPES.register("filtration_machine_tile_entity",
            () -> TileEntityType.Builder.create(FiltrationMachineTileEntity::new, ModBlocks.FILTRATION_MACHINE.get()).build(null));

    public static final RegistryObject<TileEntityType<FiningMachineTileEntity>> FINING_MACHINE_TILE_ENTITY = TILE_ENTITY_TYPES.register("fining_machine_tile_entity",
            () -> TileEntityType.Builder.create(FiningMachineTileEntity::new, ModBlocks.FINING_MACHINE.get()).build(null));

    public static final RegistryObject<TileEntityType<BottlingMachineTileEntity>> BOTTLING_MACHINE_TILE_ENTITY = TILE_ENTITY_TYPES.register("bottling_machine_tile_entity",
            () -> TileEntityType.Builder.create(BottlingMachineTileEntity::new, ModBlocks.BOTTLING_MACHINE.get()).build(null));

    public static final RegistryObject<TileEntityType<WineRackTileEntity>> WINE_RACK_TILE_ENTITY = TILE_ENTITY_TYPES.register("wine_rack_tile_entity",
            () -> TileEntityType.Builder.create(WineRackTileEntity::new, ModBlocks.OAK_WINE_RACK.get(), ModBlocks.DARK_OAK_WINE_RACK.get(), ModBlocks.SPRUCE_WINE_RACK.get(), ModBlocks.JUNGLE_WINE_RACK.get(), ModBlocks.ACACIA_WINE_RACK.get(), ModBlocks.BIRCH_WINE_RACK.get()).build(null));

    public static final RegistryObject<TileEntityType<AgingWineRackTileEntity>> AGING_WINE_RACK_TILE_ENTITY = TILE_ENTITY_TYPES.register("aging_wine_rack_tile_entity",
            () -> TileEntityType.Builder.create(AgingWineRackTileEntity::new, ModBlocks.AGING_WINE_RACK.get()).build(null));

    public static final RegistryObject<TileEntityType<BoilingCauldronTileEntity>> BOILING_CAULDRON_TILE_ENTITY = TILE_ENTITY_TYPES.register("boiling_cauldron_tile_entity",
            () -> TileEntityType.Builder.create(BoilingCauldronTileEntity::new, ModBlocks.BOILING_CAULDRON.get()).build(null));

    public static final RegistryObject<TileEntityType<TemperatureChamberTileEntity>> TEMPERATURE_CHAMBER_TILE_ENTITY = TILE_ENTITY_TYPES.register("temperature_chamber_tile_entity",
            () -> TileEntityType.Builder.create(TemperatureChamberTileEntity::new, ModBlocks.TEMPERATURE_CHAMBER.get()).build(null));

//    public static final RegistryObject<TileEntityType<MortarAndPestleTileEntity>> MORTAR_AND_PESTLE_TILE_ENTITY = TILE_ENTITY_TYPES.register("mortar_and_pestle_tile_entity",
//            () -> TileEntityType.Builder.create(MortarAndPestleTileEntity::new, ModBlocks.MORTAR_AND_PESTLE.get()).build(null));
}
