package fr.exodeus.zombies.Core;

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
import fr.exodeus.zombies.Objects.Potion.Bonebreak;
import fr.exodeus.zombies.Objects.Potion.Infection;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonEvents {

	@SubscribeEvent
	public void onMonsterSpawn(EntityJoinWorldEvent event) {

		if (event.getEntity() instanceof EntityZombie) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntitySkeleton) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntityCreeper) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntitySpider) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntityEnderman) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntitySlime) {
			event.setCanceled(true);
		} else if (event.getEntity() instanceof EntityWitch) {
			event.setCanceled(true);
		}

	}

	@SubscribeEvent
	public void onEntityGetHurt(LivingHurtEvent event) {

		if (event.getEntityLiving() instanceof EntityPlayer) {

			if (event.getSource().getEntity() instanceof ZombieHerobrine) {
				event.getEntityLiving().setFire(1);

			} else if (event.getSource().getEntity() instanceof ZombieCivil
					|| event.getSource().getEntity() instanceof ZombieButcher
					|| event.getSource().getEntity() instanceof ZombiePrisoner
					|| event.getSource().getEntity() instanceof ZombieSurgeon
					|| event.getSource().getEntity() instanceof ZombieMiner
					|| event.getSource().getEntity() instanceof ZombieNazi
					|| event.getSource().getEntity() instanceof ZombiePolice
					|| event.getSource().getEntity() instanceof ZombieSoldier
					|| event.getSource().getEntity() instanceof ZombieCrawler) {

				Infection.playerGetHurtByInfected(event);

			} else if (event.getSource() == DamageSource.FALL) {

				Bonebreak.playerFall(event);
			}
		}
	}

	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event) {

		if (!(event.getEntityLiving() instanceof EntityPlayer))
			return;

		
		if (event.getEntityLiving().isPotionActive(Infection.infection)) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			Infection.effects(player);

		}
		if (event.getEntityLiving().isPotionActive(Bonebreak.bonebreak)) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			Bonebreak.effects(player);

		}

		for (Potion p : Reference.potionTypes) {
			if (event.getEntityLiving().isPotionActive(p))

				if (event.getEntityLiving().getActivePotionEffect(p).getDuration() == 0) {

					event.getEntityLiving().removePotionEffect(p);
					// event.getEntityLiving().removePotionEffectClient(p);
				}
		}
	}

}
