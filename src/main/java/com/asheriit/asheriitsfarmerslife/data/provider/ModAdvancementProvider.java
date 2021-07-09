package com.asheriit.asheriitsfarmerslife.data.provider;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import com.asheriit.asheriitsfarmerslife.api.utils.provider.ModAbstractAdvancementProvider;
import com.asheriit.asheriitsfarmerslife.init.ModBlocks;
import com.google.common.collect.ImmutableList;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.advancements.criterion.TickTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.List;
import java.util.function.Consumer;

public class ModAdvancementProvider extends ModAbstractAdvancementProvider implements Consumer<Consumer<Advancement>> {
    public static final ResourceLocation ROOT_LOCATION = new ResourceLocation(FarmersLife.MOD_ID, "root");

    @Override
    public void accept(Consumer<Advancement> advancementConsumer) {
        Advancement root = Advancement.Builder.builder()
                .withDisplay(ModBlocks.GARDEN_FARMLAND.get(),
                        new StringTextComponent("Farmers Life"),
                        new TranslationTextComponent("advancement." + ROOT_LOCATION.getNamespace() + "." + ROOT_LOCATION.getPath()),
                        new ResourceLocation("minecraft", "textures/gui/advancements/backgrounds/stone.png"),
                        FrameType.GOAL, false, false, false)
                .withCriterion("trigger", new TickTrigger.Instance())
                .register(advancementConsumer, ROOT_LOCATION.toString());
    }

    // -------------- CONSTRUCTOR --------------
    public ModAdvancementProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    // -------------- ADVANCEMENT GETTER --------------
    @Override
    public List<Consumer<Consumer<Advancement>>> getAdvancements() {
        return ImmutableList.of(this::accept);
    }
}
