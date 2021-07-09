package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.container.*;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainerTypes {
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, FarmersLife.MOD_ID);


    public static final RegistryObject<ContainerType<DryingMachineContainer>> DRYING_MACHINE_CONTAINER = CONTAINER_TYPES.register("drying_machine_container",
            () -> IForgeContainerType.create(DryingMachineContainer::new));

    public static final RegistryObject<ContainerType<WinePressContainer>> WINE_PRESS_CONTAINER = CONTAINER_TYPES.register("wine_press_container",
            () -> IForgeContainerType.create(WinePressContainer::new));

    public static final RegistryObject<ContainerType<StompingBarrelContainer>> STOMPING_BARREL_CONTAINER = CONTAINER_TYPES.register("stomping_barrel_container",
            () -> IForgeContainerType.create(StompingBarrelContainer::new));

    public static final RegistryObject<ContainerType<FertilizerComposterContainer>> FERTILIZER_COMPOSTER_CONTAINER = CONTAINER_TYPES.register("fertilizer_composter_container",
            () -> IForgeContainerType.create(FertilizerComposterContainer::new));

    public static final RegistryObject<ContainerType<FermentingBarrelContainer>> FERMENTING_BARREL_CONTAINER = CONTAINER_TYPES.register("fermenting_barrel_container",
            () -> IForgeContainerType.create(FermentingBarrelContainer::new));

    public static final RegistryObject<ContainerType<FluidStorageContainer>> FLUID_STORAGE_CONTAINER = CONTAINER_TYPES.register("fluid_storage_container",
            () -> IForgeContainerType.create(FluidStorageContainer::new));

    public static final RegistryObject<ContainerType<FiltrationMachineContainer>> FILTRATION_MACHINE_CONTAINER = CONTAINER_TYPES.register("filtration_machine_container",
            () -> IForgeContainerType.create(FiltrationMachineContainer::new));

    public static final RegistryObject<ContainerType<FiningMachineContainer>> FINING_MACHINE_CONTAINER = CONTAINER_TYPES.register("fining_machine_container",
            () -> IForgeContainerType.create(FiningMachineContainer::new));

    public static final RegistryObject<ContainerType<BottlingMachineContainer>> BOTTLING_MACHINE_CONTAINER = CONTAINER_TYPES.register("bottling_machine_container",
            () -> IForgeContainerType.create(BottlingMachineContainer::new));

    public static final RegistryObject<ContainerType<WineRackContainer>> WINE_RACK_CONTAINER = CONTAINER_TYPES.register("wine_rack_container",
            () -> IForgeContainerType.create(WineRackContainer::new));

    public static final RegistryObject<ContainerType<AgingWineRackContainer>> AGING_WINE_RACK_CONTAINER = CONTAINER_TYPES.register("aging_wine_rack_container",
            () -> IForgeContainerType.create(AgingWineRackContainer::new));

    public static final RegistryObject<ContainerType<FarmerWorkbenchContainer>> FARMER_WORKBENCH_CONTAINER = CONTAINER_TYPES.register("farmer_workbench_container",
            () -> IForgeContainerType.create(FarmerWorkbenchContainer::new));

    public static final RegistryObject<ContainerType<BoilingCauldronContainer>> BOILING_CAULDRON_CONTAINER = CONTAINER_TYPES.register("boiling_cauldron_container",
            () -> IForgeContainerType.create(BoilingCauldronContainer::new));

    public static final RegistryObject<ContainerType<TemperatureChamberContainer>> TEMPERATURE_CHAMBER_CONTAINER = CONTAINER_TYPES.register("temperature_chamber_container",
            () -> IForgeContainerType.create(TemperatureChamberContainer::new));

//    public static final RegistryObject<ContainerType<MortarAndPestleContainer>> MORTAR_AND_PESTLE_CONTAINER = CONTAINER_TYPES.register("mortar_and_pestle_container",
//            () -> IForgeContainerType.create(MortarAndPestleContainer::new));


}
