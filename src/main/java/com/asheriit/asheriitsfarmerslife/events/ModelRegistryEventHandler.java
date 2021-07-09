package com.asheriit.asheriitsfarmerslife.events;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.client.baked_model.TrellisCropBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersLife.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegistryEventHandler {
    public static final ResourceLocation WINE_PRESS_HANDLE = new ResourceLocation(FarmersLife.MOD_ID, "block/wine_press/press_part");
    public static final ResourceLocation GENERIC_PLANE_MUST_STILL = new ResourceLocation(FarmersLife.MOD_ID, "block/generic/must_plane");
    public static final ResourceLocation GENERIC_PLANE_WATER_STILL = new ResourceLocation(FarmersLife.MOD_ID, "block/generic/water_plane");
    public static final ResourceLocation BOTTLE_HEAD = new ResourceLocation(FarmersLife.MOD_ID, "block/aging_wine_rack/bottle_north_top_left");

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelRegistryEvent event) {
        ModelLoader.addSpecialModel(TrellisCropBakedModel.TRELLIS_NORTH);
        ModelLoader.addSpecialModel(TrellisCropBakedModel.TRELLIS_EAST);
        ModelLoader.addSpecialModel(TrellisCropBakedModel.TRELLIS_SOUTH);
        ModelLoader.addSpecialModel(TrellisCropBakedModel.TRELLIS_WEST);
        ModelLoader.addSpecialModel(TrellisCropBakedModel.TRELLIS_UP);
        ModelLoader.addSpecialModel(TrellisCropBakedModel.TRELLIS_DOWN);
        ModelLoader.addSpecialModel(TrellisCropBakedModel.TRELLIS_MIDDLE);
        ModelLoader.addSpecialModel(WINE_PRESS_HANDLE);
        ModelLoader.addSpecialModel(GENERIC_PLANE_MUST_STILL);
        ModelLoader.addSpecialModel(GENERIC_PLANE_WATER_STILL);
        ModelLoader.addSpecialModel(BOTTLE_HEAD);
    }
}
