package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.objects.blocks.ModifiedPaneBlock;
import com.asheriit.asheriitsfarmerslife.objects.blocks.ModifiedStainedGlassPaneBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModifiedVanillaBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "minecraft");


    public static final RegistryObject<Block> GLASS_PANE = BLOCKS.register("glass_pane", () ->
            new ModifiedPaneBlock(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> WHITE_STAINED_GLASS_PANE = BLOCKS.register("white_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.WHITE, Block.Properties.create(Material.GLASS, DyeColor.WHITE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> ORANGE_STAINED_GLASS_PANE = BLOCKS.register("orange_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.ORANGE, Block.Properties.create(Material.GLASS, DyeColor.ORANGE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> MAGENTA_STAINED_GLASS_PANE = BLOCKS.register("magenta_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.MAGENTA, Block.Properties.create(Material.GLASS, DyeColor.MAGENTA).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> LIGHT_BLUE_STAINED_GLASS_PANE = BLOCKS.register("light_blue_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.LIGHT_BLUE, Block.Properties.create(Material.GLASS, DyeColor.LIGHT_BLUE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> YELLOW_STAINED_GLASS_PANE = BLOCKS.register("yellow_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.YELLOW, Block.Properties.create(Material.GLASS, DyeColor.YELLOW).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> LIME_STAINED_GLASS_PANE = BLOCKS.register("lime_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.LIME, Block.Properties.create(Material.GLASS, DyeColor.LIME).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> PINK_STAINED_GLASS_PANE = BLOCKS.register("pink_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.PINK, Block.Properties.create(Material.GLASS, DyeColor.PINK).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> GRAY_STAINED_GLASS_PANE = BLOCKS.register("gray_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.GRAY, Block.Properties.create(Material.GLASS, DyeColor.GRAY).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> LIGHT_GRAY_STAINED_GLASS_PANE = BLOCKS.register("light_gray_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.LIGHT_GRAY, Block.Properties.create(Material.GLASS, DyeColor.LIGHT_GRAY).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> CYAN_STAINED_GLASS_PANE = BLOCKS.register("cyan_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.CYAN, Block.Properties.create(Material.GLASS, DyeColor.CYAN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> PURPLE_STAINED_GLASS_PANE = BLOCKS.register("purple_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.PURPLE, Block.Properties.create(Material.GLASS, DyeColor.PURPLE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> BLUE_STAINED_GLASS_PANE = BLOCKS.register("blue_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.BLUE, Block.Properties.create(Material.GLASS, DyeColor.BLUE).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> BROWN_STAINED_GLASS_PANE = BLOCKS.register("brown_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.BROWN, Block.Properties.create(Material.GLASS, DyeColor.BROWN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> GREEN_STAINED_GLASS_PANE = BLOCKS.register("green_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.GREEN, Block.Properties.create(Material.GLASS, DyeColor.GREEN).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> RED_STAINED_GLASS_PANE = BLOCKS.register("red_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.RED, Block.Properties.create(Material.GLASS, DyeColor.RED).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));
    public static final RegistryObject<Block> BLACK_STAINED_GLASS_PANE = BLOCKS.register("black_stained_glass_pane", () ->
            new ModifiedStainedGlassPaneBlock(DyeColor.BLACK, Block.Properties.create(Material.GLASS, DyeColor.BLACK).hardnessAndResistance(0.3F).sound(SoundType.GLASS).notSolid()));



    public static final RegistryObject<BlockItem> GLASS_PANE_ITEM = ITEMS.register("glass_pane", () ->
            new BlockItem(GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> WHITE_STAINED_GLASS_PANE_ITEM = ITEMS.register("white_stained_glass_pane", () ->
            new BlockItem(WHITE_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> ORANGE_STAINED_GLASS_PANE_ITEM = ITEMS.register("orange_stained_glass_pane", () ->
            new BlockItem(ORANGE_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> MAGENTA_STAINED_GLASS_PANE_ITEM = ITEMS.register("magenta_stained_glass_pane", () ->
            new BlockItem(MAGENTA_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> LIGHT_BLUE_STAINED_GLASS_PANE_ITEM = ITEMS.register("light_blue_stained_glass_pane", () ->
            new BlockItem(LIGHT_BLUE_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> YELLOW_STAINED_GLASS_PANE_ITEM = ITEMS.register("yellow_stained_glass_pane", () ->
            new BlockItem(YELLOW_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> LIME_STAINED_GLASS_PANE_ITEM = ITEMS.register("lime_stained_glass_pane", () ->
            new BlockItem(LIME_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> PINK_STAINED_GLASS_PANE_ITEM = ITEMS.register("pink_stained_glass_pane", () ->
            new BlockItem(PINK_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> GRAY_STAINED_GLASS_PANE_ITEM = ITEMS.register("gray_stained_glass_pane", () ->
            new BlockItem(GRAY_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> LIGHT_GRAY_STAINED_GLASS_PANE_ITEM = ITEMS.register("light_gray_stained_glass_pane", () ->
            new BlockItem(LIGHT_GRAY_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> CYAN_STAINED_GLASS_PANE_ITEM = ITEMS.register("cyan_stained_glass_pane", () ->
            new BlockItem(CYAN_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> PURPLE_STAINED_GLASS_PANE_ITEM = ITEMS.register("purple_stained_glass_pane", () ->
            new BlockItem(PURPLE_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> BLUE_STAINED_GLASS_PANE_ITEM = ITEMS.register("blue_stained_glass_pane", () ->
            new BlockItem(BLUE_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> BROWN_STAINED_GLASS_PANE_ITEM = ITEMS.register("brown_stained_glass_pane", () ->
            new BlockItem(BROWN_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> GREEN_STAINED_GLASS_PANE_ITEM = ITEMS.register("green_stained_glass_pane", () ->
            new BlockItem(GREEN_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> RED_STAINED_GLASS_PANE_ITEM = ITEMS.register("red_stained_glass_pane", () ->
            new BlockItem(RED_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));
    public static final RegistryObject<Item> BLACK_STAINED_GLASS_PANE_ITEM = ITEMS.register("black_stained_glass_pane", () ->
            new BlockItem(BLACK_STAINED_GLASS_PANE.get(), new Item.Properties().group(ItemGroup.DECORATIONS)));

}
