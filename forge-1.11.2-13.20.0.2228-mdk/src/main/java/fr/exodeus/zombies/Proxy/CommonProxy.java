package fr.exodeus.zombies.Proxy;

import fr.exodeus.zombies.Core.CommonEvents;
import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.ServerSide.ServerEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	public void registerRenders() {
		
	}

	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
		MainZombies.logString("test common");
	}
	
	//=======================================================================
	//FOR CAPABILITIES
	
	

	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity;
	}

	public void setClientPlayerData(MessageContext ctx, NBTTagCompound tags) {
    //client side only
	}

	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().playerEntity.getServer();
	}
}
