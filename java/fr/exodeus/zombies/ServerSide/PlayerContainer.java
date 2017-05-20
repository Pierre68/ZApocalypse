package fr.exodeus.zombies.ServerSide;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstLogic;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PlayerContainer {

	// =======================================================================================
	// PlayerList

	private static final Map<String, PlayerContainer> ALL_PLAYERS = new HashMap<String, PlayerContainer>();

	public static void addPlayer(EntityPlayer player) {
		if (!ALL_PLAYERS.containsKey(player.getDisplayNameString())) {
			PlayerContainer container = new PlayerContainer(player);
			ALL_PLAYERS.put(player.getDisplayNameString(), container);
		}
	}

	public static void removePlayer(EntityPlayer player) {
		ALL_PLAYERS.remove(player.getDisplayNameString());
	}

	public static PlayerContainer getPlayer(EntityPlayer player) {
		return ALL_PLAYERS.get(player.getDisplayNameString());
	}

	public static void clearList() {
		ALL_PLAYERS.clear();
	}
	
	public static boolean containsPlayer(EntityPlayer player){
		return ALL_PLAYERS.containsKey(player);
	}
	
	public static int getLength(){
		return ALL_PLAYERS.size();
	}

	// =======================================================================================
	// Individual Player Informations

	private EntityPlayer player;
	private ThirstLogic thirstStats;

	public PlayerContainer(EntityPlayer player) {
		this.player = player;
		this.thirstStats = new ThirstLogic(player);
	}

	public ThirstLogic getThirstStats() {
		return this.thirstStats;
	}

	// ========================================================================================
	// Capabilities stuff

	//TODO the day I understand it


}
