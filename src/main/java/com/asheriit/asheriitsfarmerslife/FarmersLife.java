package com.asheriit.asheriitsfarmerslife;

import com.asheriit.asheriitsfarmerslife.api.utils.AnimationTimingHelper;
import com.asheriit.asheriitsfarmerslife.capabilities.CapabilityDrunkenState;
import com.asheriit.asheriitsfarmerslife.client.gui.*;
import com.asheriit.asheriitsfarmerslife.client.tileentity.renderer.*;
import com.asheriit.asheriitsfarmerslife.config.FarmersLifeConfig;
import com.asheriit.asheriitsfarmerslife.init.*;
import com.asheriit.asheriitsfarmerslife.utils.ItemGroupSortingHelper;
import com.asheriit.asheriitsfarmerslife.world.ModWorldGenOre;
import com.asheriit.asheriitsfarmerslife.world.ModWorldGenTrees;
import com.asheriit.asheriitsfarmerslife.world.ModWorldGenVegetation;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;

@Mod(FarmersLife.MOD_ID)
@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FarmersLife {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "asheriitsfarmerslife";
    public static FarmersLife instance;
    public static SimpleChannel simpleChannel;
    static Comparator<ItemStack> farmersLifeCategorySorter;

    public static final ItemGroup FARMERS_LIFE_CATEGORY = new ItemGroup("farmers_life_category") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.GARDEN_FARMLAND_ITEM.get());
        }


        @Override
        public void fill(NonNullList<ItemStack> items) {
            for (Item item : ForgeRegistries.ITEMS) {
                if (!ItemGroupSortingHelper.isItemDisabled(item)) {
                    item.fillItemGroup(this, items);
                }
            }
            items.sort(farmersLifeCategorySorter);
        }
    };

    public FarmersLife() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FarmersLifeConfig.COMMON_SPEC, "farmerslife-common.toml");

        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientOnlySetup);

        ModFeatures.FEATURES.register(modEventBus);
        ModEffects.EFFECT_TYPES.register(modEventBus);
        ModParticles.PARTICLE_TYPES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModAlcoholicItems.ITEMS.register(modEventBus);
        ModFluids.FLUIDS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.ITEMS.register(modEventBus);
        ModifiedVanillaBlocks.BLOCKS.register(modEventBus);
        ModifiedVanillaBlocks.ITEMS.register(modEventBus);
        ModGlobalLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZER_TYPES.register(modEventBus);
        ModRecipeSerializer.RECIPE_SERIALIZERS.register(modEventBus);
        ModTileEntityTypes.TILE_ENTITY_TYPES.register(modEventBus);
        ModContainerTypes.CONTAINER_TYPES.register(modEventBus);

        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(RegisterCompostables::registerAll);
        DeferredWorkQueue.runLater(ModWorldGenVegetation::generateVegetation);
        DeferredWorkQueue.runLater(ModWorldGenOre::generateOre);
        DeferredWorkQueue.runLater(ModWorldGenTrees::generateTrees);
        // ----------------------- Register Capabilities -----------------------
        CapabilityDrunkenState.register();

        // ----------------------- Register custom network messages -----------------------
        CommonSetup.registerNetworkMessageHandler();
    }

    private void clientOnlySetup(final FMLClientSetupEvent event) {
        // ----------------------- Register animation helper -----------------------
        MinecraftForge.EVENT_BUS.register(AnimationTimingHelper.class);
        // ----------------------- Set Render Layers for Blocks -----------------------
        ClientSetup.setRenderLayers();
        // ----------------------- Set the sorter for creative tab -----------------------
        farmersLifeCategorySorter = ItemGroupSortingHelper.orderCreativeTabs();

        // ----------------------- Register TER (Tile entity renderer) for tile entities -----------------------
        DeferredWorkQueue.runLater(() -> {
            ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.WINE_PRESS_TILE_ENTITY.get(), WinePressRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.STOMPING_BARREL_TILE_ENTITY.get(), StompingBarrelRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.FERTILIZER_COMPOSTER_TILE_ENTITY.get(), FertilizerComposterRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.FILTRATION_MACHINE_TILE_ENTITY.get(), FiltrationMachineRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.WINE_RACK_TILE_ENTITY.get(), WineRackRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.AGING_WINE_RACK_TILE_ENTITY.get(), AgingWineRackRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.BOILING_CAULDRON_TILE_ENTITY.get(), BoilingCauldronRenderer::new);
        });

        // ----------------------- Register screens for containers -----------------------
        DeferredWorkQueue.runLater(() -> {
            ScreenManager.registerFactory(ModContainerTypes.DRYING_MACHINE_CONTAINER.get(), DryingMachineScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.WINE_PRESS_CONTAINER.get(), WinePressScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.STOMPING_BARREL_CONTAINER.get(), StompingBarrelScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.FERTILIZER_COMPOSTER_CONTAINER.get(), FertilizerComposterScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.FERMENTING_BARREL_CONTAINER.get(), FermentingBarrelScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.FLUID_STORAGE_CONTAINER.get(), FluidStorageScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.FILTRATION_MACHINE_CONTAINER.get(), FiltrationMachineScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.FINING_MACHINE_CONTAINER.get(), FiningMachineScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.BOTTLING_MACHINE_CONTAINER.get(), BottlingMachineScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.WINE_RACK_CONTAINER.get(), WineRackScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.AGING_WINE_RACK_CONTAINER.get(), AgingWineRackScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.FARMER_WORKBENCH_CONTAINER.get(), FarmerWorkbenchScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.BOILING_CAULDRON_CONTAINER.get(), BoilingCauldronScreen::new);
            ScreenManager.registerFactory(ModContainerTypes.TEMPERATURE_CHAMBER_CONTAINER.get(), TemperatureChamberScreen::new);
//            ScreenManager.registerFactory(ModContainerTypes.MORTAR_AND_PESTLE_CONTAINER.get(), MortarAndPestleScreen::new);
        });
    }
}