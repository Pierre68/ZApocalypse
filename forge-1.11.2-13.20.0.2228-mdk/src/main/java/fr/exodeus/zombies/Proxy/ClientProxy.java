package fr.exodeus.zombies.Proxy;

import fr.exodeus.zombies.Core.CommonEvents;
import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities.IPlayerExtendedProperties;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieButcher;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCivil;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieCrawler;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieHerobrine;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieMiner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieNazi;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePolice;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombiePrisoner;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSoldier;
import fr.exodeus.zombies.Objects.Entity.Entities.ZombieSurgeon;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstRender;
import fr.exodeus.zombies.Objects.Items.CleanChain;
import fr.exodeus.zombies.Objects.Items.IronNugget;
import fr.exodeus.zombies.Objects.Items.RustyChain;
import fr.exodeus.zombies.Objects.Items.SandDust;
import fr.exodeus.zombies.Objects.Items.Drinks.PureWater;
import fr.exodeus.zombies.Objects.Items.Food.CookedFlesh;
import fr.exodeus.zombies.Objects.Items.Food.Donut;
import fr.exodeus.zombies.Objects.Items.Food.PurifiedFlesh;
import fr.exodeus.zombies.Objects.Items.Usable.Antibiotic;
import fr.exodeus.zombies.Objects.Items.Usable.Bandage;
import fr.exodeus.zombies.ServerSide.ServerEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void registerRenders() {
		//Usable Items
		Antibiotic.registerItemRender();
		Bandage.registerItemRender();
		//Simple Items
		CleanChain.registerItemRender();
		IronNugget.registerItemRender();
		RustyChain.registerItemRender();
		SandDust.registerItemRender();
		//Food Items
		CookedFlesh.registerItemRender();
		Donut.registerItemRender();
		PurifiedFlesh.registerItemRender();
		//Drinks
		PureWater.registerItemRender();
		//Entities
		ZombieButcher.registerRender();
		ZombieCivil.registerRender();
		ZombieCrawler.registerRender();
		ZombieHerobrine.registerRender();
		ZombieMiner.registerRender();
		ZombieNazi.registerRender();
		ZombiePolice.registerRender();
		ZombiePrisoner.registerRender();
		ZombieSoldier.registerRender();
		ZombieSurgeon.registerRender();
		
		
		/*network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_NAME);
		network.registerMessage(PacketCapabilities.ClientHandler.class, PacketCapabilities.class, 3, Side.CLIENT);
		network.registerMessage(PacketCapabilities.ServerHandler.class, PacketCapabilities.class, 3, Side.SERVER);  
		Ancien code NE PAS FAIRE*/
	}
	
	@Override
	public void registerEvents(){
		super.registerEvents(); // tres important permet de charger le commonProxy (events common)
		MinecraftForge.EVENT_BUS.register(new ThirstRender());
		MainZombies.logString("test client");
	}
	
	public void serverTick(EntityPlayer player) {
		//SERVER SIDE ONLY
	}
	
	//===============================================================================
	//FOR CAPABILITIES
	
	@Override
	  public IThreadListener getThreadFromContext(MessageContext ctx) {
	    return (ctx.side.isClient() ? Minecraft.getMinecraft() : super.getThreadFromContext(ctx));
	  }
	
	@SideOnly(Side.CLIENT)
	  public void setClientPlayerData(MessageContext ctx, NBTTagCompound tags) {
	    EntityPlayer p = this.getPlayerEntity(ctx); //Minecraft.getMinecraft().thePlayer;
	    if (p != null) {
	      IPlayerExtendedProperties props = Capabilities.getPlayerProperties(Minecraft.getMinecraft().player);
	      if (props != null) {
	        props.setDataFromNBT(tags);
	      }
	    }
	  }
	
	 @Override
	  public EntityPlayer getPlayerEntity(MessageContext ctx) {
	    // Note that if you simply return 'Minecraft.getMinecraft().thePlayer',
	    // your packets will not work as expected because you will be getting a
	    // client player even when you are on the server!
	    // Sounds absurd, but it's true.
	    //https://github.com/coolAlias/Tutorial-Demo/blob/e8fa9c94949e0b1659dc0a711674074f8752d80e/src/main/java/tutorial/ClientProxy.java
	    // Solution is to double-check side before returning the player:
	    return (ctx.side.isClient() ? Minecraft.getMinecraft().player : super.getPlayerEntity(ctx));
	  }
	
	
	
	
	
	
	
	
	
	
}
