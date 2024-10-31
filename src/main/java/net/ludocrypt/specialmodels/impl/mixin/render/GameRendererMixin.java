package net.ludocrypt.specialmodels.impl.mixin.render;

import com.mojang.datafixers.util.Pair;
import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.ludocrypt.specialmodels.impl.SpecialModels;
import net.ludocrypt.specialmodels.impl.render.SpecialVertexFormats;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.ShaderStage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.ResourceFactory;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(method = "loadPrograms", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 57, shift = Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
	private void specialModels$loadShaders(ResourceFactory manager, CallbackInfo ci, List<ShaderStage> list,
			List<Pair<ShaderProgram, Consumer<ShaderProgram>>> list2) {
		SpecialModels.LOADED_SHADERS.clear();
		SpecialModelRenderer.SPECIAL_MODEL_RENDERER
			.getEntrySet()
			.stream()
			.map(Entry::getKey)
			.map(RegistryKey::getValue)
			.forEach((id) -> {

				SpecialModelRenderer renderer = SpecialModelRenderer.SPECIAL_MODEL_RENDERER.get((Identifier) id);

				if (!renderer.performOutside) {
					return;
				}

				try {
					list2
						.add(Pair
							.of(new ShaderProgram(manager, "rendertype_" + ((Identifier) id).getNamespace() + "_" + ((Identifier) id).getPath(),
								SpecialVertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL_STATE),
								(shader) -> SpecialModels.LOADED_SHADERS.put(renderer, shader)));
				} catch (IOException e) {
					SpecialModels.LOGGER.error("Could not reload shader: {}", id);
					e.printStackTrace();

					try {
						list2
							.add(Pair
								.of(new ShaderProgram(manager, "rendertype_specialmodels_textured",
									SpecialVertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL_STATE),
									(shader) -> SpecialModels.LOADED_SHADERS.put(renderer, shader)));
					} catch (IOException e2) {
						list2.forEach((pair) -> pair.getFirst().close());
						e2.printStackTrace();
						throw new RuntimeException();
					}

				}

			});
	}

}
