package net.ludocrypt.specialmodels.impl.access;

import net.ludocrypt.specialmodels.api.SpecialModelRenderer;
import net.minecraft.util.Identifier;

import java.util.Map;

public interface UnbakedModelAccess {

	public Map<SpecialModelRenderer, Identifier> getSubModels();

}
