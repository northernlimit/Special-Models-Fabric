package net.ludocrypt.specialmodels.impl.access;

import com.mojang.datafixers.util.Pair;
import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BakedModelAccess {

	public List<Pair<SpecialModelRenderer, BakedModel>> getModels(@Nullable BlockState state);

	public void addModel(SpecialModelRenderer modelRenderer, @Nullable BlockState state, BakedModel model);

}
