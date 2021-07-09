package com.asheriit.asheriitsfarmerslife.api.utils.models;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec2f;

public class FaceBuilder {
    /**
     *
     * @param direction
     * @param matrixPos
     * @param matrixNormal
     * @param vertexBuilder
     * @param bottomLeftVertex
     * @param topRightVertex
     * @param uvBottomLeft
     * @param uvTopRight
     * @param color
     * @param lightmapValue
     */
    public static void addFaceForDirection(Direction direction, Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder vertexBuilder, Vector3f bottomLeftVertex,
                                           Vector3f topRightVertex, Vec2f uvBottomLeft, Vec2f uvTopRight, int color, int lightmapValue) {
        Vector3f bottomLeftVertexWithDirection = bottomLeftVertex.copy();
        Vector3f topRightVertexWithDirection = topRightVertex.copy();
        Vector3f bottomRightVertexWithDirection = new Vector3f(topRightVertex.getX(), bottomLeftVertex.getY(), bottomLeftVertex.getZ());
        Vector3f topLeftVertexWithDirection = new Vector3f(bottomLeftVertex.getX(), topRightVertex.getY(), topRightVertex.getZ());

        switch (direction) {
            case NORTH: { // bottom left is east
                bottomLeftVertexWithDirection = new Vector3f(topRightVertex.getX(), topRightVertex.getZ(), topRightVertex.getY());
                topRightVertexWithDirection = new Vector3f(bottomLeftVertex.getX(), bottomLeftVertex.getZ(), topRightVertex.getY());
                bottomRightVertexWithDirection = new Vector3f(bottomLeftVertex.getX(), topRightVertex.getZ(), topRightVertex.getY());
                topLeftVertexWithDirection = new Vector3f(topRightVertex.getX(), bottomLeftVertex.getZ(), topRightVertex.getY());
                break;
            }
            case SOUTH: {  // bottom left is west
                bottomLeftVertexWithDirection = new Vector3f(bottomLeftVertex.getX(), topRightVertex.getZ(), 1.0F - topRightVertex.getY());
                topRightVertexWithDirection = new Vector3f(topRightVertex.getX(), bottomLeftVertex.getZ(), 1.0F - topRightVertex.getY());
                bottomRightVertexWithDirection = new Vector3f(topRightVertex.getX(), topRightVertex.getZ(), 1.0F - topRightVertex.getY());
                topLeftVertexWithDirection = new Vector3f(bottomLeftVertex.getX(), bottomLeftVertex.getZ(), 1.0F - topRightVertex.getY());
                break;
            }
            case EAST: {  // bottom left is south
                bottomLeftVertexWithDirection = new Vector3f(1.0F - topRightVertex.getY(), bottomLeftVertex.getX(), bottomLeftVertex.getZ());
                topRightVertexWithDirection = new Vector3f(1.0F - topRightVertex.getY(), topRightVertex.getX(), topRightVertex.getZ());
                bottomRightVertexWithDirection = new Vector3f(1.0F - topRightVertex.getY(), bottomLeftVertex.getX(), topRightVertex.getZ());
                topLeftVertexWithDirection = new Vector3f(1.0F - topRightVertex.getY(), topRightVertex.getX(), bottomLeftVertex.getZ());
                break;
            }
            case WEST: { // bottom left is north
                bottomLeftVertexWithDirection = new Vector3f(topRightVertex.getY(), bottomLeftVertex.getX(), topRightVertex.getZ());
                topRightVertexWithDirection = new Vector3f(topRightVertex.getY(), topRightVertex.getX(), bottomLeftVertex.getZ());
                bottomRightVertexWithDirection = new Vector3f(topRightVertex.getY(), bottomLeftVertex.getX(), bottomLeftVertex.getZ());
                topLeftVertexWithDirection = new Vector3f(topRightVertex.getY(), topRightVertex.getX(), topRightVertex.getZ());
                break;
            }
            case DOWN: { // bottom left is northwest
                bottomLeftVertexWithDirection = topLeftVertexWithDirection;
                topRightVertexWithDirection = bottomRightVertexWithDirection;
                bottomRightVertexWithDirection = topRightVertex.copy();
                topLeftVertexWithDirection = bottomLeftVertex.copy();
                break;
            }
            case UP:
            default: {
                break;
            }
        }

        Vec2f uvBottomRight = new Vec2f(uvTopRight.x, uvBottomLeft.y);
        Vec2f uvTopLeft = new Vec2f(uvBottomLeft.x, uvTopRight.y);
        Vector3f normalVector = direction.toVector3f();

        FaceBuilder.addFace(matrixPos, matrixNormal, vertexBuilder, bottomLeftVertexWithDirection, bottomRightVertexWithDirection, topRightVertexWithDirection, topLeftVertexWithDirection,
                uvBottomLeft, uvBottomRight, uvTopRight, uvTopLeft, normalVector, color, lightmapValue);
    }


    /**
     *
     * @param matrixPos
     * @param matrixNormal
     * @param vertexBuilder
     * @param bottomLeftVertex
     * @param bottomRightVertex
     * @param topRightVertex
     * @param topLeftVertex
     * @param uvBottomLeft
     * @param uvBottomRight
     * @param uvTopRight
     * @param uvTopLeft
     * @param normalVector
     * @param color
     * @param lightmapValue
     */
    public static void addFace(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder vertexBuilder, Vector3f bottomLeftVertex, Vector3f bottomRightVertex,
                               Vector3f topRightVertex, Vector3f topLeftVertex, Vec2f uvBottomLeft, Vec2f uvBottomRight, Vec2f uvTopRight, Vec2f uvTopLeft,
                               Vector3f normalVector, int color, int lightmapValue) {
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, bottomLeftVertex, uvBottomLeft, normalVector, color, lightmapValue);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, bottomRightVertex, uvBottomRight, normalVector, color, lightmapValue);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, topRightVertex, uvTopRight, normalVector, color, lightmapValue);
        FaceBuilder.addVertex(matrixPos, matrixNormal, vertexBuilder, topLeftVertex, uvTopLeft, normalVector, color, lightmapValue);
    }

    /**
     *
     * @param matrixPos
     * @param matrixNormal
     * @param vertexBuilder
     * @param vertexPos
     * @param uv
     * @param normalVector
     * @param color
     * @param lightmapValue
     */
    public static void addVertex(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder vertexBuilder, Vector3f vertexPos, Vec2f uv, Vector3f normalVector, int color, int lightmapValue) {
        vertexBuilder.pos(matrixPos, vertexPos.getX(), vertexPos.getY(), vertexPos.getZ())
                .color(((color >> 16) & 0xFF) / 255f, ((color >> 8) & 0xFF) / 255f, (color & 0xFF) / 255f, 1.0F)
                .tex(uv.x, uv.y)
                .overlay(OverlayTexture.NO_OVERLAY)
                .lightmap(lightmapValue)
                .normal(matrixNormal, normalVector.getX(), normalVector.getY(), normalVector.getZ())
                .endVertex();
    }
}
