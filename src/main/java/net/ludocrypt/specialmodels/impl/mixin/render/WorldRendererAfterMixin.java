package net.ludocrypt.specialmodels.impl.mixin.render;

import net.ludocrypt.specialmodels.impl.access.WorldChunkBuilderAccess;
import net.ludocrypt.specialmodels.impl.access.WorldRendererAccess;
import net.ludocrypt.specialmodels.impl.bridge.IrisBridge;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldRenderer.class, priority = 1050)
public abstract class WorldRendererAfterMixin implements WorldRendererAccess, WorldChunkBuilderAccess {

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private Frustum frustum;

	@Inject(method = "render", at = @At(value = "RETURN", shift = At.Shift.BEFORE))
	private void specialModels$render$clear(float tickDelta, long limitTime,
											boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer,
											LightmapTextureManager lightmapTextureManager,
											Matrix4f matrix4f, Matrix4f positionMatrix, CallbackInfo ci) {

		if (IrisBridge.IRIS_LOADED) {

			if (IrisBridge.areShadersInUse()) {

				this.setupSpecialTerrain(camera, this.frustum, false, this.client.player.isSpectator());
				this.findSpecialChunksToRebuild(camera);
				MatrixStack matrixStack = new MatrixStack();
				matrixStack.multiplyPositionMatrix(matrix4f);
				this.render(matrixStack, positionMatrix, tickDelta, camera, true);
			}

		}

	}

	@Shadow
	abstract void captureFrustum(Matrix4f matrix4f, Matrix4f matrix4f2, double d, double e, double f, Frustum frustum);

}
