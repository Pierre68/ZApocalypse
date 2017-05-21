package fr.exodeus.zombies.Objects.Game.Thirst;

import java.util.UUID;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities.IPlayerExtendedProperties;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ThirstRender {

	@SubscribeEvent
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {
		if (!event.isCancelable() && event.getType() == ElementType.EXPERIENCE) {
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.player.capabilities.isCreativeMode)
				return;

			int posX = event.getResolution().getScaledWidth() / 2 + 11;
			int posY = event.getResolution().getScaledHeight() - 49;
			
			int blockx = (int) Math.floor(mc.player.posX);
			int blocky = (int) (mc.player.posY - mc.player.getYOffset() +1);
			int blockz = (int) Math.floor(mc.player.posZ);
			
			BlockPos block = new BlockPos(blockx, blocky, blockz);
			if (mc.player.world.getBlockState(block).getMaterial() == Material.WATER)
				posY = posY - 10;
			

			mc.renderEngine.bindTexture(new ResourceLocation("zombies", "textures/gui/status_gui.png"));

			int thirst = 7;
			
			//===========================================
			//CAPABILITY
			if(!mc.player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
				return;

			IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(mc.player);
			if (prop != null) {
				thirst = prop.getThirstLevel();
			}
			//===========================================
			
			int thirstR = thirst;
			for (int x = 1; x < 11; x++) {

				if (thirstR - 2 > -1) {
					thirstR = thirstR - 2;
					mc.ingameGUI.drawTexturedModalRect(posX + 80 - (x * 8), posY, 0, 0, 8, 8);
				} else if (thirstR - 1 > -1) {
					mc.ingameGUI.drawTexturedModalRect(posX + 80 - (x * 8), posY, 8, 0, 8, 8);
					thirstR = thirstR - 1;
				} else {
					mc.ingameGUI.drawTexturedModalRect(posX + 80 - (x * 8), posY, 16, 0, 8, 8);
				}
				// origineX,origineZ,X,-Z,length,height
			}
			
			//mc.fontRendererObj.drawString("§2level:" + thirst, posX, posY - 10, 0xffffffff);
			
			

		}
	}

}
