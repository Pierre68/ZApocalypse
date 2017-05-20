package fr.exodeus.zombies.Objects.Entity.Render;

import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Entity.Render.Model.ModelZombieCrawler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ZombieCrawlerRender extends RenderLiving{

	public ZombieCrawlerRender() {
		super(Minecraft.getMinecraft().getRenderManager(), new ModelZombieCrawler(), 0);
		
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		
		return new ResourceLocation(Reference.MOD_ID, "textures/entity/zombie_crawler.png");
	}

}
