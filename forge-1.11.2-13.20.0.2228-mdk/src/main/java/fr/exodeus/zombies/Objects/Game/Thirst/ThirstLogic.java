package fr.exodeus.zombies.Objects.Game.Thirst;

import fr.exodeus.zombies.Core.MainZombies;
import fr.exodeus.zombies.Core.Reference;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities;
import fr.exodeus.zombies.Objects.Capabilities.Capabilities.IPlayerExtendedProperties;
import fr.exodeus.zombies.Objects.Potion.Infection;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.biome.Biome.TempCategory;
import net.minecraftforge.event.entity.living.LivingHealEvent;

public class ThirstLogic {

	private static DamageThirst damager_thirst;
	private static DamageOverhydration damager_overhydration;
	
	public static void initThirstDamager() {
		damager_thirst = new DamageThirst();
		damager_overhydration = new DamageOverhydration();
	}
	
	// ======================================================================

	public static void onTick(EntityPlayer player) {

		int thirst_level = getThirstLevel(player);
		float thirst_saturation = getThirstSaturation(player);
		float thirst_exhaustion = getThirstExhaustion(player);

		// player.sendMessage(new TextComponentString("§2" + thirst_level +
		// "§a/§2" + thirst_saturation + "§a/§2" + thirst_exhaustion));

		if (thirst_exhaustion > 5f) {

			if (thirst_level <= 0) {
				thirst_exhaustion = 4.6f;
				setThirstExhaustion(player, thirst_exhaustion);
				player.attackEntityFrom(getDamagerThirst(), 1);
			} else {
				thirst_exhaustion = 0f + (1 - thirst_level / 20);
				setThirstExhaustion(player, thirst_exhaustion);
			}

			if (thirst_saturation > 0f) {
				thirst_saturation = Math.max(thirst_saturation - 1, 0);
				if (thirst_level == 0) {
					thirst_exhaustion = 4.6f;
					setThirstExhaustion(player, thirst_exhaustion);
				}

			} else {
				thirst_level = Math.max(thirst_level - 1, 0);
			}
		}

		if (thirst_saturation > Reference.MAX_THIRST_SATURATION) {
			float damage = (thirst_saturation - Reference.MAX_THIRST_SATURATION) / 2;
			player.attackEntityFrom(getDamagerOverhydration(), damage);
		}
		if (thirst_level > Reference.MAX_THIRST_LEVEL) {
			thirst_level = Math.max(thirst_level - 1, 0);
			player.attackEntityFrom(getDamagerOverhydration(), 1);
		}

		computeExhaustion(player);
		
		//player.sendMessage(new TextComponentString("§2" + thirst_level +
		// "§a/§2" + thirst_saturation + "§a/§2" + thirst_exhaustion));

		setThirstLevel(player, thirst_level);
		setThirstSaturation(player, thirst_saturation);
		//setThirstExhaustion(player, thirst_exhaustion); //TODO surtout ne pas remettre 

	}

	public static void onAttack(EntityPlayer player) {
		addExhaustion(player, 0.4f);
	}

	public static void onHurt(EntityPlayer player) {
		addExhaustion(player, 0.5f);
	}

	public static void onBlockBreak(EntityPlayer player) {
		addExhaustion(player, 0.05f);
	}

	public static void onBlockPlace(EntityPlayer player) {
		// TODO event
	}

	public static void onHeal(LivingHealEvent e) {
		if (getThirstLevel((EntityPlayer) e.getEntityLiving()) <= 3) {
			if (e.getAmount() <= 1)
				e.setCanceled(true);
		}
	}

	// =========================================================================

	public static void onPlayerDrink(EntityPlayer player, int thirstRegen, float thirstSaturation, float poisonRisk) {
		addWater(player, thirstRegen, thirstSaturation);
		handlePoison(player, poisonRisk);
		// TODO poison !
	}

	// =========================================================================

	private static void addWater(EntityPlayer player, int thirstRegen, float thirstSaturation) {
		setThirstSaturation(player, getThirstSaturation(player) + thirstSaturation);
		setThirstLevel(player, getThirstLevel(player) + thirstRegen);
		setThirstExhaustion(player, getThirstExhaustion(player) + 0);
	}

	private static void addExhaustion(EntityPlayer player, float f) {
		if (player.world.getDifficulty() == EnumDifficulty.PEACEFUL || player.capabilities.isCreativeMode)
			return;
		setThirstExhaustion(player, getThirstExhaustion(player) + f);
	}

	private static void computeExhaustion(EntityPlayer player) {

		float movement = player.isRiding() ? 0 : movementSpeed(player);
		float exhaustAmplifier = isNight(player) ? 0.9f : 1;
		float multiplier = getCurrentBiomeMultiplier(player);

		if (player.isInsideOfMaterial(Material.WATER)) {
			if (movement > 0) {
				addExhaustion(player, 0.03f * movement * 0.003F * exhaustAmplifier);
			}
		} else if (player.isInWater()) {
			if (movement > 0) {
				addExhaustion(player, 0.03f * movement * 0.003F * exhaustAmplifier);
			}
		} else if (player.onGround) {

			if (movement > 0) {
				if (player.isSprinting()) {
					addExhaustion(player, 0.1f * movement * 0.018F * multiplier * exhaustAmplifier);
				} else {

					addExhaustion(player, 0.1f * movement * 0.018F * multiplier * exhaustAmplifier);
				}
			}
		} else if (!player.onGround && !player.isRiding()) {
			if (player.isSprinting()) {
				addExhaustion(player, 0.02f * 0.5f * multiplier * exhaustAmplifier);
			} else {
				addExhaustion(player, 0.01f * 0.1f * multiplier * exhaustAmplifier);
			}
		}
	}

