package fr.exodeus.zombies.ServerSide;

import java.util.HashMap;
import java.util.Map;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities.IPlayerExtendedProperties;
import fr.exodeus.zombies.Objects.Game.Nature.NatureLogic;
import fr.exodeus.zombies.Objects.Game.Thirst.ThirstLogic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ServerEvents {

	// ======================================================

	@SubscribeEvent
	public void playerTick(PlayerTickEvent event) { // RUN BOTH SIDES ?? TODO

		if (event.side == Side.SERVER) {
			ThirstLogic.onTick(event.player);
		}
	}

	@SubscribeEvent
	public void onLogin(PlayerLoggedInEvent event) {
		// RUN SERVERSIDE ONLY
	}

	@SubscribeEvent
	public void onLogout(PlayerLoggedOutEvent event) {
	}

	@SubscribeEvent
	public void playerDeath(LivingDeathEvent event) {
		// RUN SERVERSIDE ONLY
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		// RUN SERVERSIDE ONLY
	}

	// ==========================================================

	@SubscribeEvent
	public void playerRegeneration(LivingHealEvent event) {
		// RUN SERVERSIDE ONLY

		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;
		
		if(event.getEntityLiving().isEntityAlive()){
			ThirstLogic.onHeal(event);
		}

	}

	// ==========================================================

	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		if(event.getEntity().getEntityWorld().isRemote)
			return;
		
		ThirstLogic.onAttack(event.getEntityPlayer());
		
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent hurt) {
		if(hurt.getEntity().getEntityWorld().isRemote)
			return;
		if (hurt.getEntity() instanceof EntityPlayer) {
			ThirstLogic.onHurt((EntityPlayer) hurt.getEntityLiving());
		}
	}

	@SubscribeEvent
	public void onBlockBreak(BlockEvent.BreakEvent event) {
		if(event.getPlayer().getEntityWorld().isRemote)
			return;
		if (event.getPlayer() != null) {
			ThirstLogic.onBlockBreak(event.getPlayer());
			NatureLogic.onPlayerBlockBreak(event);
		}
	}

	private static Map<String, String> LAST_ITEM = new HashMap<String, String>();

	@SubscribeEvent
	public void onPotionDrinkStart(LivingEntityUseItemEvent.Tick event) {

		if (event.getEntityLiving().world.isRemote || event.getDuration() == 1)
			return;
		
		if (event.getItem().getItem() != Items.POTIONITEM)
			return;

		String name = "" + event.getItem().serializeNBT().getCompoundTag("tag").getString("Potion");

		LAST_ITEM.put(event.getEntityLiving().getName(), name);

	}

	@SubscribeEvent
	public void onPotionDrinkFinish(LivingEntityUseItemEvent.Finish event) {
		
		
		if(event.getEntity().getEntityWorld().isRemote)
			return;

		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		if (!LAST_ITEM.containsKey(event.getEntityLiving().getName()))
			return;

		String name = LAST_ITEM.get(event.getEntityLiving().getName());

		MainZombies.logString(name + " test name");

		LAST_ITEM.remove(event.getEntityLiving().getName());
		
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();

		switch (name) {
		case "minecraft:water":
			ThirstLogic.onPlayerDrink(player, 4, 0.8f, 0.6f);
			break;

		case "minecraft:thick":
			ThirstLogic.onPlayerDrink(player, 5, 1.5f, 0.2f);
			break;

		case "minecraft:mundane":
			ThirstLogic.onPlayerDrink(player, 5, 1.5f, 0.2f);
			break;

		case "minecraft:awkward":
			ThirstLogic.onPlayerDrink(player, 5, 1.5f, 0.4f);
			break;

		default:
			break;
		}

	}

	@SubscribeEvent
	public void onItemExpire(ItemExpireEvent event) {
		
		if(event.getEntity().getEntityWorld().isRemote)
			return;

		NatureLogic.onItemDespawn(event);

	}

	// ===================================================================================
	// FOR CAPABILITIES
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) { // RUN SERVERSIDE ONLY

		boolean shouldKeepCapabilities = false; // garde les info apres mort ?
												// OK y a un truc qui tourne pas
												// rond la
		if (event.isWasDeath() == false && shouldKeepCapabilities) {
			// event.isWasDeath() == false then it was an end portal transition
			// not an actual death
			IPlayerExtendedProperties src = Capabilities.getPlayerProperties(event.getOriginal());
			IPlayerExtendedProperties dest = Capabilities.getPlayerProperties(event.getEntityPlayer());
			dest.setDataFromNBT(src.getDataAsNBT());
		}
	}

	// INITIALISATION

	@SubscribeEvent
	public void onSpawn(PlayerLoggedInEvent event) { // RUN SERVERSIDEONLY

		if (event.player instanceof EntityPlayerMP && event.player.world.isRemote == false) {
			EntityPlayerMP p = (EntityPlayerMP) event.player;
			if (p != null) {
				Capabilities.syncServerDataToClient(p); // TODO
			}
		}
	}

	@SubscribeEvent
	public void onSpawn(EntityJoinWorldEvent event) {
		// RUN CLIENT AND SERVERSIDE

		if (event.getWorld().isRemote)
			return;

		if (event.getEntity() instanceof EntityPlayerMP && event.getEntity().world.isRemote == false) {
			EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
			if (player != null) {
				// Capabilities.syncServerDataToClient(p); //TODO
				
			}
		}
	}

	@SubscribeEvent
	public void onEntityConstruct(AttachCapabilitiesEvent.Entity evt) {
		// RUN CLIENT AND SERVER SIDE TODO bug possible ??
		if (evt.getEntity() instanceof EntityPlayer == false) {
			return;// mod compatibility: IE Tinkers construct
		}
		

		MainZombies.logString("AttachCapabilities");

		evt.addCapability(new ResourceLocation(Reference.MOD_ID, "zpstats"),
				new ICapabilitySerializable<NBTTagCompound>() {
					IPlayerExtendedProperties inst = MainZombies.CAPABILITY_THIRST.getDefaultInstance();

					@Override
					public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
						return capability == MainZombies.CAPABILITY_THIRST;
					}

					@Override
					public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
						return capability == MainZombies.CAPABILITY_THIRST ? MainZombies.CAPABILITY_THIRST.<T>cast(inst)
								: null;
					}

					@Override
					public NBTTagCompound serializeNBT() {

						try {
							return (NBTTagCompound) MainZombies.CAPABILITY_THIRST.getStorage()
									.writeNBT(MainZombies.CAPABILITY_THIRST, inst, null);
						} catch (java.lang.ClassCastException e) {
							return new NBTTagCompound();
						}
					}

					@Override
					public void deserializeNBT(NBTTagCompound nbt) {

						MainZombies.logString("test2");

						MainZombies.CAPABILITY_THIRST.getStorage().readNBT(MainZombies.CAPABILITY_THIRST, inst, null,
								nbt);
					}
				});
	}

}
