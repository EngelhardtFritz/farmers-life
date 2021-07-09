//package com.asheriit.asheriitsfarmerslife.objects.blocks.machines;
//
//import com.asheriit.asheriitsfarmerslife.init.ModTileEntityTypes;
//import net.minecraft.block.Block;
//import net.minecraft.block.BlockState;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.state.DirectionProperty;
//import net.minecraft.state.StateContainer;
//import net.minecraft.state.properties.BlockStateProperties;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.ActionResultType;
//import net.minecraft.util.Direction;
//import net.minecraft.util.Hand;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.BlockRayTraceResult;
//import net.minecraft.world.IBlockReader;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.network.NetworkHooks;
//
//import javax.annotation.Nullable;
//
//public class MortarAndPestleBlock extends Block {
//    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
//
//    public MortarAndPestleBlock(Properties properties) {
//        super(properties);
//        this.setDefaultState(this.getStateContainer().getBaseState().with(FACING, Direction.NORTH));
//    }
//
//    @Override
//    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
//        if (worldIn.isRemote) {
//            return ActionResultType.SUCCESS;
//        } else {
//            TileEntity tileEntity = worldIn.getTileEntity(pos);
//            if (tileEntity != null && tileEntity instanceof MortarAndPestleTileEntity) {
//                MortarAndPestleTileEntity mortarAndPestleTileEntity = (MortarAndPestleTileEntity) tileEntity;
//
//                NetworkHooks.openGui((ServerPlayerEntity) player, mortarAndPestleTileEntity, pos);
//                return ActionResultType.SUCCESS;
//            }
//        }
//        return ActionResultType.PASS;
//    }
//
//    @Override
//    public boolean hasTileEntity(BlockState state) {
//        return true;
//    }
//
//    @Nullable
//    @Override
//    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
//        return ModTileEntityTypes.MORTAR_AND_PESTLE_TILE_ENTITY.get().create();
//    }
//
//    @Override
//    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
//        builder.add(FACING);
//    }
//}
