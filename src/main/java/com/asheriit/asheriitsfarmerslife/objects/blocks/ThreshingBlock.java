package com.asheriit.asheriitsfarmerslife.objects.blocks;

import com.asheriit.asheriitsfarmerslife.init.ModBlockStateProperties;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.loot.*;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ThreshingBlock extends Block {
    public static final IntegerProperty USES = ModBlockStateProperties.USES_0_2;
    public static final VoxelShape[] SHAPES = new VoxelShape[]{Block.makeCuboidShape(0, 0, 0, 16, 3, 16), Block.makeCuboidShape(0, 0, 0, 16, 6, 16), Block.makeCuboidShape(0, 0, 0, 16, 8, 16)};
    public static final int MAX_USES = 2;
    private final Object2IntMap<BlockState> blockStateMap = new Object2IntOpenHashMap<>();
    @Nullable
    private ResourceLocation specialLootTable;
    private final java.util.function.Supplier<ResourceLocation> specialLootTableSupplier;

    public ThreshingBlock(Properties properties) {
        super(properties);
        this.specialLootTableSupplier = () -> new ResourceLocation(this.getRegistryName().getNamespace(), "special/" + this.getRegistryName().getPath());
        this.setDefaultState(this.getStateContainer().getBaseState()
                .with(USES, MAX_USES));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[this.getIndex(state)];
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPES[this.getIndex(state)];
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack stack) {
        player.addStat(Stats.BLOCK_MINED.get(this));
        player.addExhaustion(0.005F);

        int currentUses = state.getProperties().contains(USES) ? state.get(USES) : -1;
        if (currentUses == -1) return;

        if (worldIn instanceof ServerWorld) {
            generateSpecialDrops(state, (ServerWorld) worldIn, pos, (TileEntity) null, player, player.getHeldItemMainhand())
                    .forEach((itemStack) -> spawnAsEntity(worldIn, pos, itemStack));
        }
    }

    public ResourceLocation getSpecialLootTable() {
        if (this.specialLootTable == null) {
            this.specialLootTable = this.specialLootTableSupplier.get();
        }

        return this.specialLootTable;
    }

    public List<ItemStack> generateSpecialDrops(BlockState state, ServerWorld worldIn, BlockPos pos, @Nullable TileEntity tileEntityIn, @Nullable Entity entityIn, ItemStack stack) {
        LootContext.Builder lootcontext$builder = (new LootContext.Builder(worldIn)).withRandom(worldIn.rand).withParameter(LootParameters.POSITION, pos).withParameter(LootParameters.TOOL, stack).withNullableParameter(LootParameters.THIS_ENTITY, entityIn).withNullableParameter(LootParameters.BLOCK_ENTITY, tileEntityIn);
        ResourceLocation resourcelocation = this.getSpecialLootTable();
        if (resourcelocation == LootTables.EMPTY) {
            return Collections.emptyList();
        } else {
            LootContext lootcontext = lootcontext$builder.withParameter(LootParameters.BLOCK_STATE, state).build(LootParameterSets.BLOCK);
            ServerWorld serverworld = lootcontext.getWorld();
            LootTable loottable = serverworld.getServer().getLootTableManager().getLootTableFromLocation(resourcelocation);
            List<ItemStack> loot = loottable.generate(lootcontext);
            return loot;
        }
    }

    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
        entityIn.onLivingFall(fallDistance, 0.6F);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(USES);
    }

    protected int getIndex(BlockState state) {
        return this.blockStateMap.computeIntIfAbsent(state, (blockState) -> state.getProperties().contains(USES) ? state.get(USES) : MAX_USES);
    }
}
