package com.asheriit.asheriitsfarmerslife.objects.items;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import com.asheriit.asheriitsfarmerslife.init.ModTags;
import com.asheriit.asheriitsfarmerslife.properties.FullnessOneToTen;
import com.asheriit.asheriitsfarmerslife.properties.ItemTierType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WateringCanItem extends AbstractRarityItem {
    public static final String NBT_TAG_NAME_FULLNESS = "watering_can_fullness";

    public WateringCanItem(Properties properties, ItemTierType itemTierType) {
        super(properties, itemTierType);
        this.addPropertyOverride(new ResourceLocation(NBT_TAG_NAME_FULLNESS), WateringCanItem::getFullnessPropertyOverwrite);
    }

    /**
     * Check if the block which the item was used on is a valid condition and execute code
     * Checks if the targeted block is a fluid or in range of farmland blocks
     * In case of fluid fills the watering can and of farmland sets moisture to max and decreases the fullness of the can by one
     * @param context:  Context in which the item is used
     * @return
     */
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        // Get required context information
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos blockPos = context.getPos();
        ItemStack itemStack = context.getItem();
        // RayTrace to get possible targeted fluid blocks
        RayTraceResult raytraceresult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult)raytraceresult;
        IFluidState fluidState = world.getFluidState(blockraytraceresult.getPos());
        int stateMax = 7;

        List<BlockPos> blockPosList = this.getValidPositions(world, blockPos, ModTags.Blocks.FARMLANDS.getId(), stateMax);
        FullnessOneToTen fullness = getFullness(itemStack);
        boolean isFluid = fluidState.getFluid() == Fluids.WATER;

        if (((fullness == FullnessOneToTen.EMPTY || blockPosList.size() == 0) && !isFluid) || (isFluid && fullness == FullnessOneToTen.FULL)) {
            return ActionResultType.FAIL;
        }

        // If the clicked block is water, fill watering can and ignore further conditions
        if (fullness != FullnessOneToTen.FULL && isFluid) {
            fullness = fullness.increaseFullness();
            fullness.toNBT(itemStack.getOrCreateTag(), NBT_TAG_NAME_FULLNESS);
            player.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
            return ActionResultType.SUCCESS;
        }

        // If clicked block is is not water check if farmland can be watered
        for (BlockPos pos: blockPosList) {
            BlockState blockState = world.getBlockState(pos);
            if (!world.isRemote) {
                world.setBlockState(pos, blockState.with(BlockStateProperties.MOISTURE_0_7, stateMax));
            }
        }

        // If any farmland could be watered reduce fullness by one level
        if (!player.isSpectator() && !player.isCreative()) {
            fullness = fullness.decreaseFullness();
            fullness.toNBT(itemStack.getOrCreateTag(), NBT_TAG_NAME_FULLNESS);
        }
        // TODO: change to custom sound for watering
        player.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
        this.addWaterSplashParticles(world, blockPosList);

        return ActionResultType.SUCCESS;
    }

    /**
     * Creates subitems for the state of an empty and a full watering can
     */
    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack emptyCan = new ItemStack(this, 1);
            setFullness(emptyCan, FullnessOneToTen.EMPTY);
            items.add(emptyCan);
            ItemStack fullCan = new ItemStack(this, 1);
            setFullness(fullCan, FullnessOneToTen.FULL);
            items.add(fullCan);
        }
    }

    /**
     * Modifies the display name and adds information about the fullness level
     */
    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        String fullnessText = "(" + getFullness(stack).getName() + ")";
        return new TranslationTextComponent(this.getTranslationKey(stack), fullnessText);
    }

    // Gets fullness from nbt data
    public static FullnessOneToTen getFullness(ItemStack itemStack) {
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        return FullnessOneToTen.fromNBT(compoundNBT, NBT_TAG_NAME_FULLNESS);
    }

    // Sets fullness to nbt data
    public static void setFullness(ItemStack itemStack, FullnessOneToTen fullness) {
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        fullness.toNBT(compoundNBT, NBT_TAG_NAME_FULLNESS);
    }

    // Gets property overwrite for fullness
    private static float getFullnessPropertyOverwrite(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity livingEntity) {
        FullnessOneToTen fullness = getFullness(itemStack);
        return fullness.getPropertyOverrideValue();
    }

    // Calculates all positions where the moisture can be updated
    private List<BlockPos> getValidPositions(IWorldReader worldIn, BlockPos pos, ResourceLocation blockTagLocation, int stateMax) {
        Tag<Block> blockTag = BlockTags.getCollection().getOrCreate(blockTagLocation);
        List<BlockPos> blockPosList = new ArrayList<>();

        ItemTierType itemTierType = this.getItemTierType();
        int range = itemTierType.getItemTier();
        for (BlockPos blockPos: BlockPos.getAllInBoxMutable(pos.add(-range,-1,-range), pos.add(range,1, range))) {
            BlockState blockState = worldIn.getBlockState(blockPos);
            if (blockState.isIn(blockTag) && blockState.getProperties().contains(ModBlockStateProperties.MOISTURE_0_7) && blockState.get(ModBlockStateProperties.MOISTURE_0_7) < stateMax) {
                blockPosList.add(blockPos.toImmutable());
            }
        }

        return blockPosList;
    }

    // Add splash particles to the given block positions
    private void addWaterSplashParticles(World world, List<BlockPos> splashPositions) {
        for (BlockPos blockPos: splashPositions) {
            for (int i = 0; i < 3; i++) {
                Vec3d motion = new Vec3d(world.rand.nextFloat() * 0.2F, world.rand.nextFloat() * 0.05F, world.rand.nextFloat() * 0.2F);
                world.addParticle(ParticleTypes.SPLASH, blockPos.getX() + world.rand.nextFloat() * 0.25F, blockPos.getY() + 0.9F, blockPos.getZ() + world.rand.nextFloat() * 0.25F, motion.x, motion.y, motion.z);
            }
        }
    }
}