	private static void handlePoison(EntityPlayer player, float poisonRisk) {
		float risk = Math.min(poisonRisk, 1.0f);
		float infectionRisk = 0.5f;

		if (player.getEntityWorld().rand.nextFloat() <= poisonRisk) {

			if (player.getEntityWorld().rand.nextFloat() <= infectionRisk) {

				Infection.addPlayerInfectionLevel(player, 1);

			} else {

				player.addPotionEffect(
						new PotionEffect(Potion.getPotionById(19), 90 + player.getEntityWorld().rand.nextInt(100), 0));
				// poison
				player.addPotionEffect(
						new PotionEffect(Potion.getPotionById(17), 250 + player.getEntityWorld().rand.nextInt(300), 0));
				// hunger
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(18),
						1000 + player.getEntityWorld().rand.nextInt(1200), 0));
				// hunger
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(4),
						1000 + player.getEntityWorld().rand.nextInt(1500), 0));
				// hunger

			}
		}
	}

	// --------------------------------

	private static float getCurrentBiomeMultiplier(EntityPlayer player) {

		TempCategory cat = player.world.getBiome(player.getPosition()).getTempCategory();

		if (cat == TempCategory.COLD)
			return 1.5f;
		if (cat == TempCategory.MEDIUM)
			return 1f;
		if (cat == TempCategory.WARM)
			return 2f;
		if (cat == TempCategory.OCEAN)
			return 1.25f;

		return 1;

	}

	private static float movementSpeed(EntityPlayer player) {

		if (player.isSneaking()) {
			return 0.6f;
		} else if (player.isSprinting()) {
			return 2.4f;
		} else if (player.isInWater()) {
			return 1.0f;
		} else if (player.isElytraFlying()) {
			return 0.8f;
		} else {
			return 1.2f;
		}

	}

	private static boolean isNight(EntityPlayer player) {

		return !player.world.isDaytime();
	}

	// ==============================================================================
	// getters
	public static int getThirstLevel(EntityPlayer player) {
		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return 20;
		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop == null)
			return 20;

		return prop.getThirstLevel();
	}

	public static float getThirstSaturation(EntityPlayer player) {
		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return 4;
		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop == null)
			return 4;

		return prop.getThirstSaturation();
	}

	public static float getThirstExhaustion(EntityPlayer player) {
		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return 0;
		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop == null)
			return 0;

		return prop.getThirstExhaustion();
	}

	public static void setThirstLevel(EntityPlayer player, int thirst_level) {
		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return;
		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop == null)
			return;

		prop.setThirstLevel(thirst_level);
		Capabilities.syncServerDataToClient((EntityPlayerMP)player);
	}

	public static void setThirstSaturation(EntityPlayer player, float thirst_saturation) {
		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return;
		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop == null)
			return;

		prop.setThirstSaturation(thirst_saturation);
	}

	public static void setThirstExhaustion(EntityPlayer player, float thirst_exhaustion) {
		
		if (!player.hasCapability(MainZombies.CAPABILITY_THIRST, null))
			return;
		IPlayerExtendedProperties prop = Capabilities.getPlayerProperties(player);
		if (prop == null)
			return;

		prop.setThirstExhaustion(thirst_exhaustion);
	}

	// -----------------------------------------------------------------------------

	private static DamageSource getDamagerThirst() {
		return damager_thirst;
	}

	private static DamageSource getDamagerOverhydration() {
		return damager_overhydration;
	}

	// ==============================================================================
	// DAMAGER

	public static class DamageThirst extends DamageSource {
		public DamageThirst() {
			super("thirst");
			setDamageBypassesArmor();
			setDamageIsAbsolute();
		}

		@Override
		public TextComponentString getDeathMessage(EntityLivingBase entity) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;

				String deathmessage = "";
				// TODO do custom death messages lang("death.dried") ?

				if (entity.getEntityWorld().rand.nextBoolean()) {
					deathmessage = player.getDisplayName().getFormattedText() + "'s body is now made up of 0% water!";
				} else {
					deathmessage = player.getDisplayName().getFormattedText() + " dried out";
				}

				return new TextComponentString(deathmessage);

			}
			return (TextComponentString) super.getDeathMessage(entity);
		}
	}

	public static class DamageOverhydration extends DamageSource {
		public DamageOverhydration() {
			super("overhydration");
			setDamageBypassesArmor();
			setDamageIsAbsolute();
		}

		@Override
		public TextComponentString getDeathMessage(EntityLivingBase entity) {
			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;

				String deathmessage = "";
				// TODO do custom death messages lang("death.dried") ?
				if (entity.getEntityWorld().rand.nextBoolean()) {
					deathmessage = player.getDisplayName().getFormattedText() + "'s body exploded";
				} else {
					deathmessage = player.getDisplayName().getFormattedText() + " had to much presure";
				}

				return new TextComponentString(deathmessage);

			}
			return (TextComponentString) super.getDeathMessage(entity);
		}
	}

}
