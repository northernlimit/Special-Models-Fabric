package net.ludocrypt.specialmodels.impl.mixin.render;

import com.mojang.blaze3d.systems.RenderSystem.ShapeIndexBuffer;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.gl.VertexBuffer.Usage;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormat.IndexType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(VertexBuffer.class)
public interface VertexBufferAccessor {

	@Accessor
	int getIndexCount();

	@Accessor
	void setIndexCount(int indexCount);

	@Accessor
	DrawMode getDrawMode();

	@Accessor
	void setDrawMode(DrawMode drawMode);

	@Accessor
	Usage getUsage();

	@Mutable
	@Accessor
	void setUsage(Usage usage);

	@Accessor
	int getVertexBufferId();

	@Accessor
	void setVertexBufferId(int vertexBufferId);

	@Accessor
	int getIndexBufferId();

	@Accessor
	void setIndexBufferId(int indexBufferId);

	@Accessor
	VertexFormat getVertexFormat();

	@Accessor
	void setVertexFormat(VertexFormat vertexFormat);

	@Accessor
	ShapeIndexBuffer getSharedSequentialIndexBuffer();

	@Accessor
	void setSharedSequentialIndexBuffer(ShapeIndexBuffer sharedSequentialIndexBuffer);

	@Accessor
	IndexType getIndexType();

	@Accessor
	void setIndexType(IndexType indexType);

}
