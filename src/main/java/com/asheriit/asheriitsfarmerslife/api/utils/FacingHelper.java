package com.asheriit.asheriitsfarmerslife.api.utils;

import net.minecraft.block.SixWayBlock;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FacingHelper {
    public static final Map<Direction, BooleanProperty> FOUR_WAY_FACING = SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((map) ->
            map.getKey().getAxis().isHorizontal()).collect(Util.toMapCollector());

    public static final List<Direction> HORIZONTAL_DIRECTIONS = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST);
}
