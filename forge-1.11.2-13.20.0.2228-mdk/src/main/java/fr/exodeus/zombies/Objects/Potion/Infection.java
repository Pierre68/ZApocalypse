package fr.exodeus.zombies.Objects.Potion;

import java.util.HashMap;
import java.util.Random;

import fr.exodeus.zombies.Core.MainZombies;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Infection extends Potion {
	public static Potion infection;

	public Infection(int par1, boolean par2, int par3) {
		super(par2, par3);
	}

	public Potion setIconIndex(int par1, int par2) {
		super.setIconIndex(par1, par2);
		return this;
	}

	public static void init() {
		infection = (new Infection(32, true, 0)).setIconIndex(0, 0).setPotionName("potion.infection");

		REGISTRY.register(50, new ResourceLocation("zombies", "infection"), infection);
	}

	public static ResourceLocation textureResource = new ResourceLocation("zombies", "textures/gui/status_gui.png");

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().renderEngine.bindTexture(textureResource);
		return true;
	}

	public static void effects(EntityPlayer player) {
		Random r = new Random();
		int lvl = player.getActivePotionEffect(infection).getAmplifier() + 1;

		switch ((int)r.nextInt(6000 / lvl)) {

		case 0:
			player.attackEntityFrom(DamageSource.GENERIC, 1);
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(15), 40 + r.nextInt(100) * lvl, 0));
			// blindness
			break;
		case 1:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 100 + r.nextInt(100) * lvl, 0));
			// confusion
			break;
		case 2:
			player.playSound(SoundEvents.AMBIENT_CAVE, 1f, 1f);
			break;
		case 3:
			player.playSound(SoundEvents.ENTITY_ZOMBIE_AMBIENT, 1f, 1f);
			break;
		case 4:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 500 + r.nextInt(600) * lvl, r.nextInt(2)));
			// Slow
			break;
		case 5:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 500 + r.nextInt(600) * lvl, r.nextInt(2)));
			// dig slow down
			break;
		case 6:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(17), 300 + r.nextInt(500), r.nextInt(2)));
			// hunger
			break;
		case 7:
			player.addPotionEffect(
					new PotionEffect(Potion.getPotionById(18), 500 + r.nextInt(600) * lvl, r.nextInt(2) + lvl));
			// weakness
			break;
		case 8:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 150 + r.nextInt(300), 0));
			// poison
			break;
		case 9:
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 500 + r.nextInt(600) * lvl, r.nextInt(2)));
			// dig slow down
			break;
		case 10:
			player.addPotionEffect(
					new PotionEffect(Potion.getPotionById(18), 500 + r.nextInt(600) * lvl, r.nextInt(2) + lvl));
			// weakness
			break;

		}

	}

	public static void playerGetHurtByInfected(LivingHurtEvent event) {

		if (event.getAmount() >= event.getEntityLiving().getHealth())
			return; // not longer useful

		EntityPlayer player = (EntityPlayer) event.getEntityLiving();

		// -----------------

		Random r = new Random();
		if (r.nextInt(15) != 0)
			return; // infect player if equal 0

		// ------------------

		addPlayerInfectionLevel(player, 1);
		/*
		 * if (event.getEntityLiving().isPotionActive(Infection.infection)) {
		 * 
		 * int lvl =
		 * event.getEntityLiving().getActivePotionEffect(Infection.infection).
		 * getAmplifier();
		 * 
		 * // Infection.infectPlayer(player, lvl + 1); MARCHE PAS
		 * infectPlayer(player, lvl + 1);
		 * 
		 * } else {
		 * 
		 * // Infection.infectPlayer(player, 0); MARCHE PAS infectPlayer(player,
		 * 0); }
		 */
	}

	public static int addPlayerInfectionLevel(EntityPlayer player, int addedLevel) {
		int finalLevel = addedLevel - 1;

		if (player.isPotionActive(Infection.infection))
			finalLevel = finalLevel + player.getActivePotionEffect(Infection.infection).getAmplifier() + 1;

		infectPlayer(player, finalLevel);
		return finalLevel;
	}

	private static void infectPlayer(EntityPlayer playerIn, Integer lvl) {

		EntityPlayer player = (EntityPlayer) playerIn;

		if (player.getHealth() == 0)
			return;
		
		if (lvl != 0)
			MainZombies.sendPlayerMessage(player, "Your infection is getting worse", TextFormatting.DARK_RED);
		if (lvl == 0) {
			MainZombies.sendPlayerMessage(player, "You have been infected !", TextFormatting.DARK_RED);
			MainZombies.sendPlayerMessage(player, "Take an Antibiotic as fast as possible", TextFormatting.GRAY);
		}

		playerIn.addPotionEffect(new PotionEffect(Infection.infection, 10000 * (lvl + 1), lvl));

	}

}
