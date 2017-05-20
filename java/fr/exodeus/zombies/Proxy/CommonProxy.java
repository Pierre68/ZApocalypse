package fr.exodeus.zombies.Proxy;

import fr.exodeus.zombies.Core.CommonEvents;
import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstRender;
import fr.exodeus.zombies.ServerSide.PlayerContainer;
import fr.exodeus.zombies.ServerSide.ServerEvents;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class CommonProxy {
	public void registerRenders() {
		
	}

	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new ServerEvents());
		MinecraftForge.EVENT_BUS.register(new CommonEvents());
		MainZombies.logString("test common");
	}

	public void serverTick(EntityPlayer player) {

		
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
