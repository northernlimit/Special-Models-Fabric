package net.ludocrypt.specialmodels.impl;

import com.google.common.collect.Maps;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.ludocrypt.specialmodels.api.TexturedSpecialModelRenderer;
import net.minecraft.client.gl.ShaderProgram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class SpecialModels implements ClientModInitializer {

	public static final Logger LOGGER = LoggerFactory.getLogger("Special-Models");

	@Environment(EnvType.CLIENT)
	public static final Map<SpecialModelRenderer, ShaderProgram> LOADED_SHADERS = Maps.newHashMap();

	@Override
	public void onInitializeClient() {
		TexturedSpecialModelRenderer.init();
	}
}
