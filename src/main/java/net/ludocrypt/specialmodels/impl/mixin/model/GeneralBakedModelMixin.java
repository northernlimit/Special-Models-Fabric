package net.ludocrypt.specialmodels.impl.mixin.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.ludocrypt.specialmodels.impl.access.BakedModelAccess;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BasicBakedModel;
import net.minecraft.client.render.model.BuiltinBakedModel;
import net.minecraft.client.render.model.WeightedBakedModel;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;
import java.util.Map;

@Mixin(value = { BasicBakedModel.class, BuiltinBakedModel.class, WeightedBakedModel.class, ForwardingBakedModel.class })
public class GeneralBakedModelMixin implements BakedModelAccess {

	private final Map<BlockState, List<Pair<SpecialModelRenderer, BakedModel>>> modelsMap = Maps.newHashMap();
	private List<Pair<SpecialModelRenderer, BakedModel>> defaultModels = Lists.newArrayList();

	@Override
	public List<Pair<SpecialModelRenderer, BakedModel>> getModels(@Nullable BlockState state) {
		return modelsMap.getOrDefault(state, defaultModels);
	}

	@Override
	public void addModel(SpecialModelRenderer modelRenderer, @Nullable BlockState state, BakedModel model) {

		if (state == null) {
			defaultModels.add(Pair.of(modelRenderer, model));
		} else {
			List<Pair<SpecialModelRenderer, BakedModel>> list = modelsMap.get(state);

			if (list == null) {
				list = Lists.newArrayList();
				modelsMap.put(state, list);
			}

			list.add(Pair.of(modelRenderer, model));
		}

	}

}
