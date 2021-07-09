package com.asheriit.asheriitsfarmerslife.init;

import com.asheriit.asheriitsfarmerslife.state.properties.DiagonalBlockType;
import com.asheriit.asheriitsfarmerslife.state.properties.TrellisMaterialType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class ModBlockStateProperties extends BlockStateProperties {
    // ----- INTEGER PROPERTIES -----
    public static final IntegerProperty AGE_0_6 = IntegerProperty.create("age", 0, 6);
    public static final IntegerProperty FERTILIZER_LEVEL_0_4 = IntegerProperty.create("fertilizer_level", 0, 4);
    public static final IntegerProperty DISTANCE_0_5 = IntegerProperty.create("distance", 0, 5);
    public static final IntegerProperty FULLNESS_0_4 = IntegerProperty.create("fullness", 0, 4);
    public static final IntegerProperty FULLNESS_0_6 = IntegerProperty.create("fullness", 0, 6);
    public static final IntegerProperty FULLNESS_0_8 = IntegerProperty.create("fullness", 0, 8);
    public static final IntegerProperty USES_0_2 = IntegerProperty.create("uses", 0, 2);
    // ----- BOOLEAN PROPERTIES -----
    public static final BooleanProperty IS_ROOT_BLOCK = BooleanProperty.create("root");
    public static final BooleanProperty FILLED = BooleanProperty.create("filled");
    public static final BooleanProperty HAS_CONNECTION = BooleanProperty.create("has_connection");
    public static final BooleanProperty HAS_LID = BooleanProperty.create("has_lid");
    // ----- ENUM PROPERTIES -----
    public static final EnumProperty<TrellisMaterialType> TRELLIS_MATERIAL_TYPE = EnumProperty.create("type", TrellisMaterialType.class);
    public static final EnumProperty<DiagonalBlockType> DIAGONAL_BLOCK_TYPE = EnumProperty.create("diagonal_block", DiagonalBlockType.class);
}
