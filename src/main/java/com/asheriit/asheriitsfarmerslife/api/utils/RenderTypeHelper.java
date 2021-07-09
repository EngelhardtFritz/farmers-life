package com.asheriit.asheriitsfarmerslife.api.utils;

import com.asheriit.asheriitsfarmerslife.FarmersLife;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.lwjgl.opengl.GL11;

import java.util.OptionalDouble;

/**
 * Helper for special types of renderings
 */
public class RenderTypeHelper {
    public static final RenderState.TransparencyState NO_TRANSPARENCY;
    public static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY;

    public static final RenderState.LayerState PROJECTION_LAYERING;

    public static final RenderType LINE_RENDERER_WITH_DEPTH_TEST;
    public static final RenderType LINE_RENDERER_WITHOUT_DEPTH_TEST;

    static {
        NO_TRANSPARENCY = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228510_b_");
        TRANSLUCENT_TRANSPARENCY = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228515_g_");
        PROJECTION_LAYERING = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228500_J_");

        final boolean ENABLE_DEPTH_WRITING = true;
        final boolean ENABLE_COLOUR_COMPONENTS_WRITING = false;
        final RenderState.WriteMaskState WRITE_TO_DEPTH_AND_COLOR = new RenderState.WriteMaskState(ENABLE_DEPTH_WRITING, ENABLE_COLOUR_COMPONENTS_WRITING);
        final RenderState.DepthTestState NO_DEPTH = new RenderState.DepthTestState(GL11.GL_ALWAYS);

        final int INITIAL_BUFFER_SIZE = 128;
        final boolean AFFECTS_OUTLINE = false;
        RenderType.State renderState;

        renderState = RenderType.State.getBuilder()
                .line(new RenderState.LineState(OptionalDouble.of(1)))
                .layer(PROJECTION_LAYERING)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .writeMask(WRITE_TO_DEPTH_AND_COLOR)
                .build(AFFECTS_OUTLINE);
        LINE_RENDERER_WITH_DEPTH_TEST = RenderType.makeType(FarmersLife.MOD_ID + "line_renderer_with_depth_test",
                DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, INITIAL_BUFFER_SIZE, renderState);

        renderState = RenderType.State.getBuilder()
                .line(new RenderState.LineState(OptionalDouble.of(2)))
                .layer(PROJECTION_LAYERING)
                .transparency(TRANSLUCENT_TRANSPARENCY)
                .writeMask(WRITE_TO_DEPTH_AND_COLOR)
                .depthTest(NO_DEPTH)
                .build(AFFECTS_OUTLINE);
        LINE_RENDERER_WITHOUT_DEPTH_TEST = RenderType.makeType(FarmersLife.MOD_ID + "line_renderer_without_depth_test",
                DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, INITIAL_BUFFER_SIZE, renderState);
    }
}
