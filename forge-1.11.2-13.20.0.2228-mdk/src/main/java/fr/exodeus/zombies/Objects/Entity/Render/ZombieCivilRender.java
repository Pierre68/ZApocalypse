package fr.exodeus.zombies.Objects.Entity.Render;

import fr.exodeus.zombies.Core.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ZombieCivilRender extends RenderLiving{

	public ZombieCivilRender() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelBiped(), 0);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		return new ResourceLocation(Reference.MOD_ID, "textures/entity/zombie_civil.png");
	}

}
